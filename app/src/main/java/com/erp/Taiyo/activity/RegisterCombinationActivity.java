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
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.erp.Taiyo.Dialog.HoldingDialog;
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
import java.util.Date;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;


public class RegisterCombinationActivity extends AppCompatActivity {

    Toolbar toolbar;
    String strIp, strUserName, strUserId, SDate, strMenuDesc;
    String filter = null;
    String strDate;

    EditText etT2FileNoScan, etT2ItemDesc,etT2MixStartTime,etT2OperaionDesc, etT2MixTankDescScan,etT2EquipmentScan,etT2Stir1StartDate , etT2Stir1EndDate,
            etT2Stir1WorkerName, etT2Stir2StartDate, etT2Stir2EndDate,etT2Stir2WorkerName, etT2Stir3StartDate, etT2Stir3EndDate, etT2Stir3WorkerName, etT2MixEndTime,
            etT2Stir1WorkerNameScan, etT2Stir2WorkerNameScan, etT2Stir3WorkerNameScan, etH2HiddenWorker,


    //숨김값
    etH2XworkId, etH2XworkCode, etH2XworkDesc,
    //숨김값 file 스캔
    etH2JobId, etH2OperationId, etH2ModFlag, etH2TankLcode, etH2Stir1WorkerId, etH2Stir2WorkerId, etH2Stir3WorkerId,
    //숨김값 탱크
    etH2EntryCode, etH2LookupEntryId,
    //숨김값 배합
    etH2EquipmentId, etH2EquipmentCode, etH2OldEquipmentName,
    //세이브 숨김값
    etH2SobId, etH2OrgId, etH2WipJobEntitiesSubId;

    Button bT2Recent, btnT2MixStartTime, btnT2Stir1StartDate, btnT2Stir1EndDate , btnT2Stir2StartDate, btnT2Stir2EndDate, btnT2Stir3StartDate,
            btnT2Stir3EndDate, btnT2MixEndTime, bT2Save,btnHolding;




    String strSobId = "70";
    String strOrgId = "701";
    String strAssemplyDeac ="";

    FrameLayout log_panel;
    SharedPreferences auto;
    TextView tvLog;
    TextView tvUserName;

