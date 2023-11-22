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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

public class RegisterAgingActivity extends AppCompatActivity {

    Toolbar toolbar;
    String strIp, strUserName, strUserId, SDate, strMenuDesc;
    String filter = null;
    String strDate;

    EditText t8FileNoScan ,t8ItemDesc ,t8OperaionDesc ,t8StartDate , t8TankDesc ,t8EquipmentName , t8StirWorkerName , t8EndDate
          ,t8TankCode , t8EquipmentId , t8StirWorker1Id , t8JobId ,t8OperationId ,t8ModFlag
        , t8WorkcenterDesc , t8WorkcenterCode , t8WorkcenterId , t8TankId , t8OldEquipmentName , t8EquipmentCode
            ;
    String strSobId = "70";
    String strOrgId = "701";
    String strAssembly = "PPMF2220";


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
    Button  btnt8StartDate ,btnt8EndDate ,btnt8Save;
    //키보드

    InputMethodManager imm;
    JSONObject jspSumObject = new JSONObject();

    private boolean ScanModify = true;
    private boolean Mod_Flag = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_aging_work8);
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
        btnt8Save = (Button) findViewById(R.id.btn_t8_save);

        auto = getSharedPreferences("appData_Log", Context.MODE_PRIVATE);
        //키보드 내리기

        //키보드
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        t8FileNoScan = (EditText) findViewById(R.id.et_t8_file_no_scan);
        t8ItemDesc  = (EditText) findViewById(R.id.et_t8_item_desc);
        t8OperaionDesc  = (EditText) findViewById(R.id.et_t8_operaion_desc);
        t8StartDate = (EditText) findViewById(R.id.et_t8_start_date);
        t8TankDesc = (EditText) findViewById(R.id.et_t8_tank_desc);
        t8EquipmentName = (EditText) findViewById(R.id.et_t8_equipment_name);
        t8StirWorkerName = (EditText) findViewById(R.id.et_t8_stir_worker_name);
        t8EndDate = (EditText) findViewById(R.id.et_t8_end_date);
        t8TankCode = (EditText) findViewById(R.id.et_t8_tank_code);
        t8EquipmentId = (EditText) findViewById(R.id.et_t8_equipment_id);
        t8StirWorker1Id = (EditText) findViewById(R.id.et_t8_stir_worker1_id);
        t8JobId = (EditText) findViewById(R.id.et_t8_job_id);
        t8OperationId = (EditText) findViewById(R.id.et_t8_operation_id);
        t8ModFlag = (EditText) findViewById(R.id.et_t8_mod_flag);
        t8WorkcenterDesc  = (EditText) findViewById(R.id.et_t8_workcenter_desc);
        t8WorkcenterCode = (EditText) findViewById(R.id.et_t8_workcenter_code);
        t8WorkcenterId = (EditText) findViewById(R.id.et_t8_workcenter_id);
        t8TankId = (EditText) findViewById(R.id.et_t8_tank_id);

        t8OldEquipmentName = (EditText) findViewById(R.id.et_t8_old_equipment_id);
        t8EquipmentCode = (EditText) findViewById(R.id.et_t8_equipment_code);


        btnt8StartDate = (Button) findViewById(R.id.btn_t8_start_date);
        btnt8EndDate = (Button) findViewById(R.id.btn_t8_end_date);
        btnt8Save =  (Button) findViewById(R.id.btn_t8_save);


        initializeToolbar();

        t8FileNoScan.requestFocus();

        keyboardFocus(t8FileNoScan);
        keyboardFocus(t8TankDesc);
        keyboardFocus(t8EquipmentName);
        keyboardFocus(t8StirWorkerName);

        //workcenter 가져오기
        GET_WORKCENTER_IN_AUTHORITY gET_WORKCENTER_IN_AUTHORITY = new GET_WORKCENTER_IN_AUTHORITY();
        gET_WORKCENTER_IN_AUTHORITY.execute(strIp, strSobId,strOrgId ,strUserId, strAssembly);

        btnt8StartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ScanModify==false){
                    btnt8Save.setBackgroundColor(Color.YELLOW);
                    btnt8Save.setTextColor(Color.BLACK);
                }
                t8StartDate.setText(getNowDate());;
            }
        });


        btnt8EndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ScanModify==false){
                    btnt8Save.setBackgroundColor(Color.YELLOW);
                    btnt8Save.setTextColor(Color.BLACK);
                }
                t8EndDate.setText(getNowDate());;
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
            btnt8Save.setBackgroundColor(Color.YELLOW);
            btnt8Save.setTextColor(Color.BLACK);
        }
    }

    private String getNowDate() {

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.KOREAN);

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


        t8FileNoScan.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {

                if(getCurrentFocus() == t8FileNoScan && !s.toString().isEmpty()){
                    FILE_NO_SCAN fILE_NO_SCAN = new FILE_NO_SCAN();
                    fILE_NO_SCAN.execute(strIp, strSobId,strOrgId ,t8FileNoScan.getText().toString(),t8WorkcenterId.getText().toString());

                }else{
                    return;
                }

            }
        });

        t8EquipmentName.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(getCurrentFocus() == t8EquipmentName && !s.toString().isEmpty()){

                    LU_AJ_EQP lU_AJ_EQP = new LU_AJ_EQP();
                    lU_AJ_EQP.execute(strIp, strSobId,strOrgId, t8WorkcenterId.getText().toString(),t8EquipmentName.getText().toString());

                }else{
                    return;
                }
            }
        });


        t8TankDesc.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(getCurrentFocus() == t8TankDesc && !s.toString().isEmpty()){

                    LU_TANK_TYPE lU_TANK_TYPE = new LU_TANK_TYPE();
                    lU_TANK_TYPE.execute(strIp, strSobId,strOrgId, t8TankDesc.getText().toString());

                }else{
                    return;
                }
            }
        });

        t8StirWorkerName.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(getCurrentFocus() == t8StirWorkerName && !s.toString().isEmpty()){

                    LU_WORKER lu_worker = new LU_WORKER();
                    lu_worker.execute(strIp, strSobId,strOrgId, t8WorkcenterId.getText().toString(), t8StirWorkerName.getText().toString());

                }else{
                    return;
                }
            }
        });


        t8FileNoScan.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                t8FileNoScan.setText("");
                t8OperaionDesc.setText("");
                t8ItemDesc.setText("");


                return false;
            }
        });


        t8TankDesc.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                t8TankDesc.setText("");
                return false;
            }
        });

        t8EquipmentName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                t8EquipmentName.setText("");
                return false;
            }
        });
        t8StirWorkerName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                t8StirWorkerName.setText("");
                return false;
            }
        });

        btnt8Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(RegisterAgingActivity.this);
                alert.setTitle("저장");
                alert.setMessage("수정한 내역을 저장하시겠습니까?");
                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(t8FileNoScan.getText().toString().isEmpty()){
                            Toast.makeText(getApplicationContext(), "File No는 필수입니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        AJ_UPDATE aj_UPDATE = new AJ_UPDATE();
                        aj_UPDATE.execute(strIp,t8JobId.getText().toString(),t8OperationId.getText().toString(),t8WorkcenterId.getText().toString(),strSobId ,strOrgId ,
                         t8StartDate.getText().toString().replaceAll(" ",""), t8EndDate.getText().toString().replaceAll(" ","")
                         , t8TankCode.getText().toString(), t8EquipmentId.getText().toString() ,t8StirWorker1Id.getText().toString(), strUserId
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
                    t8WorkcenterCode.setText(job.getString("X_WORKCENTER_CODE"));
                    t8WorkcenterDesc.setText(job.getString("X_WORKCENTER_DESC"));
                    t8WorkcenterId.setText(job.getString("X_WORKCENTER_ID"));
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



    //lutanktype 설비 탱크
    protected class LU_TANK_TYPE extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... urls) {
            StringBuffer jsonHtml = new StringBuffer();

            //서버로 보낼 데이터 설정
            String search_title = "W_SOB_ID=" + urls[1]
                    + "&W_ORG_ID=" + urls[2]
                    + "&W_BARCODE=" + urls[3];

            try {
                URL obj = new URL("http://"+urls[0]+"/TAIYO/LuTankType.jsp"); //주소 지정

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
                t8EquipmentName.requestFocus();
            }

            return jsonHtml.toString(); //결과값 리턴
        }

        protected void onPostExecute(String result) {
            //페이지 결과값 파싱
            try {
                JSONObject RESURT = new JSONObject(result); // JSON 오브젝트 받음
                JSONArray resultArray = RESURT.getJSONArray("RESULT"); // JSONArray 파싱

                if(resultArray.length() < 1){
                    t8EquipmentName.requestFocus();
                    return;
                }

                JSONObject job = resultArray.getJSONObject(0); // JSON 오브젝트 파싱
                String status = job.getString("Status");

                if (status.equals("S")) {
                    t8TankCode.setText(job.getString("ENTRY_CODE"));
                    t8TankDesc.setText(job.getString("ENTRY_DESCRIPTION"));
                    t8TankId.setText(job.getString("LOOKUP_ENTRY_ID"));

                    saveColorChange();
                }

                t8EquipmentName.requestFocus();

            } catch (JSONException e) {
                e.printStackTrace();
                t8EquipmentName.requestFocus();

            } catch (Exception e) {
                e.printStackTrace();
                t8EquipmentName.requestFocus();

            }
        }

    }

    // LU_TANK_TYPE
    protected class LU_AJ_EQP extends AsyncTask<String, Void, String>
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
            {  URL obj = new URL("http://" + urls[0] + "/TAIYO/LuAJEqp.jsp"); //주소 지정

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
                t8StirWorkerName.requestFocus();
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
                    t8StirWorkerName.requestFocus();
                    return;
                }

                JSONObject job = jarrayWorkLevel.getJSONObject(0);
                if(job.getString("Status").equals("S")){

                    t8OldEquipmentName.setText(job.getString("OLD_EQUIPMENT_NAME"));
                    t8EquipmentName.setText(job.getString("TOP_EQUIPMENT_NAME"));
                    t8EquipmentCode.setText(job.getString("TOP_EQUIPMENT_CODE"));
                    t8EquipmentId.setText(job.getString("TOP_EQUIPMENT_ID"));
                }
                t8StirWorkerName.requestFocus();

            }catch (JSONException e)
            {
                e.printStackTrace();
                t8StirWorkerName.requestFocus();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                t8StirWorkerName.requestFocus();
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
                    t8FileNoScan.requestFocus();
                    return;
                }

                JSONObject job = jarrayWorkLevel.getJSONObject(0);
                if(job.getString("Status").equals("S")){

                    t8StirWorker1Id.setText(job.getString("USER_ID"));
                    t8StirWorkerName.setText(job.getString("DESCRIPTION"));
                }
                t8FileNoScan.requestFocus();

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
            {  URL obj = new URL("http://" + urls[0] + "/TAIYO/FileNoScanAJ.jsp"); //주소 지정

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
                t8TankDesc.requestFocus();
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
                    t8TankDesc.requestFocus();
                    return;
                }

                JSONObject job = jarrayWorkLevel.getJSONObject(0);
                if(job.getString("Status").equals("S")){


                    if(job.getString("WORK_ORDER_NO").equals("null")){
                        t8FileNoScan.setText("");
                    }else{
                        t8FileNoScan.setText(job.getString("WORK_ORDER_NO"));
                    }

                    if(job.getString("ITEM_DESCRIPTION").equals("null")){
                        t8ItemDesc.setText("");
                    }else{
                        t8ItemDesc.setText(job.getString("ITEM_DESCRIPTION"));
                    }
                    if(job.getString("OPERATION_DESCRIPTION").equals("null")){
                        t8OperaionDesc.setText("");
                    }else{
                        t8OperaionDesc.setText(job.getString("OPERATION_DESCRIPTION"));
                    }
                    if(job.getString("STIR_1_START_DATE").equals("null")){
                        t8StartDate.setText("");
                    }else{
                        t8StartDate.setText(job.getString("STIR_1_START_DATE"));
                    }
                    if(job.getString("TANK_NO").equals("null")){
                        t8TankDesc.setText("");
                    }else{
                        t8TankDesc.setText(job.getString("TANK_NO"));
                    }
                    if(job.getString("EQUIPMENT_NAME").equals("null")){
                        t8EquipmentName.setText("");
                    }else{
                        t8EquipmentName.setText(job.getString("EQUIPMENT_NAME"));
                    }
                    if(job.getString("STIR_1_WORKER_NAME").equals("null")){
                        t8StirWorkerName.setText("");
                    }else{
                        t8StirWorkerName.setText(job.getString("STIR_1_WORKER_NAME"));
                    }
                    if(job.getString("STIR_1_END_DATE").equals("null")){
                        t8EndDate.setText("");
                    }else{
                        t8EndDate.setText(job.getString("STIR_1_END_DATE"));

                    }
                    if(job.getString("MIX_TANK_LCODE").equals("null")){
                        t8TankCode.setText("");
                    }else{
                        t8TankCode.setText(job.getString("MIX_TANK_LCODE"));
                    }
                    if(job.getString("EQUIPMENT_ID").equals("null")){
                        t8EquipmentId.setText("");
                    }else{
                        t8EquipmentId.setText(job.getString("EQUIPMENT_ID"));
                    }
                    if(job.getString("STIR_1_WORKER_ID").equals("null")){
                        t8StirWorker1Id.setText("");
                    }else{
                        t8StirWorker1Id.setText(job.getString("STIR_1_WORKER_ID"));
                    }
                    if(job.getString("JOB_ID").equals("null")){
                        t8JobId.setText("");
                    }else{
                        t8JobId.setText(job.getString("JOB_ID"));
                    }
                    if(job.getString("OPERATION_ID").equals("null")){
                        t8OperationId.setText("");
                    }else{
                        t8OperationId.setText(job.getString("OPERATION_ID"));
                    }
                    if(job.getString("MOD_FLAG").equals("null")){
                        t8ModFlag.setText("");
                    }else{
                        t8ModFlag.setText(job.getString("MOD_FLAG"));
                    }

                }

                t8TankDesc.requestFocus();

                ScanModify = false;

                if(t8ModFlag.equals("N")){
                    t8FileNoScan.setInputType(InputType.TYPE_NULL);
                    t8ItemDesc.setInputType(InputType.TYPE_NULL);
                    t8OperaionDesc.setInputType(InputType.TYPE_NULL);
                    t8StartDate.setInputType(InputType.TYPE_NULL);
                    t8TankDesc.setInputType(InputType.TYPE_NULL);
                    t8EquipmentName.setInputType(InputType.TYPE_NULL);
                    t8StirWorkerName.setInputType(InputType.TYPE_NULL);
                    t8EndDate.setInputType(InputType.TYPE_NULL);
                    t8TankCode.setInputType(InputType.TYPE_NULL);
                    t8EquipmentId.setInputType(InputType.TYPE_NULL);
                    t8StirWorker1Id.setInputType(InputType.TYPE_NULL);
                    t8JobId.setInputType(InputType.TYPE_NULL);
                    t8OperationId.setInputType(InputType.TYPE_NULL);
                    t8ModFlag.setInputType(InputType.TYPE_NULL);
                    t8WorkcenterDesc.setInputType(InputType.TYPE_NULL);
                    t8WorkcenterCode.setInputType(InputType.TYPE_NULL);
                    t8WorkcenterId.setInputType(InputType.TYPE_NULL);
                    t8TankId.setInputType(InputType.TYPE_NULL);
                    t8OldEquipmentName.setInputType(InputType.TYPE_NULL);
                    t8EquipmentCode.setInputType(InputType.TYPE_NULL);

                }

            }catch (JSONException e)
            {
                e.printStackTrace();
                t8TankDesc.requestFocus();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                t8TankDesc.requestFocus();
            }
        }
    }

    protected class AJ_UPDATE extends AsyncTask<String, Void, String>
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
                    + "&P_STIR_1_START_DATE=" + urls[6]
                    + "&P_STIR_1_END_DATE=" + urls[7]
                    + "&P_MIX_TANK_LCODE=" + urls[8]
                    + "&P_EQUIPMENT_ID=" + urls[9]
                    + "&P_STIR_1_WORKER_ID=" + urls[10]
                    + "&P_USER_ID=" + urls[11]
                    ;

            try
            {
                //String ip = context.getApplicationContext().getResources().getString(R.string.ip);

                URL obj = new URL("http://" + urls[0] + "/TAIYO/AJUpdate.jsp"); //주소 지정

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
                    fILE_NO_SCAN.execute(strIp, strSobId,strOrgId ,t8FileNoScan.getText().toString(),t8WorkcenterId.getText().toString()); //다시 fill


                    btnt8Save.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.dark_green));
                    btnt8Save.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));

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




}

