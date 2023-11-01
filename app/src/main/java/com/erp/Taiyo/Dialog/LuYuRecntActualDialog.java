package com.erp.Taiyo.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.erp.Taiyo.R;
import com.erp.Taiyo.adapter.YuRecentActuallAdapter;
import com.erp.Taiyo.item.YuRecentActualListtem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LuYuRecntActualDialog {
    private Context context;

    EditText etFilter;
    Button btnClose;
    ListView IvItem;
    String ip ,strSobId, strOrgId;


    public LuYuRecntActualDialog(Context context) {
        this.context = context;
    }

    // 호출할 다이얼로그 함수를 정의한다.
    public void call_YuRecent(String ip, final TextView tvCode) {

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dialog = new Dialog(context);

        strSobId = "70";
        strOrgId ="701";
        // 액티비티의 타이틀바를 숨긴다.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dialog.setContentView(R.layout.dialog_lu_yu_recent_actual);

        DisplayMetrics dm = dialog.getContext().getApplicationContext().getResources().getDisplayMetrics();
        WindowManager.LayoutParams wm = new WindowManager.LayoutParams();
        wm.copyFrom(dialog.getWindow().getAttributes());
        wm.width = dm.widthPixels - (dm.widthPixels / 10);
        wm.height = dm.heightPixels - (dm.widthPixels / 3);
        dialog.getWindow().setAttributes(wm);

        // 커스텀 다이얼로그를 노출한다.
        dialog.show();

        //LAYOUT
        etFilter = dialog.findViewById(R.id.et_filter);
        btnClose = dialog.findViewById(R.id.n1_btn_close);
        IvItem = dialog.findViewById(R.id.lv_lu_request);

        Lu_Yu_Recent lu_Yu_Recent = new Lu_Yu_Recent();
        lu_Yu_Recent.execute(ip.toString(), tvCode.getText().toString());


        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

/*
        IvItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                YuRecentActualListtem luListitem = (YuRecentActualListtem) parent.getAdapter().getItem(position);

                tvCode.setText(luListitem.getStrEquimentCode());
                tvName.setText(luListitem.getStrEquimentDesc());
                tvId.setText(luListitem.getStrEquimentId());
                dialog.dismiss();
            }
        });*/

        etFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                ((YuRecentActuallAdapter) IvItem.getAdapter()).getFilter().filter(etFilter.getText().toString());
            }
        });


    }



    /*아이템 룩업*/
    protected class Lu_Yu_Recent extends AsyncTask<String, Void, String>
    {
        final YuRecentActuallAdapter yuRecentActuallAdapter = new YuRecentActuallAdapter();
        protected  String doInBackground(String... urls)
        {
            StringBuffer jsonHtml = new StringBuffer();

            //서버로 보낼 데이터 설정
            String search_title = "W_FILE_NO=" + urls[1]
                   ;


            try
            {  URL obj = new URL("http://" + urls[0] + "/TAIYO/YuRecentActual.jsp"); //주소 지정

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

                    YuRecentActualListtem luListItem = new YuRecentActualListtem();

                    luListItem.setStrFileNo(job.getString("FILE_NO"));
                    luListItem.setStrEquipmentName(job.getString("EQUIPMENT_NAME"));
                    luListItem.setStrRollPressure(job.getString("ROLL_PRESSURE"));
                    luListItem.setStrRollTemperature(job.getString("ROLL_TEMPERATURE"));
                    luListItem.setStrRollSpeed(job.getString("ROLL_SPEED"));
                    luListItem.setStrParrticleDiamter(job.getString("PARTICLE_DIAMETER"));
                    luListItem.setStrDispersionDegree(job.getString("DISPERSION_DEGREE"));
                    luListItem.setStrItemTemperature(job.getString("ITEM_TEMPERATURE"));

                    yuRecentActuallAdapter.addItem(luListItem);

                }

                IvItem.setAdapter(yuRecentActuallAdapter);

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
