package com.erp.Taiyo.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.erp.Taiyo.Dialog.LuComLatelyDialog;
import com.erp.Taiyo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.protocol.HTTP;

public class CombinationWork2Activity extends AppCompatActivity {

    Toolbar toolbar;
    String strIp, strUserName, strUserId, SDate, strMenuDesc;
    String filter = null;
    String strDate;

    EditText etT2FileNoScan, etT2ItemDesc,etT2MixStartTime,etT2OperaionDesc, etT2MixTankDescScan,etT2EquipmentScan,etT2Stir1StartDate , etT2Stir1EndDate,
            etT2Stir1WorkerName, etT2Stir2StartDate, etT2Stir2EndDate,etT2Stir2WorkerName, etT2Stir3StartDate, etT2Stir3EndDate, etT2Stir3WorkerName, etT2MixEndTime,

            //숨김값
            etH2XworkId, etH2XworkCode, etH2XworkDesc,
            //숨김값 file 스캔
            etH2JobId, etH2OperationId, etH2ModFlag, etH2TankLcode, etH2Stir1WorkerId, etH2Stir2WorkerId, etH2Stir3WorkerId,
            //숨김값 탱크
             etH2EntryCode, etH2LookupEntryId,
             //숨김값 배합
             etH2EquipmentId, etH2EquipmentCode, etH2OldEquipmentName;

    Button bT2Recent, btnT2MixStartTime, btnT2Stir1StartDate, btnT2Stir1EndDate , btnT2Stir2StartDate, btnT2Stir2EndDate, btnT2Stir3StartDate,
            btnT2Stir3EndDate, btnT2MixEndTime, bT2Save;




