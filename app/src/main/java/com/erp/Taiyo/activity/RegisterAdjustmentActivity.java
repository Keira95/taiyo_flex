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

public class RegisterAdjustmentActivity extends AppCompatActivity {

    Toolbar toolbar;
    String strIp, strUserName, strUserId, SDate, strMenuDesc;
    String filter = null;
    String strDate;

    EditText t4FileNo,t4ItemDesc, t4OperaionDesc , t4OpOrderSeq , t4Jojung1 ,t4Jojung2,t4OrderQty1 ,t4OrderQty2, t4PoiseSubSeq, t4EquipmentDesc ,t4WorkStartDate , t4WorkEndDate, t4WorkerDesc
    ,t4EquipmentId, t4WorkerId ,t4OpOrderId, t4JobId ,t4OperationId, t4OpPoiseOrderId,t4ModFlag , t4WorkcenterCode ,t4WorkcenterDesc ,t4WorkcenterId
    , t4TopEquimentId, t4TopEquimentCode, t4OldEquimentName
    ;


    String strSobId = "70";
    String strOrgId = "701";
    String strAssembly = "PPMF2204";


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

    Button btnt4save;
    Button btnWorkStartDate , btnWorkEndDate ,btnWorkerDesc, btnHolding;
    //키보드

    InputMethodManager imm;
    JSONObject jspSumObject = new JSONObject();

