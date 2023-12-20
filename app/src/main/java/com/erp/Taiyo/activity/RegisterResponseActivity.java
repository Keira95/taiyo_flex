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
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

public class RegisterResponseActivity extends AppCompatActivity{

    Toolbar toolbar;
    String strIp, strUserName, strUserId, SDate, strMenuDesc;
    String filter = null;
    String strDate;
    TextView tvUserName,tvMenu;

    String strSobId = "70";
    String strOrgId = "701";
    String strAssembly ="PPMF2208";

    EditText etT6FileNoScan, etT6StartTime, etT6ItemDesc, etT6OperaionDesc, etT6TankDesc, etT6EquipmentName, etT6Stir1StartDate , etT6Stir2StartDate, etT6Stir3StartDate,
            etT6Stir4StartDate, etT6Stir5StartDate , etT6Stir1EndDate , etT6Stir2EndDate, etT6Stir3EndDate, etT6Stir4EndDate, etT6Stir5EndDate, etT6Stir1WorkerName,
            etT6Stir2WorkerName, etT6Stir3WorkerName, etT6Stir4WorkerName, etT6Stir5WorkerName, etT6EndTime ,etT6WorkercenterId ,etT6WorkercenterCode ,etT6WorkercenterDesc
            ,etT6TankId , etT6TankCode ,etT6EquipmentCode ,etT6OldEquipmentName ,etT6EquipmentId, etT6WorkerHidden ,
             etT6Stir1WorkerId ,etT6Stir2WorkerId ,etT6Stir3WorkerId ,etT6Stir4WorkerId ,etT6Stir5WorkerId , etT6ModFlag , etT6JobId , etT6OperationId
            ;


    LinearLayout mainLayout;


    Button btnt6save;
    Button btnT6StartTime, btnT6Stir1StartDate, btnT6Stir2StartDate, btnT6Stir3StartDate, btnT6Stir4StartDate, btnT6Stir5StartDate ,btnT6Stir1EndDate,
            btnT6Stir2EndDate, btnT6Stir3EndDate, btnT6Stir4EndDate, btnT6Stir5EndDate, btnT6EndTime,btnHolding;

    InputMethodManager imm;
    JSONObject jspSumObject = new JSONObject();
    TextView tvUserGroup;

    FrameLayout log_panel;
    ListView lv_Receiving;
    TextView tvLog;
    SharedPreferences auto;
    EditText editTextFilter;

    ListView lv_log;
    ListView lvPaldlet, lvInput;
    private boolean ScanModify = true;
    private boolean Mod_Flag = true;