    private boolean FileScan = true;
    private String Jobno = "";
    private String TankDesc = "";
    private String LuBhEQpDesc = "";
    private String Worker1 = "";
    private String Worker2 = "";
    private String Worker3 = "";





    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_combination_work2);


        Intent intent = getIntent();
        strIp = intent.getStringExtra("Ip");
        strUserId = intent.getStringExtra("O_USER_ID");
        strUserName = intent.getStringExtra("O_USER_NAME");
        strAssemplyDeac ="PPMF2202";

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvUserName = (TextView) findViewById(R.id.user_nm);


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
        etT2Stir1WorkerNameScan  = (EditText) findViewById(R.id.et_t2_stir_1_worker_name_scan);
        etT2Stir2WorkerNameScan  = (EditText) findViewById(R.id.et_t2_stir_2_worker_name_scan);
        etT2Stir3WorkerNameScan  = (EditText) findViewById(R.id.et_t2_stir_3_worker_name_scan);



        //WORKCENTER_IN_AUTHORITY 숨김값
        etH2XworkId = (EditText) findViewById(R.id.et_h2_x_work_id);
        etH2XworkCode = (EditText) findViewById(R.id.et_h2_x_work_code);
        etH2XworkDesc = (EditText) findViewById(R.id.et_h2_x_work_desc);

        //파일스캔 숨김값들
        etH2TankLcode = (EditText) findViewById(R.id.et_h2_tank_lcode);
        etH2Stir1WorkerId = (EditText) findViewById(R.id.et_h2_stir_1_worker_id);
        etH2Stir2WorkerId = (EditText) findViewById(R.id.et_h2_stir_2_worker_id);
        etH2Stir3WorkerId = (EditText) findViewById(R.id.et_h2_stir_3_worker_id);
        etH2HiddenWorker = (EditText) findViewById(R.id.et_h2_hidden_worker);


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


        //세이브 숨김갑
        etH2SobId = (EditText) findViewById(R.id.et_h2_sob_id);
        etH2OrgId = (EditText) findViewById(R.id.et_h2_org_id);
        etH2WipJobEntitiesSubId = (EditText) findViewById(R.id.et_h2_wip_job_entities_sub_id);





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
        btnHolding = (Button) findViewById(R.id.btn_t2_holding);

        bT2Save = (Button) findViewById(R.id.btn_t2_save);

        WorkCenter workCenter = new WorkCenter();
        workCenter.execute(strSobId, strOrgId,strUserId,strAssemplyDeac);

        auto = getSharedPreferences("appData_Log", Context.MODE_PRIVATE);

        etT2FileNoScan.requestFocus();

        initializeToolbar();

        //키보드 내리고 포커스 주기
        keyboardFocus(etT2FileNoScan);
        keyboardFocus(etT2MixTankDescScan);
        keyboardFocus(etT2EquipmentScan);
        keyboardFocus(etT2Stir1WorkerNameScan);
        keyboardFocus(etT2Stir2WorkerNameScan);
        keyboardFocus(etT2Stir3WorkerNameScan);

        etT2FileNoScan.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if (getCurrentFocus() == etT2FileNoScan && !etT2FileNoScan.getText().toString().isEmpty() && s != null && etH2JobId.getText().toString().equals("")) {

                    FileNoScanBH fileNoScanBH = new FileNoScanBH();
                    //fileNoScanGR.execute(strSobId, strOrgId, edT2FileNoScan.getText().toString(), strUserId); 기존
                    fileNoScanBH.execute(strSobId, strOrgId, etT2FileNoScan.getText().toString(), etH2XworkId.getText().toString());

                }else{
                    return;
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
                if (getCurrentFocus() == etT2MixTankDescScan && !s.toString().isEmpty() && s != null && etH2TankLcode.getText().toString().equals("")) {
                    LuTankType luTankType = new LuTankType();
                    luTankType.execute(strSobId, strOrgId, etT2MixTankDescScan.getText().toString());
                }else{
                    return;
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

                if (getCurrentFocus() == etT2EquipmentScan && !s.toString().isEmpty() && s != null && etH2EquipmentId.getText().toString().equals("")) {
                    LuBhEQp luBhEQp = new LuBhEQp();
                    luBhEQp.execute(strSobId, strOrgId,etH2XworkId.getText().toString(), etT2EquipmentScan.getText().toString());   //eth2xworkid를 usrid  etH2XworkId.getText().toString()

                }else{
                    return;
                }

            }
        });

        etT2Stir1WorkerNameScan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (getCurrentFocus() == etT2Stir1WorkerNameScan && !s.toString().isEmpty() && s != null && etH2Stir1WorkerId.getText().toString().equals("")) {

                        LU_WORKER luWorker = new LU_WORKER();
                        etH2HiddenWorker.setText("worker1");
                        luWorker.execute(strIp, strSobId, strOrgId, etH2XworkId.getText().toString(), etT2Stir1WorkerNameScan.getText().toString());

                }else{
                    return;
                }
            }
        });


        etT2Stir2WorkerNameScan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (getCurrentFocus() == etT2Stir2WorkerNameScan && !s.toString().isEmpty() && s != null && etH2Stir2WorkerId.getText().toString().equals("")) {

                    LU_WORKER luWorker = new LU_WORKER();
                    etH2HiddenWorker.setText("worker2");
                    luWorker.execute(strIp, strSobId, strOrgId,etH2XworkId.getText().toString(), etT2Stir2WorkerNameScan.getText().toString());




                }else{
                    return;
                }
            }
        });


        etT2Stir3WorkerNameScan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (getCurrentFocus() == etT2Stir3WorkerNameScan && !s.toString().isEmpty() && s != null && etH2Stir3WorkerId.getText().toString().equals("")) {

                    LU_WORKER luWorker = new LU_WORKER();
                    etH2HiddenWorker.setText("worker3");
                    luWorker.execute(strIp, strSobId, strOrgId,etH2XworkId.getText().toString(), etT2Stir3WorkerNameScan.getText().toString());

                }else{
                    return;
                }
            }
        });


        btnHolding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(etT2FileNoScan.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "File No는 필수입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                HoldingDialog holdingDialog = new HoldingDialog(RegisterCombinationActivity.this);
                holdingDialog.call_Level_Dialog(etT2FileNoScan, strIp , strUserId, etH2OperationId,etH2JobId);
            }
        });

        etT2FileNoScan.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                etT2FileNoScan.setText("");
                etH2JobId.setText("");
                etT2ItemDesc.setText("");
                etT2MixStartTime.setText("");
                etT2OperaionDesc.setText("");
                etT2MixTankDescScan.setText("");
                etT2EquipmentScan.setText("");
                etT2Stir1StartDate.setText("");
                etT2Stir1EndDate.setText("");
                etT2Stir2StartDate.setText("");
                etT2Stir2EndDate.setText("");
                etT2Stir3StartDate.setText("");
                etT2Stir3EndDate.setText("");
                etT2MixEndTime.setText("");
                etT2Stir1WorkerNameScan.setText("");
                etT2Stir2WorkerNameScan.setText("");
                etT2Stir3WorkerNameScan.setText("");
                etH2Stir1WorkerId.setText("");
                etH2Stir2WorkerId.setText("");
                etH2Stir3WorkerId.setText("");
                etH2TankLcode.setText("");
                etH2EquipmentId.setText("");
                return false;
            }
        });

        etT2MixTankDescScan.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                etT2MixTankDescScan.setText("");
                etH2TankLcode.setText("");
                return false;
            }
        });

        etT2EquipmentScan.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                etT2EquipmentScan.setText("");
                etH2EquipmentId.setText("");
                return false;
            }
        });

        etT2Stir1WorkerNameScan.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                etT2Stir1WorkerNameScan.setText("");
                etH2Stir1WorkerId.setText("");
                return false;
            }
        });

        etT2Stir2WorkerNameScan.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                etT2Stir2WorkerNameScan.setText("");
                etH2Stir2WorkerId.setText("");
                return false;
            }
        });
        etT2Stir3WorkerNameScan.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                etT2Stir3WorkerNameScan.setText("");
                etH2Stir3WorkerId.setText("");
                return false;
            }
        });





        btnT2MixStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(FileScan==false){
                    bT2Save.setBackgroundColor(Color.YELLOW);
                    bT2Save.setTextColor(Color.BLACK);
                }
                etT2MixStartTime.setText(getNowDate());
            }
        });

        btnT2Stir1StartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(FileScan==false){
                    bT2Save.setBackgroundColor(Color.YELLOW);
                    bT2Save.setTextColor(Color.BLACK);
                }
                etT2Stir1StartDate.setText(getNowDate());
            }
        });

        btnT2Stir1EndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(FileScan==false){
                    bT2Save.setBackgroundColor(Color.YELLOW);
                    bT2Save.setTextColor(Color.BLACK);
                }
                etT2Stir1EndDate.setText(getNowDate());
            }
        });

        btnT2Stir2StartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(FileScan==false){
                    bT2Save.setBackgroundColor(Color.YELLOW);
                    bT2Save.setTextColor(Color.BLACK);
                }
                etT2Stir2StartDate.setText(getNowDate());
            }
        });
        btnT2Stir2EndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(FileScan==false){
                    bT2Save.setBackgroundColor(Color.YELLOW);
                    bT2Save.setTextColor(Color.BLACK);
                }
                etT2Stir2EndDate.setText(getNowDate());
            }
        });
        btnT2Stir3StartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(FileScan==false){
                    bT2Save.setBackgroundColor(Color.YELLOW);
                    bT2Save.setTextColor(Color.BLACK);
                }
                etT2Stir3StartDate.setText(getNowDate());
            }
        });
        btnT2Stir3EndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(FileScan==false){
                    bT2Save.setBackgroundColor(Color.YELLOW);
                    bT2Save.setTextColor(Color.BLACK);
                }
                etT2Stir3EndDate.setText(getNowDate());
            }
        });

        btnT2MixEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(FileScan==false){
                    bT2Save.setBackgroundColor(Color.YELLOW);
                    bT2Save.setTextColor(Color.BLACK);
                }
                etT2MixEndTime.setText(getNowDate());

            }
        });


        //        다이얼로그
        bT2Recent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LuComLatelyDialog luComLatelyDialog = new LuComLatelyDialog(RegisterCombinationActivity.this);
                luComLatelyDialog.call_Lately_Dialog(strIp, strSobId, strOrgId, etT2FileNoScan);  //가져오는거 다시 보기
            }
        });


        bT2Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(RegisterCombinationActivity.this);
                alert.setTitle("저장");
                alert.setMessage("저장하시겠습니까?");
                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(etT2FileNoScan.getText().toString().isEmpty()){
                            Toast.makeText(getApplicationContext(), "File No는 필수입니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        BhUpdate bhUpdate = new BhUpdate();
                        bhUpdate.execute(etH2JobId.getText().toString(), etH2OperationId.getText().toString(), etH2XworkId.getText().toString(), strSobId, strOrgId, "", etH2TankLcode.getText().toString(),
                                etH2EquipmentId.getText().toString(), etT2MixStartTime.getText().toString().replaceAll(" ", ""), etT2Stir1StartDate.getText().toString().replaceAll(" ", ""), etT2Stir1EndDate.getText().toString().replaceAll(" ", ""),
                                etH2Stir1WorkerId.getText().toString(), etT2Stir2StartDate.getText().toString().replaceAll(" ", ""), etT2Stir2EndDate.getText().toString().replaceAll(" ", ""), etH2Stir2WorkerId.getText().toString(),
                                etT2Stir3StartDate.getText().toString().replaceAll(" ", ""), etT2Stir3EndDate.getText().toString().replaceAll(" ", ""), etH2Stir3WorkerId.getText().toString(), etT2MixEndTime.getText().toString().replaceAll(" ", ""),
                                strUserId);
                    }

                });
                alert.setNegativeButton("취소", new DialogInterface.OnClickListener() { //no
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });
                alert.show();

            }
            });
    }


    //노란색으로 오류 나는 이유는 시각 장애가 있는 사람들에 대해 생각해 보도록 상기시키는 것

    //키보드 내리고 포커스 주는 함수
    @SuppressLint("ClickableViewAccessibility") //노란색 경고 없애기
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

    //저장버튼 색 바꾸는 함수
    private void saveColorChange() {
        if (FileScan==false) {
            bT2Save.setBackgroundColor(Color.YELLOW);
            bT2Save.setTextColor(Color.BLACK);
        }
    }

    private void ClearView(){
        etT2FileNoScan.setText("");
        etT2OperaionDesc.setText("");
        etT2MixStartTime.setText("");
        etH2TankLcode.setText("");
        etT2MixTankDescScan.setText("");
        etH2EquipmentId.setText("");
        etT2EquipmentScan.setText("");
        etT2Stir1StartDate .setText("");
        etT2Stir1EndDate .setText("");
        etH2Stir1WorkerId.setText("");
        etT2Stir1WorkerName.setText("");
        etT2Stir2StartDate.setText("");
        etT2Stir2EndDate .setText("");
        etH2Stir2WorkerId .setText("");
        etT2Stir2WorkerName.setText("");
        etT2Stir3StartDate.setText("");
        etT2Stir3EndDate.setText("");
        etH2Stir3WorkerId.setText("");
        etT2Stir3WorkerName.setText("");
        etT2MixEndTime.setText("");
        etH2OperationId.setText("");
        etH2ModFlag.setText("");
    }

    private void initializeToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.white));

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

    //workcenter
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
    //파일넘버스캔
    protected class FileNoScanBH extends AsyncTask<String, Void, String> {

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
                etT2MixTankDescScan.requestFocus();
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



                if (resultArray.length() < 1) {
                    if(!Jobno.equals(etT2FileNoScan.getText().toString()))
                    {
                        FileScan = false;
                        etT2FileNoScan.requestFocus();
                        etT2FileNoScan.setText("");
                    }

                    return;
                }else{
                    JSONObject job = resultArray.getJSONObject(0); // JSON 오브젝트 파싱
                    String status = job.getString("Status");


                    if (status.equals("S") ) {

                        etT2FileNoScan.setText(job.getString("WORK_ORDER_NO"));
                        etT2ItemDesc.setText(job.getString("ITEM_DESCRIPTION"));
                        etT2OperaionDesc.setText(job.getString("OPERATION_DESCRIPTION"));
                        if(job.getString("MIX_START_DATE").equals("null")){
                            etT2MixStartTime.setText("");
                        }else{
                            etT2MixStartTime.setText(job.getString("MIX_START_DATE"));
                        }



                        if(job.getString("MIX_TANK_LCODE").equals("null")){
                            etH2TankLcode.setText("");
                        }else{
                            etH2TankLcode.setText(job.getString("MIX_TANK_LCODE"));
                        }

                        if(job.getString("MIX_TANK_DESC").equals("null")){
                            etT2MixTankDescScan.setText("");
                        }else{
                            etT2MixTankDescScan.setText(job.getString("MIX_TANK_DESC"));
                        }


                        if(job.getString("EQUIPMENT_ID").equals("null")){
                            etH2EquipmentId.setText("");
                        }else{
                            etH2EquipmentId.setText(job.getString("EQUIPMENT_ID"));
                        }


                        if(job.getString("EQUIPMENT_NAME").equals("null")){
                            etT2EquipmentScan.setText("");
                        }else{
                            etT2EquipmentScan .setText(job.getString("EQUIPMENT_NAME"));
                        }

                        if(job.getString("STIR_1_START_DATE").equals("null")){
                            etT2Stir1StartDate.setText("");
                        }else{
                            etT2Stir1StartDate.setText(job.getString("STIR_1_START_DATE"));

                        }
                        if(job.getString("STIR_1_END_DATE").equals("null")){
                            etT2Stir1EndDate.setText("");
                        }else{
                            etT2Stir1EndDate.setText(job.getString("STIR_1_END_DATE"));
                        }


                        if(job.getString("STIR_1_WORKER_ID").equals("null")){
                            etH2Stir1WorkerId.setText("");
                        }else{
                            etH2Stir1WorkerId.setText(job.getString("STIR_1_WORKER_ID"));
                        }

                        if(job.getString("STIR_1_WORKER_NAME").equals("null")){
                            etT2Stir1WorkerNameScan.setText("");
                        }else{
                            etT2Stir1WorkerNameScan.setText(job.getString("STIR_1_WORKER_NAME"));
                        }
                        if(job.getString("STIR_2_START_DATE").equals("null")){
                            etT2Stir2StartDate.setText("");
                        }else{
                            etT2Stir2StartDate.setText(job.getString("STIR_2_START_DATE"));
                        }
                        if(job.getString("STIR_2_END_DATE").equals("null")){
                            etT2Stir2EndDate.setText("");
                        }else{
                            etT2Stir2EndDate.setText(job.getString("STIR_2_END_DATE"));
                        }


                        if(job.getString("STIR_2_WORKER_ID").equals("null")){
                            etH2Stir2WorkerId.setText("");
                        }else{
                            etH2Stir2WorkerId.setText(job.getString("STIR_2_WORKER_ID"));
                        }


                        if(job.getString("STIR_2_WORKER_NAME").equals("null")){
                            etT2Stir2WorkerNameScan.setText("");
                        }else{
                            etT2Stir2WorkerNameScan.setText(job.getString("STIR_2_WORKER_NAME"));
                        }
                        if(job.getString("STIR_3_START_DATE").equals("null")){
                            etT2Stir3StartDate.setText("");
                        }else{
                            etT2Stir3StartDate.setText(job.getString("STIR_3_START_DATE"));
                        }
                        if(job.getString("STIR_3_END_DATE").equals("null")){
                            etT2Stir3EndDate.setText("");
                        }else{
                            etT2Stir3EndDate.setText(job.getString("STIR_3_END_DATE"));
                        }


                        if(job.getString("STIR_3_WORKER_ID").equals("null")){
                            etH2Stir3WorkerId.setText("");
                        }else{
                            etH2Stir3WorkerId.setText(job.getString("STIR_3_WORKER_ID"));
                        }

                        if(job.getString("STIR_3_WORKER_NAME").equals("null")){
                            etT2Stir3WorkerNameScan.setText("");
                        }else{
                            etT2Stir3WorkerNameScan.setText(job.getString("STIR_3_WORKER_NAME"));
                        }

                        if(job.getString("MIX_END_DATE").equals("null")){
                            etT2MixEndTime.setText("");
                        }else{
                            etT2MixEndTime.setText(job.getString("MIX_END_DATE"));
                        }

                        etH2JobId.setText(job.getString("JOB_ID"));
                        etH2OperationId.setText(job.getString("OPERATION_ID"));
                        etH2ModFlag.setText(job.getString("MOD_FLAG"));
                        Jobno = job.getString("WORK_ORDER_NO");

                    }
                    etT2MixTankDescScan.requestFocus();
                }







                    if(etH2ModFlag.getText().toString().equals("N")){  //수정이 가능하지않으면 tape_null

                        etT2FileNoScan.setEnabled(false);
                        etT2OperaionDesc.setEnabled(false);
                        etT2MixStartTime.setEnabled(false);
                        etH2TankLcode.setEnabled(false);
                        etT2MixTankDescScan.setEnabled(false);
                        etH2EquipmentId.setEnabled(false);
                        etT2EquipmentScan.setEnabled(false);
                        etT2Stir1StartDate.setEnabled(false);
                        etT2Stir1EndDate.setEnabled(false);
                        etH2Stir1WorkerId.setEnabled(false);
                        etT2Stir1WorkerNameScan.setEnabled(false);
                        etT2Stir2StartDate.setEnabled(false);
                        etT2Stir2EndDate.setEnabled(false);
                        etH2Stir2WorkerId.setEnabled(false);
                        etT2Stir2WorkerNameScan.setEnabled(false);
                        etT2Stir3StartDate.setEnabled(false);
                        etT2Stir3EndDate.setEnabled(false);
                        etH2Stir3WorkerId.setEnabled(false);
                        etT2Stir3WorkerNameScan.setEnabled(false);
                        etT2MixEndTime.setEnabled(false);
                        etH2OperationId.setEnabled(false);
                        etH2ModFlag.setEnabled(false);
                        btnT2MixStartTime.setEnabled(false);
                        btnT2MixEndTime.setEnabled(false);
                        btnT2Stir1StartDate.setEnabled(false);
                        btnT2Stir1EndDate.setEnabled(false);
                        btnT2Stir2StartDate.setEnabled(false);
                        btnT2Stir2EndDate.setEnabled(false);
                        btnT2Stir3StartDate.setEnabled(false);
                        btnT2Stir3EndDate.setEnabled(false);
                        bT2Save.setEnabled(false);



                    }
                    //etT2MixTankDescScan.requestFocus();
                    //etT2MixTankDescScan.setFocusableInTouchMode(true);

                FileScan = false;



            } catch (JSONException e) {
                e.printStackTrace();
                etT2MixTankDescScan.requestFocus();

            } catch (Exception e) {
                e.printStackTrace();
                etT2MixTankDescScan.requestFocus();

            }
        }
        // 스트링 null 처리하는 메서드