    String strSobId = "70";
    String strOrgId = "701";
    String strAssemplyDeac ="";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.combination_weighing_work2);


        Intent intent = getIntent();
        strIp = intent.getStringExtra("Ip");
        strUserId = intent.getStringExtra("O_USER_ID");
        strUserName = intent.getStringExtra("O_USER_NAME");
        strAssemplyDeac ="PPMF2202";


        etT2FileNoScan  = (EditText) findViewById(R.id.et_t2_file_no_scan);
        etT2ItemDesc  = (EditText) findViewById(R.id.et_t2_item_desc);
        etT2MixStartTime  = (EditText) findViewById(R.id.et_t2_mix_start_time);
        etT2OperaionDesc  = (EditText) findViewById(R.id.et_t2_operaion_desc);
        etT2MixTankDescScan  = (EditText) findViewById(R.id.et_t2_mix_tank_desc_scan);
        etT2EquipmentScan  = (EditText) findViewById(R.id.et_t2_equipment_scan);
        etT2Stir1StartDate  = (EditText) findViewById(R.id.et_t2_stir_1_start_date);
        etT2Stir1EndDate  = (EditText) findViewById(R.id.et_t2_stir_1_end_date);
        etT2Stir1WorkerName  = (EditText) findViewById(R.id.et_t2_stir_1_worker_name);
        etT2Stir2StartDate  = (EditText) findViewById(R.id.et_t2_stir_2_start_date);
        etT2Stir2EndDate  = (EditText) findViewById(R.id.et_t2_stir_2_end_date);
        etT2Stir2WorkerName  = (EditText) findViewById(R.id.et_t2_stir_2_worker_name);
        etT2Stir3StartDate  = (EditText) findViewById(R.id.et_t2_stir_3_start_date);
        etT2Stir3EndDate  = (EditText) findViewById(R.id.et_t2_stir_3_end_date);
        etT2Stir3WorkerName  = (EditText) findViewById(R.id.et_t2_stir_3_worker_name);
        etT2MixEndTime  = (EditText) findViewById(R.id.et_t2_mix_end_time);




        //WORKCENTER_IN_AUTHORITY 숨김값
        etH2XworkId = (EditText) findViewById(R.id.et_h2_x_work_id);
        etH2XworkCode = (EditText) findViewById(R.id.et_h2_x_work_code);
        etH2XworkDesc = (EditText) findViewById(R.id.et_h2_x_work_desc);

        //파일스캔 숨김값들
        etH2TankLcode = (EditText) findViewById(R.id.et_h2_tank_lcode);
        etH2Stir1WorkerId = (EditText) findViewById(R.id.et_h2_stir_1_worker_id);
        etH2Stir2WorkerId = (EditText) findViewById(R.id.et_h2_stir_2_worker_id);
        etH2Stir3WorkerId = (EditText) findViewById(R.id.et_h2_stir_3_worker_id);



        etH2JobId = (EditText) findViewById(R.id.et_h2_job_id);
        etH2OperationId = (EditText) findViewById(R.id.et_h2_operation_id);
        etH2ModFlag = (EditText) findViewById(R.id.et_h2_mod_flag);



        //탱크 숨김값들
        etH2EntryCode = (EditText) findViewById(R.id.et_h2_entry_code);
        etH2LookupEntryId = (EditText) findViewById(R.id.et_h2_lookup_entry_id);

        //배합 숨김값들
        etH2EquipmentId = (EditText) findViewById(R.id.et_h2_equipment_id);
        etH2EquipmentCode = (EditText) findViewById(R.id.et_h2_equipment_code);
        etH2OldEquipmentName = (EditText) findViewById(R.id.et_h2_old_equipment_name);




        //버튼
        bT2Recent = (Button) findViewById(R.id.btn_t2_recent);

        btnT2MixStartTime = (Button) findViewById(R.id.btn_t2_mix_start_time);
        btnT2Stir1StartDate = (Button) findViewById(R.id.btn_t2_stir_1_start_date);
        btnT2Stir1EndDate = (Button) findViewById(R.id.btn_t2_stir_1_end_date);
        btnT2Stir2StartDate = (Button) findViewById(R.id.btn_t2_stir_2_start_date);
        btnT2Stir2EndDate = (Button) findViewById(R.id.btn_t2_stir_2_end_date	);
        btnT2Stir3StartDate  = (Button) findViewById(R.id.btn_t2_stir_3_start_date	);
        btnT2Stir3EndDate  = (Button) findViewById(R.id.btn_t2_stir_3_end_date	);
        btnT2MixEndTime  = (Button) findViewById(R.id.btn_t2_mix_end_time	);

        bT2Save = (Button) findViewById(R.id.btn_t2_save);

        WorkCenter workCenter = new WorkCenter();
        workCenter.execute(strSobId, strOrgId,strUserId,strAssemplyDeac);

        etT2FileNoScan.requestFocus();


        etT2FileNoScan.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if (getCurrentFocus() == etT2FileNoScan && !etT2FileNoScan.getText().toString().isEmpty()) {
                    FileNoScanGR fileNoScanGR = new FileNoScanGR();
                    //fileNoScanGR.execute(strSobId, strOrgId, edT2FileNoScan.getText().toString(), strUserId); 기존
                    fileNoScanGR.execute(strSobId, strOrgId, etT2FileNoScan.getText().toString(), etH2XworkId.getText().toString());

                }
            }
        });

        etT2MixTankDescScan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (getCurrentFocus() == etT2MixTankDescScan && !etT2MixTankDescScan.getText().toString().isEmpty()) {
                    LuTankType luTankType = new LuTankType();
                    luTankType.execute(strSobId, strOrgId, etT2MixTankDescScan.getText().toString());
                }
            }
        });

        etT2EquipmentScan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {

                if (getCurrentFocus() == etT2MixTankDescScan && !etT2EquipmentScan.getText().toString().isEmpty()) {
                    LuBhEQp luBhEQp = new LuBhEQp();
                    luBhEQp.execute(strSobId, strOrgId,strUserId, etT2EquipmentScan.getText().toString());

                }

            }
        });

        etT2Stir1WorkerName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (getCurrentFocus() == etT2Stir1WorkerName && !etT2Stir1WorkerName.getText().toString().isEmpty()) {

                    LuWorker luWorker = new LuWorker();
                    luWorker.execute(strSobId, strOrgId,strUserId, etT2Stir1WorkerName.getText().toString());


                }
            }
        });




        btnT2MixStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String currentDateAndTime = sdf.format(new Date());

                btnT2MixStartTime.setText(currentDateAndTime);
            }
        });

        btnT2Stir1StartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String currentDateAndTime = sdf.format(new Date());

                btnT2Stir1StartDate.setText(currentDateAndTime);

            }
        });

        btnT2Stir1EndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String currentDateAndTime = sdf.format(new Date());

                btnT2Stir1EndDate.setText(currentDateAndTime);
            }
        });

        btnT2Stir2StartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String currentDateAndTime = sdf.format(new Date());

                btnT2Stir2StartDate.setText(currentDateAndTime);
            }
        });
        btnT2Stir2EndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String currentDateAndTime = sdf.format(new Date());

                btnT2Stir2EndDate.setText(currentDateAndTime);
            }
        });
        btnT2Stir3StartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String currentDateAndTime = sdf.format(new Date());

                btnT2Stir3StartDate.setText(currentDateAndTime);
            }
        });
        btnT2Stir3EndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String currentDateAndTime = sdf.format(new Date());

                btnT2Stir3EndDate.setText(currentDateAndTime);
            }
        });

        btnT2MixEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String currentDateAndTime = sdf.format(new Date());

                btnT2MixEndTime.setText(currentDateAndTime);
            }
        });








        //        다이얼로그

        bT2Recent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LuComLatelyDialog luComLatelyDialog = new LuComLatelyDialog(CombinationWork2Activity.this);
                luComLatelyDialog.call_Lately_Dialog(strIp, strSobId, strOrgId, etT2FileNoScan);  //가져오는거 다시 보기
            }
        });



    }

    protected class WorkCenter extends AsyncTask<String, Void, String> {


        protected String doInBackground(String... urls) {

            StringBuffer jsonHtml = new StringBuffer();
            //서버로 보낼 데이터 설정
            String search_title = "W_SOB_ID=" + urls[0]
                    + "&W_ORG_ID=" + urls[1]
                    + "&W_USER_ID=" + urls[2]
                    + "&W_ASSEMBLY_DESC=" + urls[3];


            try {
                //String ip = context.getApplicationContext().getResources().getString(R.string.ip);

                //URL obj = new URL("http://" + strIp +"/TAIYO/WorkCenterInAuthority.jsp"); //인텐트로 바꾸기
                URL obj = new URL("http://" + strIp + "/TAIYO/WorkCenterInAuthority.jsp"); //주소 지정 전역변수로 만들어서 쉽게 사용하기

                HttpURLConnection conn = (HttpURLConnection) obj.openConnection(); //지정된 주소로 연결

                if (conn != null) //
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

                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) //서버에서 응답을 받았을 경우
                    {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8")); //받은 정보를 버퍼에 저장
                        while (true) {
                            String line = br.readLine();
                            if (line == null) //라인이 없어질때까지 버퍼를 한줄씩 읽음
                                break;
                            jsonHtml.append(line);// + "\n");
                        }
                        br.close();
                    }
                    conn.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonHtml.toString(); //결과값 리턴
        }

        protected void onPostExecute(String result) {
            //페이지 결과값 파싱
            try {
                JSONObject RESURT = new JSONObject(result); //JSON 오브젝트 받음
                JSONArray jarrayWorkLevel = RESURT.getJSONArray("RESULT"); //JSONArray 파싱

                JSONObject job = jarrayWorkLevel.getJSONObject(0); //JSON 오브젝트 파싱
//                xWorkCode.setText(job.getString("P_USER_ID"));
//                xWorkCode.setText(job.getString("P_ASSEMBLY_DESC"));
                etH2XworkId.setText(job.getString("X_WORKCENTER_ID"));
                etH2XworkCode.setText(job.getString("X_WORKCENTER_CODE"));
                etH2XworkDesc.setText(job.getString("X_WORKCENTER_DESC"));



            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    protected class FileNoScanGR extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... urls) {
            StringBuffer jsonHtml = new StringBuffer();

            //서버로 보낼 데이터 설정
            String search_title = "W_SOB_ID=" + urls[0]
                    + "&W_ORG_ID=" + urls[1]
                    + "&W_FILE_NO=" + urls[2]
                    + "&W_WORKCENTER_ID=" + urls[3];
            try {
                URL obj = new URL("http://"+strIp+"/TAIYO/FileNoScanBH.jsp"); //주소 지정

                HttpURLConnection conn = (HttpURLConnection) obj.openConnection(); //지정된 주소로 연결

                if (conn != null) //
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

                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) //서버에서 응답을 받았을 경우
                    {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8")); //받은 정보를 버퍼에 저장
                        while (true) {
                            String line = br.readLine();
                            if (line == null) //라인이 없어질때까지 버퍼를 한줄씩 읽음
                                break;
                            jsonHtml.append(line);// + "\n");
                        }
                        br.close();
                    }
                    conn.disconnect();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return jsonHtml.toString(); //결과값 리턴
        }

        protected void onPostExecute(String result) {
            //페이지 결과값 파싱
            try {

                JSONObject RESURT = new JSONObject(result); //JSON 오브젝트 받음
                JSONArray resultArray = RESURT.getJSONArray("RESULT"); //JSONArray 파싱

                //JSONObject job = resultArray.getJSONObject(0); //JSON 오브젝트 파싱

                //JSONObject job = resultArray.getJSONObject(0);  필요없는 이유를 확실하게



                if (resultArray.length() > 0) {
                    JSONObject job = resultArray.getJSONObject(0); // JSON 오브젝트 파싱
                    String status = job.getString("Status");

                    if (status.equals("S")) {
                        etT2FileNoScan.setText(job.getString("WORK_ORDER_NO"));
                        etT2ItemDesc.setText(job.getString("ITEM_DESCRIPTION"));
                        etT2OperaionDesc.setText(job.getString("OPERATION_DESCRIPTION"));
                        etT2MixStartTime.setText(job.getString("MIX_START_DATE"));
                        etH2TankLcode.setText(job.getString("MIX_TANK_LCODE"));
                        etT2MixTankDescScan.setText(job.getString("MIX_TANK_DESC"));
                        etH2EquipmentId.setText(job.getString("EQUIPMENT_ID"));
                        etT2EquipmentScan.setText(job.getString("EQUIPMENT_NAME"));
                        etT2Stir1StartDate.setText(job.getString("STIR_1_START_DATE"));
                        etT2Stir1EndDate.setText(job.getString("STIR_1_END_DATE"));
                        etH2Stir1WorkerId.setText(job.getString("STIR_1_WORKER_ID"));
                        etT2Stir1WorkerName.setText(job.getString("STIR_1_WORKER_NAME"));
                        etT2Stir2StartDate.setText(job.getString("STIR_2_START_DATE"));
                        etT2Stir2EndDate.setText(job.getString("STIR_2_END_DATE"));
                        etH2Stir2WorkerId.setText(job.getString("STIR_2_WORKER_ID"));
                        etT2Stir2WorkerName.setText(job.getString("STIR_2_WORKER_NAME"));
                        etT2Stir3StartDate.setText(job.getString("STIR_3_START_DATE"));
                        etT2Stir3EndDate.setText(job.getString("STIR_3_END_DATE"));
                        etH2Stir3WorkerId.setText(job.getString("STIR_3_WORKER_ID"));
                        etT2Stir3WorkerName.setText(job.getString("STIR_3_WORKER_NAME"));
                        etT2MixEndTime.setText(job.getString("MIX_END_DATE"));
                        etH2JobId.setText(job.getString("JOB_ID"));
                        etH2OperationId.setText(job.getString("OPERATION_ID"));
                        etH2ModFlag.setText(job.getString("MOD_FLAG"));

                    }
                    etT2MixTankDescScan.requestFocus();

                }




            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    protected class LuTankType extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... urls) {
            StringBuffer jsonHtml = new StringBuffer();

            //서버로 보낼 데이터 설정
            String search_title = "W_SOB_ID=" + urls[0]
                    + "&W_ORG_ID=" + urls[1]
                    + "&W_BARCODE=" + urls[2];

            try {
                URL obj = new URL("http://"+strIp+"/TAIYO/LuTankType.jsp"); //주소 지정

                HttpURLConnection conn = (HttpURLConnection) obj.openConnection(); //지정된 주소로 연결

                if (conn != null) //
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

                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) //서버에서 응답을 받았을 경우
                    {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8")); //받은 정보를 버퍼에 저장
                        while (true) {
                            String line = br.readLine();
                            if (line == null) //라인이 없어질때까지 버퍼를 한줄씩 읽음
                                break;
                            jsonHtml.append(line);// + "\n");
                        }
                        br.close();
                    }
                    conn.disconnect();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return jsonHtml.toString(); //결과값 리턴
        }

        protected void onPostExecute(String result) {
            //페이지 결과값 파싱
            try {
                JSONObject RESURT = new JSONObject(result); // JSON 오브젝트 받음
                JSONArray resultArray = RESURT.getJSONArray("RESULT"); // JSONArray 파싱


                if (resultArray.length() > 0) {
                    JSONObject job = resultArray.getJSONObject(0); // JSON 오브젝트 파싱
                    String status = job.getString("Status");

                    if (status.equals("S")) {
                        etH2EntryCode.setText(job.getString("ENTRY_CODE")); // entryDesc
                        etT2MixTankDescScan.setText(job.getString("ENTRY_DESCRIPTION")); // edT2TankScan
                        etH2LookupEntryId.setText(job.getString("LOOKUP_ENTRY_ID")); // lookupEntryId
                    }

                    etT2EquipmentScan.requestFocus();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    protected class LuBhEQp extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... urls) {
            StringBuffer jsonHtml = new StringBuffer();

            //서버로 보낼 데이터 설정
            String search_title = "W_SOB_ID=" + urls[0]
                    + "&W_ORG_ID=" + urls[1]
                    + "&W_WORKCENTER_ID=" + urls[2]
                    + "&W_BARCODE=" + urls[3];

            try {
                URL obj = new URL("http://"+strIp+"/TAIYO/LuBhEqp.jsp"); //주소 지정

                HttpURLConnection conn = (HttpURLConnection) obj.openConnection(); //지정된 주소로 연결

                if (conn != null) //
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

                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) //서버에서 응답을 받았을 경우
                    {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8")); //받은 정보를 버퍼에 저장
                        while (true) {
                            String line = br.readLine();
                            if (line == null) //라인이 없어질때까지 버퍼를 한줄씩 읽음
                                break;
                            jsonHtml.append(line);// + "\n");
                        }
                        br.close();
                    }
                    conn.disconnect();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return jsonHtml.toString(); //결과값 리턴
        }

        protected void onPostExecute(String result) {
            //페이지 결과값 파싱
            try {

                JSONObject RESURT = new JSONObject(result); //JSON 오브젝트 받음
                JSONArray resultArray = RESURT.getJSONArray("RESULT"); //JSONArray 파싱

                //JSONObject job = resultArray.getJSONObject(0); //JSON 오브젝트 파싱


                if (resultArray.length() > 0) {
                    JSONObject job = resultArray.getJSONObject(0); // JSON 오브젝트 파싱
                    String status = job.getString("Status");

                    if (status.equals("S")) {
                        //테스트용으로 넣었으니 된다면 다시 수정하기
                        etH2EquipmentId.setText(job.getString("EQUIPMENT_ID"));
                        etH2EquipmentCode.setText(job.getString("EQUIPMENT_CODE"));
                        etT2EquipmentScan.setText(job.getString("EQUIPMENT_NAME"));
                        etH2OldEquipmentName.setText(job.getString("OLD_EQUIPMENT_NAME"));

                    }
                    etT2Stir1WorkerName.requestFocus();

                }



            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    protected class LuWorker extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... urls) {
            StringBuffer jsonHtml = new StringBuffer();

            //서버로 보낼 데이터 설정
            String search_title = "W_SOB_ID=" + urls[0]
                    + "&W_ORG_ID=" + urls[1]
                    + "&W_WORKCENTER_ID=" + urls[2]
                    + "&W_BARCODE=" + urls[3];
            try {
                URL obj = new URL("http://"+strIp+"/TAIYO/LuWorker.jsp"); //주소 지정

                HttpURLConnection conn = (HttpURLConnection) obj.openConnection(); //지정된 주소로 연결

                if (conn != null) //
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

                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) //서버에서 응답을 받았을 경우
                    {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8")); //받은 정보를 버퍼에 저장
                        while (true) {
                            String line = br.readLine();
                            if (line == null) //라인이 없어질때까지 버퍼를 한줄씩 읽음
                                break;
                            jsonHtml.append(line);// + "\n");
                        }
                        br.close();
                    }
                    conn.disconnect();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return jsonHtml.toString(); //결과값 리턴
        }

        protected void onPostExecute(String result) {
            //페이지 결과값 파싱
            try {

                JSONObject RESURT = new JSONObject(result); //JSON 오브젝트 받음
                JSONArray resultArray = RESURT.getJSONArray("RESULT"); //JSONArray 파싱

                //JSONObject job = resultArray.getJSONObject(0); //JSON 오브젝트 파싱



                if (resultArray.length() > 0) {
                    JSONObject job = resultArray.getJSONObject(0); // JSON 오브젝트 파싱
                    String status = job.getString("Status");

                    if (status.equals("S")) {
                        etH2Stir1WorkerId.setText(job.getString("USER_ID"));
                        etT2Stir1WorkerName.setText(job.getString("DESCRIPTION"));


                    }
                    etT2Stir2WorkerName.requestFocus();

                }



            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
