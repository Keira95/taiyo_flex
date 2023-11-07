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
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
            etT6Stir2WorkerName, etT6Stir3WorkerName, etT6Stir4WorkerName, etT6Stir5WorkerName, etT6EndTime  ;





    LinearLayout mainLayout;


    Button btnt6save;
    Button btnT6StartTime, btnT6Stir1StartDate, btnT6Stir2StartDate, btnT6Stir3StartDate, btnT6Stir4StartDate, btnT6Stir5StartDate ,btnT6Stir1EndDate,
            btnT6Stir2EndDate, btnT6Stir3EndDate, btnT6Stir4EndDate, btnT6Stir5EndDate, btnT6EndTime;

    InputMethodManager imm;
    JSONObject jspSumObject = new JSONObject();

    private boolean ScanModify = true;
    private boolean Mod_Flag = true;


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



    }


}

