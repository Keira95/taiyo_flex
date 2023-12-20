package com.erp.Taiyo.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.erp.Taiyo.Dialog.HoldingDialog;
import com.erp.Taiyo.Dialog.LuOillerDialog;
import com.erp.Taiyo.adapter.FileNoProcessAdapter;
import com.erp.Taiyo.item.FileNoProcessListItem;

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

public class RegisterProcessActivity extends AppCompatActivity {

    Toolbar toolbar;
    String strIp, strUserName, strUserId, SDate, strMenuDesc;
    String filter = null;
    String strDate;

    EditText etT9ItemDesc, etT9FileNo, etT9WorkcenterDesc,etT9OperaionDesc, etT9WorkcenterId, etT9WorkcenterCode, etT9MoveTrxType, etT9MoveTrxTypeId, etT9MoveTrxTypeDesc, etT9ReleaseDateId,
            etT9OpPoiseOrderSeq , etT9OpUnitOrderSeq, etT9OpActualQty ,etT9Remark, etT9SectionDesc, etT9SplitFlag, etT9OpPoiseOrderId, etT9OpUnitOrderId, etT9OperationId,
            etT9JobId ,etT9HiddenFocus , etT9HoldingJobId , etT9HoldingOperationId , etT9HoldingFileNo;
    String strSobId = "70";
    String strOrgId = "701";
    String strAssembly = "PPMF2201";

    ProgressDialog asyncDialog;

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

    Button btnt9save;
    Button btnT9WorkcenterLookup, btnT9MoveLookup,  btnT9Holding;
    //키보드

    InputMethodManager imm;
    JSONObject jspSumObject = new JSONObject();

    FileNoProcessAdapter fileNoProcessAdapter = new FileNoProcessAdapter();
    FileNoProcessListItem fileNoProcessListItem = new FileNoProcessListItem();

