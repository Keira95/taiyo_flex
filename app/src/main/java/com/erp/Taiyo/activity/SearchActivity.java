package com.erp.Taiyo.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.erp.Taiyo.Dialog.LuOillerDialog;
import com.erp.Taiyo.adapter.FileNoProcessAdapter;
import com.erp.Taiyo.adapter.SearchListAdapter;
import com.erp.Taiyo.item.FileNoProcessListItem;
import com.erp.Taiyo.item.SearchListItem;

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

public class SearchActivity extends AppCompatActivity {

    Toolbar toolbar;
    String strIp, strUserName, strUserId, SDate, strMenuDesc;
    String filter = null;
    String strDate;

    EditText t10WorkcenterDesc, t10WorkcenterId, t10WorkcenterCode, t10FileNo, t10OperationId;
    String strSobId = "70";
    String strOrgId = "701";
    String strAssembly = "PPMF2201";


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

    Button btntsave, btnWorkcenteLu;

    //키보드

    InputMethodManager imm;
    JSONObject jspSumObject = new JSONObject();

    private boolean ScanModify = true;
    private boolean Mod_Flag = true;



    SearchListAdapter searchListAdapter = new SearchListAdapter();
    SearchListItem searchListItem = new SearchListItem();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_search_work10);
        Intent intent = getIntent();
        strIp = intent.getStringExtra("Ip");
        strUserId = intent.getStringExtra("O_USER_ID");
        strUserName = intent.getStringExtra("O_USER_NAME");
        strMenuDesc = intent.getStringExtra("TOP_MENU_DESC");

        mainLayout = findViewById(R.id.main_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvUserName = (TextView) findViewById(R.id.user_nm);
        tvMenu = (TextView) findViewById(R.id.tv_menu);

        t10WorkcenterDesc = (EditText) findViewById(R.id.et_t10_workcenter_desc);
        t10WorkcenterId = (EditText) findViewById(R.id.et_t10_workcenter_id);
        t10WorkcenterCode = (EditText) findViewById(R.id.et_t10_workcenter_code);
        t10FileNo = (EditText) findViewById(R.id.et_t10_file_no);
        t10OperationId = (EditText) findViewById(R.id.et_t10_operation_id);

        btntsave = (Button) findViewById(R.id.btn_t10_search);
        btnWorkcenteLu = (Button) findViewById(R.id.btn_t10_workcenter_lookup);

        lvInput = (ListView)  findViewById(R.id.lv_search);
        auto = getSharedPreferences("appData_Log", Context.MODE_PRIVATE);
        //키보드 내리기

        //키보드
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);



        initializeToolbar();

        t10FileNo.requestFocus();




        t10FileNo.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(getCurrentFocus() == t10FileNo && !s.toString().isEmpty() && ScanModify){

                  /*  t10FileNo.setText(s.toString());
                    t10WorkcenterId.requestFocus();*/

                    SEARCH sEARCH = new SEARCH();
                    sEARCH.execute(strIp, strSobId, strOrgId, t10WorkcenterId.getText().toString(), t10FileNo.getText().toString().replaceAll("\n", ""));


                }else{
                    ScanModify = true;
                    return;
                }
            }
        });

        t10FileNo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClearView();
                ScanModify = false;
                return false;
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


    private void ClearView(){

        t10FileNo.setText("");
        t10WorkcenterDesc.setText("");
        searchListAdapter.clearItem();
        lvInput.setAdapter(searchListAdapter);
        searchListAdapter.notifyDataSetChanged();
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

        btnWorkcenteLu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LuOillerDialog luOillerDialog = new LuOillerDialog(SearchActivity.this);
                luOillerDialog.call_Workcetner(strIp ,t10WorkcenterCode,t10WorkcenterDesc,t10WorkcenterId,strUserId, t10OperationId);

                t10FileNo.requestFocus();
            }
        });
        btntsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SEARCH sEARCH = new SEARCH();
                sEARCH.execute(strIp, strSobId, strOrgId, t10WorkcenterId.getText().toString(), t10FileNo.getText().toString().replaceAll("\n", ""));

            }
        });


    }



    // FILE_NO_SCAN
    protected class SEARCH extends AsyncTask<String, Void, String> {
        final SearchListAdapter searchListAdapter = new SearchListAdapter();

        protected String doInBackground(String... urls) {
            StringBuffer jsonHtml = new StringBuffer();

            //서버로 보낼 데이터 설정
            String search_title = "W_SOB_ID=" + urls[1]
                    + "&W_ORG_ID=" + urls[2]
                    + "&W_WORKCENTER_ID=" + urls[3]
                    + "&W_WORK_ORDER_NO=" + urls[4]
                   ;

            try {
                URL obj = new URL("http://" + urls[0] + "/TAIYO/WipOnhandSelect.jsp"); //주소 지정

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


                if (jarrayWorkLevel.length() < 1) {
                    // Toast.makeText(getApplicationContext(), "데이터가 없습니다.", Toast.LENGTH_SHORT).show();
                    ScanModify = false;
                    return;
                }
                String chk = "√";
                for (int j = 0; j < jarrayWorkLevel.length(); j++) {
                    JSONObject job = jarrayWorkLevel.getJSONObject(j);
                    if (!job.getString("Status").equals("S")) {
                        return;
                    }

                    searchListAdapter.addItem(
                            handleStringNull(job.getString("OPERATION_DESCRIPTION")),
                            handleStringNull(job.getString("STEP_DESC")),
                            handleStringNull(job.getString("WORK_ORDER_NO")),
                            handleStringNull(job.getString("ITEM_DESCRIPTION")),
                            handleStringNull(job.getString("TOTAL_ONHAND_QTY")),
                            handleStringNull(job.getString("OP_POISE_SEQ")),
                            handleStringNull(job.getString("OP_UNIT_SEQ")),
                            handleStringNull(job.getString("SORT_NO"))

                    );

                }
                t10FileNo.setText("");
                lvInput.setAdapter(searchListAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String handleStringNull(String input) {

        String rtStr = input.equals("null") ? "" : input;

        return rtStr;
    }


}