    private boolean ScanModify = true;
    private boolean Mod_Flag = true;
    private String FileScan = "";
    private String JjEqp = "";
    private String Worker = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_adjustment_work4);
        Intent intent = getIntent();
        strIp = intent.getStringExtra("Ip");
        strUserId = intent.getStringExtra("O_USER_ID");
        strUserName = intent.getStringExtra("O_USER_NAME");
        strMenuDesc = intent.getStringExtra("TOP_MENU_DESC");


        mainLayout = findViewById(R.id.main_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvUserName = (TextView) findViewById(R.id.user_nm);
        tvMenu = (TextView) findViewById(R.id.tv_menu);

        t4FileNo = (EditText) findViewById(R.id.et_t4_file_no );
        t4ItemDesc = (EditText) findViewById(R.id.et_t4_item_desc);
        t4OperaionDesc  = (EditText) findViewById(R.id.et_t4_operaion_desc);
        t4OpOrderSeq = (EditText) findViewById(R.id.et_t4_op_order_seq);
        t4Jojung1 = (EditText) findViewById(R.id.et_t4_jojung1);
        t4Jojung2 = (EditText) findViewById(R.id.et_t4_jojung2);
        t4OrderQty1  = (EditText) findViewById(R.id.et_t4_order_qty1);
        t4OrderQty2 = (EditText) findViewById(R.id.et_t4_order_qty2);
        t4PoiseSubSeq = (EditText) findViewById(R.id.et_t4_poise_sub_seq);
        t4EquipmentDesc = (EditText) findViewById(R.id.et_t4_equipment_desc);
        t4WorkStartDate  = (EditText) findViewById(R.id.et_t4_work_start_date);
        t4WorkEndDate = (EditText) findViewById(R.id.et_t4_work_end_date);
        t4WorkerDesc = (EditText) findViewById(R.id.et_t4_worker_desc);
        t4EquipmentId = (EditText) findViewById(R.id.et_t4_equipment_id);
        t4WorkerId  = (EditText) findViewById(R.id.et_t4_worker_id);
        t4OpOrderId = (EditText) findViewById(R.id.et_t4_op_order_id);
        t4JobId = (EditText) findViewById(R.id.et_t4_job_id);
        t4OperationId = (EditText) findViewById(R.id.et_t4_operation_id);
        t4OpPoiseOrderId = (EditText) findViewById(R.id.et_t4_op_poise_order_id);
        t4ModFlag = (EditText) findViewById(R.id.et_t4_mod_flag);

        t4WorkcenterCode  = (EditText) findViewById(R.id.et_t4_workcenter_code);
        t4WorkcenterDesc = (EditText) findViewById(R.id.et_t4_workcenter_desc);
        t4WorkcenterId = (EditText) findViewById(R.id.et_t4_workcenter_id);
        t4TopEquimentId = (EditText) findViewById(R.id.et_t4_top_equiment_id);
        t4TopEquimentCode  = (EditText) findViewById(R.id.et_t4_top_equiment_code);
        t4OldEquimentName = (EditText) findViewById(R.id.et_t4_old_equiment_name);


        btnWorkStartDate = (Button) findViewById(R.id.btn_t4_work_start_date);
        btnWorkEndDate = (Button) findViewById(R.id.btn_t4_work_end_date);
        btnHolding = (Button) findViewById(R.id.btn_t4_holding);


        keyboardFocus(t4FileNo);
        keyboardFocus(t4EquipmentDesc);
        keyboardFocus(t4WorkerDesc);

        btnt4save = (Button) findViewById(R.id.btn_t4_save);


        auto = getSharedPreferences("appData_Log", Context.MODE_PRIVATE);
        //키보드 내리기

        //키보드
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);


        t4FileNo.requestFocus();

        initializeToolbar();

        GET_WORKCENTER_IN_AUTHORITY gET_WORKCENTER_IN_AUTHORITY = new GET_WORKCENTER_IN_AUTHORITY();
        gET_WORKCENTER_IN_AUTHORITY.execute(strIp, strSobId,strOrgId ,strUserId, strAssembly);


        //키보드 내리고 포커스 주기
        keyboardFocus(t4FileNo);
        keyboardFocus(t4EquipmentDesc);
        keyboardFocus(t4WorkerDesc);


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


        t4FileNo.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {

                if(getCurrentFocus() == t4FileNo && !s.toString().isEmpty() && s != null && t4JobId.getText().toString().equals("")){

                    FILE_NO_SCAN fILE_NO_SCAN = new FILE_NO_SCAN();
                    fILE_NO_SCAN.execute(strIp, strSobId,strOrgId ,t4FileNo.getText().toString(),t4WorkcenterId.getText().toString());

                }else{
                    return;
                }

            }
        });

        t4EquipmentDesc.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(getCurrentFocus() == t4EquipmentDesc && !s.toString().isEmpty() && s != null && t4TopEquimentId.getText().toString().equals("")){

                    LU_JJ_EQP lU_JJ_EQP = new LU_JJ_EQP();
                    lU_JJ_EQP.execute(strIp, strSobId,strOrgId, t4WorkcenterId.getText().toString(),t4EquipmentDesc.getText().toString());

                }else{
                    return;
                }
            }
        });

        t4WorkerDesc.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(getCurrentFocus() == t4WorkerDesc && !s.toString().isEmpty() && s != null && t4WorkerId.getText().toString().equals("")){

                    LU_WORKER lu_worker = new LU_WORKER();
                    lu_worker.execute(strIp, strSobId,strOrgId, t4WorkcenterId.getText().toString(), t4WorkerDesc.getText().toString());

                }else{
                    return;
                }
            }
        });



        t4FileNo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                ClearView();
                return false;
            }
        });
        t4WorkerDesc.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                t4WorkerDesc.setText("");
                t4WorkerId.setText("");
                return false;
            }
        });
        t4EquipmentDesc.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                t4EquipmentDesc.setText("");
                t4TopEquimentId.setText("");
                return false;
            }
        });




        btnWorkStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ScanModify==false){
                    btnt4save.setBackgroundColor(Color.YELLOW);
                    btnt4save.setTextColor(Color.BLACK);
                }
                t4WorkStartDate.setText(getNowDate());

            }
        });

        btnWorkEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ScanModify==false){
                    btnt4save.setBackgroundColor(Color.YELLOW);
                    btnt4save.setTextColor(Color.BLACK);
                }
                t4WorkEndDate.setText(getNowDate());

            }
        });




        btnt4save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(RegisterAdjustmentActivity.this);
                alert.setTitle("저장");
                alert.setMessage("수정한 내역을 저장하시겠습니까?");
                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(t4FileNo.getText().toString().isEmpty()){
                            Toast.makeText(getApplicationContext(), "File No는 필수입니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        JJ_UPDATE jj_update = new JJ_UPDATE();
                        jj_update.execute(strIp,t4JobId.getText().toString(),t4OperationId.getText().toString(),t4WorkcenterId.getText().toString(),strSobId ,strOrgId ,t4TopEquimentId.getText().toString()
                                , t4WorkStartDate.getText().toString().replaceAll(" ",""),t4WorkEndDate.getText().toString().replaceAll(" ",""),t4WorkerId.getText().toString()  , strUserId
                                ,  t4OpPoiseOrderId.getText().toString());


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

    private void saveColorChange() {
        if (ScanModify==false) {
            btnt4save.setBackgroundColor(Color.YELLOW);
            btnt4save.setTextColor(Color.BLACK);
        }
    }

    private void ClearView(){
        t4FileNo.setText("");
        t4ItemDesc.setText("");
        t4OperaionDesc.setText("");
        t4OpOrderSeq.setText("");
        t4Jojung1.setText("");
        t4Jojung2.setText("");
        t4OrderQty1.setText("");
        t4OrderQty2 .setText("");
        t4PoiseSubSeq .setText("");
        t4EquipmentDesc.setText("");
        t4WorkStartDate.setText("");
        t4WorkEndDate.setText("");
        t4WorkerDesc .setText("");
        t4EquipmentId .setText("");
        t4WorkerId.setText("");
        t4OpOrderId.setText("");
        t4JobId.setText("");
        t4OperationId.setText("");
        t4OpPoiseOrderId.setText("");
        t4ModFlag.setText("");
    }


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
                    t4WorkcenterCode.setText(job.getString("X_WORKCENTER_CODE"));
                    t4WorkcenterDesc.setText(job.getString("X_WORKCENTER_DESC"));
                    t4WorkcenterId.setText(job.getString("X_WORKCENTER_ID"));
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
            {  URL obj = new URL("http://" + urls[0] + "/TAIYO/FileNoScanJJ.jsp"); //주소 지정

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
                t4EquipmentDesc.requestFocus();
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
                    if(!FileScan.equals(t4FileNo.getText().toString())){
                        ScanModify = false;
                        t4FileNo.requestFocus();
                        t4FileNo.setText("");
                    }

                    return;
                }else {
                     JSONObject job = jarrayWorkLevel.getJSONObject(0);
                if(job.getString("Status").equals("S")) {

                    if(job.getString("WORK_ORDER_NO").equals("null")){
                        t4FileNo.setText("");
                    }else{
                        t4FileNo.setText(job.getString("WORK_ORDER_NO"));
                    }
                    if(job.getString("ITEM_DESCRIPTION").equals("null")){
                        t4ItemDesc.setText("");
                    }else{
                        t4ItemDesc.setText(job.getString("ITEM_DESCRIPTION"));
                    }
                    if(job.getString("OPERATION_DESCRIPTION").equals("null")){
                        t4OperaionDesc.setText("");
                    }else{
                        t4OperaionDesc.setText(job.getString("OPERATION_DESCRIPTION"));
                    }
                    if(job.getString("OP_ORDER_SEQ").equals("null")){
                        t4OpOrderSeq.setText("");
                    }else{
                        t4OpOrderSeq.setText(job.getString("OP_ORDER_SEQ"));
                    }
                    if(job.getString("JOJUNG_1").equals("null")){
                        t4Jojung1.setText("");
                    }else{
                        t4Jojung1.setText(job.getString("JOJUNG_1"));
                    }
                    if(job.getString("JOJUNG_2").equals("null")){
                        t4Jojung2.setText("");
                    }else{
                        t4Jojung2.setText(job.getString("JOJUNG_2"));
                    }
                    if(job.getString("ORDER_QTY_1").equals("null" )){
                        t4OrderQty1.setText("");
                    }else{
                        t4OrderQty1.setText(job.getString("ORDER_QTY_1"));
                    }
                    if(job.getString("ORDER_QTY_2").equals("null")){
                        t4OrderQty2.setText("");
                    }else{
                        t4OrderQty2.setText(job.getString("ORDER_QTY_2"));
                    }
                    if(job.getString("POISE_SUB_SEQ").equals("null")){
                        t4PoiseSubSeq.setText("");
                    }else{
                        t4PoiseSubSeq.setText(job.getString("POISE_SUB_SEQ"));
                    }
                    if(job.getString("EQUIPMENT_NAME").equals("null")){
                        t4EquipmentDesc.setText("");
                    }else{
                        t4EquipmentDesc.setText(job.getString("EQUIPMENT_NAME"));
                    }

                    if(job.getString("WORK_START_DATE").equals("null")){
                        t4WorkStartDate.setText("");
                    }else{
                        t4WorkStartDate.setText(job.getString("WORK_START_DATE"));
                    }
                    if(job.getString("WORK_END_DATE").equals("null")){
                        t4WorkEndDate.setText("");
                    }else{
                        t4WorkEndDate.setText(job.getString("WORK_END_DATE"));
                    }
                    if(job.getString("WORKER_DESC").equals("null")){
                        t4WorkerDesc.setText("");
                    }else{
                        t4WorkerDesc.setText(job.getString("WORKER_DESC"));
                    }
                    if(job.getString("EQUIPMENT_ID").equals("null")){
                        t4TopEquimentId.setText("");
                    }else{
                        t4TopEquimentId.setText(job.getString("EQUIPMENT_ID"));
                    }
                    if(job.getString("WORKER_ID").equals("null")){
                        t4WorkerId.setText("");
                    }else{
                        t4WorkerId.setText(job.getString("WORKER_ID"));
                    }
                    if(job.getString("OP_ORDER_ID").equals("null")){
                        t4OpOrderId.setText("");
                    }else{
                        t4OpOrderId.setText(job.getString("OP_ORDER_ID"));
                    }
                    if(job.getString("JOB_ID").equals("null")){
                        t4JobId.setText("");
                    }else{
                        t4JobId.setText(job.getString("JOB_ID"));
                    }
                    if(job.getString("OPERAITON_ID").equals("null")){
                        t4OperationId.setText("");
                    }else{
                        t4OperationId.setText(job.getString("OPERAITON_ID"));
                    }
                    if(job.getString("OP_POISE_ORDER_ID").equals("null")){
                        t4OpPoiseOrderId.setText("");
                    }else{
                        t4OpPoiseOrderId.setText(job.getString("OP_POISE_ORDER_ID"));
                    }
                    if(job.getString("MOD_FLAG").equals("null")){
                        t4ModFlag.setText("");
                    }else{
                        t4ModFlag.setText(job.getString("MOD_FLAG"));
                    }
                    FileScan = job.getString("WORK_ORDER_NO");
                }

                    t4EquipmentDesc.requestFocus();


                }



                ScanModify = false;

                if(t4ModFlag.equals("N")){

                    t4FileNo.setInputType(InputType.TYPE_NULL);
                    t4ItemDesc.setInputType(InputType.TYPE_NULL);
                    t4OperaionDesc.setInputType(InputType.TYPE_NULL);
                    t4OpOrderSeq.setInputType(InputType.TYPE_NULL);
                    t4Jojung1.setInputType(InputType.TYPE_NULL);
                    t4Jojung2.setInputType(InputType.TYPE_NULL);
                    t4OrderQty1.setInputType(InputType.TYPE_NULL);
                    t4OrderQty2.setInputType(InputType.TYPE_NULL);
                    t4PoiseSubSeq.setInputType(InputType.TYPE_NULL);
                    t4EquipmentDesc.setInputType(InputType.TYPE_NULL);
                    t4WorkStartDate.setInputType(InputType.TYPE_NULL);
                    t4WorkEndDate.setInputType(InputType.TYPE_NULL);
                    t4WorkerDesc.setInputType(InputType.TYPE_NULL);
                    t4EquipmentId.setInputType(InputType.TYPE_NULL);
                    t4WorkerId.setInputType(InputType.TYPE_NULL);
                    t4OpOrderId.setInputType(InputType.TYPE_NULL);
                    t4JobId.setInputType(InputType.TYPE_NULL);
                    t4OperationId.setInputType(InputType.TYPE_NULL);
                    t4OpPoiseOrderId.setInputType(InputType.TYPE_NULL);
                    t4ModFlag.setInputType(InputType.TYPE_NULL);

                }

            }catch (JSONException e)
            {
                e.printStackTrace();
                t4EquipmentDesc.requestFocus();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                t4EquipmentDesc.requestFocus();
            }
        }

    }
    protected class JJ_UPDATE extends AsyncTask<String, Void, String>
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
                    + "&P_EQUIPMENT_ID=" + urls[6]
                    + "&P_WORK_START_DATE=" + urls[7]
                    + "&P_WORK_END_DATE=" + urls[8]
                    + "&P_WORKER_ID=" + urls[9]
                    + "&P_USER_ID=" + urls[10]
                    + "&P_OP_POISE_ORDER_ID=" + urls[11]
                    ;

            try
            {
                //String ip = context.getApplicationContext().getResources().getString(R.string.ip);

                URL obj = new URL("http://" + urls[0] + "/TAIYO/JJUpdate.jsp"); //주소 지정

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
                    fILE_NO_SCAN.execute(strIp, strSobId,strOrgId ,t4FileNo.getText().toString(),t4WorkcenterId.getText().toString()); //다시 fill


                    btnt4save.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.dark_green));
                    btnt4save.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));

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

    // LU_TANK_TYPE
    protected class LU_JJ_EQP extends AsyncTask<String, Void, String>
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
            {  URL obj = new URL("http://" + urls[0] + "/TAIYO/LuJJEqp.jsp"); //주소 지정

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
                t4WorkerDesc.requestFocus();
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
                    if(!JjEqp.equals(t4EquipmentDesc.getText().toString())){
                        t4EquipmentDesc.requestFocus();
                        t4EquipmentDesc.setText("");
                    }

                    return;
                }

                JSONObject job = jarrayWorkLevel.getJSONObject(0);
                if(job.getString("Status").equals("S")){

                    t4OldEquimentName.setText(job.getString("OLD_EQUIPMENT_NAME"));
                    t4EquipmentDesc.setText(job.getString("TOP_EQUIPMENT_NAME"));
                    t4TopEquimentCode.setText(job.getString("TOP_EQUIPMENT_CODE"));
                    t4TopEquimentId.setText(job.getString("TOP_EQUIPMENT_ID"));

                    JjEqp = job.getString("TOP_EQUIPMENT_NAME");

                    saveColorChange();
                }
                t4WorkerDesc.requestFocus();

            }catch (JSONException e)
            {
                e.printStackTrace();
                t4WorkerDesc.requestFocus();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                t4WorkerDesc.requestFocus();
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
                t4FileNo.requestFocus();
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
                    if(!Worker.equals(t4WorkerDesc.getText().toString())){
                        t4WorkerDesc.requestFocus();
                        t4WorkerDesc.setText("");
                    }

                    return;
                }else{
                    JSONObject job = jarrayWorkLevel.getJSONObject(0);
                    if(job.getString("Status").equals("S")){

                        t4WorkerId.setText(job.getString("USER_ID"));
                        t4WorkerDesc.setText(job.getString("DESCRIPTION"));

                        Worker = job.getString("DESCRIPTION");
                        saveColorChange();

                    }
                    t4WorkerDesc.requestFocus();
                }




            }catch (JSONException e)
            {
                e.printStackTrace();
                t4FileNo.requestFocus();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                t4FileNo.requestFocus();
            }
        }
    }
}