    private String FileScan = "";
    private String TankType = "";
    private String BoEqp = "";
    private String Worker1 = "";
    private String Worker2 = "";
    private String Worker3 = "";
    private String Worker4 = "";
    private String Worker5 = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_response_work6);
        Intent intent = getIntent();
        strIp = intent.getStringExtra("Ip");
        strUserId = intent.getStringExtra("O_USER_ID");
        strUserName = intent.getStringExtra("O_USER_NAME");
        strMenuDesc = intent.getStringExtra("TOP_MENU_DESC");


        mainLayout = findViewById(R.id.main_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvUserName = (TextView) findViewById(R.id.user_nm);
        tvMenu = (TextView) findViewById(R.id.tv_menu);

        etT6FileNoScan = (EditText) findViewById(R.id.et_t6_file_no_scan);
        etT6StartTime = (EditText) findViewById(R.id.et_t6_start_time);
        etT6ItemDesc = (EditText) findViewById(R.id.et_t6_item_desc);
        etT6OperaionDesc = (EditText) findViewById(R.id.et_t6_operaion_desc);
        etT6TankDesc = (EditText) findViewById(R.id.et_t6_tank_desc);
        etT6EquipmentName = (EditText) findViewById(R.id.et_t6_equipment_name);
        etT6Stir1StartDate = (EditText) findViewById(R.id.et_t6_stir_1_start_date);
        etT6Stir2StartDate = (EditText) findViewById(R.id.et_t6_stir_2_start_date);
        etT6Stir3StartDate = (EditText) findViewById(R.id.et_t6_stir_3_start_date);
        etT6Stir4StartDate = (EditText) findViewById(R.id.et_t6_stir_4_start_date);
        etT6Stir5StartDate = (EditText) findViewById(R.id.et_t6_stir_5_start_date);
        etT6Stir1EndDate = (EditText) findViewById(R.id.et_t6_stir_1_end_date);
        etT6Stir2EndDate = (EditText) findViewById(R.id.et_t6_stir_2_end_date);
        etT6Stir3EndDate = (EditText) findViewById(R.id.et_t6_stir_3_end_date);
        etT6Stir4EndDate = (EditText) findViewById(R.id.et_t6_stir_4_end_date);
        etT6Stir5EndDate = (EditText) findViewById(R.id.et_t6_stir_5_end_date);
        etT6Stir1WorkerName = (EditText) findViewById(R.id.et_t6_stir_1_worker_name);
        etT6Stir2WorkerName = (EditText) findViewById(R.id.et_t6_stir_2_worker_name);
        etT6Stir3WorkerName = (EditText) findViewById(R.id.et_t6_stir_3_worker_name);
        etT6Stir4WorkerName = (EditText) findViewById(R.id.et_t6_stir_4_worker_name);
        etT6Stir5WorkerName = (EditText) findViewById(R.id.et_t6_stir_5_worker_name);

        etT6EndTime = (EditText) findViewById(R.id.et_t6_end_time);
        etT6WorkercenterId = (EditText) findViewById(R.id.et_h2_workercenter_id);
        etT6WorkercenterCode = (EditText) findViewById(R.id.et_h2_workercenter_code);
        etT6WorkercenterDesc = (EditText) findViewById(R.id.et_h2_workercenter_desc);
        etT6TankId  =  (EditText) findViewById(R.id.et_t6_tank_id);
        etT6TankCode = (EditText) findViewById(R.id.et_t6_tank_code);
        etT6EquipmentCode = (EditText) findViewById(R.id.et_t6_equipment_code);
        etT6OldEquipmentName = (EditText) findViewById(R.id.et_t6_old_equipment_name);
        etT6EquipmentId =  (EditText) findViewById(R.id.et_t6_equipment_id);
        etT6WorkerHidden = (EditText) findViewById(R.id.et_t6_worker_hidden);

        etT6Stir1WorkerId = (EditText) findViewById(R.id.et_t6_stir_1_worker_id);
        etT6Stir2WorkerId = (EditText) findViewById(R.id.et_t6_stir_2_worker_id);
        etT6Stir3WorkerId = (EditText) findViewById(R.id.et_t6_stir_3_worker_id);
        etT6Stir4WorkerId  = (EditText) findViewById(R.id.et_t6_stir_4_worker_id);
        etT6Stir5WorkerId = (EditText) findViewById(R.id.et_t6_stir_5_worker_id);
        etT6ModFlag =  (EditText) findViewById(R.id.et_t6_mod_flag);
        etT6JobId =  (EditText) findViewById(R.id.et_t6_job_id);
        etT6OperationId = (EditText) findViewById(R.id.et_t6_operation_id);

        btnT6StartTime = (Button) findViewById(R.id.btn_t6_start_time);
        btnT6Stir1StartDate = (Button) findViewById(R.id.btn_t6_stir_1_start_date);
        btnT6Stir2StartDate = (Button) findViewById(R.id.btn_t6_stir_2_start_date);
        btnT6Stir3StartDate = (Button) findViewById(R.id.btn_t6_stir_3_start_date);
        btnT6Stir4StartDate = (Button) findViewById(R.id.btn_t6_stir_4_start_date);
        btnT6Stir5StartDate = (Button) findViewById(R.id.btn_t6_stir_5_start_date);
        btnT6Stir1EndDate = (Button) findViewById(R.id.btn_t6_stir_1_end_date);
        btnT6Stir2EndDate = (Button) findViewById(R.id.btn_t6_stir_2_end_date);
        btnT6Stir3EndDate = (Button) findViewById(R.id.btn_t6_stir_3_end_date);
        btnT6Stir4EndDate = (Button) findViewById(R.id.btn_t6_stir_4_end_date);
        btnT6Stir5EndDate = (Button) findViewById(R.id.btn_t6_stir_5_end_date);
        btnT6EndTime = (Button) findViewById(R.id.btn_t6_end_time);
        btnt6save = (Button) findViewById(R.id.btn_t6_save);
        btnHolding = (Button) findViewById(R.id.btn_t6_holding);


        //먼저 실행되는 함수
        GET_WORKCENTER_IN_AUTHORITY gET_WORKCENTER_IN_AUTHORITY = new GET_WORKCENTER_IN_AUTHORITY();
        gET_WORKCENTER_IN_AUTHORITY.execute(strIp, strSobId,strOrgId ,strUserId, strAssembly);


        etT6FileNoScan.requestFocus();

        initializeToolbar();

        keyboardFocus(etT6FileNoScan);
        keyboardFocus(etT6TankDesc);
        keyboardFocus(etT6EquipmentName);
        keyboardFocus(etT6Stir1WorkerName);
        keyboardFocus(etT6Stir2WorkerName);
        keyboardFocus(etT6Stir3WorkerName);
        keyboardFocus(etT6Stir4WorkerName);
        keyboardFocus(etT6Stir5WorkerName);

        etT6FileNoScan.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(getCurrentFocus() == etT6FileNoScan && !s.toString().isEmpty() && s != null && etT6JobId.getText().toString().equals("")){


                    FILE_NO_SCAN fILE_NO_SCAN = new FILE_NO_SCAN();
                    fILE_NO_SCAN.execute(strIp, strSobId,strOrgId ,etT6FileNoScan.getText().toString(),etT6WorkercenterId.getText().toString()); //다시 fill



                }else{
                    return;
                }
            }
        });



        etT6TankDesc.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(getCurrentFocus() == etT6TankDesc && !s.toString().isEmpty() && s != null && etT6TankCode.getText().toString().equals("")){

                    LU_TANK_TYPE lU_TANK_TYPE = new LU_TANK_TYPE();
                    lU_TANK_TYPE.execute(strIp, strSobId,strOrgId, etT6TankDesc.getText().toString());

                }else{
                    return;
                }
            }
        });
        etT6EquipmentName.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(getCurrentFocus() == etT6EquipmentName && !s.toString().isEmpty() && s != null && etT6EquipmentId.getText().toString().equals("")){

                    LU_BO_EQP lU_BO_EQP = new LU_BO_EQP();
                    lU_BO_EQP.execute( strIp,strSobId, strOrgId,etT6WorkercenterId.getText().toString(), etT6EquipmentName.getText().toString());
                }else{
                    return;
                }
            }
        });

        etT6Stir1WorkerName.addTextChangedListener(new TextWatcher() { //작업자 1

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(getCurrentFocus() == etT6Stir1WorkerName && !s.toString().isEmpty() && s != null && etT6Stir1WorkerId.getText().toString().equals("")){

                    LU_WORKER lU_WORKER = new LU_WORKER();
                    etT6WorkerHidden.setText("worker1");
                    lU_WORKER.execute(strIp, strSobId,strOrgId, etT6WorkercenterId.getText().toString(), etT6Stir1WorkerName.getText().toString());
                }else{
                    return;
                }
            }
        });

        etT6Stir2WorkerName.addTextChangedListener(new TextWatcher() { //작업자 1

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(getCurrentFocus() == etT6Stir2WorkerName && !s.toString().isEmpty() && s != null && etT6Stir2WorkerId.getText().toString().equals("")){

                    LU_WORKER lU_WORKER = new LU_WORKER();
                    etT6WorkerHidden.setText("worker2");
                    lU_WORKER.execute(strIp, strSobId,strOrgId, etT6WorkercenterId.getText().toString(), etT6Stir2WorkerName.getText().toString());
                }else{
                    return;
                }
            }
        });


        etT6Stir3WorkerName.addTextChangedListener(new TextWatcher() { //작업자 1

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(getCurrentFocus() == etT6Stir3WorkerName && !s.toString().isEmpty() && s != null && etT6Stir3WorkerId.getText().toString().equals("")){

                    LU_WORKER lU_WORKER = new LU_WORKER();
                    etT6WorkerHidden.setText("worker3");
                    lU_WORKER.execute(strIp, strSobId,strOrgId, etT6WorkercenterId.getText().toString(), etT6Stir3WorkerName.getText().toString());
                }else{
                    return;
                }
            }
        });

        etT6Stir4WorkerName.addTextChangedListener(new TextWatcher() { //작업자 1

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(getCurrentFocus() == etT6Stir4WorkerName && !s.toString().isEmpty() && s != null && etT6Stir4WorkerId.getText().toString().equals("")){

                    LU_WORKER lU_WORKER = new LU_WORKER();
                    etT6WorkerHidden.setText("worker4");
                    lU_WORKER.execute(strIp, strSobId,strOrgId, etT6WorkercenterId.getText().toString(), etT6Stir4WorkerName.getText().toString());
                }else{
                    return;
                }
            }
        });

        etT6Stir5WorkerName.addTextChangedListener(new TextWatcher() { //작업자 1

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(getCurrentFocus() == etT6Stir5WorkerName && !s.toString().isEmpty() && s != null && etT6Stir5WorkerId.getText().toString().equals("")){

                    LU_WORKER lU_WORKER = new LU_WORKER();
                    etT6WorkerHidden.setText("worker5");
                    lU_WORKER.execute(strIp, strSobId,strOrgId, etT6WorkercenterId.getText().toString(), etT6Stir5WorkerName.getText().toString());
                }else{
                    return;
                }
            }
        });

        btnHolding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(etT6FileNoScan.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "File No는 필수입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                HoldingDialog holdingDialog = new HoldingDialog(RegisterResponseActivity.this);
                holdingDialog.call_Level_Dialog(etT6FileNoScan, strIp , strUserId, etT6OperationId, etT6JobId);
            }
        });


        btnt6save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(RegisterResponseActivity.this);
                alert.setTitle("저장");
                alert.setMessage("수정한 내역을 저장하시겠습니까?");
                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(etT6FileNoScan.getText().toString().isEmpty()){
                            Toast.makeText(getApplicationContext(), "File No는 필수입니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        BO_UPDATE bO_UPDATE = new BO_UPDATE();
                        bO_UPDATE.execute(strIp,etT6JobId.getText().toString(),etT6OperationId.getText().toString(),etT6WorkercenterId.getText().toString(),strSobId ,strOrgId ,etT6StartTime.getText().toString().replaceAll(" ","")
                                , etT6TankCode.getText().toString() , etT6EquipmentId.getText().toString(), etT6Stir1StartDate.getText().toString().replaceAll(" ",""), etT6Stir2StartDate.getText().toString().replaceAll(" ","")
                                ,  etT6Stir3StartDate.getText().toString().replaceAll(" ","") ,  etT6Stir4StartDate.getText().toString().replaceAll(" ","") ,  etT6Stir5StartDate.getText().toString().replaceAll(" ","")
                                , etT6Stir1EndDate.getText().toString().replaceAll(" ","") ,etT6Stir2EndDate.getText().toString().replaceAll(" ",""), etT6Stir3EndDate.getText().toString().replaceAll(" ","")
                                , etT6Stir4EndDate.getText().toString().replaceAll(" ","") , etT6Stir5EndDate.getText().toString().replaceAll(" ","") , etT6Stir1WorkerId.getText().toString()
                                , etT6Stir2WorkerId.getText().toString() , etT6Stir3WorkerId.getText().toString() , etT6Stir4WorkerId.getText().toString() , etT6Stir5WorkerId.getText().toString() ,strUserId , etT6EndTime.getText().toString().replaceAll(" ","")

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

        etT6FileNoScan.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                etT6FileNoScan.setText("");
                etT6JobId.setText("");
                return false;
            }
        });

        etT6TankDesc.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                etT6TankDesc.setText("");
                etT6TankCode.setText("");
                return false;
            }
        });


        etT6EquipmentName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                etT6EquipmentName.setText("");
                etT6EquipmentId.setText("");
                return false;
            }
        });


        etT6Stir1WorkerName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                etT6Stir1WorkerName.setText("");
                etT6Stir1WorkerId.setText("");
                return false;
            }
        });


        etT6Stir2WorkerName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                etT6Stir2WorkerName.setText("");
                etT6Stir2WorkerId.setText("");
                return false;
            }
        });

        etT6Stir3WorkerName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                etT6Stir3WorkerName.setText("");
                etT6Stir3WorkerId.setText("");
                return false;
            }
        });

        etT6Stir4WorkerName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                etT6Stir4WorkerName.setText("");
                etT6Stir4WorkerId.setText("");
                return false;
            }
        });

        etT6Stir5WorkerName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                etT6Stir5WorkerName.setText("");
                etT6Stir5WorkerId.setText("");
                return false;
            }
        });





        btnT6StartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                etT6StartTime.setText(getNowDate());;
            }
        });
        btnT6Stir1StartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                etT6Stir1StartDate.setText(getNowDate());;
            }
        });

        btnT6Stir2StartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                etT6Stir2StartDate.setText(getNowDate());;
            }
        });
        btnT6Stir3StartDate .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                etT6Stir3StartDate.setText(getNowDate());;
            }
        });
        btnT6Stir4StartDate .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                etT6Stir4StartDate.setText(getNowDate());;
            }
        });
        btnT6Stir5StartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                etT6Stir5StartDate.setText(getNowDate());;
            }
        });
        btnT6Stir1EndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                etT6Stir1EndDate.setText(getNowDate());;
            }
        });
        btnT6Stir2EndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                etT6Stir2EndDate.setText(getNowDate());;
            }
        });
        btnT6Stir3EndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                etT6Stir3EndDate.setText(getNowDate());;
            }
        });
        btnT6Stir4EndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                etT6Stir4EndDate.setText(getNowDate());;
            }
        });
        btnT6Stir5EndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                etT6Stir5EndDate.setText(getNowDate());;
            }
        });
        btnT6EndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                etT6EndTime.setText(getNowDate());;
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

    private void setModeFlag(boolean enabled) {
        etT6FileNoScan.setEnabled(enabled);
        etT6StartTime.setEnabled(enabled);
        etT6ItemDesc.setEnabled(enabled);
        etT6OperaionDesc.setEnabled(enabled);
        etT6TankDesc.setEnabled(enabled);
        etT6EquipmentName.setEnabled(enabled);
        etT6Stir1StartDate.setEnabled(enabled);
        etT6Stir2StartDate.setEnabled(enabled);
        etT6Stir3StartDate.setEnabled(enabled);
        etT6Stir4StartDate.setEnabled(enabled);
        etT6Stir5StartDate.setEnabled(enabled);
        etT6Stir1EndDate.setEnabled(enabled);
        etT6Stir2EndDate.setEnabled(enabled);
        etT6Stir3EndDate.setEnabled(enabled);
        etT6Stir4EndDate.setEnabled(enabled);
        etT6Stir5EndDate.setEnabled(enabled);
        etT6Stir1WorkerName.setEnabled(enabled);
        etT6Stir2WorkerName.setEnabled(enabled);
        etT6Stir3WorkerName.setEnabled(enabled);
        etT6Stir4WorkerName.setEnabled(enabled);
        etT6Stir5WorkerName.setEnabled(enabled);
        etT6EndTime.setEnabled(enabled);
        etT6WorkercenterId.setEnabled(enabled);
        etT6WorkercenterCode.setEnabled(enabled);
        etT6WorkercenterDesc.setEnabled(enabled);
        etT6TankId.setEnabled(enabled);
        etT6TankCode.setEnabled(enabled);
        etT6EquipmentCode.setEnabled(enabled);
        etT6OldEquipmentName.setEnabled(enabled);
        etT6EquipmentId.setEnabled(enabled);
        etT6WorkerHidden.setEnabled(enabled);
        etT6Stir1WorkerId.setEnabled(enabled);
        etT6Stir2WorkerId.setEnabled(enabled);
        etT6Stir3WorkerId.setEnabled(enabled);
        etT6Stir4WorkerId.setEnabled(enabled);
        etT6Stir5WorkerId.setEnabled(enabled);
        etT6ModFlag.setEnabled(enabled);
        etT6JobId.setEnabled(enabled);
        etT6OperationId.setEnabled(enabled);
        btnT6StartTime.setEnabled(enabled);
        btnT6Stir1StartDate.setEnabled(enabled);
        btnT6Stir2StartDate.setEnabled(enabled);
        btnT6Stir3StartDate.setEnabled(enabled);
        btnT6Stir4StartDate.setEnabled(enabled);
        btnT6Stir5StartDate.setEnabled(enabled);
        btnT6Stir1EndDate.setEnabled(enabled);
        btnT6Stir2EndDate.setEnabled(enabled);
        btnT6Stir3EndDate.setEnabled(enabled);
        btnT6Stir4EndDate.setEnabled(enabled);
        btnT6Stir5EndDate.setEnabled(enabled);
        btnT6EndTime.setEnabled(enabled);
        btnt6save.setEnabled(enabled);
        btnHolding.setEnabled(enabled);
    }
    //저장버튼 색 바꾸는 함수
    private void saveColorChange() {
        if (ScanModify==false) {
            btnt6save.setBackgroundColor(Color.YELLOW);
            btnt6save.setTextColor(Color.BLACK);
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
                    etT6WorkercenterCode.setText(job.getString("X_WORKCENTER_CODE"));
                    etT6WorkercenterDesc.setText(job.getString("X_WORKCENTER_DESC"));
                    etT6WorkercenterId.setText(job.getString("X_WORKCENTER_ID"));
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
                etT6EquipmentName.requestFocus();
            }

            return jsonHtml.toString(); //결과값 리턴
        }

        protected void onPostExecute(String result) {
            //페이지 결과값 파싱
            try {
                JSONObject RESURT = new JSONObject(result); // JSON 오브젝트 받음
                JSONArray resultArray = RESURT.getJSONArray("RESULT"); // JSONArray 파싱

                if(resultArray.length() < 1){
                    if(!TankType.equals(etT6TankDesc.getText().toString())){
                        etT6TankDesc.requestFocus();
                        etT6TankDesc.setText("");
                    }

                    return;
                }else{
                    JSONObject job = resultArray.getJSONObject(0); // JSON 오브젝트 파싱
                    String status = job.getString("Status");

                    if (status.equals("S")) {
                        etT6TankCode.setText(job.getString("ENTRY_CODE"));
                        etT6TankDesc.setText(job.getString("ENTRY_DESCRIPTION"));
                        etT6TankId.setText(job.getString("LOOKUP_ENTRY_ID"));

                        TankType = job.getString("ENTRY_DESCRIPTION");

                        saveColorChange();
                    }
                    etT6EquipmentName.requestFocus();
                }




            } catch (JSONException e) {
                e.printStackTrace();
                etT6EquipmentName.requestFocus();

            } catch (Exception e) {
                e.printStackTrace();
                etT6EquipmentName.requestFocus();

            }
        }

    }

    protected class LU_BO_EQP extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... urls) {
            StringBuffer jsonHtml = new StringBuffer();

            //서버로 보낼 데이터 설정
            String search_title = "W_SOB_ID=" + urls[1]
                    + "&W_ORG_ID=" + urls[2]
                    + "&W_WORKCENTER_ID=" + urls[3]
                    + "&W_BARCODE=" + urls[4];

            try {
                URL obj = new URL("http://"+urls[0]+"/TAIYO/LuBoEqp.jsp"); //주소 지정

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
                etT6Stir1WorkerName.requestFocus();
            }

            return jsonHtml.toString(); //결과값 리턴
        }

        protected void onPostExecute(String result) {
            //페이지 결과값 파싱
            try {

                JSONObject RESURT = new JSONObject(result); //JSON 오브젝트 받음
                JSONArray resultArray = RESURT.getJSONArray("RESULT"); //JSONArray 파싱

                //JSONObject job = resultArray.getJSONObject(0); //JSON 오브젝트 파싱
                if(resultArray.length() < 1){
                    if(!BoEqp.equals(etT6EquipmentName.getText().toString())){
                        etT6EquipmentName.requestFocus();
                        etT6EquipmentName.setText("");
                    }

                    return;
                }else{
                    JSONObject job = resultArray.getJSONObject(0); // JSON 오브젝트 파싱
                    String status = job.getString("Status");

                    if (status.equals("S")) {

                        etT6EquipmentId.setText(job.getString("TOP_EQUIPMENT_ID"));
                        etT6EquipmentCode.setText(job.getString("TOP_EQUIPMENT_CODE"));
                        etT6EquipmentName.setText(job.getString("TOP_EQUIPMENT_NAME"));
                        etT6OldEquipmentName.setText(job.getString("OLD_EQUIPMENT_NAME"));

                        BoEqp = job.getString("TOP_EQUIPMENT_NAME");

                        saveColorChange();

                    }
                    etT6Stir1WorkerName.requestFocus();
                }






            } catch (JSONException e) {
                e.printStackTrace();
                etT6Stir1WorkerName.requestFocus();

            } catch (Exception e) {
                e.printStackTrace();
                etT6Stir1WorkerName.requestFocus();

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
                    if(etT6WorkerHidden.getText().toString().equals("worker1") && !Worker1.equals(etT6Stir1WorkerName.getText().toString()))
                    {
                        etT6Stir1WorkerName.requestFocus();
                        etT6Stir1WorkerName.setText("");
                    }else if(etT6WorkerHidden.getText().toString().equals("worker2") && !Worker2.equals(etT6Stir2WorkerName.getText().toString()))
                    {
                        etT6Stir2WorkerName.requestFocus();
                        etT6Stir2WorkerName.setText("");
                    }else if(etT6WorkerHidden.getText().toString().equals("worker3") && !Worker3.equals(etT6Stir3WorkerName.getText().toString()))
                    {
                        etT6Stir3WorkerName.requestFocus();
                        etT6Stir3WorkerName.setText("");
                    }else if(etT6WorkerHidden.getText().toString().equals("worker4") && !Worker4.equals(etT6Stir4WorkerName.getText().toString()))
                    {
                        etT6Stir4WorkerName.requestFocus();
                        etT6Stir4WorkerName.setText("");
                    }else if(etT6WorkerHidden.getText().toString().equals("worker5") && !Worker4.equals(etT6Stir5WorkerName.getText().toString()))
                    {
                        etT6Stir5WorkerName.requestFocus();
                        etT6Stir5WorkerName.setText("");
                    }

                    return;
                }else{
                    JSONObject job = jarrayWorkLevel.getJSONObject(0);
                    if(job.getString("Status").equals("S")){


                        if(etT6WorkerHidden.getText().toString().equals("worker1")){ //작업자 스캔 1

                            etT6Stir1WorkerId.setText(job.getString("USER_ID"));
                            etT6Stir1WorkerName.setText(job.getString("DESCRIPTION"));

                            Worker1 = job.getString("DESCRIPTION");
                            saveColorChange();
                            etT6Stir2WorkerName.requestFocus();

                        }else if(etT6WorkerHidden.getText().toString().equals("worker2")){ //작업자 스캔 2

                            etT6Stir2WorkerId.setText(job.getString("USER_ID"));
                            etT6Stir2WorkerName.setText(job.getString("DESCRIPTION"));

                            Worker2 = job.getString("DESCRIPTION");
                            saveColorChange();
                            etT6Stir3WorkerName.requestFocus();

                        }else if(etT6WorkerHidden.getText().toString().equals("worker3")){//작업자 스캔 3

                            etT6Stir3WorkerId.setText(job.getString("USER_ID"));
                            etT6Stir3WorkerName.setText(job.getString("DESCRIPTION"));

                            Worker3 = job.getString("DESCRIPTION");
                            saveColorChange();
                            etT6Stir4WorkerName.requestFocus();

                        }else if(etT6WorkerHidden.getText().toString().equals("worker4")){ //작업자 스캔 4
                            etT6Stir4WorkerId.setText(job.getString("USER_ID"));
                            etT6Stir4WorkerName.setText(job.getString("DESCRIPTION"));

                            Worker4 = job.getString("DESCRIPTION");
                            saveColorChange();
                            etT6Stir5WorkerName.requestFocus();
                        }else{                                                            //작업자 스캔 5

                            etT6Stir5WorkerId.setText(job.getString("USER_ID"));
                            etT6Stir5WorkerName.setText(job.getString("DESCRIPTION"));

                            Worker5 = job.getString("DESCRIPTION");
                            saveColorChange();
                            etT6FileNoScan.requestFocus();
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
            {  URL obj = new URL("http://" + urls[0] + "/TAIYO/FileNoScanRE.jsp"); //주소 지정

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
                etT6TankDesc.requestFocus();
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
                    if(!FileScan.equals(etT6FileNoScan.getText().toString())){
                        ScanModify = false;
                        etT6FileNoScan.requestFocus();
                        etT6FileNoScan.setText("");
                    }



                    return;
                }else{
                    JSONObject job = jarrayWorkLevel.getJSONObject(0);
                    if(job.getString("Status").equals("S")) {


                        etT6FileNoScan.setText(job.getString("WORK_ORDER_NO"));
                        etT6ItemDesc.setText(job.getString("ITEM_DESCRIPTION"));
                        etT6OperaionDesc.setText(job.getString("OPERATION_DESCRIPTION"));

                        if (job.getString("START_DATE").equals("null")) {
                            etT6StartTime.setText("");
                        } else {
                            etT6StartTime.setText(job.getString("START_DATE"));
                        }

                        if (job.getString("TANK_DESC").equals("null")) {
                            etT6TankDesc.setText("");
                        } else {
                            etT6TankDesc.setText(job.getString("TANK_DESC"));
                        }

                        if (job.getString("TANK_LCODE").equals("null")) {
                            etT6TankCode.setText("");
                        } else {
                            etT6TankCode.setText(job.getString("TANK_LCODE"));
                        }
                        if (job.getString("EQUIPMENT_ID").equals("null")) {
                            etT6EquipmentId.setText("");
                        } else {
                            etT6EquipmentId.setText(job.getString("EQUIPMENT_ID"));
                        }
                        if (job.getString("EQUIPMENT_NAME").equals("null")) {
                            etT6EquipmentName.setText("");
                        } else {
                            etT6EquipmentName.setText(job.getString("EQUIPMENT_NAME"));
                        }
                        if (job.getString("STIR_1_START_DATE").equals("null")) {
                            etT6Stir1StartDate.setText("");
                        } else {
                            etT6Stir1StartDate.setText(job.getString("STIR_1_START_DATE"));
                        }
                        if (job.getString("STIR_2_START_DATE").equals("null")) {
                            etT6Stir2StartDate.setText("");
                        } else {
                            etT6Stir2StartDate.setText(job.getString("STIR_2_START_DATE"));
                        }
                        if (job.getString("STIR_3_START_DATE").equals("null")) {
                            etT6Stir3StartDate.setText("");
                        } else {
                            etT6Stir3StartDate.setText(job.getString("STIR_3_START_DATE"));
                        }
                        if (job.getString("STIR_4_START_DATE").equals("null")) {
                            etT6Stir4StartDate.setText("");
                        } else {
                            etT6Stir4StartDate.setText(job.getString("STIR_4_START_DATE"));
                        }
                        if (job.getString("STIR_5_START_DATE").equals("null")) {
                            etT6Stir5StartDate.setText("");
                        } else {
                            etT6Stir5StartDate.setText(job.getString("STIR_5_START_DATE"));
                        }
                        if (job.getString("STIR_1_END_DATE").equals("null")) {
                            etT6Stir1EndDate.setText("");
                        } else {
                            etT6Stir1EndDate.setText(job.getString("STIR_1_END_DATE"));
                        }
                        if (job.getString("STIR_2_END_DATE").equals("null")) {
                            etT6Stir2EndDate.setText("");
                        } else {
                            etT6Stir2EndDate.setText(job.getString("STIR_2_END_DATE"));
                        }
                        if (job.getString("STIR_3_END_DATE").equals("null")) {
                            etT6Stir3EndDate.setText("");
                        } else {
                            etT6Stir3EndDate.setText(job.getString("STIR_3_END_DATE"));
                        }
                        if (job.getString("STIR_4_END_DATE").equals("null")) {
                            etT6Stir4EndDate.setText("");
                        } else {
                            etT6Stir4EndDate.setText(job.getString("STIR_4_END_DATE"));
                        }
                        if (job.getString("STIR_5_END_DATE").equals("null")) {
                            etT6Stir5EndDate.setText("");
                        } else {
                            etT6Stir5EndDate.setText(job.getString("STIR_5_END_DATE"));
                        }
                        if (job.getString("STIR_1_WORKER_NAME").equals("null")) {
                            etT6Stir1WorkerName.setText("");
                        } else {
                            etT6Stir1WorkerName.setText(job.getString("STIR_1_WORKER_NAME"));
                        }
                        if (job.getString("STIR_2_WORKER_NAME").equals("null")) {
                            etT6Stir2WorkerName.setText("");
                        } else {
                            etT6Stir2WorkerName.setText(job.getString("STIR_2_WORKER_NAME"));
                        }
                        if (job.getString("STIR_3_WORKER_NAME").equals("null")) {
                            etT6Stir3WorkerName.setText("");
                        } else {
                            etT6Stir3WorkerName.setText(job.getString("STIR_3_WORKER_NAME"));
                        }
                        if (job.getString("STIR_4_WORKER_NAME").equals("null")) {
                            etT6Stir4WorkerName.setText("");
                        } else {
                            etT6Stir4WorkerName.setText(job.getString("STIR_4_WORKER_NAME"));
                        }
                        if (job.getString("STIR_5_WORKER_NAME").equals("null")) {
                            etT6Stir5WorkerName.setText("");
                        } else {
                            etT6Stir5WorkerName.setText(job.getString("STIR_5_WORKER_NAME"));
                        }

                        if (job.getString("END_DATE").equals("null")) { //수정해야함
                            etT6EndTime.setText("");
                        } else {
                            etT6EndTime.setText(job.getString("END_DATE"));
                        }

                        if (job.getString("STIR_1_WORKER_ID").equals("null")) {
                            etT6Stir1WorkerId.setText("");
                        } else {
                            etT6Stir1WorkerId.setText(job.getString("STIR_1_WORKER_ID"));
                        }

                        if (job.getString("STIR_2_WORKER_ID").equals("null")) {
                            etT6Stir2WorkerId.setText("");
                        } else {
                            etT6Stir2WorkerId.setText(job.getString("STIR_2_WORKER_ID"));
                        }

                        if (job.getString("STIR_3_WORKER_ID").equals("null")) {
                            etT6Stir3WorkerId.setText("");

                        } else {
                            etT6Stir3WorkerId.setText(job.getString("STIR_3_WORKER_ID"));
                        }
                        if (job.getString("STIR_4_WORKER_ID").equals("null")) {
                            etT6Stir4WorkerId.setText("");
                        } else {
                            etT6Stir4WorkerId.setText(job.getString("STIR_4_WORKER_ID"));
                        }
                        if (job.getString("STIR_5_WORKER_ID").equals("null")) {
                            etT6Stir5WorkerId.setText("");
                        } else {
                            etT6Stir5WorkerId.setText(job.getString("STIR_5_WORKER_ID"));
                        }

                        if (job.getString("JOB_ID").equals("null")) {
                            etT6JobId.setText("");
                        } else {
                            etT6JobId.setText(job.getString("JOB_ID"));
                        }
                        if (job.getString("OPERATION_ID").equals("null")) {
                            etT6OperationId.setText("");
                        } else {
                            etT6OperationId.setText(job.getString("OPERATION_ID"));
                        }

                        FileScan = job.getString("WORK_ORDER_NO");
                    }
                }


                etT6TankDesc.requestFocus();

                    if(etT6ModFlag.getText().toString().equals("N")){
                        setModeFlag(false);
                    }


                etT6TankDesc.requestFocus();

                ScanModify = false;

            }catch (JSONException e)
            {
                e.printStackTrace();
                etT6TankDesc.requestFocus();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                etT6TankDesc.requestFocus();
            }
        }

    }


    protected class BO_UPDATE extends AsyncTask<String, Void, String>
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
                    + "&P_START_DATE=" + urls[6]
                    + "&P_TANK_LCODE=" + urls[7]
                    + "&P_EQUIPMENT_ID=" + urls[8]
                    + "&P_STIR_1_START_DATE=" + urls[9]
                    + "&P_STIR_2_START_DATE=" + urls[10]
                    + "&P_STIR_3_START_DATE=" + urls[11]
                    + "&P_STIR_4_START_DATE=" + urls[12]
                    + "&P_STIR_5_START_DATE=" + urls[13]
                    + "&P_STIR_1_END_DATE=" + urls[14]
                    + "&P_STIR_2_END_DATE=" + urls[15]
                    + "&P_STIR_3_END_DATE=" + urls[16]
                    + "&P_STIR_4_END_DATE=" + urls[17]
                    + "&P_STIR_5_END_DATE=" + urls[18]
                    + "&P_STIR_1_WORKER_ID=" + urls[19]
                    + "&P_STIR_2_WORKER_ID=" + urls[20]
                    + "&P_STIR_3_WORKER_ID=" + urls[21]
                    + "&P_STIR_4_WORKER_ID=" + urls[22]
                    + "&P_STIR_5_WORKER_ID=" + urls[23]
                    + "&P_USER_ID=" + urls[24]
                    + "&P_END_DATE=" + urls[25]
                    ;

            try
            {
                //String ip = context.getApplicationContext().getResources().getString(R.string.ip);

                URL obj = new URL("http://" + urls[0] + "/TAIYO/ReUpdate.jsp"); //주소 지정

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
                    fILE_NO_SCAN.execute(strIp, strSobId,strOrgId ,etT6FileNoScan.getText().toString(),etT6WorkercenterId.getText().toString()); //다시 fill

                    btnt6save.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.button_color));
                    btnt6save.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));


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