    private boolean ScanModify = true;
    private boolean Mod_Flag = true;
    private String JobNo = "";
    private String updateJobNo ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_process_work9);
        Intent intent = getIntent();
        strIp = intent.getStringExtra("Ip");
        strUserId = intent.getStringExtra("O_USER_ID");
        strUserName = intent.getStringExtra("O_USER_NAME");
        strMenuDesc = intent.getStringExtra("TOP_MENU_DESC");


        mainLayout = findViewById(R.id.main_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvUserName = (TextView) findViewById(R.id.user_nm);
        tvMenu = (TextView) findViewById(R.id.tv_menu);


        etT9ItemDesc = (EditText) findViewById(R.id.et_t9_item_desc);
        etT9FileNo = (EditText) findViewById(R.id.et_t9_file_no);
        etT9WorkcenterDesc = (EditText) findViewById(R.id.et_t9_workcenter_desc);
        etT9OperaionDesc = (EditText) findViewById(R.id.et_t9_operaion_desc);
        etT9WorkcenterId = (EditText) findViewById(R.id.et_t9_workcenter_id);
        etT9WorkcenterCode = (EditText) findViewById(R.id.et_t9_workcenter_code);
        etT9MoveTrxType = (EditText) findViewById(R.id.et_t9_move_trx_type);
        etT9MoveTrxTypeId = (EditText) findViewById(R.id.et_t9_move_trx_type_id);
        etT9MoveTrxTypeDesc = (EditText) findViewById(R.id.et_t9_move_trx_desc);
        etT9ReleaseDateId = (EditText) findViewById(R.id.et_t9_release_date_id);
        etT9OpPoiseOrderSeq = (EditText) findViewById(R.id.et_t9_op_poise_order_seq);
        etT9OpUnitOrderSeq = (EditText) findViewById(R.id.et_t9_op_unit_order_seq);
        etT9OpActualQty = (EditText) findViewById(R.id.et_t9_op_actual_qty);
        etT9Remark = (EditText) findViewById(R.id.et_t9_remark);
        etT9SectionDesc = (EditText) findViewById(R.id.et_t9_section_desc);
        etT9SplitFlag = (EditText) findViewById(R.id.et_t9_split_flag);
        etT9OpPoiseOrderId = (EditText) findViewById(R.id.et_t9_op_poise_order_id);
        etT9OpUnitOrderId = (EditText) findViewById(R.id.et_t9_op_unit_order_id);
        etT9OperationId = (EditText) findViewById(R.id.et_t9_operation_id);
        etT9JobId = (EditText) findViewById(R.id.et_t9_job_id);
        etT9HiddenFocus = (EditText) findViewById(R.id.et_t9_hidden_focus);

        etT9HoldingJobId = (EditText) findViewById(R.id.et_t9_holding_job_id);
        etT9HoldingOperationId = (EditText) findViewById(R.id.et_t9_holding_operation_id);

        etT9HoldingFileNo = (EditText) findViewById(R.id.et_t9_holding_file_no);


        btnt9save = (Button) findViewById(R.id.btn_t9_save);
        btnT9WorkcenterLookup = (Button) findViewById(R.id.btn_t9_workcenter_lookup);
        btnT9MoveLookup = (Button) findViewById(R.id.btn_t9_move_lookup);
        btnT9Holding = (Button) findViewById(R.id.btn_t9_holding);
        lvInput = (ListView)  findViewById(R.id.lv_search);



        auto = getSharedPreferences("appData_Log", Context.MODE_PRIVATE);
        //키보드 내리기

        //키보드
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);




        initializeToolbar();


        keyboardFocus(etT9WorkcenterDesc);
        keyboardFocus(etT9MoveTrxTypeDesc);
        keyboardFocus(etT9HiddenFocus);

        keyboardFocus(etT9FileNo);
        etT9FileNo.requestFocus();

        //먼저 실행되는 함수
        GET_WORKCENTER_IN_AUTHORITY gET_WORKCENTER_IN_AUTHORITY = new GET_WORKCENTER_IN_AUTHORITY();
        gET_WORKCENTER_IN_AUTHORITY.execute(strIp, strSobId,strOrgId ,strUserId, strAssembly);


        etT9FileNo.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(ScanModify==false && getCurrentFocus() == etT9FileNo){
                    // btnt1save.setBackgroundColor(Color.YELLOW);
                    // btnt1save.setTextColor(Color.BLACK);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                if(getCurrentFocus() == etT9FileNo && !s.toString().isEmpty()){

                    FileNoProcessAdapter adapter = (FileNoProcessAdapter) lvInput.getAdapter();
                    String chk = "S";
                    for(int x=0; x< lvInput.getCount(); x++){
                        FileNoProcessListItem item = (FileNoProcessListItem) adapter.getItem(x);

                        if(item.getStrFileNo().equals(etT9FileNo.getText().toString())){
                            chk = "F";
                        }
                    }
                    if(chk.equals("S")){
                        FILE_NO_SCAN fILE_NO_SCAN = new FILE_NO_SCAN();
                        fILE_NO_SCAN.execute(strIp, strSobId,strOrgId, etT9FileNo.getText().toString() ,etT9WorkcenterId.getText().toString() ,strUserId, etT9MoveTrxType.getText().toString());
                    }else{
                        return;
                    }

                }else{
                    return;
                }
            }
        });

        btnT9Holding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FileNoProcessAdapter adapter = (FileNoProcessAdapter) lvInput.getAdapter();
                for(int x=0; x< lvInput.getCount(); x++){
                    FileNoProcessListItem item = (FileNoProcessListItem) adapter.getItem(x);

                    if(item.getStrChk().equals("√")){
                        etT9HoldingJobId.setText(item.getStrJobId());
                        etT9HoldingOperationId.setText(item.getStrOperationId());
                        etT9HoldingFileNo.setText(item.getStrFileNo());

                    }
                }

                if(etT9HoldingFileNo.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "File No는 필수입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }


                HoldingDialog holdingDialog = new HoldingDialog(RegisterProcessActivity.this);
                holdingDialog.call_Level_Dialog(etT9HoldingFileNo, strIp , strUserId , etT9HoldingOperationId, etT9HoldingJobId);



            }
        });

        lvInput.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FileNoProcessAdapter adapter = (FileNoProcessAdapter) lvInput.getAdapter();
                FileNoProcessListItem item = (FileNoProcessListItem) adapter.getItem(position);

                for (int i = 0; i < lvInput.getCount(); i++) {

                    if (i != position) {
                        FileNoProcessListItem proItem = (FileNoProcessListItem) adapter.getItem(i);
                        proItem.setStrChk("");
                    }
                }

                if (item.getStrChk().equals("√")) {
                    item.setStrChk("");
                } else {
                    item.setStrChk("√");
                }

                adapter.notifyDataSetChanged();
            }
        });

        etT9FileNo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                etT9FileNo.setText("");
                etT9OperaionDesc.setText("");
                etT9ItemDesc.setText("");
                return false;
            }
        });
        etT9WorkcenterDesc.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                etT9WorkcenterDesc.setText("");
                return false;
            }
        });
        etT9MoveTrxTypeDesc.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                etT9MoveTrxTypeDesc.setText("");
                return false;
            }
        });

        btnt9save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(RegisterProcessActivity.this);
                alert.setTitle("저장");
                alert.setMessage(etT9MoveTrxTypeDesc.getText().toString() +" 상태로 변경하시겠습니까?");
                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(lvInput.getCount() == 0){
                            Toast.makeText(getApplicationContext(), "처리할 데이터가 없습니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        showDialoag("show");
                        String SaveChk = "F";

                        FileNoProcessAdapter adapter =(FileNoProcessAdapter) lvInput.getAdapter();
                        for(int a=0; a < lvInput.getCount(); a++){
                            FileNoProcessListItem item = (FileNoProcessListItem)adapter.getItem(a);
                            if(item.getStrChk().equals("√")){

                                if(item.getStrSplitFlag().equals("Y")){ //SPLIT_FLAG 가 Y 이면 분할

                                    JOB_SPLIT_INSERT job_split_insert = new JOB_SPLIT_INSERT();
                                    job_split_insert.execute(strIp, strSobId,strOrgId, "" ,strUserId, "", item.getStrJobId() , "", item.getStrOperationSeqNo() , item.getStrOperationId(),
                                            item.getStrActualQty() , item.getStrActualQty() ,item.getStrActualQty() ,item.getStrActualQty() ,item.getStrActualQty() ,item.getStrActualQty() ,item.getStrActualQty(),
                                            strUserId,item.getStrOpPoiseOrderId(), item.getStrOpUnitOrderId());

                                }else{ //JOB_NOT_SPLIT_UPDATE 아니면 UPDATE
                                    updateJobNo = item.getStrJobNo();

                                    JOB_NOT_SPLIT_UPDATE job_not_split_update = new JOB_NOT_SPLIT_UPDATE();
                                    job_not_split_update.execute(strIp, strSobId,strOrgId,item.getStrOperationId(),item.getStrJobId(),strUserId,item.getStrOpPoiseOrderId(), item.getStrOpUnitOrderId());

                                }

                                SaveChk ="S";
                            }
                        }
                        if(!SaveChk.equals("S")){
                            Toast.makeText(getApplicationContext(), "선택된 데이터가 없습니다.", Toast.LENGTH_SHORT).show();
                            showDialoag("false");
                            return;
                        }
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

        btnT9WorkcenterLookup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LuOillerDialog luOillerDialog = new LuOillerDialog(RegisterProcessActivity.this);
                luOillerDialog.call_Workcetner(strIp ,etT9WorkcenterCode,etT9WorkcenterDesc,etT9WorkcenterId,strUserId,etT9OperationId);


            }
        });

        btnT9MoveLookup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                etT9MoveTrxType.setText("");
                LuOillerDialog luOillerDialog = new LuOillerDialog(RegisterProcessActivity.this);
                luOillerDialog.call_Move_Trx(strIp ,etT9MoveTrxType,etT9MoveTrxTypeDesc,etT9MoveTrxTypeId,strUserId,etT9MoveTrxType);


            }
        });


    }

    private void ClearView(){

        etT9MoveTrxType.setText("");
        etT9MoveTrxTypeId.setText("");
        etT9MoveTrxTypeDesc.setText("");
        etT9FileNo.setText("");
        etT9ItemDesc.setText("");
        etT9OperaionDesc.setText("");

        fileNoProcessAdapter.clearItem();
        lvInput.setAdapter(fileNoProcessAdapter);
        fileNoProcessAdapter.notifyDataSetChanged();
    }



    //11-20 다훈 추가
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
                    etT9WorkcenterCode.setText(job.getString("X_WORKCENTER_CODE"));
                    etT9WorkcenterDesc.setText(job.getString("X_WORKCENTER_DESC"));
                    etT9WorkcenterId.setText(job.getString("X_WORKCENTER_ID"));
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
    ////11-20 다훈 추가 끝


    // FILE_NO_SCAN
    protected class FILE_NO_SCAN extends AsyncTask<String, Void, String>
    {
        final FileNoProcessAdapter fileNoProcessAdapter = new FileNoProcessAdapter();
        protected  String doInBackground(String... urls)
        {
            StringBuffer jsonHtml = new StringBuffer();

            //서버로 보낼 데이터 설정
            String search_title = "W_SOB_ID=" + urls[1]
                    + "&W_ORG_ID=" + urls[2]
                    + "&W_FILE_NO=" +urls[3]
                    + "&W_WORKCENTER_ID=" +urls[4]
                    + "&P_USER_ID=" +urls[5]
                    + "&P_MOVE_TRX_TYPE=" +urls[6]
                    ;

            try
            {  URL obj = new URL("http://" + urls[0] + "/TAIYO/FileNoScanProcess.jsp"); //주소 지정

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
                    // Toast.makeText(getApplicationContext(), "데이터가 없습니다.", Toast.LENGTH_SHORT).show();
                    ScanModify = false;
                    return;
                }
                String chk ="√";
                for(int j=0; j< jarrayWorkLevel.length(); j++){
                    JSONObject job = jarrayWorkLevel.getJSONObject(j);
                    if(!job.getString("Status").equals("S")){
                        return;
                    }

                    if(jarrayWorkLevel.length() > 1){
                        if(Integer.parseInt(handleStringNull(job.getString("OP_POISE_ORDER_SEQ"))) == 1){
                            chk = "√";
                        }else{
                            chk ="";
                        }
                    }
                   fileNoProcessAdapter.addItem(chk,
                           handleStringNull(job.getString("FILE_NO")),
                           handleStringNull(job.getString("OP_POISE_ORDER_SEQ")),
                           handleStringNull(job.getString("OP_UNIT_ORDER_SEQ")),
                           handleStringNull(job.getString("RELEASE_DATE")),
                           handleStringNull(job.getString("ITEM_DESCRIPTION")),
                           handleStringNull(job.getString("WEEK_ACTUAL_QTY")),
                           handleStringNull(job.getString("REMARK")),
                           handleStringNull(job.getString("SECTION_DESC")),
                           handleStringNull(job.getString("JOB_NO")),
                           handleStringNull(job.getString("SPLIT_FLAG")),
                           handleStringNull(job.getString("OP_POISE_ORDER_ID")),
                           handleStringNull(job.getString("OP_UNIT_ORDER_ID")),
                           handleStringNull(job.getString("OPERATION_ID")),
                           handleStringNull(job.getString("OPERATION_DESC")),
                           handleStringNull(job.getString("OPERATION_SEQ_NO")),
                           handleStringNull(job.getString("JOB_ID"))
                   );

                    etT9FileNo.setText(job.getString("FILE_NO"));
                    etT9OperaionDesc.setText(job.getString("OPERATION_DESC"));
                    etT9ItemDesc.setText(job.getString("ITEM_DESCRIPTION"));


                }

                lvInput.setAdapter(fileNoProcessAdapter);
                etT9HiddenFocus.requestFocus();
                etT9FileNo.setText("");
                /*if(!t1ModeFlag.equals("N")){


                }
*/
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


    private String handleStringNull(String input) {

        String rtStr = input.equals("null") ? "" : input;

        return rtStr;
    }

    protected class PROCESS_UPDATE extends AsyncTask<String, Void, String>
    {
        //final LuLoctionListAdapter luLoctionListAdapter = new LuLoctionListAdapter();
        protected  String doInBackground(String... urls)
        {
            StringBuffer jsonHtml = new StringBuffer();

            //서버로 보낼 데이터 설정
            String search_title = "W_SOB_ID=" + urls[1]
                    + "&W_ORG_ID=" + urls[2]
                    + "&W_USER_ID=" + urls[3]
                    + "&W_JOB_NO=" + urls[4]
                    + "&W_WORKCENTER_ID=" + urls[5]
                    + "&W_MOVE_TRX_TYPE=" + urls[6]
                    ;

            try
            {
                //String ip = context.getApplicationContext().getResources().getString(R.string.ip);

                URL obj = new URL("http://" + urls[0] + "/TAIYO/ProcessUpdate.jsp"); //주소 지정

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
                    showDialoag("false");

                    Toast.makeText(getApplicationContext(), "작업상태가 ( "+etT9MoveTrxTypeDesc.getText().toString()+" ) 변경 되었습니다.", Toast.LENGTH_SHORT).show();
                     ClearView();

                    btnt9save.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.button_color));
                    btnt9save.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));

                }else{
                    Toast.makeText(getApplicationContext(), "오류입니다." +result, Toast.LENGTH_SHORT).show();
                    showDialoag("false");
                    return;
                }



            }catch (JSONException e)
            {
                e.printStackTrace();
                showDialoag("false");
            }
            catch (Exception e)
            {
                e.printStackTrace();
                showDialoag("false");
            }
        }
    }

    protected class JOB_SPLIT_INSERT extends AsyncTask<String, Void, String>
    {
        //final LuLoctionListAdapter luLoctionListAdapter = new LuLoctionListAdapter();
        protected  String doInBackground(String... urls)
        {
            StringBuffer jsonHtml = new StringBuffer();

            //서버로 보낼 데이터 설정
            String search_title = "P_SOB_ID=" + urls[1]
                    + "&P_ORG_ID=" + urls[2]
                    + "&P_SPLIT_TYPE_LCODE=" + urls[3]
                    + "&P_SPLIT_PERSON_ID=" + urls[4]
                    + "&P_SPLIT_REASON_COMMENT=" + urls[5]
                    + "&P_FROM_JOB_ID=" + urls[6]
                    + "&P_MES_JOB_NO=" + urls[7]
                    + "&P_OPERATION_SEQ_NO=" + urls[8]
                    + "&P_OPERATION_ID=" + urls[9]
                    + "&P_PNL_QTY=" + urls[10]
                    + "&P_UOM_QTY=" + urls[11]
                    + "&P_ARRAY_QTY=" + urls[12]
                    + "&P_ARRAY1_MTX_QTY=" + urls[13]
                    + "&P_ARRAY2_MTX_QTY=" + urls[14]
                    + "&P_ARRAY3_MTX_QTY=" + urls[15]
                    + "&P_ARRAY4_MTX_QTY=" + urls[16]
                    + "&P_USER_ID=" + urls[17]
                    + "&P_OP_POISE_ORDER_ID=" + urls[18]
                    + "&P_OP_UNIT_ORDER_ID=" + urls[19]
                    ;

            try
            {
                //String ip = context.getApplicationContext().getResources().getString(R.string.ip);

                URL obj = new URL("http://" + urls[0] + "/TAIYO/JobSplitInsert.jsp"); //주소 지정

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
                    showDialoag("false");

                    JobNo = job.getString("P_TO_JOB_NO");

                    PROCESS_UPDATE pROCESS_UPDATE = new PROCESS_UPDATE();
                    pROCESS_UPDATE.execute(strIp,strSobId,strOrgId, strUserId, JobNo, etT9WorkcenterId.getText().toString() ,etT9MoveTrxType.getText().toString());

                }else{
                    Toast.makeText(getApplicationContext(), "오류입니다"+ result, Toast.LENGTH_SHORT).show();
                    showDialoag("false");
                    return;
                }



            }catch (JSONException e)
            {
                e.printStackTrace();
                showDialoag("false");
            }
            catch (Exception e)
            {
                e.printStackTrace();
                showDialoag("false");
            }
        }
    }

    protected class JOB_NOT_SPLIT_UPDATE extends AsyncTask<String, Void, String>
    {
        //final LuLoctionListAdapter luLoctionListAdapter = new LuLoctionListAdapter();
        protected  String doInBackground(String... urls)
        {
            StringBuffer jsonHtml = new StringBuffer();

            //서버로 보낼 데이터 설정
            String search_title = "P_SOB_ID=" + urls[1]
                    + "&P_ORG_ID=" + urls[2]
                    + "&P_OPERATION_ID=" + urls[3]
                    + "&P_JOB_ID=" + urls[4]
                    + "&P_USER_ID=" + urls[5]
                    + "&P_OP_POISE_ORDER_ID=" + urls[6]
                    + "&P_OP_UNIT_ORDER_ID=" + urls[7]
                    ;

            try
            {
                //String ip = context.getApplicationContext().getResources().getString(R.string.ip);

                URL obj = new URL("http://" + urls[0] + "/TAIYO/JobNotSplitUpdate.jsp"); //주소 지정

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
                    showDialoag("false");

                    PROCESS_UPDATE pROCESS_UPDATE = new PROCESS_UPDATE();
                    pROCESS_UPDATE.execute(strIp,strSobId,strOrgId, strUserId, updateJobNo, etT9WorkcenterId.getText().toString() ,etT9MoveTrxType.getText().toString());


                }else{
                    Toast.makeText(getApplicationContext(), "오류입니다"+ result, Toast.LENGTH_SHORT).show();
                    showDialoag("false");
                    return;
                }



            }catch (JSONException e)
            {
                e.printStackTrace();
                showDialoag("false");
            }
            catch (Exception e)
            {
                e.printStackTrace();
                showDialoag("false");
            }
        }
    }




    public void showDialoag(String result) {

        //  AlertDialog.Builder alert = new AlertDialog.Builder(MainReleaseVerson2Activity.this);

        if(result.equals("show")){
            asyncDialog = new ProgressDialog(RegisterProcessActivity.this);
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("처리중입니다. 잠시만 기다려주세요.");
            asyncDialog.setCancelable(false);
            asyncDialog.show();


        }else {
            asyncDialog.dismiss();

        }
    }



}

