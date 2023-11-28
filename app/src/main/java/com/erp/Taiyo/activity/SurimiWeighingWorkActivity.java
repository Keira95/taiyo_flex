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
import com.erp.Taiyo.Dialog.LuOillerDialog;
import com.erp.Taiyo.Dialog.LuYuRecntActualDialog;
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

public class SurimiWeighingWorkActivity extends AppCompatActivity {

    Toolbar toolbar;
    String strIp, strUserName, strUserId, SDate, strMenuDesc;
    String filter = null;
    String strDate;

    EditText t3FileNo ,t3ItemDesc ,t3OperaionDesc ,t3LastSurimiCount ,t3Surimi1StartTime ,t3TankScan,t3OillerDesc
    ,t3LiqidPersonDesc,t3Suimi1EndTime, t3Surimi2StartTime, t3Surimi2EndTime, t3LastTankScan ,t3WorkcenterCode, t3WorkcenterDesc, t3WorkcenterId , t3Surimi1EndTime
    ,t3EquimentId, t3OillerId , t3IndicatorUserId , t3TankCode , t3JobId ,t3ModFlag , t3OperationId ,t3OldEquimentName ,t3EquimentName,  t3EquimentCode ,t3OillerCode,
            t3LiqidPersonId      ,t3LastTankCode, t3LookupEntryId;
    String strSobId = "70";
    String strOrgId = "701";
    String strAssembly = "PPMF2203";


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

    Button btnt1save;
    Button btnRecent ,btnLastSurimiCount ,btnSurimi1StartTime ,btnOillerLookup,btnSurimi1EndTime ,btnSurimi2StartTime, btnSurimi2EndTime, btnSave, btnHolding;
    //키보드

    InputMethodManager imm;
    JSONObject jspSumObject = new JSONObject();

    private boolean ScanModify = true;
    private boolean Mod_Flag = true;


