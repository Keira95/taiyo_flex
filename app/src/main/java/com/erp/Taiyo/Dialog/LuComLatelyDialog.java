package com.erp.Taiyo.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ListView;
import android.widget.TextView;

import com.erp.Taiyo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.erp.Taiyo.adapter.ComLatelyAdapter;
import com.erp.Taiyo.item.CombinLatelyItem;


public class LuComLatelyDialog {
    private Context context;
    private Dialog dialog;
    private String tvCnt;
    // ListView lvIssueListView;
    int Number;

    EditText etFilter;
    Button btnClose;
    ListView lv_lately;
    String ip;


    public LuComLatelyDialog(Context context) {
        this.context = context;
    }

    // 호출할 다이얼로그 함수를 정의한다.
    public void call_Lately_Dialog(String ip , String sob_id, String org_id , TextView fileNo) {

        ip = "192.168.1.8:8080";

        String strSobId = "70";
        String strOrgId = "701";

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dialog = new Dialog(context);
        //dialog = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dialog.setContentView(R.layout.dialog_lu_com_lately);

        DisplayMetrics dm = dialog.getContext().getApplicationContext().getResources().getDisplayMetrics();
        WindowManager.LayoutParams wm = new WindowManager.LayoutParams();
        wm.copyFrom(dialog.getWindow().getAttributes());
        wm.width = dm.widthPixels - (dm.widthPixels / 10);
        wm.height = dm.heightPixels - (dm.widthPixels / 3);
        dialog.getWindow().setAttributes(wm);



        etFilter = dialog.findViewById(R.id.et_filter);
        btnClose = dialog.findViewById(R.id.btn_close);
        lv_lately = dialog.findViewById(R.id.lv_lu_com_lately);



        BhRecentActual bhRecentActual = new BhRecentActual();
        bhRecentActual.execute(ip,strSobId,strOrgId,fileNo.getText().toString());


        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();


    }
    protected class BhRecentActual extends AsyncTask<String, Void, String>
    {
        final ComLatelyAdapter comLatelyAdapter = new ComLatelyAdapter();

        protected  String doInBackground(String... urls)
        {
            StringBuffer jsonHtml = new StringBuffer();

            //서버로 보낼 데이터 설정
            String search_title = "W_SOB_ID=" + urls[1]
                    + "&W_ORG_ID=" + urls[2]
                    + "&W_FILE_NO=" + urls[3];

            try
            {  URL obj = new URL("http://"+urls[0]+"/TAIYO/BhRecentActual.jsp"); //주소 지정

                HttpURLConnection conn = (HttpURLConnection)obj.openConnection(); //지정된 주소로 연결

                if(conn != null) //
                {
                    conn.setReadTimeout(5000);
                    conn.setConnectTimeout(10000);
                    conn.setRequestMethod("POST"); //메세지 전달 방식 POST로 설정
                    conn.setDoInput(true);
                    conn.connect(); //???

                    //서버에 데이터 전달
                    OutputStream out = conn.getOutputStream();
                    out.write(search_title.getBytes("UTF-8"));
                    out.flush();
                    out.close();

                    if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) //서버에서 응답을 받았을 경우
                    {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8")); //받은 정보를 버퍼에 저장
                        while (true)
                        {
                            String line = br.readLine();
                            if(line == null) //라인이 없어질때까지 버퍼를 한줄씩 읽음
                                break;
                            jsonHtml.append(line);// + "\n");
                        }
                        br.close();
                    }
                    conn.disconnect();
                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            return  jsonHtml.toString(); //결과값 리턴
        }


        protected void onPostExecute(String result)
        {
            //페이지 결과값 파싱
            try
            {
                JSONObject RESURT = new JSONObject(result); //JSON 오브젝트 받음

                JSONArray jarrayWorkLevel = RESURT.getJSONArray("RESULT"); //JSONArray 파싱


                for(int i = 0; i < jarrayWorkLevel.length(); i++)
                {
                    JSONObject job = jarrayWorkLevel.getJSONObject(i); //JSON 오브젝트 파싱

                    CombinLatelyItem combinLatelyItem = new CombinLatelyItem();

                    combinLatelyItem.setStrWorkOrderNo(job.getString("FILE_NO"));
                    combinLatelyItem.setStrWeekActualQty(job.getString("WEEK_ACTUAL_QTY"));
                    combinLatelyItem.setStrEquipmentName(job.getString("EQUIPMENT_NAME"));
                    combinLatelyItem.setStrStir1Rpm(job.getString("STIR_1_RPM"));
                    combinLatelyItem.setStrStir2Rpm(job.getString("STIR_2_RPM"));
                    combinLatelyItem.setStrStir3Rpm(job.getString("STIR_3_RPM"));
                    combinLatelyItem.setStrStir1MMinute(job.getString("STIR_1_MINUTE"));
                    combinLatelyItem.setStrStir2MMinute(job.getString("STIR_2_MINUTE"));
                    combinLatelyItem.setStrStir3MMinute(job.getString("STIR_3_MINUTE"));



                    comLatelyAdapter.addItem(combinLatelyItem);

                }

                lv_lately.setAdapter(comLatelyAdapter);

            }catch (JSONException e)
            {
                e.printStackTrace();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

}
