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
import java.io.File;
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

public class RegisterPackingActivity extends AppCompatActivity {

    Toolbar toolbar;
    String strIp, strUserName, strUserId, SDate, strMenuDesc;
    String filter = null;
    String strDate;

    EditText t5FileNo , t5ItemDesc, t5operaionDesc , t5OpOrderSeq , t5OpUnitOrderSeq ,t5PackingUnit, t5Request_qty , t5UnitQty,
            t5LotNo ,t5StirEquipmentDesc, t5StirStartDate ,t5StirEndDate , t5UnitEquipmentDesc, t5UnitStartDate , t5UnitEndDate ,t5PumpNoDes , t5StirEquipmentId
            , t5UnitEquipmentId, t5WorkerId ,t5PumpNoCode ,t5WorkerDesc ,t5WorkcenterCode , t5WorkcenterDesc, t5WorkcenterId ,t5StirEquipmentCode ,t5StirOldEquipmentCode
            ,t5UnitEquipmentCode , t5OldUnitEquipmentDesc , t5PumpNoId , t5JobId ,t5OperationId ,t5OpUnitOrderId ,t5UnitWorkDate, t5ModFlag
    ;
    String strSobId = "70";
    String strOrgId = "701";
    String strAssembly = "PPMF2205";


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
    Button btnStirStarDate , btnStirEndDate ,  btnUnitStartDate ,btnUnitEndDate , btnSave, btnHolding;
    //키보드

    InputMethodManager imm;
    JSONObject jspSumObject = new JSONObject();

    private boolean ScanModify = true;
    private boolean Mod_Flag = true;

