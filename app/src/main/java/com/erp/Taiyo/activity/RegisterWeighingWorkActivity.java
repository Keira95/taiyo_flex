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


import com.erp.Taiyo.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

public class RegisterWeighingWorkActivity extends AppCompatActivity{

    Toolbar toolbar;
    String strIp, strUserName, strUserId, SDate, strMenuDesc;
    String filter = null;
    String strDate;

    EditText t1FileNo,t1ItemDesc ,t1OperaionDesc,t1TankScan,t1LiqidPersonDesc ,t1RiqidStartTime , t1RiqidEndTime,t1PowderStartTime ,t1PowderEndTime ,t1WorkEndTime
            ,t1WorkcenterCode, t1WorkcenterDesc ,t1WorkcenterId, t1WorkStartTiem ,t1TankLcode ,t1LiqidPersonId, t1PowderPersonId , t1Job_id ,t1OprationId ,t1PowderPersonDesc, t1ModeFlag;
    String strSobId = "70";
    String strOrgId = "701";
    String strAssembly ="PPMF2201";


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
    Button btnWorkStartTiem,btnRiqidStartTime, btnRiqidEndTime,btnPowderStartTime,btnPowderEndTime, btnWorkEndTime, btnHolding ;
    //키보드

    InputMethodManager imm;
    JSONObject jspSumObject = new JSONObject();