//        private String handleStringNull(String input) {
//
//            String rtStr = input.equals("null") ? "" : input;
//
//            return rtStr;
//        }

    }
    //lutanktype 설비 탱크
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
                etT2EquipmentScan.requestFocus();

            }

            return jsonHtml.toString(); //결과값 리턴
        }

        protected void onPostExecute(String result) {
            //페이지 결과값 파싱
            try {
                JSONObject RESURT = new JSONObject(result); // JSON 오브젝트 받음
                JSONArray resultArray = RESURT.getJSONArray("RESULT"); // JSONArray 파싱


                if (resultArray.length() < 1) {
                    if(!TankDesc.equals(etT2MixTankDescScan.getText().toString())){
                        etT2MixTankDescScan.requestFocus();
                        etT2MixTankDescScan.setText("");

                    }
                return;

            }else{
                JSONObject job = resultArray.getJSONObject(0); // JSON 오브젝트 파싱
                String status = job.getString("Status");


                if (status.equals("S")) {
                    etH2TankLcode.setText(job.getString("ENTRY_CODE")); // entryDesc
                    etT2MixTankDescScan.setText(job.getString("ENTRY_DESCRIPTION")); // edT2TankScan
                    etH2LookupEntryId.setText(job.getString("LOOKUP_ENTRY_ID")); // lookupEntryId
                    saveColorChange();
                    TankDesc = job.getString("ENTRY_DESCRIPTION");
                }

                    etT2EquipmentScan.requestFocus();
            }

            } catch (JSONException e) {
                e.printStackTrace();
               etT2MixTankDescScan.setText("");

            } catch (Exception e) {
                e.printStackTrace();
              //etT2EquipmentScan.requestFocus();
              etT2MixTankDescScan.setText("");

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
                etT2Stir1WorkerNameScan.requestFocus();

            }

            return jsonHtml.toString(); //결과값 리턴
        }

        protected void onPostExecute(String result) {
            //페이지 결과값 파싱
            try {

                JSONObject RESURT = new JSONObject(result); //JSON 오브젝트 받음
                JSONArray resultArray = RESURT.getJSONArray("RESULT"); //JSONArray 파싱

                //JSONObject job = resultArray.getJSONObject(0); //JSON 오브젝트 파싱


                if (resultArray.length() < 1) {
                    if (!LuBhEQpDesc.equals(etT2EquipmentScan.getText().toString())){
                        etT2EquipmentScan.requestFocus();
                        etT2EquipmentScan.setText("");
                       }
                    return;
                }else{
                    JSONObject job = resultArray.getJSONObject(0); // JSON 오브젝트 파싱
                    String status = job.getString("Status");

                    if (status.equals("S")) {

                        etH2EquipmentId.setText(job.getString("TOP_EQUIPMENT_ID"));
                        etH2EquipmentCode.setText(job.getString("TOP_EQUIPMENT_CODE"));
                        etT2EquipmentScan.setText(job.getString("TOP_EQUIPMENT_NAME"));
                        etH2OldEquipmentName.setText(job.getString("OLD_EQUIPMENT_NAME"));

                        LuBhEQpDesc = job.getString("TOP_EQUIPMENT_NAME");

                        saveColorChange();
                    }


                    etT2Stir1WorkerNameScan.requestFocus();
                    }



            } catch (JSONException e) {
                e.printStackTrace();
                etT2Stir1WorkerNameScan.requestFocus();

            } catch (Exception e) {
                e.printStackTrace();
                etT2Stir1WorkerNameScan.requestFocus();

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
                    if(etH2HiddenWorker.getText().toString().equals("worker1") && !Worker1.equals(etT2Stir1WorkerNameScan.getText().toString()))
                    {
                        etT2Stir1WorkerNameScan.requestFocus();
                        etT2Stir1WorkerNameScan.setText("");
                    }else if(etH2HiddenWorker.getText().toString().equals("worker2") && !Worker2.equals(etT2Stir2WorkerNameScan.getText().toString()))
                    {
                        etT2Stir2WorkerNameScan.requestFocus();
                        etT2Stir2WorkerNameScan.setText("");
                    }else if(etH2HiddenWorker.getText().toString().equals("worker3") && !Worker3.equals(etT2Stir3WorkerNameScan.getText().toString()))
                    {
                        etT2Stir3WorkerNameScan.requestFocus();
                        etT2Stir3WorkerNameScan.setText("");
                    }

                    return;
                }else{
                    JSONObject job = jarrayWorkLevel.getJSONObject(0);
                    if(job.getString("Status").equals("S")){


                        if(etH2HiddenWorker.getText().toString().equals("worker1")){ //작업자 스캔 1

                            etH2Stir1WorkerId.setText(job.getString("USER_ID"));
                            etT2Stir1WorkerNameScan.setText(job.getString("DESCRIPTION"));

                            Worker1 = job.getString("DESCRIPTION");
                            saveColorChange();
                            etT2Stir2WorkerNameScan.requestFocus();

                        }else if(etH2HiddenWorker.getText().toString().equals("worker2")){ //작업자 스캔 2

                            etH2Stir2WorkerId.setText(job.getString("USER_ID"));
                            etT2Stir2WorkerNameScan.setText(job.getString("DESCRIPTION"));

                            Worker2 = job.getString("DESCRIPTION");
                            saveColorChange();
                            etT2Stir3WorkerNameScan.requestFocus();

                        }else {                                                             //작업자 스캔 3

                            etH2Stir3WorkerId.setText(job.getString("USER_ID"));
                            etT2Stir3WorkerNameScan.setText(job.getString("DESCRIPTION"));

                            Worker3 = job.getString("DESCRIPTION");
                            saveColorChange();



                        }

                    }
                }



            } catch (JSONException e) {
                e.printStackTrace();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }



    protected class BhUpdate extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... urls) {
            StringBuffer jsonHtml = new StringBuffer();

            //서버로 보낼 데이터 설정
            String search_title = "W_JOB_ID=" + urls[0]
                    + "&W_OPERATION_ID=" + urls[1]
                    + "&W_WORKCENTER_ID=" + urls[2]
                    + "&P_SOB_ID=" + urls[3]
                    + "&P_ORG_ID=" + urls[4]
                    + "&P_MIX_TANK_LCODE=" + urls[6]
                    + "&P_EQUIPMENT_ID=" + urls[7]
                    + "&P_MIX_START_DATE=" + urls[8]
                    + "&P_STIR_1_START_DATE=" + urls[9]
                    + "&P_STIR_1_END_DATE=" + urls[10]
                    + "&P_STIR_1_WORKER_ID=" + urls[11]
                    + "&P_STIR_2_START_DATE=" + urls[12]
                    + "&P_STIR_2_END_DATE=" + urls[13]
                    + "&P_STIR_2_WORKER_ID=" + urls[14]
                    + "&P_STIR_3_START_DATE=" + urls[15]
                    + "&P_STIR_3_END_DATE=" + urls[16]
                    + "&P_STIR_3_WORKER_ID=" + urls[17]
                    + "&P_MIX_END_DATE=" + urls[18]
                    + "&P_USER_ID=" + urls[19];


            try {
                URL obj = new URL("http://"+strIp+"/TAIYO/BhUpdate.jsp"); //주소 지정

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

        @SuppressLint("ResourceAsColor")
        protected void onPostExecute(String result) {
            try{

                JSONObject RESULT = new JSONObject(result);
                JSONArray arr = RESULT.getJSONArray("RESULT");

                try{
                        JSONObject obj = arr.getJSONObject(0);
                        String ResultStatus = obj.getString("P_RESULT_STATUS");

                        if(ResultStatus.equals("S")){
                            //ClearListView();
                            Toast.makeText(getApplicationContext(), "저장이 완료되었습니다.", Toast.LENGTH_SHORT).show();


                            FileNoScanBH fileNoScanBH = new FileNoScanBH();
                            fileNoScanBH.execute(strSobId, strOrgId, etT2FileNoScan.getText().toString(), etH2XworkId.getText().toString()); //재조회


                            bT2Save.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.button_color));
                            bT2Save.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));

                        }else{

                            Toast.makeText(getApplicationContext(), "오류입니다"+ result, Toast.LENGTH_SHORT).show();

                        }

                }catch(Exception e){
                    e.printStackTrace();

                }
            }catch (Exception e){
                e.printStackTrace();

            }
        }
    }



}
