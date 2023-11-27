package com.erp.Taiyo.Dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.erp.Taiyo.R;
import com.erp.Taiyo.activity.RegisterWeighingWorkActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.core.content.ContextCompat;


public class HoldingDialog {
    private Context context;
    private Dialog dialog;
    private String tvCnt;
   // ListView lvIssueListView;
    int Number;

    String strIp, strSobId, strOrgId, strWorkOrderNo , strUserId;
    EditText etFileNo, etStartTime, etEndTime , etJobId, etOperationId,etHoldingControlId , etStatusCode;
    Button btnStartTime, btnEndTime, btnSave, btnClose;


    public HoldingDialog(Context context) {
        this.context = context;
    }

    // 호출할 다이얼로그 함수를 정의한다.
    public void call_Level_Dialog(final TextView tvWorkOrderNo , String ip , String userId) {

        strIp = ip;
        strSobId = "70";
        strOrgId = "701";
        strUserId = userId;

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        dialog = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dialog.setContentView(R.layout.dialog_holding);

        DisplayMetrics dm = dialog.getContext().getApplicationContext().getResources().getDisplayMetrics();
        WindowManager.LayoutParams wm = new WindowManager.LayoutParams();
        wm.copyFrom(dialog.getWindow().getAttributes());
        wm.width = dm.widthPixels - (dm.widthPixels / 20);
        wm.height = dm.heightPixels - (dm.widthPixels / 3);
        dialog.getWindow().setAttributes(wm);


        etFileNo = (EditText) dialog.findViewById(R.id.dialog_file_no);
        etStartTime = (EditText) dialog.findViewById(R.id.dialog_holding_start_time);
        etEndTime = (EditText) dialog.findViewById(R.id.dialog_holding_end_time);

        etJobId = (EditText) dialog.findViewById(R.id.job_id);
        etOperationId = (EditText) dialog.findViewById(R.id.operation_id);
        etHoldingControlId = (EditText) dialog.findViewById(R.id.holding_control_id);
        etStatusCode = (EditText) dialog.findViewById(R.id.dialog_holding_status_code);
        strWorkOrderNo = tvWorkOrderNo.getText().toString();

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

        btnStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                etStartTime.setText(getNowDate());
            }
        });

        btnEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            etEndTime.setText(getNowDate());
            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    HOLDING_CONTROL_UPDATE hOLDING_CONTROL_UPDATE = new HOLDING_CONTROL_UPDATE();
                    hOLDING_CONTROL_UPDATE.execute(strIp,etHoldingControlId.getText().toString() ,strSobId, strOrgId ,etJobId.getText().toString(), etOperationId.getText().toString()
                    ,etStartTime.getText().toString().replaceAll(" ",""), etEndTime.getText().toString().replaceAll(" ","") ,strUserId);

                }catch (Exception e){
                    e.printStackTrace();
                }


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

                if(jarrayWorkLevel.length() < 1){
                    etFileNo.setText(strWorkOrderNo);
                    return;
                }

                JSONObject job = jarrayWorkLevel.getJSONObject(0);
                if(job.getString("Status").equals("S")){

                    etJobId.setText(job.getString("JOB_ID"));
                    etOperationId.setText(job.getString("OPERATION_ID"));
                    etHoldingControlId.setText(job.getString("JOB_HOLDING_CONTROL_ID"));

                    HOLDING_CONTROL_SELECT hOLDING_CONTROL_SELECT = new HOLDING_CONTROL_SELECT();
                    hOLDING_CONTROL_SELECT.execute(strIp, strSobId, strOrgId,etOperationId.getText().toString(),"","",etHoldingControlId.getText().toString());

                }else{
                etFileNo.setText(strWorkOrderNo);
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
                    etFileNo.setText(job.getString("FILE_NO"));
                    etOperationId.setText(job.getString("OPERATION_ID"));
                    etHoldingControlId.setText(job.getString("JOB_HOLDING_CONTROL_ID"));
                    etStatusCode.setText(job.getString("JOB_STATUS_CODE"));


                    if(job.getString("HOLDING_START_DATE").equals("null")){
                        etStartTime.setText("");
                    }else{
                        etStartTime.setText(job.getString("HOLDING_START_DATE"));
                    }
                    if(job.getString("HOLDING_END_DATE").equals("null")){
                        etEndTime.setText("");
                    }else{
                        etEndTime.setText(job.getString("HOLDING_END_DATE"));
                    }

                    if(!job.getString("HOLDING_START_DATE").equals("") ){
                        if(etEndTime.getText().toString().equals("")){
                            etEndTime.setText("");
                        }else if(!etEndTime.getText().toString().equals("")){
                            etStartTime.setText("");
                            etEndTime.setText("");
                        }
                    }


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



    protected class HOLDING_CONTROL_UPDATE extends AsyncTask<String, Void, String>
    {
        //final LuLoctionListAdapter luLoctionListAdapter = new LuLoctionListAdapter();
        protected  String doInBackground(String... urls)
        {
            StringBuffer jsonHtml = new StringBuffer();

            //서버로 보낼 데이터 설정
            String search_title = "P_JOB_HOLDING_CONTROL_ID=" + urls[1]
                    + "&P_SOB_ID=" + urls[2]
                    + "&P_ORG_ID=" + urls[3]
                    + "&P_JOB_ID=" + urls[4]
                    + "&P_OPERATION_ID=" + urls[5]
                    + "&P_HOLDING_START_DATE=" + urls[6]
                    + "&P_HOLDING_END_DATE=" + urls[7]
                    + "&P_USER_ID=" + urls[8]

                    ;

            try
            {
                //String ip = context.getApplicationContext().getResources().getString(R.string.ip);

                URL obj = new URL("http://" + urls[0] + "/TAIYO/HoldingControlUpdate.jsp"); //주소 지정

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

        @SuppressLint("ResourceAsColor")
        protected void onPostExecute(String result)
        {
            //페이지 결과값 파싱
            try
            {
                JSONObject RESURT = new JSONObject(result); //JSON 오브젝트 받음
                JSONArray jarray = RESURT.getJSONArray("RESULT"); //JSONArray 파싱
                JSONObject job = jarray.getJSONObject(0); //JSON 오브젝트 파싱

                if(job.getString("P_RESULT_STATUS").equals("S")){

                    Toast.makeText(context.getApplicationContext(), "저장이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    // ClearView();


                    HOLDING_CONTROL_SELECT hOLDING_CONTROL_SELECT = new HOLDING_CONTROL_SELECT();
                    hOLDING_CONTROL_SELECT.execute(strIp, strSobId, strOrgId,etOperationId.getText().toString(),"","",etHoldingControlId.getText().toString());

                }else{
                    Toast.makeText(context.getApplicationContext(), "오류입니다." + result, Toast.LENGTH_SHORT).show();
                    return;
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





    private String getNowDate() {

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.KOREAN);

        String getTime = sdf.format(date);

        return getTime;
    }



}