    private boolean ScanModify = true;
    private boolean Mod_Flag = true;
    private String TankDesc = "";
    private String RiqidWorkerDesc = "";
    private String PowderPersonDesc = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_weighing_work1);
        Intent intent = getIntent();
        strIp = intent.getStringExtra("Ip");
        strUserId = intent.getStringExtra("O_USER_ID");
        strUserName = intent.getStringExtra("O_USER_NAME");
        strMenuDesc = intent.getStringExtra("TOP_MENU_DESC");


        mainLayout = findViewById(R.id.main_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvUserName = (TextView) findViewById(R.id.user_nm);
        tvMenu = (TextView) findViewById(R.id.tv_menu);

        t1FileNo  = (EditText) findViewById(R.id.et_t1_file_no);
        t1ItemDesc = (EditText) findViewById(R.id.et_t1_item_desc);
        t1OperaionDesc = (EditText) findViewById(R.id.et_t1_operaion_desc);
        t1TankScan = (EditText) findViewById(R.id.et_t1_tank_scan);
        t1LiqidPersonDesc  = (EditText) findViewById(R.id.et_t1_liqid_person_desc);
        t1RiqidStartTime  = (EditText) findViewById(R.id.et_t1_riqid_start_time);
        t1RiqidEndTime = (EditText) findViewById(R.id.et_t1_riqid_end_time);
        t1PowderStartTime  = (EditText) findViewById(R.id.et_t1_powder_start_time);
        t1PowderEndTime  = (EditText) findViewById(R.id.et_t1_powder_end_time);
        t1WorkEndTime = (EditText) findViewById(R.id.et_t1_work_end_time);
        t1WorkcenterCode = (EditText) findViewById(R.id.et_t1_workcenter_code);
        t1WorkcenterDesc = (EditText) findViewById(R.id.et_t1_workcenter_desc);
        t1WorkcenterId = (EditText) findViewById(R.id.et_t1_workcenter_id);
        t1WorkcenterId = (EditText) findViewById(R.id.et_t1_workcenter_id);
        t1PowderPersonDesc = (EditText) findViewById(R.id.et_t1_powder_person_desc);
        t1WorkStartTiem = (EditText) findViewById(R.id.et_t1_work_start_tiem);

        t1TankLcode =(EditText) findViewById(R.id.et_t1_tank_lcode);
        t1LiqidPersonId =(EditText) findViewById(R.id.et_t1_liqid_person_id);
        t1PowderPersonId =(EditText) findViewById(R.id.et_t1_powder_person_id);
        t1Job_id =(EditText) findViewById(R.id.et_t1_job_id);
        t1OprationId =(EditText) findViewById(R.id.et_t1_opration_id);
        t1ModeFlag =(EditText) findViewById(R.id.et_t1_mode_flag);


        btnWorkStartTiem = (Button) findViewById(R.id.btn_t1_work_start_tiem);
        btnRiqidStartTime = (Button) findViewById(R.id.btn_t1_riqid_start_time);
        btnRiqidEndTime = (Button) findViewById(R.id.btn_t1_riqid_end_time);
        btnPowderStartTime = (Button) findViewById(R.id.btn_t1_powder_start_time);
        btnPowderEndTime = (Button) findViewById(R.id.btn_t1_powder_end_time);
        btnWorkEndTime = (Button) findViewById(R.id.btn_t1_work_end_time);
        btnHolding = (Button) findViewById(R.id.btn_t1_holding);


        btnt1save  = (Button) findViewById(R.id.btn_t1_save);
        t1FileNo.requestFocus(); //포커스 자동 스캔으로

        auto = getSharedPreferences("appData_Log", Context.MODE_PRIVATE);
        //키보드 내리기

        //키보드
        imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

        //먼저 실행되는 함수
        GET_WORKCENTER_IN_AUTHORITY gET_WORKCENTER_IN_AUTHORITY = new GET_WORKCENTER_IN_AUTHORITY();
        gET_WORKCENTER_IN_AUTHORITY.execute(strIp, strSobId,strOrgId ,strUserId, strAssembly);

        initializeToolbar();

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(t1FileNo.getWindowToken(), 0);

        keyboardFocus(t1FileNo);
        keyboardFocus(t1TankScan);
        keyboardFocus(t1LiqidPersonDesc);
        keyboardFocus(t1PowderPersonDesc);


        //file no scan
        t1FileNo.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {

                if(getCurrentFocus() == t1FileNo && !s.toString().isEmpty()){

                    FILE_NO_SCAN fILE_NO_SCAN = new FILE_NO_SCAN();
                    fILE_NO_SCAN.execute(strIp, strSobId,strOrgId ,t1FileNo.getText().toString(),t1WorkcenterId.getText().toString());

                }else{
                    return;
                }

            }
        });
        //tank scan
        t1TankScan.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                //사용자 스캔또는 터치 포커스              //값이 널이아니고           // locde가 null이어야만한다
                if(getCurrentFocus() == t1TankScan && !s.toString().equals("") && t1TankLcode.getText().toString().equals("")) {

                    LU_TANK_TYPE lU_TANK_TYPE = new LU_TANK_TYPE();
                    lU_TANK_TYPE.execute(strIp, strSobId, strOrgId, t1TankScan.getText().toString());

                }else{
                    return;
                }

            }
        });

        //액상작업자
        t1LiqidPersonDesc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(ScanModify==false && getCurrentFocus() == t1LiqidPersonDesc){
                    //  btnt1save.setBackgroundColor(Color.YELLOW);
                    // btnt1save.setTextColor(Color.BLACK);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                if(getCurrentFocus() == t1LiqidPersonDesc && !s.toString().isEmpty() && s != null && t1LiqidPersonId.getText().toString().equals("")){

                    LU_RIQID_WORKER lU_RIQID_WORKER = new LU_RIQID_WORKER();
                    lU_RIQID_WORKER.execute(strIp, strSobId,strOrgId, t1WorkcenterId.getText().toString(), t1LiqidPersonDesc.getText().toString());

                }else{
                    return;

                }
            }
        });

        //분체작업자
        t1PowderPersonDesc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(ScanModify==false && getCurrentFocus() == t1PowderPersonDesc){
                    // btnt1save.setBackgroundColor(Color.YELLOW);
                    //btnt1save.setTextColor(Color.BLACK);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                if(getCurrentFocus() == t1PowderPersonDesc && !s.toString().isEmpty() && t1PowderPersonId.getText().toString().equals("")){

                    LU_POWEDR_WORKER lU_POWEDR_WORKER = new LU_POWEDR_WORKER();
                    lU_POWEDR_WORKER.execute(strIp, strSobId,strOrgId, t1WorkcenterId.getText().toString(), t1PowderPersonDesc.getText().toString());

                }else{
                    return;

                }
            }
        });


   /*    p3BoxScan.setInputType(0);
        p3ItemCode.setInputType(0);
        p3FgWarDesc.setInputType(0);
*/

        //계량 작업시작
        btnWorkStartTiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ScanModify==false){
                    btnt1save.setBackgroundColor(Color.YELLOW);
                    btnt1save.setTextColor(Color.BLACK);
                }

                t1WorkStartTiem.setText(getNowDate());
            }
        });

        // 액상작업시작
        btnRiqidStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ScanModify==false){
                    btnt1save.setBackgroundColor(Color.YELLOW);
                    btnt1save.setTextColor(Color.BLACK);
                }
                t1RiqidStartTime.setText(getNowDate());
            }
        });

        //액상 작업종료
        btnRiqidEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ScanModify==false){
                    btnt1save.setBackgroundColor(Color.YELLOW);
                    btnt1save.setTextColor(Color.BLACK);
                }

                t1RiqidEndTime.setText(getNowDate());
            }
        });

        //분체작업시작
        btnPowderStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ScanModify==false){
                    btnt1save.setBackgroundColor(Color.YELLOW);
                    btnt1save.setTextColor(Color.BLACK);
                }

                t1PowderStartTime.setText(getNowDate());
            }
        });
        //분체작업종료

        btnPowderEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ScanModify==false){
                    btnt1save.setBackgroundColor(Color.YELLOW);
                    btnt1save.setTextColor(Color.BLACK);
                }
                t1PowderEndTime.setText(getNowDate());
            }
        });
        //계량작업종료
        btnWorkEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ScanModify==false){
                    btnt1save.setBackgroundColor(Color.YELLOW);
                    btnt1save.setTextColor(Color.BLACK);
                }

                t1WorkEndTime.setText(getNowDate());
            }
        });


        t1FileNo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClearView();
                return false;
            }
        });

        btnt1save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(RegisterWeighingWorkActivity.this);
                alert.setTitle("저장");
                alert.setMessage("수정한 내역을 저장하시겠습니까?");
                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(t1FileNo.getText().toString().isEmpty()){
                            Toast.makeText(getApplicationContext(), "File No는 필수입니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        GR_UPDATE gr_update = new GR_UPDATE();
                        gr_update.execute(strIp,t1Job_id.getText().toString(),t1OprationId.getText().toString(),t1WorkcenterId.getText().toString(),strSobId ,strOrgId ,t1WorkStartTiem.getText().toString().replaceAll(" ","")
                                , t1WorkEndTime.getText().toString().replaceAll(" ",""),t1TankLcode.getText().toString(),t1LiqidPersonId.getText().toString() ,t1PowderPersonId.getText().toString() , strUserId
                                , t1RiqidStartTime.getText().toString().replaceAll(" ",""), t1RiqidEndTime.getText().toString().replaceAll(" ","") , t1PowderStartTime.getText().toString().replaceAll(" ","")
                                ,t1PowderEndTime.getText().toString().replaceAll(" ",""));


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

        t1TankScan.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                t1TankScan.setText("");
                t1TankLcode.setText("");
                return false;
            }
        });

        t1LiqidPersonDesc.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                t1LiqidPersonDesc.setText("");
                t1LiqidPersonId.setText("");
                return false;
            }
        });

        t1PowderPersonDesc.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                t1PowderPersonDesc.setText("");
                t1PowderPersonId.setText("");
                return false;
            }
        });


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

        //Calendar 클래스의 getTime()함수 사용
        Calendar calendar = Calendar.getInstance();
        String time2 = sdf.format(calendar.getTime());

        //System 클래스의 currentTimeMillis()함수 사용
        String time3 = sdf.format(System.currentTimeMillis());


        return time3;
    }

    //저장버튼 색 바꾸는 함수
    private void saveColorChange() {
        if (ScanModify==false) {
            btnt1save.setBackgroundColor(Color.YELLOW);
            btnt1save.setTextColor(Color.BLACK);
        }
    }

    private void ClearView(){
        t1FileNo.setText("");
        t1ItemDesc.setText("");
        t1OperaionDesc.setText("");
        t1WorkStartTiem.setText("");
        t1TankLcode.setText("");
        t1TankScan.setText("");
        t1LiqidPersonId.setText("");
        t1LiqidPersonDesc .setText("");
        t1RiqidStartTime .setText("");
        t1RiqidEndTime.setText("");
        t1PowderPersonId.setText("");
        t1PowderPersonDesc.setText("");
        t1PowderStartTime .setText("");
        t1PowderEndTime .setText("");
        t1WorkEndTime.setText("");
        t1Job_id.setText("");
        t1OprationId.setText("");
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
                    t1WorkcenterCode.setText(job.getString("X_WORKCENTER_CODE"));
                    t1WorkcenterDesc.setText(job.getString("X_WORKCENTER_DESC"));
                    t1WorkcenterId.setText(job.getString("X_WORKCENTER_ID"));
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
    protected class FILE_NO_SCAN extends AsyncTask<String, Void, String>
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
            {  URL obj = new URL("http://" + urls[0] + "/TAIYO/FileNoScanGR.jsp"); //주소 지정

                HttpURLConnection conn = (HttpURLConnection)obj.openConnection(); //지정된 주소로 연결

                if(conn != null)
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
                    // Toast.makeText(getApplicationContext(), "데이터가 없습니다.", Toast.LENGTH_SHORT).show();
                    ScanModify = false;
                    t1TankScan.requestFocus();
                    return;
                }

                JSONObject job = jarrayWorkLevel.getJSONObject(0);
                if(job.getString("Status").equals("S")){
                    t1FileNo.setText(job.getString("WORK_ORDER_NO"));
                    t1ItemDesc.setText(job.getString("ITEM_DESCRIPTION"));
                    t1OperaionDesc.setText(job.getString("OPERATION_DESCRIPTION"));
                    if(job.getString("WORK_START_DATE").equals("null")){
                        t1WorkStartTiem.setText("");
                    }else{
                        t1WorkStartTiem.setText(job.getString("WORK_START_DATE"));
                    }

                    if(job.getString("TANK_LCODE").equals("null")){
                        t1TankLcode.setText("");
                    }else{
                        t1TankLcode.setText(job.getString("TANK_LCODE"));
                    }


                    if(job.getString("TANK_DESC").equals("null")){
                        t1TankScan.setText("");
                    }else{
                        t1TankScan.setText(job.getString("TANK_DESC"));
                    }

                    if(job.getString("LIQUID_WORKER_ID").equals("null")){
                        t1LiqidPersonId.setText("");
                    }else{
                        t1LiqidPersonId.setText(job.getString("LIQUID_WORKER_ID"));
                    }


                    if(job.getString("LIQUID_WORKER_NAME").equals("null")){
                        t1LiqidPersonDesc.setText("");
                    }else{
                        t1LiqidPersonDesc .setText(job.getString("LIQUID_WORKER_NAME"));
                    }

                    if(job.getString("LIQUID_START_DATE").equals("null")){
                        t1RiqidStartTime.setText("");
                    }else{
                        t1RiqidStartTime.setText(job.getString("LIQUID_START_DATE"));

                    }
                    if(job.getString("LIQUID_END_DATE").equals("null")){
                        t1RiqidEndTime.setText("");
                    }else{
                        t1RiqidEndTime.setText(job.getString("LIQUID_END_DATE"));
                    }
                    if(job.getString("POWDER_WORKER_NAME").equals("null")){
                        t1PowderPersonDesc.setText("");
                    }else{
                        t1PowderPersonDesc.setText(job.getString("POWDER_WORKER_NAME"));
                    }
                    if(job.getString("POWDER_START_DATE").equals("null")){
                        t1PowderStartTime.setText("");
                    }else{
                        t1PowderStartTime.setText(job.getString("POWDER_START_DATE"));
                    }
                    if(job.getString("POWDER_END_DATE").equals("null")){
                        t1PowderEndTime.setText("");
                    }else{
                        t1PowderEndTime.setText(job.getString("POWDER_END_DATE"));
                    }
                    if(job.getString("WORK_END_DATE").equals("null")){
                        t1WorkEndTime.setText("");
                    }else{
                        t1WorkEndTime.setText(job.getString("WORK_END_DATE"));
                    }


                    if(job.getString("POWDER_WORKER_ID").equals("null")){
                        t1PowderPersonId.setText("");
                    }else{
                        t1PowderPersonId.setText(job.getString("POWDER_WORKER_ID"));
                    }

                    t1Job_id.setText(job.getString("JOB_ID"));
                    t1OprationId.setText(job.getString("OPERATION_ID"));
                    t1ModeFlag.setText(job.getString("MOD_FLAG"));
                }

                t1TankScan.requestFocus();

                ScanModify = false;

                if(t1ModeFlag.equals("N")){

                    t1FileNo.setInputType(InputType.TYPE_NULL);
                    t1ItemDesc.setInputType(InputType.TYPE_NULL);
                    t1OperaionDesc.setInputType(InputType.TYPE_NULL);
                    t1WorkStartTiem.setInputType(InputType.TYPE_NULL);
                    t1TankLcode.setInputType(InputType.TYPE_NULL);
                    t1TankScan.setInputType(InputType.TYPE_NULL);
                    t1LiqidPersonId.setInputType(InputType.TYPE_NULL);
                    t1LiqidPersonDesc.setInputType(InputType.TYPE_NULL);
                    t1RiqidStartTime.setInputType(InputType.TYPE_NULL);
                    t1RiqidEndTime.setInputType(InputType.TYPE_NULL);
                    t1PowderPersonId.setInputType(InputType.TYPE_NULL);
                    t1PowderPersonDesc.setInputType(InputType.TYPE_NULL);
                    t1PowderStartTime.setInputType(InputType.TYPE_NULL);
                    t1PowderEndTime.setInputType(InputType.TYPE_NULL);
                    t1WorkEndTime.setInputType(InputType.TYPE_NULL);
                    t1Job_id.setInputType(InputType.TYPE_NULL);
                    t1OprationId.setInputType(InputType.TYPE_NULL);
                    t1ModeFlag.setInputType(InputType.TYPE_NULL);

                }

            }catch (JSONException e)
            {
                e.printStackTrace();
                t1TankScan.requestFocus();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                t1TankScan.requestFocus();
            }
        }
    }


    // LU_TANK_TYPE
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
            {  URL obj = new URL("http://" + urls[0] + "/TAIYO/LuTankType.jsp"); //주소 지정

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
                t1LiqidPersonDesc.requestFocus();
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
                    if(!TankDesc.equals(t1TankScan.getText().toString())){
                        t1TankScan.requestFocus();
                        t1TankScan.setText("");

                    }
                    return;
                }else{
                    JSONObject job = jarrayWorkLevel.getJSONObject(0);
                    if(job.getString("Status").equals("S")){

                        t1TankLcode.setText(job.getString("ENTRY_CODE"));
                        t1TankScan.setText(job.getString("ENTRY_DESCRIPTION"));

                        saveColorChange();
                        TankDesc =job.getString("ENTRY_DESCRIPTION");

                 }
                    t1LiqidPersonDesc.requestFocus();
                }


            }catch (JSONException e)
            {
                e.printStackTrace();
                t1LiqidPersonDesc.requestFocus();

            }
            catch (Exception e)
            {
                e.printStackTrace();
                t1LiqidPersonDesc.requestFocus();
            }
        }
    }

    // LU_WORKER
    protected class LU_RIQID_WORKER extends AsyncTask<String, Void, String>
    {
        protected  String doInBackground(String... urls)
        {
            StringBuffer jsonHtml = new StringBuffer();

            //서버로 보낼 데이터 설정
            String search_title = "W_SOB_ID=" + urls[1]
                    + "&W_ORG_ID=" + urls[2]
                    + "&W_WORKCENTER_ID" + urls[3]
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
                t1PowderPersonDesc.requestFocus();
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
                    if(!RiqidWorkerDesc.equals(t1LiqidPersonDesc.getText().toString())){
                        t1LiqidPersonDesc.requestFocus();
                        t1LiqidPersonDesc.setText("");
                    }
                    return;

                }else{
                    JSONObject job = jarrayWorkLevel.getJSONObject(0);
                    if(job.getString("Status").equals("S")){

                        t1LiqidPersonId.setText(job.getString("USER_ID"));
                        t1LiqidPersonDesc.setText(job.getString("DESCRIPTION"));
                        RiqidWorkerDesc = job.getString("DESCRIPTION");
                        t1PowderPersonDesc.requestFocus();

                    }
                }


            }catch (JSONException e)
            {
                e.printStackTrace();
                t1PowderPersonDesc.requestFocus();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                t1PowderPersonDesc.requestFocus();
            }
        }
    }

    // LU_WORKER
    protected class LU_POWEDR_WORKER extends AsyncTask<String, Void, String>
    {
        protected  String doInBackground(String... urls)
        {
            StringBuffer jsonHtml = new StringBuffer();

            //서버로 보낼 데이터 설정
            String search_title = "W_SOB_ID=" + urls[1]
                    + "&W_ORG_ID=" + urls[2]
                    + "&W_WORKCENTER_ID" + urls[3]
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
                    if(!PowderPersonDesc.equals(t1PowderPersonDesc.getText().toString())){
                        t1PowderPersonDesc.setText("");
                        t1PowderPersonDesc.requestFocus();

                    }
                    return;
                }else{
                    JSONObject job = jarrayWorkLevel.getJSONObject(0);
                    if(job.getString("Status").equals("S")){

                        t1PowderPersonId.setText(job.getString("USER_ID"));
                        t1PowderPersonDesc.setText(job.getString("DESCRIPTION"));
                        PowderPersonDesc = job.getString("DESCRIPTION");


                    }

                }
                t1PowderPersonDesc.requestFocus();

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

    protected class GR_UPDATE extends AsyncTask<String, Void, String>
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
                    + "&P_WORK_START_DATE=" + urls[6]
                    + "&P_WORK_END_DATE=" + urls[7]
                    + "&P_TANK_LCODE=" + urls[8]
                    + "&P_LIQUID_WORKER_ID=" + urls[9]
                    + "&P_POWDER_WORKER_ID=" + urls[10]
                    + "&P_USER_ID=" + urls[11]
                    + "&P_LIQUID_START_DATE=" + urls[12]
                    + "&P_LIQUID_END_DATE=" + urls[13]
                    + "&P_POWDER_START_DATE=" + urls[14]
                    + "&P_POWDER_END_DATE=" + urls[15]
                    ;

            try
            {
                //String ip = context.getApplicationContext().getResources().getString(R.string.ip);

                URL obj = new URL("http://" + urls[0] + "/TAIYO/GrUpdate.jsp"); //주소 지정

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


                    FILE_NO_SCAN fILE_NO_SCAN = new FILE_NO_SCAN();
                    fILE_NO_SCAN.execute(strIp, strSobId,strOrgId ,t1FileNo.getText().toString(),t1WorkcenterId.getText().toString()); //다시 fill

                    btnt1save.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.dark_green));
                    btnt1save.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));

                }else{
                    Toast.makeText(getApplicationContext(), "오류입니다." + result, Toast.LENGTH_SHORT).show();
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

