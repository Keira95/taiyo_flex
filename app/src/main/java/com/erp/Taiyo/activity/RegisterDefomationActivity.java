package com.erp.Taiyo.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.erp.Taiyo.Dialog.HoldingDialog;
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
import java.util.Date;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

public class RegisterDefomationActivity extends AppCompatActivity {

    Toolbar toolbar;
    String strIp, strUserName, strUserId, SDate, strMenuDesc;
    String filter = null;
    String strDate;

    EditText etT7FileNoScan, etT7ItemDesc, etT7OperaionDesc, etT7StartDate, etT7TankDesc ,etT7EquipmentName , etT7WorkerName, etT7EndDate,
                    //여기서부터는 숨김값
             etT7OpOrderSeq, etT7WorkcenterId, etT7WorkcenterCode ,etT7WorkcenterDesc, etT7TankLcode, etT7EquipmentId, etT7WorkerId, etT7JobId, etT7OperationId, etT7ModFlag,
             etT7TopEquipmentId, etT7TopEquipmentCode, etT7OldEquipmentName
                            ;



    String strSobId = "70";
    String strOrgId = "701";
    String strAssembly = "PPMF2209";


    TextView tvUserGroup;
    TextView tvUserName;
    TextView tvMenu;
    FrameLayout log_panel;
    ListView lv_Receiving;
    TextView tvLog;
    SharedPreferences auto;
    EditText editTextFilter;
    LinearLayout mainLayout;
    ListView lv_log;
    ListView lvPaldlet, lvInput;

    Button btnt7save;
    Button btnT7StartDate,btnT7EndDate, btnHolding;
    //키보드

    InputMethodManager imm;
    JSONObject jspSumObject = new JSONObject();

    private boolean ScanModify = true;

