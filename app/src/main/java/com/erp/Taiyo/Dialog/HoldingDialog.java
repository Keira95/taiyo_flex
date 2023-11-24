package com.erp.Taiyo.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
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


public class HoldingDialog {
    private Context context;
    private Dialog dialog;
    private String tvCnt;
   // ListView lvIssueListView;
    int Number;

    String strIp, strSobId, strOrgId;
    EditText etFileNo, etStartTime, etEndTime , etJobId, etOperationId,etHoldingControlId;
    Button btnStartTime, btnEndTime, btnSave, btnClose;


    public HoldingDialog(Context context) {
        this.context = context;
    }

    // 호출할 다이얼로그 함수를 정의한다.
    public void call_Level_Dialog(final TextView tvWorkOrderNo , String ip) {

        strIp = ip;
        strSobId = "70";
        strOrgId = "701";

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        dialog = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dialog.setContentView(R.layout.dialog_holding);

        etFileNo = (EditText) dialog.findViewById(R.id.dialog_file_no);
        etStartTime = (EditText) dialog.findViewById(R.id.dialog_holding_start_time);
        etEndTime = (EditText) dialog.findViewById(R.id.dialog_holding_end_time);

        etJobId = (EditText) dialog.findViewById(R.id.job_id);
        etOperationId = (EditText) dialog.findViewById(R.id.operation_id);
        etHoldingControlId = (EditText) dialog.findViewById(R.id.holding_control_id);



        btnStartTime = (Button) dialog.findViewById(R.id.btn_holding_start_time);
        btnEndTime = (Button) dialog.findViewById(R.id.btn_holding_end_time);
        btnSave = (Button) dialog.findViewById(R.id.btn_save);
        btnClose = (Button) dialog.findViewById(R.id.btn_close);

        dialog.show();

        WIP_JOB_HOLDING_CONTROL_CHK wIP_JOB_HOLDING_CONTROL_CHK = new WIP_JOB_HOLDING_CONTROL_CHK();
        wIP_JOB_HOLDING_CONTROL_CHK.execute(strIp ,strSobId ,strOrgId, tvWorkOrderNo.getText().toString());


        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{






                }catch (Exception e){
                    e.printStackTrace();
                }

                dialog.dismiss();
            }
        });

    }



    protected class WIP_JOB_HOLDING_CONTROL_CHK extends AsyncTask<String, Void, String>
    {
        protected  String doInBackground(String... urls)
        {
            StringBuffer jsonHtml = new StringBuffer();

            //서버로 보낼 데이터 설정
            String search_title = "P_SOB_ID=" + urls[1]
                    + "&P_ORG_ID=" + urls[2]
                    + "&P_WORK_ORDER_NO=" +urls[3]
                    ;

            try
            {  URL obj = new URL("http://" + urls[0] + "/TAIYO/WipJobHoldingControlChk.jsp"); //주소 지정

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
                JSONObject job = jarrayWorkLevel.getJSONObject(0);

                if(job.getString("Status").equals("S")){

                    etJobId.setText(job.getString("JOB_ID"));
                    etOperationId.setText(job.getString("OPERATION_ID"));
                    etHoldingControlId.setText(job.getString("JOB_HOLDING_CONTROL_ID"));

                    HOLDING_CONTROL_SELECT hOLDING_CONTROL_SELECT = new HOLDING_CONTROL_SELECT();
                    hOLDING_CONTROL_SELECT.execute(strIp, strSobId, strOrgId,etOperationId.getText().toString(),"","",etHoldingControlId.getText().toString());

                }else{


                }

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


    // WORKCENTER_IN_AUTHORITY
    protected class HOLDING_CONTROL_SELECT extends AsyncTask<String, Void, String>
    {
        protected  String doInBackground(String... urls)
        {
            StringBuffer jsonHtml = new StringBuffer();

            //서버로 보낼 데이터 설정
            String search_title = "W_SOB_ID=" + urls[1]
                    + "&W_ORG_ID=" + urls[2]
                    + "&P_OPERATION_ID=" +urls[3]
                    + "&P_HOLDING_START_DATE=" +urls[4]
                    + "&P_HOLDING_END_DATE=" +urls[5]
                    + "&P_HOLDING_CONTROL_ID=" +urls[6]
                    ;

            try
            {  URL obj = new URL("http://" + urls[0] + "/TAIYO/HoldingControlSelect.jsp"); //주소 지정

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
                JSONObject job = jarrayWorkLevel.getJSONObject(0);

                if(job.getString("Status").equals("S")){

                    etJobId.setText(job.getString("JOB_ID"));
                    etOperationId.setText(job.getString("OPERATION_ID"));
                    etHoldingControlId.setText(job.getString("JOB_HOLDING_CONTROL_ID"));
                    etStartTime.setText(job.getString("HOLDING_START_DATE"));
                    etEndTime.setText(job.getString("HOLDING_END_DATE"));

                }

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