    private String FileScan = "";
    private String ChCs = "";
    private String ChGs = "";
    private String Worker = "";
    private String PumpNo = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_packing_work5);
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


        auto = getSharedPreferences("appData_Log", Context.MODE_PRIVATE);
        //키보드 내리기

        //키보드
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        t5FileNo = (EditText) findViewById(R.id.et_t5_file_no);
        t5ItemDesc = (EditText) findViewById(R.id.et_t5_item_desc);
        t5operaionDesc  = (EditText) findViewById(R.id.et_t5_operaion_desc);
        t5OpOrderSeq  = (EditText) findViewById(R.id.et_t5_op_order_seq);
        t5OpUnitOrderSeq  = (EditText) findViewById(R.id.et_t5_op_unit_order_seq);
        t5PackingUnit = (EditText) findViewById(R.id.et_t5_packing_unit);
        t5Request_qty  = (EditText) findViewById(R.id.et_t5_request_qty);
        t5UnitQty = (EditText) findViewById(R.id.et_t5_unit_qty);
        t5LotNo  = (EditText) findViewById(R.id.et_t5_lot_no);
        t5StirEquipmentDesc = (EditText) findViewById(R.id.et_t5_stir_equipment_desc);
        t5StirStartDate  = (EditText) findViewById(R.id.et_t5_stir_start_date);
        t5StirEndDate  = (EditText) findViewById(R.id.et_t5_stir_end_date);
        t5UnitEquipmentDesc = (EditText) findViewById(R.id.et_t5_unit_equipment_desc);
        t5UnitStartDate  = (EditText) findViewById(R.id.et_t5_unit_start_date);
        t5UnitEndDate  = (EditText) findViewById(R.id.et_t5_unit_end_date);
        t5WorkerDesc = (EditText) findViewById(R.id.et_t5_worker_desc);
        t5PumpNoDes  = (EditText) findViewById(R.id.et_t5_pump_no_desc);
        t5StirEquipmentId = (EditText) findViewById(R.id.et_t5_stir_equipment_id);
        t5UnitEquipmentId = (EditText) findViewById(R.id.et_t5_unit_equipment_id);
        t5WorkerId  = (EditText) findViewById(R.id.et_t5_worker_id);
        t5PumpNoCode = (EditText) findViewById(R.id.et_t5_pump_no_code);
        t5WorkcenterCode = (EditText) findViewById(R.id.et_t5_workcenter_code);
        t5WorkcenterDesc = (EditText) findViewById(R.id.et_t5_workcenter_desc);
        t5WorkcenterId = (EditText) findViewById(R.id.et_t5_workcenter_id);
        t5StirEquipmentCode = (EditText) findViewById(R.id.et_t5_stir_equipment_code);
        t5StirOldEquipmentCode = (EditText) findViewById(R.id.et_t5_stir_old_equipment_code);
        t5UnitEquipmentCode = (EditText) findViewById(R.id.et_t5_unit_equipment_code);
        t5OldUnitEquipmentDesc = (EditText) findViewById(R.id.et_t5_old_unit_equipment_desc);
        t5PumpNoId = (EditText) findViewById(R.id.et_t5_pump_no_id);
        t5JobId = (EditText) findViewById(R.id.et_t5_job_id);
        t5OperationId =(EditText) findViewById(R.id.et_t5_operation_id);
        t5OpUnitOrderId = (EditText) findViewById(R.id.et_t5_op_unit_order_id);
        t5UnitWorkDate   = (EditText) findViewById(R.id.et_t5_unit_work_date);
        t5ModFlag   = (EditText) findViewById(R.id.et_t5_mod_flag);



        btnStirStarDate = (Button) findViewById(R.id.btn_t5_stir_start_date);
        btnStirEndDate  = (Button) findViewById(R.id.btn_t5_stir_end_date);
        btnUnitStartDate = (Button) findViewById(R.id.btn_t5_unit_start_date);
        btnUnitEndDate = (Button) findViewById(R.id.btn_t5_unit_end_date);
        btnSave = (Button) findViewById(R.id.btn_t5_save);
        btnHolding = (Button) findViewById(R.id.btn_t5_holding);


        initializeToolbar();

        t5FileNo.requestFocus();

        keyboardFocus(t5FileNo);
        keyboardFocus(t5StirEquipmentDesc);
        keyboardFocus(t5UnitEquipmentDesc);
        keyboardFocus(t5WorkerDesc);
        keyboardFocus(t5PumpNoDes);

        t5FileNo.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {

                if(getCurrentFocus() == t5FileNo && !s.toString().isEmpty() && s != null && t5JobId.getText().toString().equals("")){

                    FILE_NO_CJ_SCAN fILE_NO_SCAN = new FILE_NO_CJ_SCAN();
                    fILE_NO_SCAN.execute(strIp, strSobId,strOrgId ,t5FileNo.getText().toString(),t5WorkcenterId.getText().toString());

                }else{
                    return;
                }

            }
        });

        t5StirEquipmentDesc.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(getCurrentFocus() == t5StirEquipmentDesc && !s.toString().isEmpty() && s != null && t5StirEquipmentId.getText().toString().equals("")){

                    LU_CH_C_S lU_CH_C_S = new LU_CH_C_S();
                    lU_CH_C_S.execute(strIp, strSobId,strOrgId, t5WorkcenterId.getText().toString());

                }else{
                    return;
                }
            }
        });
        t5UnitEquipmentDesc.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(getCurrentFocus() == t5UnitEquipmentDesc && !s.toString().isEmpty() && s != null && t5UnitEquipmentId.getText().toString().equals("")){

                    LU_CH_G_S lU_CH_G_S = new LU_CH_G_S();
                    lU_CH_G_S.execute(strIp, strSobId,strOrgId, t5WorkcenterId.getText().toString());

                }else{
                    return;
                }
            }
        });
        t5WorkerDesc.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(getCurrentFocus() == t5WorkerDesc && !s.toString().isEmpty() && s != null && t5WorkerId.getText().toString().equals("")){

                    LU_WORKER lU_WORKER = new LU_WORKER();
                    lU_WORKER.execute(strIp, strSobId,strOrgId, t5WorkcenterId.getText().toString(), t5WorkerDesc.getText().toString());

                }else{
                    return;
                }
            }
        });

        t5PumpNoDes.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(getCurrentFocus() == t5PumpNoDes && !s.toString().isEmpty() && s != null && t5PumpNoCode.getText().toString().equals("")){

                    LU_PUMP_NO lU_PUMP_NO = new LU_PUMP_NO();
                    lU_PUMP_NO.execute(strIp, strSobId,strOrgId, t5PumpNoDes.getText().toString());

                }else{
                    return;
                }
            }
        });


        t5FileNo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                t5FileNo.setText("");
                t5ItemDesc.setText("");
                t5operaionDesc.setText("");
                t5OpOrderSeq.setText("");
                t5PackingUnit.setText("");
                t5Request_qty.setText("");
                t5UnitQty.setText("");
                t5LotNo.setText("");
                t5StirEquipmentDesc.setText("");
                t5StirStartDate.setText("");
                t5StirEndDate.setText("");
                t5UnitEquipmentDesc.setText("");
                t5UnitStartDate.setText("");
                t5UnitEndDate.setText("");
                t5PumpNoDes.setText("");
                t5StirEquipmentId.setText("");
                t5UnitEquipmentId.setText("");
                t5PumpNoCode.setText("");
                t5WorkerDesc.setText("");


                return false;
            }
        });

        t5StirEquipmentDesc.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                t5StirEquipmentDesc.setText("");
                t5StirEquipmentId.setText("");
                return false;
            }
        });
        t5UnitEquipmentDesc.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                t5UnitEquipmentDesc.setText("");
                t5UnitEquipmentId.setText("");
                return false;
            }
        });
        t5PumpNoDes.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                t5PumpNoDes.setText("");
                t5PumpNoCode.setText("");
                return false;
            }
        });
        t5WorkerDesc.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                t5WorkerDesc.setText("");
                t5WorkerId.setText("");
                return false;
            }
        });


        btnStirStarDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ScanModify==false){
                    btnSave.setBackgroundColor(Color.YELLOW);
                    btnSave.setTextColor(Color.BLACK);
                }

                t5StirStartDate.setText(getNowDate());
            }
        });

        btnStirEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ScanModify==false){
                    btnSave.setBackgroundColor(Color.YELLOW);
                    btnSave.setTextColor(Color.BLACK);
                }

                t5StirEndDate.setText(getNowDate());
            }
        });

        btnUnitStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ScanModify==false){
                    btnSave.setBackgroundColor(Color.YELLOW);
                    btnSave.setTextColor(Color.BLACK);
                }

                t5UnitStartDate.setText(getNowDate());
            }
        });

        btnUnitEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ScanModify==false){
                    btnSave.setBackgroundColor(Color.YELLOW);
                    btnSave.setTextColor(Color.BLACK);
                }

                t5UnitEndDate.setText(getNowDate());
            }
        });

    btnSave.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            AlertDialog.Builder alert = new AlertDialog.Builder(RegisterPackingActivity.this);
            alert.setTitle("저장");
            alert.setMessage("수정한 내역을 저장하시겠습니까?");
            alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if(t5FileNo.getText().toString().isEmpty()){
                        Toast.makeText(getApplicationContext(), "File No는 필수입니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    CJ_UPDATE cj_UPDATE = new CJ_UPDATE();
                    cj_UPDATE.execute(strIp,t5JobId.getText().toString(),t5OperationId.getText().toString(),t5WorkcenterId.getText().toString(),strSobId ,strOrgId ,t5StirEquipmentId.getText().toString(),
                            t5StirStartDate.getText().toString().replaceAll(" ", ""), t5StirEndDate.getText().toString().replaceAll(" ",""), getNowWorkDate() ,  t5UnitEquipmentId.getText().toString()
                            , t5UnitStartDate.getText().toString().replaceAll(" ",""),t5UnitEndDate.getText().toString().replaceAll(" ",""), t5WorkerId.getText().toString(),
                            t5PumpNoCode.getText().toString(), strUserId , t5OpUnitOrderId.getText().toString() , t5OpUnitOrderSeq.getText().toString()
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

    //저장버튼 색 바꾸는 함수
    private void saveColorChange() {
        if (ScanModify==false) {
            btnSave.setBackgroundColor(Color.YELLOW);
            btnSave.setTextColor(Color.BLACK);
        }
    }

    private String getNowDate() {

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.KOREAN);

        String getTime = sdf.format(date);

        return getTime;
    }
    private String getNowWorkDate() {

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREAN);

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

        //먼저 실행되는 함수
        GET_WORKCENTER_IN_AUTHORITY gET_WORKCENTER_IN_AUTHORITY = new GET_WORKCENTER_IN_AUTHORITY();
        gET_WORKCENTER_IN_AUTHORITY.execute(strIp, strSobId,strOrgId ,strUserId, strAssembly);

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
                    t5WorkcenterCode.setText(job.getString("X_WORKCENTER_CODE"));
                    t5WorkcenterDesc.setText(job.getString("X_WORKCENTER_DESC"));
                    t5WorkcenterId.setText(job.getString("X_WORKCENTER_ID"));
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
    protected class FILE_NO_CJ_SCAN extends AsyncTask<String, Void, String>
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
            {  URL obj = new URL("http://" + urls[0] + "/TAIYO/FileNoScanCJ.jsp"); //주소 지정

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
                t5StirEquipmentDesc.requestFocus();
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
                    if(!FileScan.equals(t5FileNo.getText().toString())){
                        ScanModify = false;
                        t5FileNo.requestFocus();
                        t5FileNo.setText("");
                    }

                    return;
                }else{
                                    JSONObject job = jarrayWorkLevel.getJSONObject(0);
                if(job.getString("Status").equals("S")){


                    t5FileNo.setText(job.getString("WORK_ORDER_NO"));
                    t5ItemDesc.setText(job.getString("ITEM_DESCRIPTION"));
                    t5operaionDesc.setText(job.getString("OPERATION_DESCRIPTION"));

                    if(job.getString("OP_ORDER_SEQ").equals("null")){
                        t5OpOrderSeq.setText("");
                    }else{
                        t5OpOrderSeq.setText(job.getString("OP_ORDER_SEQ"));
                    }
                    if(job.getString("OP_UNIT_ORDER_SEQ").equals("null")){
                        t5OpUnitOrderSeq.setText("");
                    }else{
                        t5OpUnitOrderSeq.setText(job.getString("OP_UNIT_ORDER_SEQ"));
                    }
                    if(job.getString("PACKING_UNIT").equals("null")){
                        t5PackingUnit.setText("");
                    }else{
                        t5PackingUnit.setText(job.getString("PACKING_UNIT"));
                    }
                    if(job.getString("REQUEST_QTY").equals("null")){
                        t5Request_qty.setText("");
                    }else{
                        t5Request_qty.setText(job.getString("REQUEST_QTY"));
                    }
                    if(job.getString("UNIT_QTY").equals("null")){
                        t5UnitQty.setText("");
                    }else{
                        t5UnitQty.setText(job.getString("UNIT_QTY"));
                    }
                    if(job.getString("LOT_NO").equals("null")){
                        t5LotNo.setText("");
                    }else{
                        t5LotNo.setText(job.getString("LOT_NO"));
                    }
                    if(job.getString("STIR_EQUIPMENT_NAME").equals("null")){
                        t5StirEquipmentDesc.setText("");
                    }else{
                        t5StirEquipmentDesc.setText(job.getString("STIR_EQUIPMENT_NAME"));
                    }
                    if(job.getString("STIR_START_DATE").equals("null")){
                        t5StirStartDate.setText("");
                    }else{
                        t5StirStartDate.setText(job.getString("STIR_START_DATE"));
                    }
                    if(job.getString("STIR_END_DATE").equals("null")){
                        t5StirEndDate.setText("");
                    }else{
                        t5StirEndDate.setText(job.getString("STIR_END_DATE"));
                    }
                    if(job.getString("UNIT_EQUIPMENT_NAME").equals("null")){
                        t5UnitEquipmentDesc.setText("");
                    }else{
                        t5UnitEquipmentDesc.setText(job.getString("UNIT_EQUIPMENT_NAME"));
                    }
                    if(job.getString("UNIT_START_DATE").equals("null")){
                        t5UnitStartDate.setText("");
                    }else{
                        t5UnitStartDate.setText(job.getString("UNIT_START_DATE"));
                    }
                    if(job.getString("UNIT_END_DATE").equals("null")){
                        t5UnitEndDate.setText("");
                    }else{
                        t5UnitEndDate.setText(job.getString("UNIT_END_DATE"));
                    }
                    if(job.getString("PUMP_NO_DESC").equals("null")){
                        t5PumpNoDes.setText("");
                    }else{
                        t5PumpNoDes.setText(job.getString("PUMP_NO_DESC"));
                    }
                    if(job.getString("STIR_EQUIPMENT_ID").equals("null")){
                        t5StirEquipmentId.setText("");
                    }else{
                        t5StirEquipmentId.setText(job.getString("STIR_EQUIPMENT_ID"));
                    }
                    if(job.getString("UNIT_EQUIPMENT_ID").equals("null")){
                        t5UnitEquipmentId.setText("");
                    }else{
                        t5UnitEquipmentId.setText(job.getString("UNIT_EQUIPMENT_ID"));
                    }
                    if(job.getString("WORKER_ID").equals("null")){
                        t5WorkerId.setText("");
                    }else{
                        t5WorkerId.setText(job.getString("WORKER_ID"));
                    }
                    if(job.getString("PUMP_NO_CODE").equals("null")){
                        t5PumpNoCode.setText("");
                    }else{
                        t5PumpNoCode.setText(job.getString("PUMP_NO_CODE"));
                    }
                    if(job.getString("WORKER_DESC").equals("null")){
                        t5WorkerDesc.setText("");
                    }else{
                        t5WorkerDesc.setText(job.getString("WORKER_DESC"));
                    }
                    if(job.getString("JOB_ID").equals("null")){
                        t5JobId.setText("");
                    }else{
                        t5JobId.setText(job.getString("JOB_ID"));
                    }
                    if(job.getString("OPERAITON_ID").equals("null")){
                        t5OperationId.setText("");
                    }else{
                        t5OperationId.setText(job.getString("OPERAITON_ID"));
                    }
                    if(job.getString("OP_UNIT_ORDER_ID").equals("null")){
                        t5OpUnitOrderId.setText("");
                    }else{
                        t5OpUnitOrderId.setText(job.getString("OP_UNIT_ORDER_ID"));
                    }
                    if(job.getString("MOD_FLAG").equals("null")){
                        t5ModFlag.setText("");
                    }else{
                        t5ModFlag.setText(job.getString("MOD_FLAG"));
                    }
                    FileScan = job.getString("WORK_ORDER_NO");
                }
                    t5StirEquipmentDesc.requestFocus();

                }


                ScanModify = false;

                if(t5ModFlag.equals("N")){
                    t5FileNo.setInputType(InputType.TYPE_NULL);
                    t5ItemDesc.setInputType(InputType.TYPE_NULL);
                    t5operaionDesc.setInputType(InputType.TYPE_NULL);
                    t5OpOrderSeq.setInputType(InputType.TYPE_NULL);
                    t5OpUnitOrderSeq.setInputType(InputType.TYPE_NULL);
                    t5PackingUnit.setInputType(InputType.TYPE_NULL);
                    t5Request_qty.setInputType(InputType.TYPE_NULL);
                    t5UnitQty.setInputType(InputType.TYPE_NULL);
                    t5LotNo.setInputType(InputType.TYPE_NULL);
                    t5StirEquipmentDesc.setInputType(InputType.TYPE_NULL);
                    t5StirStartDate.setInputType(InputType.TYPE_NULL);
                    t5StirEndDate.setInputType(InputType.TYPE_NULL);
                    t5UnitEquipmentDesc.setInputType(InputType.TYPE_NULL);
                    t5UnitStartDate.setInputType(InputType.TYPE_NULL);
                    t5UnitEndDate.setInputType(InputType.TYPE_NULL);
                    t5PumpNoDes.setInputType(InputType.TYPE_NULL);
                    t5StirEquipmentId.setInputType(InputType.TYPE_NULL);
                    t5UnitEquipmentId.setInputType(InputType.TYPE_NULL);
                    t5WorkerId.setInputType(InputType.TYPE_NULL);
                    t5PumpNoCode.setInputType(InputType.TYPE_NULL);
                    t5WorkerDesc.setInputType(InputType.TYPE_NULL);
                    t5JobId.setInputType(InputType.TYPE_NULL);
                    t5OperationId.setInputType(InputType.TYPE_NULL);
                    t5OpUnitOrderId.setInputType(InputType.TYPE_NULL);
                    t5ModFlag.setInputType(InputType.TYPE_NULL);


                }
            }catch (JSONException e)
            {
                e.printStackTrace();
                t5StirEquipmentDesc.requestFocus();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                t5StirEquipmentDesc.requestFocus();
            }
        }
    }


    protected class LU_CH_C_S extends AsyncTask<String, Void, String>
    {
        protected  String doInBackground(String... urls)
        {
            StringBuffer jsonHtml = new StringBuffer();

            //서버로 보낼 데이터 설정
            String search_title = "W_SOB_ID=" + urls[1]
                    + "&W_ORG_ID=" + urls[2]
                    + "&W_WORKCENTER_ID=" + urls[3]
                    ;

            try
            {  URL obj = new URL("http://" + urls[0] + "/TAIYO/LuChCS.jsp"); //주소 지정

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
                t5UnitEquipmentDesc.requestFocus();
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
                    if(!ChCs.equals(t5StirEquipmentDesc.getText().toString()))
                        t5StirEquipmentDesc.requestFocus();
                        t5StirEquipmentDesc.setText("");
                    return;
                }

                JSONObject job = jarrayWorkLevel.getJSONObject(0);
                if(job.getString("Status").equals("S")){

                    t5StirOldEquipmentCode.setText(job.getString("OLD_EQUIPMENT_NAME"));
                    t5StirEquipmentDesc.setText(job.getString("TOP_EQUIPMENT_NAME"));
                    t5StirEquipmentCode.setText(job.getString("TOP_EQUIPMENT_CODE"));
                    t5StirEquipmentId.setText(job.getString("TOP_EQUIPMENT_ID"));

                    ChCs = job.getString("TOP_EQUIPMENT_NAME");

                    saveColorChange();
                }
                t5UnitEquipmentDesc.requestFocus();

            }catch (JSONException e)
            {
                e.printStackTrace();
                t5UnitEquipmentDesc.requestFocus();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                t5UnitEquipmentDesc.requestFocus();
            }
        }
    }

    protected class LU_CH_G_S extends AsyncTask<String, Void, String>
    {
        protected  String doInBackground(String... urls)
        {
            StringBuffer jsonHtml = new StringBuffer();

            //서버로 보낼 데이터 설정
            String search_title = "W_SOB_ID=" + urls[1]
                    + "&W_ORG_ID=" + urls[2]
                    + "&W_WORKCENTER_ID=" + urls[3]
                    ;

            try
            {  URL obj = new URL("http://" + urls[0] + "/TAIYO/LuChGS.jsp"); //주소 지정

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
                t5WorkerDesc.requestFocus();
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
                    if(!ChGs.equals(t5UnitEquipmentDesc.getText().toString())){
                        t5UnitEquipmentDesc.requestFocus();
                        t5UnitEquipmentDesc.setText("");
                    }

                    return;
                }

                JSONObject job = jarrayWorkLevel.getJSONObject(0);
                if(job.getString("Status").equals("S")){

                    t5OldUnitEquipmentDesc.setText(job.getString("OLD_EQUIPMENT_NAME"));
                    t5UnitEquipmentDesc.setText(job.getString("TOP_EQUIPMENT_NAME"));
                    t5UnitEquipmentCode.setText(job.getString("TOP_EQUIPMENT_CODE"));
                    t5UnitEquipmentId.setText(job.getString("TOP_EQUIPMENT_ID"));

                    ChGs = job.getString("TOP_EQUIPMENT_NAME");

                    saveColorChange();
                }
                t5WorkerDesc.requestFocus();

            }catch (JSONException e)
            {
                e.printStackTrace();
                t5WorkerDesc.requestFocus();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                t5WorkerDesc.requestFocus();
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
                t5PumpNoDes.requestFocus();
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
                    if(!Worker.equals(t5WorkerDesc.getText().toString())) {
                        t5WorkerDesc.requestFocus();
                        t5WorkerDesc.setText("");
                    }


                    return;
                }

                JSONObject job = jarrayWorkLevel.getJSONObject(0);
                if(job.getString("Status").equals("S")){

                    t5WorkerId.setText(job.getString("USER_ID"));
                    t5WorkerDesc.setText(job.getString("DESCRIPTION"));

                    Worker = job.getString("DESCRIPTION");

                }
                t5PumpNoDes.requestFocus();

            }catch (JSONException e)
            {
                e.printStackTrace();
                t5PumpNoDes.requestFocus();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                t5PumpNoDes.requestFocus();
            }
        }
    }

    protected class LU_PUMP_NO extends AsyncTask<String, Void, String>
    {
        protected  String doInBackground(String... urls)
        {
            StringBuffer jsonHtml = new StringBuffer();

            //서버로 보낼 데이터 설정
            String search_title = "W_SOB_ID=" + urls[1]
                    + "&W_ORG_ID=" + urls[2]
                    + "&W_BARCODE=" + urls[3]
                    ;

            try
            {  URL obj = new URL("http://" + urls[0] + "/TAIYO/LuPumpNo.jsp"); //주소 지정

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
                    if(!PumpNo.equals(t5PumpNoDes.getText().toString())){
                        t5PumpNoDes.requestFocus();
                        t5PumpNoDes.setText("");

                    }

                    return;
                }

                JSONObject job = jarrayWorkLevel.getJSONObject(0);
                if(job.getString("Status").equals("S")){

                    t5PumpNoId.setText(job.getString("LOOKUP_ENTRY_ID"));
                    t5PumpNoCode.setText(job.getString("ENTRY_CODE"));
                    t5PumpNoDes.setText(job.getString("ENTRY_DESCRIPTION"));

                    PumpNo = job.getString("ENTRY_DESCRIPTION");

                    saveColorChange();
                }
                t5FileNo.requestFocus();

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

    protected class CJ_UPDATE extends AsyncTask<String, Void, String>
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
                    + "&P_STIR_EQUIPMENT_ID=" + urls[6]
                    + "&P_STIR_START_DATE=" + urls[7]
                    + "&P_STIR_END_DATE=" + urls[8]
                    + "&P_UNIT_WORK_DATE=" + urls[9]
                    + "&P_UNIT_EQUIPMENT_ID=" + urls[10]
                    + "&P_UNIT_START_DATE=" + urls[11]
                    + "&P_UNIT_END_DATE=" + urls[12]
                    + "&P_WORKER_ID=" + urls[13]
                    + "&P_PUMP_NO_CODE=" + urls[14]
                    + "&P_USER_ID=" + urls[15]
                    + "&P_OP_UNIT_ORDER_ID=" + urls[16]
                    + "&P_SEQ=" + urls[17]
                    ;


            try
            {
                //String ip = context.getApplicationContext().getResources().getString(R.string.ip);

                URL obj = new URL("http://" + urls[0] + "/TAIYO/CJUpdate.jsp"); //주소 지정

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

                    FILE_NO_CJ_SCAN fILE_NO_SCAN = new  FILE_NO_CJ_SCAN();
                    fILE_NO_SCAN.execute(strIp, strSobId,strOrgId ,t5FileNo.getText().toString(),t5WorkcenterId.getText().toString()); //다시 fill

                    btnSave.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.dark_green));
                    btnSave.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));


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