    private String FileNoScan = "";
    private String TankDesc = "";
    private String LuYuEQpDesc = "";
    private String Worker = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_surimi_work3);
        Intent intent = getIntent();
        strIp = intent.getStringExtra("Ip");
        strUserId = intent.getStringExtra("O_USER_ID");
        strUserName = intent.getStringExtra("O_USER_NAME");
        strMenuDesc = intent.getStringExtra("TOP_MENU_DESC");


        mainLayout = findViewById(R.id.main_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvUserName = (TextView) findViewById(R.id.user_nm);
        tvMenu = (TextView) findViewById(R.id.tv_menu);


        btnt1save = (Button) findViewById(R.id.btn_t1_save);
        t3FileNo  = (EditText) findViewById(R.id.et_t3_file_no);
        t3ItemDesc  = (EditText) findViewById(R.id.et_t3_item_desc);
        t3OperaionDesc  = (EditText) findViewById(R.id.et_t3_operaion_desc);
        t3LastSurimiCount  = (EditText) findViewById(R.id.et_t3_last_surimi_count);
        t3Surimi1StartTime  = (EditText) findViewById(R.id.et_t3_surimi1_start_time);
        t3TankScan = (EditText) findViewById(R.id.et_t3_tank_scan);
        t3OillerDesc = (EditText) findViewById(R.id.et_t3_oiller_desc);
        t3LiqidPersonDesc = (EditText) findViewById(R.id.et_t3_liqid_person_desc);
        t3Surimi1EndTime = (EditText) findViewById(R.id.et_t3_surimi1_end_time);
        t3Surimi2StartTime = (EditText) findViewById(R.id.et_t3_surimi2_start_time);
        t3Surimi2EndTime = (EditText) findViewById(R.id.et_t3_surimi2_end_time);
        t3LastTankScan = (EditText) findViewById(R.id.et_t3_last_tank_scan);
        t3WorkcenterCode = (EditText) findViewById(R.id.et_t3_workcenter_code);
        t3WorkcenterDesc = (EditText) findViewById(R.id.et_t3_workcenter_desc);
        t3WorkcenterId  = (EditText) findViewById(R.id.et_t3_workcenter_id);
        t3LiqidPersonId = (EditText) findViewById(R.id.et_t3_liqid_person_id);
        t3EquimentId  = (EditText) findViewById(R.id.et_t3_equiment_id);

        t3OillerId  = (EditText) findViewById(R.id.et_t3_oiller_id);
        t3IndicatorUserId  = (EditText) findViewById(R.id.et_t3_indicator_user_id);
        t3TankCode  = (EditText) findViewById(R.id.et_t3_tank_code);
        t3JobId  = (EditText) findViewById(R.id.et_t3_job_id);
        t3ModFlag = (EditText) findViewById(R.id.et_t3_mod_flag);
        t3OperationId= (EditText) findViewById(R.id.et_t3_operation_id);

        t3OldEquimentName= (EditText) findViewById(R.id.et_t3_old_equiment_name);
        t3EquimentName= (EditText) findViewById(R.id.et_t3_equiment_name);
        t3EquimentCode= (EditText) findViewById(R.id.et_t3_equiment_code);
        t3OillerCode =(EditText) findViewById(R.id.et_t3_oiller_code);
        t3LastTankCode= (EditText) findViewById(R.id.et_t3_last_tank_code);
        t3LookupEntryId= (EditText) findViewById(R.id.et_t3_lookup_entry_id);




        auto = getSharedPreferences("appData_Log", Context.MODE_PRIVATE);
        //키보드 내리기

        //키보드
       // imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        btnRecent = (Button) findViewById(R.id.btn_t2_recent);
        btnLastSurimiCount = (Button) findViewById(R.id.btn_t3_last_surimi_count);
        btnSurimi1StartTime = (Button) findViewById(R.id.btn_t3_surimi1_start_time);
        btnOillerLookup= (Button) findViewById(R.id.btn_t3_oiller_lookup);
        btnSurimi1EndTime = (Button) findViewById(R.id.btn_t3_surimi1_end_time);
        btnSurimi2StartTime = (Button) findViewById(R.id.btn_t3_surimi2_start_time);
        btnSurimi2EndTime= (Button) findViewById(R.id.btn_t3_surimi2_end_time);
        btnSave= (Button) findViewById(R.id.btn_t3_save);
        btnHolding = (Button) findViewById(R.id.btn_t3_holding);



        t3FileNo.requestFocus();


        initializeToolbar();


        //먼저 실행되는 함수
        GET_WORKCENTER_IN_AUTHORITY gET_WORKCENTER_IN_AUTHORITY = new GET_WORKCENTER_IN_AUTHORITY();
        gET_WORKCENTER_IN_AUTHORITY.execute(strIp, strSobId,strOrgId ,strUserId, strAssembly);

        //키보드 내리고 포커스 주기
        keyboardFocus(t3FileNo);
        keyboardFocus(t3TankScan);
        keyboardFocus(t3LiqidPersonDesc);
        keyboardFocus(t3LastTankScan);


    }
    //키보드 내리고 포커스 주는 함수
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



    private String getNowDate(){

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss" ,Locale.KOREAN);

        String getTime = sdf.format(date);

        return getTime;
    }

    //저장버튼 색 바꾸는 함수
    public void saveColorChange() {
        if (ScanModify==false) {
            btnSave.setBackgroundColor(Color.YELLOW);
            btnSave.setTextColor(Color.BLACK);
        }
    }

    private void ClearView(){
        t3FileNo.setText("");
        t3ItemDesc.setText("");
        t3OperaionDesc.setText("");
        t3LastSurimiCount.setText("");
        t3Surimi1StartTime.setText("");
        t3TankScan.setText("");
        t3OillerDesc.setText("");
        t3LiqidPersonDesc .setText("");
        t3Surimi1EndTime .setText("");
        t3Surimi2StartTime.setText("");
        t3Surimi2EndTime.setText("");
        t3EquimentId.setText("");
        t3OillerId .setText("");
        t3IndicatorUserId .setText("");
        t3TankCode.setText("");
        t3JobId.setText("");
        t3ModFlag.setText("");
        t3OperationId.setText("");
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



        t3FileNo.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {

                if(getCurrentFocus() == t3FileNo && !s.toString().isEmpty() && s != null && t3JobId.getText().toString().equals("")){
                    FILE_NO_YU_SCAN fILE_NO_SCAN = new FILE_NO_YU_SCAN();
                    fILE_NO_SCAN.execute(strIp, strSobId,strOrgId ,t3FileNo.getText().toString(),t3WorkcenterId.getText().toString());

                }else{
                    return;
                }

            }
        });

        t3LiqidPersonDesc.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(getCurrentFocus() == t3LiqidPersonDesc && !s.toString().isEmpty() && s != null && t3IndicatorUserId.getText().toString().equals("")){

                    LU_WORKER lu_worker = new LU_WORKER();
                    lu_worker.execute(strIp, strSobId,strOrgId, t3WorkcenterId.getText().toString(), t3LiqidPersonDesc.getText().toString());

                }else{
                    return;
                }
            }
        });



        t3TankScan.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(getCurrentFocus() == t3TankScan && !s.toString().isEmpty() && s != null && t3EquimentId.getText().toString().equals("")){

                    LU_YU_EQP lU_YU_EQP = new LU_YU_EQP();
                    lU_YU_EQP.execute(strIp, strSobId,strOrgId, t3WorkcenterId.getText().toString(),t3TankScan.getText().toString());

                }else{
                    return;
                }
            }
        });

        t3LastTankScan.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(getCurrentFocus() == t3LastTankScan && !s.toString().isEmpty() && s != null && t3LastTankCode.getText().toString().equals("")){

                  LU_TANK_TYPE lU_TANK_TYPE = new LU_TANK_TYPE();
                  lU_TANK_TYPE.execute(strIp, strSobId,strOrgId, t3LastTankScan.getText().toString());


                }else{
                    return;
                }
            }
        });

        btnHolding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(t3FileNo.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "File No는 필수입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                HoldingDialog holdingDialog = new HoldingDialog(SurimiWeighingWorkActivity.this);
                holdingDialog.call_Level_Dialog(t3FileNo, strIp , strUserId, t3OperationId,t3JobId);
            }
        });

    btnOillerLookup.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            LuOillerDialog luOillerDialog = new LuOillerDialog(SurimiWeighingWorkActivity.this);
            luOillerDialog.call_Oiller(strIp ,t3OillerCode, t3OillerDesc ,t3OillerId);

        }
    });
        btnRecent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LuYuRecntActualDialog luYuRecntActualDialog = new LuYuRecntActualDialog(SurimiWeighingWorkActivity.this);
                luYuRecntActualDialog.call_YuRecent(strIp ,t3FileNo);
            }
        });

        btnLastSurimiCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YU_ADD yU_ADD = new YU_ADD();
                yU_ADD.execute(strIp, t3JobId.getText().toString() ,t3OperationId.getText().toString() , t3WorkcenterId.getText().toString(),
                        strSobId, strOrgId,strUserId);
            }
        });

        btnSurimi1StartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ScanModify==false){
                    btnSave.setBackgroundColor(Color.YELLOW);
                    btnSave.setTextColor(Color.BLACK);
                }

                t3Surimi1StartTime.setText(getNowDate());
            }
        });

        btnSurimi1EndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ScanModify==false){
                    btnSave.setBackgroundColor(Color.YELLOW);
                    btnSave.setTextColor(Color.BLACK);
                }

                t3Surimi1EndTime.setText(getNowDate());
            }
        });

        btnSurimi2StartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ScanModify==false){
                    btnSave.setBackgroundColor(Color.YELLOW);
                    btnSave.setTextColor(Color.BLACK);
                }

                t3Surimi2StartTime.setText(getNowDate());
            }
        });

        btnSurimi2EndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ScanModify==false){
                    btnSave.setBackgroundColor(Color.YELLOW);
                    btnSave.setTextColor(Color.BLACK);
                }

                t3Surimi2EndTime.setText(getNowDate());
            }
        });

    btnSave.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            AlertDialog.Builder alert = new AlertDialog.Builder(SurimiWeighingWorkActivity.this);
            alert.setTitle("저장");
            alert.setMessage("수정한 내역을 저장하시겠습니까?");
            alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if(t3FileNo.getText().toString().isEmpty()){
                        Toast.makeText(getApplicationContext(), "File No는 필수입니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    YU_UPDATE yU_UPDATE = new YU_UPDATE();
                    yU_UPDATE.execute(strIp,t3JobId.getText().toString(),t3OperationId.getText().toString(),t3WorkcenterId.getText().toString(),strSobId ,strOrgId ,t3JobId.getText().toString(),
                            t3LastSurimiCount.getText().toString(), t3EquimentId.getText().toString(), t3OillerId.getText().toString() ,t3Surimi1StartTime.getText().toString().replaceAll(" ",""),
                            t3Surimi1EndTime.getText().toString().replaceAll(" ",""), t3Surimi2StartTime.getText().toString().replaceAll(" ",""),  t3Surimi2EndTime.getText().toString().replaceAll(" ","") ,
                            strUserId , t3IndicatorUserId.getText().toString(),t3LastTankCode.getText().toString()
                    );


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

        t3FileNo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClearView();
                return false;
            }
        });

        t3TankScan.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                t3TankScan.setText("");
                t3EquimentId.setText("");
                return false;
            }
        });

        t3LiqidPersonDesc.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                t3LiqidPersonDesc.setText("");
                t3IndicatorUserId.setText("");
                return false;
            }
        });
        t3LastTankScan.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                t3LastTankScan.setText("");
                t3LastTankCode.setText("");
                return false;
            }
        });
        t3OillerDesc.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                t3OillerDesc.setText("");
                return false;
            }
        });
        t3Surimi1EndTime.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                t3Surimi1EndTime.setText("");
                return false;
            }
        });
        t3Surimi2StartTime.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                t3Surimi2StartTime.setText("");
                return false;
            }
        });
        t3Surimi2EndTime.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                t3Surimi2EndTime.setText("");
                return false;
            }
        });


    }


    // WORKCENTER_IN_AUTHORITY
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
                    t3WorkcenterCode.setText(job.getString("X_WORKCENTER_CODE"));
                    t3WorkcenterDesc.setText(job.getString("X_WORKCENTER_DESC"));
                    t3WorkcenterId.setText(job.getString("X_WORKCENTER_ID"));
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
    protected class FILE_NO_YU_SCAN extends AsyncTask<String, Void, String>
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
            {  URL obj = new URL("http://" + urls[0] + "/TAIYO/FileNoScanYU.jsp"); //주소 지정

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
                t3TankScan.requestFocus();
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
                    if(!FileNoScan.equals(t3FileNo.getText().toString())){
                        ScanModify = false;
                        t3FileNo.requestFocus();
                        t3FileNo.setText("");
                    }


                    return;
                }else{
                    JSONObject job = jarrayWorkLevel.getJSONObject(0);
                    if(job.getString("Status").equals("S")){

                        t3FileNo.setText(job.getString("WORK_ORDER_NO"));
                        t3ItemDesc.setText(job.getString("ITEM_DESCRIPTION"));
                        t3OperaionDesc.setText(job.getString("OPERATION_DESCRIPTION"));

                        if(job.getString("LINE_NO").equals("null")){
                            t3LastSurimiCount.setText("");
                        }else{
                            t3LastSurimiCount.setText(job.getString("LINE_NO"));
                        }
                        if(job.getString("WORKING_START_DATE").equals("null")){
                            t3Surimi1StartTime.setText("");
                        }else{
                            t3Surimi1StartTime.setText(job.getString("WORKING_START_DATE"));
                        }
                        if(job.getString("TANK_DESC").equals("null")){
                            t3LastTankScan.setText("");
                        }else{
                            t3LastTankScan.setText(job.getString("TANK_DESC"));
                        }
                        if(job.getString("OILLER_DESC").equals("null")){
                            t3OillerDesc.setText("");
                        }else{
                            t3OillerDesc.setText(job.getString("OILLER_DESC"));
                        }

                        if(job.getString("USER_DESC").equals("null")){
                            t3LiqidPersonDesc.setText("");
                        }else{
                            t3LiqidPersonDesc.setText(job.getString("USER_DESC"));
                        }
                        if(job.getString("WORKING_END_DATE").equals("null")){
                            t3Surimi1EndTime.setText("");
                        }else{
                            t3Surimi1EndTime.setText(job.getString("WORKING_END_DATE"));
                        }
                        if(job.getString("WORKING_START_DATE2").equals("null")){
                            t3Surimi2StartTime.setText("");
                        }else{
                            t3Surimi2StartTime.setText(job.getString("WORKING_START_DATE2"));
                        }
                        if(job.getString("WORKING_END_DATE2").equals("null")){
                            t3Surimi2EndTime.setText("");
                        }else{
                            t3Surimi2EndTime.setText(job.getString("WORKING_END_DATE2"));
                        }

                        //  t3LastTankScan.setText(job.getString("POWDER_WORKER_ID"));

                        if(job.getString("EQUIPMENT_ID").equals("null")){
                            t3EquimentId.setText("");
                        }else{
                            t3EquimentId.setText(job.getString("EQUIPMENT_ID"));
                        }

                        if(job.getString("OILLER_ID").equals("null")){
                            t3OillerId.setText("");
                        }else{
                            t3OillerId.setText(job.getString("OILLER_ID"));
                        }
                        if(job.getString("EQUIPMENT_NAME").equals("null")){
                            t3TankScan.setText("");
                        }else{
                            t3TankScan.setText(job.getString("EQUIPMENT_NAME"));
                        }

                        if(job.getString("INDICATOR_USER_ID").equals("null")){
                            t3IndicatorUserId.setText("");
                        }else{
                            t3IndicatorUserId.setText(job.getString("INDICATOR_USER_ID"));
                        }


                        t3LastTankCode.setText(job.getString("TANK_CODE"));
                        t3JobId.setText(job.getString("JOB_ID"));
                        t3ModFlag.setText(job.getString("MOD_FLAG"));

                        t3OperationId.setText(job.getString("OPERATION_ID"));

                        FileNoScan = job.getString("WORK_ORDER_NO");

                    }
                }



                t3TankScan.requestFocus();

                ScanModify = false;

                if(t3ModFlag.equals("N")){
                    t3FileNo.setInputType(InputType.TYPE_NULL);
                    t3ItemDesc.setInputType(InputType.TYPE_NULL);
                    t3OperaionDesc.setInputType(InputType.TYPE_NULL);
                    t3LastSurimiCount.setInputType(InputType.TYPE_NULL);
                    t3Surimi1StartTime.setInputType(InputType.TYPE_NULL);
                    t3TankScan.setInputType(InputType.TYPE_NULL);
                    t3OillerDesc.setInputType(InputType.TYPE_NULL);
                    t3LiqidPersonDesc.setInputType(InputType.TYPE_NULL);
                    t3Surimi1EndTime.setInputType(InputType.TYPE_NULL);
                    t3Surimi2StartTime.setInputType(InputType.TYPE_NULL);
                    t3Surimi2EndTime.setInputType(InputType.TYPE_NULL);
                    t3EquimentId.setInputType(InputType.TYPE_NULL);
                    t3OillerId.setInputType(InputType.TYPE_NULL);
                    t3IndicatorUserId.setInputType(InputType.TYPE_NULL);
                    t3TankCode.setInputType(InputType.TYPE_NULL);
                    t3JobId.setInputType(InputType.TYPE_NULL);
                    t3ModFlag.setInputType(InputType.TYPE_NULL);
                    t3OperationId.setInputType(InputType.TYPE_NULL);

                }

            }catch (JSONException e)
            {
                e.printStackTrace();
                t3TankScan.requestFocus();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                t3TankScan.requestFocus();
            }
        }
    }


    // LU_TANK_TYPE
    protected class LU_YU_EQP extends AsyncTask<String, Void, String>
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
            {  URL obj = new URL("http://" + urls[0] + "/TAIYO/LuYuEqp.jsp"); //주소 지정

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
                t3LiqidPersonDesc.requestFocus();
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
                    if(!LuYuEQpDesc.equals(t3TankScan.getText().toString())){
                        t3TankScan.requestFocus();
                        t3TankScan.setText("");
                    }
                    return;

                }else{
                    JSONObject job = jarrayWorkLevel.getJSONObject(0);
                    if(job.getString("Status").equals("S")){

                        t3OldEquimentName.setText(job.getString("OLD_EQUIPMENT_NAME"));
                        t3TankScan.setText(job.getString("TOP_EQUIPMENT_NAME"));
                        LuYuEQpDesc = job.getString("TOP_EQUIPMENT_NAME");
                        t3EquimentCode.setText(job.getString("TOP_EQUIPMENT_CODE"));
                        t3EquimentId.setText(job.getString("TOP_EQUIPMENT_ID"));

                        saveColorChange();
                }
                    t3LiqidPersonDesc.requestFocus();

                }


            }catch (JSONException e)
            {
                e.printStackTrace();
                t3LiqidPersonDesc.requestFocus();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                t3LiqidPersonDesc.requestFocus();
            }
        }
    }
    // LU_WORKER
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
                    if(!Worker.equals(t3LiqidPersonDesc.getText().toString())){
                        t3LiqidPersonDesc.requestFocus();
                        t3LiqidPersonDesc.setText("");

                    }

                    return;
                }else{
                    JSONObject job = jarrayWorkLevel.getJSONObject(0);
                    if(job.getString("Status").equals("S")){

                        t3IndicatorUserId.setText(job.getString("USER_ID"));
                        t3LiqidPersonDesc.setText(job.getString("DESCRIPTION"));
                        Worker = job.getString("DESCRIPTION");

                        saveColorChange();
                }
                    t3LastTankScan.requestFocus();

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

    // LU_TANK_TYPE SURIMI
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
                    if(!TankDesc.equals(t3LastTankScan.getText().toString())){
                        t3LastTankScan.requestFocus();
                        t3LastTankScan.setText("");
                    }
                    return;
                }else{
                    JSONObject job = jarrayWorkLevel.getJSONObject(0);
                    if(job.getString("Status").equals("S")){

                        t3LookupEntryId.setText(job.getString("LOOKUP_ENTRY_ID"));
                        t3LastTankCode.setText(job.getString("ENTRY_CODE"));
                        t3LastTankScan.setText(job.getString("ENTRY_DESCRIPTION"));
                        TankDesc = job.getString("ENTRY_DESCRIPTION");

                        saveColorChange();
                    }
                    t3LastTankScan.requestFocus();
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

    protected class YU_ADD extends AsyncTask<String, Void, String>
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
                    + "&P_USER_ID=" + urls[6]
                    ;

            try
            {
                //String ip = context.getApplicationContext().getResources().getString(R.string.ip);

                URL obj = new URL("http://" + urls[0] + "/TAIYO/YuAdd.jsp"); //주소 지정

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

                    Toast.makeText(getApplicationContext(), "연육횟수가 추가되었습니다.", Toast.LENGTH_SHORT).show();
                    // ClearView();

                    FILE_NO_YU_SCAN fILE_NO_SCAN = new FILE_NO_YU_SCAN();
                    fILE_NO_SCAN.execute(strIp, strSobId,strOrgId ,t3FileNo.getText().toString(),t3WorkcenterId.getText().toString());



                }else{
                    Toast.makeText(getApplicationContext(), "오류입니다."+ result, Toast.LENGTH_SHORT).show();
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

    protected class YU_UPDATE extends AsyncTask<String, Void, String>
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
                    + "&P_JOB_ID=" + urls[6]
                    + "&P_LINE_NO=" + urls[7]
                    + "&P_EQUIPMENT_ID=" + urls[8]
                    + "&P_OILLER_ID=" + urls[9]
                    + "&P_WORKING_START_DATE=" + urls[10]
                    + "&P_WORKING_END_DATE=" + urls[11]
                    + "&P_WORKING_START_DATE2=" + urls[12]
                    + "&P_WORKING_END_DATE2=" + urls[13]
                    + "&P_USER_ID=" + urls[14]
                    + "&P_INDICATOR_USER_ID=" + urls[15]
                    + "&P_TANK_CODE=" + urls[16]
                    ;

            try
            {
                //String ip = context.getApplicationContext().getResources().getString(R.string.ip);

                URL obj = new URL("http://" + urls[0] + "/TAIYO/YuUpdate.jsp"); //주소 지정

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

                    FILE_NO_YU_SCAN fILE_NO_SCAN = new FILE_NO_YU_SCAN();
                    fILE_NO_SCAN.execute(strIp, strSobId,strOrgId ,t3FileNo.getText().toString(),t3WorkcenterId.getText().toString()); //다시 fill

                    btnSave.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.dark_green));
                    btnSave.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));

                }else{
                    Toast.makeText(getApplicationContext(), "오류입니다.", Toast.LENGTH_SHORT).show();
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