    private String FileScan = "";
    private String TankType = "";
    private String TpEqp = "";
    private String Worker = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_defoamation_work7);
        Intent intent = getIntent();
        strIp = intent.getStringExtra("Ip");
        strUserId = intent.getStringExtra("O_USER_ID");
        strUserName = intent.getStringExtra("O_USER_NAME");
        strMenuDesc = intent.getStringExtra("TOP_MENU_DESC");


        mainLayout = findViewById(R.id.main_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvUserName = (TextView) findViewById(R.id.user_nm);
        tvMenu = (TextView) findViewById(R.id.tv_menu);

        etT7FileNoScan = (EditText) findViewById(R.id.et_t7_file_no_scan);
        etT7ItemDesc = (EditText) findViewById(R.id.et_t7_item_desc);
        etT7OperaionDesc = (EditText) findViewById(R.id.et_t7_operaion_desc);
        etT7StartDate = (EditText) findViewById(R.id.et_t7_start_date);
        etT7TankDesc = (EditText) findViewById(R.id.et_t7_tank_desc);
        etT7EquipmentName = (EditText) findViewById(R.id.et_t7_equipment_name);
        etT7WorkerName = (EditText) findViewById(R.id.et_t7_worker_name);
        etT7EndDate = (EditText) findViewById(R.id.et_t7_end_date);

        //여기서부터는 숨김값
        etT7OpOrderSeq = (EditText) findViewById(R.id.et_t7_op_order_seq);
        etT7WorkcenterId = (EditText) findViewById(R.id.et_t7_workcenter_id);
        etT7WorkcenterCode = (EditText) findViewById(R.id.et_t7_workcenter_code);
        etT7WorkcenterDesc = (EditText) findViewById(R.id.et_t7_workcenter_desc);
        etT7TankLcode = (EditText) findViewById(R.id.et_t7_tank_lcode);
        etT7EquipmentId = (EditText) findViewById(R.id.et_t7_equipment_id);
        etT7WorkerId = (EditText) findViewById(R.id.et_t7_worker_id);
        etT7JobId = (EditText) findViewById(R.id.et_t7_job_id);
        etT7OperationId = (EditText) findViewById(R.id.et_t7_operation_id);
        etT7ModFlag = (EditText) findViewById(R.id.et_t7_mod_flag);

        etT7TopEquipmentId = (EditText) findViewById(R.id.et_t7_top_equipment_id);
        etT7TopEquipmentCode = (EditText) findViewById(R.id.et_t7_top_equipment_code);
        etT7OldEquipmentName = (EditText) findViewById(R.id.et_t7_old_equipment_name);



        btnT7StartDate = (Button) findViewById(R.id.btn_t7_start_date);
        btnT7EndDate = (Button) findViewById(R.id.btn_t7_end_date);
        btnt7save = (Button) findViewById(R.id.btn_t7_save);
        btnHolding = (Button) findViewById(R.id.btn_t7_holding);


        auto = getSharedPreferences("appData_Log", Context.MODE_PRIVATE);
        //키보드 내리기

        //키보드
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);


        GET_WORKCENTER_IN_AUTHORITY gET_WORKCENTER_IN_AUTHORITY = new GET_WORKCENTER_IN_AUTHORITY();
        gET_WORKCENTER_IN_AUTHORITY.execute(strIp, strSobId,strOrgId ,strUserId, strAssembly);

        initializeToolbar();

        keyboardFocus(etT7FileNoScan);
        keyboardFocus(etT7TankDesc);
        keyboardFocus(etT7EquipmentName);
        keyboardFocus(etT7WorkerName);

        etT7FileNoScan.requestFocus();


        btnT7StartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ScanModify==false){
                    btnt7save.setBackgroundColor(Color.YELLOW);
                    btnt7save.setTextColor(Color.BLACK);
                }

                etT7StartDate.setText(getNowDate());
            }
        });

        btnT7EndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ScanModify==false){
                    btnt7save.setBackgroundColor(Color.YELLOW);
                    btnt7save.setTextColor(Color.BLACK);
                }

                etT7EndDate.setText(getNowDate());
            }
        });

        etT7FileNoScan.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                etT7FileNoScan.setText("");
                etT7JobId.setText("");
                return false;
            }
        });

        etT7TankDesc.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                etT7TankDesc.setText("");
                etT7TankLcode.setText("");
                return false;
            }
        });

        etT7EquipmentName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                etT7EquipmentName.setText("");
                etT7EquipmentId.setText("");
                return false;
            }
        });


        etT7WorkerName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                etT7WorkerName.setText("");
                etT7WorkerId.setText("");
                return false;
            }
        });


    }
    public void keyboardFocus(final EditText editText) {
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int inType = editText.getInputType(); // 현재 입력 모드 저장
                editText.setInputType(InputType.TYPE_NULL); // 키보드 막기
                editText.onTouchEvent(event); // 이벤트 처리
                editText.setInputType(inType); // 원래 입력 모드를 복구
                editText.setCursorVisible(true); // 커서 표시
                return true; // 리턴
            }
        });
    }

    private void saveColorChange() {
        if (ScanModify==false) {
            btnt7save.setBackgroundColor(Color.YELLOW);
            btnt7save.setTextColor(Color.BLACK);
        }
    }



    private String getNowDate() {

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.KOREAN);

        String getTime = sdf.format(date);

        return getTime;
    }


    //Toolbar 정보
    private void initializeToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.white));

        //toolbar.setNavigationOnClickListener(v -> onBackPressed());

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        toolbar.getNavigationIcon().mutate().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);

        toolbar.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (log_panel.getVisibility() == View.INVISIBLE ||
                        log_panel.getVisibility() == View.GONE) {
                    log_panel.setVisibility(View.VISIBLE);

                    if (auto.getString("log", "").length() > 0) {
                        tvLog.setText(auto.getString("log", ""));
                    }

//                logFragment.setHasOptionsMenu(true);
                } else {
                    log_panel.setVisibility(View.GONE);
//                logFragment.setHasOptionsMenu(false);
                }
                return false;
            }
        });

        // 유저 정보 삽입.
        tvUserName.setText(strUserName);


        etT7FileNoScan.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {

                if(getCurrentFocus() == etT7FileNoScan && !s.toString().isEmpty() && s != null && etT7JobId.getText().toString().equals("")){
                    FILE_NO_TP_SCAN fILE_NO_TP_SCAN = new FILE_NO_TP_SCAN();
                    fILE_NO_TP_SCAN.execute(strIp, strSobId,strOrgId ,etT7FileNoScan.getText().toString(),etT7WorkcenterId.getText().toString());

                }else{
                    return;
                }

            }
        });

        //tank scan
        etT7TankDesc.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(getCurrentFocus() == etT7TankDesc && !s.toString().isEmpty() && s != null && etT7TankLcode.getText().toString().equals("")){

                    LU_TANK_TYPE lU_TANK_TYPE = new LU_TANK_TYPE();
                    lU_TANK_TYPE.execute(strIp, strSobId,strOrgId, etT7TankDesc.getText().toString());

                }else{
                    return;
                }
            }
        });


        etT7EquipmentName.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(getCurrentFocus() == etT7EquipmentName && !s.toString().isEmpty() && s != null && etT7EquipmentId.getText().toString().equals("")){

                    LU_TP_EQP lU_TP_EQP = new LU_TP_EQP();
                    lU_TP_EQP.execute(strIp, strSobId,strOrgId, etT7WorkcenterId.getText().toString(),etT7EquipmentName.getText().toString());

                }else{
                    return;
                }
            }
        });

        etT7WorkerName.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(getCurrentFocus() == etT7WorkerName && !s.toString().isEmpty() && s != null && etT7WorkerId.getText().toString().equals("")){

                    LU_WORKER lu_worker = new LU_WORKER();
                    lu_worker.execute(strIp, strSobId,strOrgId, etT7WorkcenterId.getText().toString(), etT7WorkerName.getText().toString());

                }else{
                    return;
                }
            }
        });


        btnt7save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(RegisterDefomationActivity.this);
                alert.setTitle("저장");
                alert.setMessage("수정한 내역을 저장하시겠습니까?");
                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(etT7FileNoScan.getText().toString().isEmpty()){
                            Toast.makeText(getApplicationContext(), "File No는 필수입니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        TP_UPDATE tP_UPDATE = new TP_UPDATE();
                        tP_UPDATE.execute(strIp,etT7JobId.getText().toString(),etT7OperationId.getText().toString(),etT7WorkcenterId.getText().toString(),strSobId ,strOrgId ,etT7StartDate.getText().toString().replaceAll(" ",""),
                                etT7EndDate.getText().toString().replaceAll(" ",""), etT7TankLcode.getText().toString(), etT7EquipmentId.getText().toString() ,etT7WorkerId.getText().toString(),
                                strUserId);



                    }
                });
                alert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {


                    }

                });
                alert.show();


            }
        });


        btnHolding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(etT7FileNoScan.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "File No는 필수입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                HoldingDialog holdingDialog = new HoldingDialog(RegisterDefomationActivity.this);
              //  holdingDialog.call_Level_Dialog(etT7FileNoScan, strIp , strUserId);
            }
        });


    }





    //자동세팅
    protected class GET_WORKCENTER_IN_AUTHORITY extends AsyncTask<String, Void, String>
    {
        protected  String doInBackground(String... urls)
        {
            StringBuffer jsonHtml = new StringBuffer();

            //서버로 보낼 데이터 설정
            String search_title = "W_SOB_ID=" + urls[1]
                    + "&W_ORG_ID=" + urls[2]
                    + "&W_USER_ID=" +urls[3]
                    + "&W_ASSEMBLY_DESC=" + urls[4]
                    ;

            try
            {  URL obj = new URL("http://" + urls[0] + "/TAIYO/WorkCenterInAuthority.jsp"); //주소 지정

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
                    etT7WorkcenterCode.setText(job.getString("X_WORKCENTER_CODE"));
                    etT7WorkcenterDesc.setText(job.getString("X_WORKCENTER_DESC"));
                    etT7WorkcenterId.setText(job.getString("X_WORKCENTER_ID"));
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

    // FILE_NO_SCAN
    protected class FILE_NO_TP_SCAN extends AsyncTask<String, Void, String>
    {
        protected  String doInBackground(String... urls)
        {
            StringBuffer jsonHtml = new StringBuffer();

            //서버로 보낼 데이터 설정
            String search_title = "W_SOB_ID=" + urls[1]
                    + "&W_ORG_ID=" + urls[2]
                    + "&W_FILE_NO=" +urls[3]
                    + "&W_WORKCENTER_ID=" +urls[4]
                    ;

            try
            {  URL obj = new URL("http://" + urls[0] + "/TAIYO/FileNoScanTP.jsp"); //주소 지정

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
                etT7TankDesc.requestFocus();
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
                    if(!FileScan.equals(etT7FileNoScan.getText().toString())){
                        ScanModify = false;
                        etT7FileNoScan.requestFocus();
                        etT7FileNoScan.setText("");

                    }

                    return;
                }else{
                                    JSONObject job = jarrayWorkLevel.getJSONObject(0);
                if(job.getString("Status").equals("S")){

                    etT7FileNoScan.setText(job.getString("WORK_ORDER_NO"));
                    etT7ItemDesc.setText(job.getString("ITEM_DESCRIPTION"));
                    etT7OperaionDesc.setText(job.getString("OPERATION_DESCRIPTION"));
                    etT7OpOrderSeq.setText(job.getString("OP_ORDER_SEQ"));

                    if(job.getString("WORKING_START_DATE").equals("null")){
                        etT7StartDate.setText("");
                    }else{
                        etT7StartDate.setText(job.getString("WORKING_START_DATE"));
                    }

                    if(job.getString("TANK_LCODE").equals("null")){
                        etT7TankLcode.setText("");
                    }else{
                        etT7TankLcode.setText(job.getString("TANK_LCODE"));
                    }

                    if(job.getString("TANK_DESC").equals("null")){
                        etT7TankDesc.setText("");
                    }else{
                        etT7TankDesc.setText(job.getString("TANK_DESC"));
                    }


                    if(job.getString("EQUIPMENT_ID").equals("null")){
                        etT7EquipmentId.setText("");
                    }else{
                        etT7EquipmentId.setText(job.getString("EQUIPMENT_ID"));
                    }


                    if(job.getString("EQUIPMENT_NAME").equals("null")){
                        etT7EquipmentName.setText("");
                    }else{
                        etT7EquipmentName.setText(job.getString("EQUIPMENT_NAME"));
                    }


                    if(job.getString("WORKER_ID").equals("null")){
                        etT7WorkerId.setText("");
                    }else{
                        etT7WorkerId.setText(job.getString("WORKER_ID"));
                    }


                    if(job.getString("WORKER").equals("null")){
                        etT7WorkerName.setText("");
                    }else{
                        etT7WorkerName.setText(job.getString("WORKER"));
                    }

                    if(job.getString("WORKING_END_DATE").equals("null")){
                        etT7EndDate.setText("");
                    }else{
                        etT7EndDate.setText(job.getString("WORKING_END_DATE"));
                    }


                    etT7JobId.setText(job.getString("JOB_ID"));
                    etT7OperationId.setText(job.getString("OPERAITON_ID"));
                    etT7ModFlag.setText(job.getString("MOD_FLAG"));

                    FileScan = job.getString("WORK_ORDER_NO");

                }
                    etT7TankDesc.requestFocus();

                }




                ScanModify = false;

                if(etT7ModFlag.equals("N")){

                    etT7FileNoScan.setInputType(InputType.TYPE_NULL);
                    etT7ItemDesc.setInputType(InputType.TYPE_NULL);
                    etT7OperaionDesc.setInputType(InputType.TYPE_NULL);
                    etT7OpOrderSeq.setInputType(InputType.TYPE_NULL);
                    etT7StartDate.setInputType(InputType.TYPE_NULL);
                    etT7TankLcode.setInputType(InputType.TYPE_NULL);
                    etT7TankDesc.setInputType(InputType.TYPE_NULL);
                    etT7EquipmentId.setInputType(InputType.TYPE_NULL);
                    etT7EquipmentName.setInputType(InputType.TYPE_NULL);
                    etT7WorkerId.setInputType(InputType.TYPE_NULL);
                    etT7WorkerName.setInputType(InputType.TYPE_NULL);
                    etT7EndDate.setInputType(InputType.TYPE_NULL);
                    etT7JobId.setInputType(InputType.TYPE_NULL);
                    etT7OperationId.setInputType(InputType.TYPE_NULL);
                    etT7ModFlag.setInputType(InputType.TYPE_NULL);

                }
            }catch (JSONException e)
            {
                e.printStackTrace();
                etT7TankDesc.requestFocus();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                etT7TankDesc.requestFocus();
            }
        }
    }





    protected class LU_TANK_TYPE extends AsyncTask<String, Void, String>
    {
        protected  String doInBackground(String... urls)
        {
            StringBuffer jsonHtml = new StringBuffer();

            //서버로 보낼 데이터 설정
            String search_title = "W_SOB_ID=" + urls[1]
                    + "&W_ORG_ID=" + urls[2]
                    + "&W_BARCODE=" +urls[3]
                    ;

            try
            {  URL obj = new URL("http://" + urls[0] + "/TAIYO/LuSurimiTankType.jsp"); //주소 지정

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
                etT7EquipmentName.requestFocus();
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
                    if(!TankType.equals(etT7TankDesc.getText().toString())){
                        etT7TankDesc.requestFocus();
                        etT7TankDesc.setText("");

                    }

                    return;
                }else{
                    JSONObject job = jarrayWorkLevel.getJSONObject(0);
                    if(job.getString("Status").equals("S")){

                        etT7TankLcode.setText(job.getString("ENTRY_CODE"));
                        etT7TankDesc.setText(job.getString("ENTRY_DESCRIPTION"));

                        TankType = job.getString("ENTRY_DESCRIPTION");

                        saveColorChange();
                    }
                    etT7EquipmentName.requestFocus();
                }




            }catch (JSONException e)
            {
                e.printStackTrace();
                etT7EquipmentName.requestFocus();

            }
            catch (Exception e)
            {
                e.printStackTrace();
                etT7EquipmentName.requestFocus();
            }
        }
    }


    protected class LU_TP_EQP extends AsyncTask<String, Void, String>
    {
        protected  String doInBackground(String... urls)
        {
            StringBuffer jsonHtml = new StringBuffer();

            //서버로 보낼 데이터 설정
            String search_title = "W_SOB_ID=" + urls[1]
                    + "&W_ORG_ID=" + urls[2]
                    + "&W_WORKCENTER_ID=" + urls[3]
                    + "&W_BARCODE=" +urls[4]
                    ;

            try
            {  URL obj = new URL("http://" + urls[0] + "/TAIYO/LuTpEqp.jsp"); //주소 지정

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
                etT7WorkerName.requestFocus();
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
                    if(!TpEqp.equals(etT7EquipmentName.getText().toString())){
                        etT7EquipmentName.requestFocus();
                        etT7EquipmentName.setText("");

                    }

                    return;
                }else{
                    JSONObject job = jarrayWorkLevel.getJSONObject(0);
                    if(job.getString("Status").equals("S")){

                        etT7OldEquipmentName.setText(job.getString("OLD_EQUIPMENT_NAME"));
                        etT7EquipmentName.setText(job.getString("TOP_EQUIPMENT_NAME"));
                        etT7TopEquipmentCode.setText(job.getString("TOP_EQUIPMENT_CODE"));
                        etT7EquipmentId.setText(job.getString("TOP_EQUIPMENT_ID"));

                        TpEqp = job.getString("TOP_EQUIPMENT_NAME");
                        saveColorChange();
                    }
                    etT7WorkerName.requestFocus();
                }




            }catch (JSONException e)
            {
                e.printStackTrace();
                etT7WorkerName.requestFocus();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                etT7WorkerName.requestFocus();
            }
        }
    }

    protected class LU_WORKER extends AsyncTask<String, Void, String>
    {
        protected  String doInBackground(String... urls)
        {
            StringBuffer jsonHtml = new StringBuffer();

            //서버로 보낼 데이터 설정
            String search_title = "W_SOB_ID=" + urls[1]
                    + "&W_ORG_ID=" + urls[2]
                    + "&W_WORKCENTER_ID=" + urls[3]
                    + "&W_BARCODE=" +urls[4]
                    ;

            try
            {  URL obj = new URL("http://" + urls[0] + "/TAIYO/LuWorker.jsp"); //주소 지정

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
                    if(!Worker.equals(etT7WorkerName.getText().toString())){
                        etT7WorkerName.requestFocus();
                        etT7WorkerName.setText("");
                    }

                    return;
                }else{
                    JSONObject job = jarrayWorkLevel.getJSONObject(0);
                    if(job.getString("Status").equals("S")){

                        etT7WorkerId.setText(job.getString("USER_ID"));
                        etT7WorkerName.setText(job.getString("DESCRIPTION"));

                        Worker = job.getString("DESCRIPTION");
                        saveColorChange();
                    }
                    etT7WorkerName.requestFocus();
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

    protected class TP_UPDATE extends AsyncTask<String, Void, String>
    {
        //final LuLoctionListAdapter luLoctionListAdapter = new LuLoctionListAdapter();
        protected  String doInBackground(String... urls)
        {
            StringBuffer jsonHtml = new StringBuffer();

            //서버로 보낼 데이터 설정
            String search_title = "W_JOB_ID=" + urls[1]
                    + "&W_OPERATION_ID=" + urls[2]
                    + "&W_WORKCENTER_ID=" + urls[3]
                    + "&P_SOB_ID=" + urls[4]
                    + "&P_ORG_ID=" + urls[5]
                    + "&P_WORKING_START_DATE=" + urls[6]
                    + "&P_WORKING_END_DATE=" + urls[7]
                    + "&P_TANK_LCODE=" + urls[8]
                    + "&P_EQUIPMENT_ID=" + urls[9]
                    + "&P_WORKER_ID=" + urls[10]
                    + "&P_USER_ID=" + urls[11]
                    ;

            try
            {
                //String ip = context.getApplicationContext().getResources().getString(R.string.ip);

                URL obj = new URL("http://" + urls[0] + "/TAIYO/TpUpdate.jsp"); //주소 지정

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

                    Toast.makeText(getApplicationContext(), "저장이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    // ClearView();

                    FILE_NO_TP_SCAN fILE_NO_TP_SCAN = new FILE_NO_TP_SCAN();
                    fILE_NO_TP_SCAN.execute(strIp, strSobId,strOrgId ,etT7FileNoScan.getText().toString(),etT7WorkcenterId.getText().toString()); //다시 fill

                    btnt7save.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.dark_green));
                    btnt7save.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));


                }else{
                    Toast.makeText(getApplicationContext(), "오류입니다." +result, Toast.LENGTH_SHORT).show();
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



}

