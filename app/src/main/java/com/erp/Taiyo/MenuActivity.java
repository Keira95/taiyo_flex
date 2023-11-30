package com.erp.Taiyo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.erp.Taiyo.activity.CombinationWorkActivity;

import com.erp.Taiyo.activity.RegisterAdjustmentActivity;
import com.erp.Taiyo.activity.RegisterAgingActivity;
import com.erp.Taiyo.activity.RegisterDefomationActivity;
import com.erp.Taiyo.activity.RegisterPackingActivity;
import com.erp.Taiyo.activity.RegisterProcessActivity;
import com.erp.Taiyo.activity.RegisterResponseActivity;
import com.erp.Taiyo.activity.RegisterWeighingWorkActivity;
import com.erp.Taiyo.activity.SearchActivity;
import com.erp.Taiyo.activity.SurimiWeighingWorkActivity;
import com.erp.Taiyo.adapter.DbMenuListAdapter;
import com.erp.Taiyo.adapter.FileNoProcessAdapter;
import com.erp.Taiyo.adapter.MenuButtonAdapter;
import com.erp.Taiyo.adapter.MenuListAdapter;
import com.erp.Taiyo.adapter.DbMenuListAdapter;
import com.erp.Taiyo.item.DbMenuListItem;
import com.erp.Taiyo.item.FileNoProcessListItem;
import com.google.android.material.navigation.NavigationView;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class MenuActivity extends AppCompatActivity {
    DrawerLayout mDrawerLayout;
    Toolbar mToolbar;
    NavigationView mNavigationView;

    ActionBarDrawerToggle mDrawerToggle;

    String strUserId, strUserPassword, strUserStatus, strUserName, strSabeon, strVerison, strIp, strUserAuthority;
    String strSobId = "70";
    String strOrgId = "701";
    TextView tvId, tvUserName;
    ListView lvSearch;

    //버튼

    Button btnGR ,btnBH ,btnYU ,btnJJ ,btnCJ ,btnTP,btbBO,btnAg,btnPr,btnSr;
    GridView gv;

    MenuItem ItemId;

    String[] MENU_NAME ;
    String[] MENU_PROMPT ;
    String[] MENU_DESC = new String[2];
    String[] MENU_SHOW_FLAG;

    ArrayList<String> arr2 = new ArrayList<>();

    private List<ResolveInfo> menu;
    private PackageManager pm;
    com.erp.Taiyo.item.MenuIListtem menuIListtem;
    MenuListAdapter menuListAdapter;

    //login
    SharedPreferences appData;

    private ArrayList<String> arrayListTokens = new ArrayList<>();

    DbMenuListAdapter dbMenuListAdapter = new DbMenuListAdapter();
    DbMenuListItem dbMenuListItem = new DbMenuListItem();

    private RecyclerView recyclerView;
    private MenuButtonAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        strIp = intent.getStringExtra("Ip");
        strUserId = intent.getStringExtra("O_USER_ID");
        strUserName = intent.getStringExtra("O_USER_NAME");
        strUserAuthority = intent.getStringExtra("O_USER_AUTHORITY_TYPE");

        DbMenuListAdapter dbMenuListAdapter = new DbMenuListAdapter();
        DbMenuListItem dbMenuListItem = new DbMenuListItem();

//        final Intent = getIntent();
//        sintenttrUserId = intent.getStringExtra("O_USER_ID");
//        strUserName = intent.getStringExtra("O_USER_NAME");
//        //strSabeon = intent.getStringExtra("Sabeon");
//        strVerison = intent.getStringExtra("versionString");
//        strIp = intent.getStringExtra("Ip");
        overridePendingTransition(R.anim.silde_in_down, R.anim.silde_out_down);




        //로그인 정보
        appData = getSharedPreferences("appData", MODE_PRIVATE);

        menuListAdapter = new MenuListAdapter();


        //그리드뷰
        gv = (GridView) findViewById(R.id.gv_main_menu);

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MenuActivity.this, ""+i, Toast.LENGTH_SHORT).show();
            }
        });

        //메인
        tvId = (TextView) findViewById(R.id.tv_main_id);
        //.setText(strUserName + "(" + strSabeon + ")");
        tvId.setText(strUserName);

        tvUserName = (TextView) findViewById(R.id.tv_user_name);
        tvUserName.setText(strUserName);

        lvSearch = (ListView)  findViewById(R.id.lv_search);
        //Toolbar 설정
        mToolbar = findViewById(R.id.toolbar);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.navigation_view);


//        btnGR = findViewById(R.id.btn_1); //계량
//        btnBH = findViewById(R.id.btn_2); //배합
//        btnYU = findViewById(R.id.btn_3); //연육
//
//        btnJJ = findViewById(R.id.btn_4); //조정
//        btnCJ = findViewById(R.id.btn_5); //충진
//        btnTP = findViewById(R.id.btn_7); //탈포
//        btbBO = findViewById(R.id.btn_6); //반응
//        btnAg = findViewById(R.id.btn_8); //에이징
//        btnPr = findViewById(R.id.btn_9); //프로세스
//        btnSr = findViewById(R.id.btn_10); //프로세스

        Menu menu =  mNavigationView.getMenu();

        ItemId = menu.findItem(R.id.user_id);
        ItemId.setTitle(strUserId);

        MenuItem ItemName = menu.findItem(R.id.user_name);
        ItemName.setTitle(strUserName);

        //
      //  MenuItem MOBF0001 = menu.findItem(R.id.MOBF0009);
       // MenuItem MOBF0004 = menu.findItem(R.id.MOBF0010);
       // MenuItem MOBF0007 = menu.findItem(R.id.MOBF0011);


        MenuItem logOut   = menu.findItem(R.id.log_out);
        MenuItem appOut   = menu.findItem(R.id.app_out);


       // MenuItem ItemVerison = menu.findItem(R.id.user_verison);
      //  ItemVerison.setTitle(strVerison);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true); // 햄버거 아이콘 활성화

        //매뉴

        DB_Menu_Header  dbMenuHeader = new DB_Menu_Header();
        dbMenuHeader.execute(strIp,strSobId,strOrgId,strUserId);
       // DB_Menu_Detail dbMenuDetail = new DB_Menu_Detail();
       // dbMenuDetail.execute(strUserId);

     /*   mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NewApi")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.user_id:
                        break;
                    case R.id.user_name:
                        break;

                    case R.id.MOBF0009:
                        Intent MOBF0001 = new Intent(MenuActivity.this, RegisterAdjustmentActivity.class);
                        MOBF0001.putExtra("O_USER_ID", strUserId);
                        MOBF0001.putExtra("O_USER_NAME", strUserName);
                        MOBF0001.putExtra("Ip", strIp);
                        MOBF0001.putExtra("TOP_MENU_DESC", btnGR.getText().toString());
                        startActivity(MOBF0001);
                        break;
                  case R.id.MOBF0010:
                        Intent MOBF0004 = new Intent(MenuActivity.this, CombinationWorkActivity.class);
                        MOBF0004.putExtra("O_USER_ID", strUserId);
                        MOBF0004.putExtra("O_USER_NAME", strUserName);
                        MOBF0004.putExtra("Ip", strIp);
                        MOBF0004.putExtra("TOP_MENU_DESC", btnBH.getText().toString());
                        startActivity(MOBF0004);
                        break;
                    case R.id.MOBF0011:
                        Intent MOBF0007 = new Intent(MenuActivity.this, SurimiWeighingWorkActivity.class);
                        MOBF0007.putExtra("O_USER_ID", strUserId);
                        MOBF0007.putExtra("O_USER_NAME", strUserName);
                        MOBF0007.putExtra("Ip", strIp);
                        MOBF0007.putExtra("TOP_MENU_DESC", btnYU.getText().toString());
                        startActivity(MOBF0007);
             *//*       case R.id.MOBF0011:
                        Intent MOBF0011 = new Intent(MenuActivity.this, RegisterAdjustmentActivity.class);
                        MOBF0011.putExtra("O_USER_ID", strUserId);
                        MOBF0011.putExtra("O_USER_NAME", strUserName);
                        MOBF0011.putExtra("Ip", strIp);
                        MOBF0011.putExtra("TOP_MENU_DESC", btnMobf0007.getText().toString());
                        startActivity(MOBF0011);*//*



                    default:
                        break;
                }

                return false;
            }
        });*/

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.string.open, R.string.close);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        lvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DbMenuListAdapter adapter = (DbMenuListAdapter) lvSearch.getAdapter();
                for(int i =0; i< lvSearch.getCount(); i++){

                    DbMenuListItem item = (DbMenuListItem) adapter.getItem(i);

                    if( i == position){
                        moveActivity(item.getStrTopMenuId() ,item.getStrTopName());

                    }

                }

            }
        });


//        btnGR.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intentMat = new Intent(MenuActivity.this, RegisterWeighingWorkActivity.class);
//                intentMat.putExtra("O_USER_ID", strUserId);
//                intentMat.putExtra("O_USER_NAME", strUserName);
//                intentMat.putExtra("Ip", strIp);
//                intentMat.putExtra("TOP_MENU_DESC", btnGR.getText().toString());
//                startActivity(intentMat);
//            }
//        });
//
//        btnBH.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intentMat = new Intent(MenuActivity.this, CombinationWorkActivity.class);
//                intentMat.putExtra("O_USER_ID", strUserId);
//                intentMat.putExtra("O_USER_NAME", strUserName);
//                intentMat.putExtra("Ip", strIp);
//                intentMat.putExtra("TOP_MENU_DESC", btnBH.getText().toString());
//                startActivity(intentMat);
//            }
//        });
//
//        btnYU.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intentMat = new Intent(MenuActivity.this, SurimiWeighingWorkActivity.class);
//                intentMat.putExtra("O_USER_ID", strUserId);
//                intentMat.putExtra("O_USER_NAME", strUserName);
//                intentMat.putExtra("Ip", strIp);
//                intentMat.putExtra("TOP_MENU_DESC", btnYU.getText().toString());
//                startActivity(intentMat);
//            }
//        });
//
//        btnJJ .setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intentMat = new Intent(MenuActivity.this, RegisterAdjustmentActivity.class);
//                intentMat.putExtra("O_USER_ID", strUserId);
//                intentMat.putExtra("O_USER_NAME", strUserName);
//                intentMat.putExtra("Ip", strIp);
//                intentMat.putExtra("TOP_MENU_DESC", btnJJ .getText().toString());
//                startActivity(intentMat);
//            }
//        });
//
//        btnCJ.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intentMat = new Intent(MenuActivity.this, RegisterPackingActivity.class);
//                intentMat.putExtra("O_USER_ID", strUserId);
//                intentMat.putExtra("O_USER_NAME", strUserName);
//                intentMat.putExtra("Ip", strIp);
//                intentMat.putExtra("TOP_MENU_DESC", btnJJ .getText().toString());
//                startActivity(intentMat);
//            }
//        });
//
//        btbBO.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intentMat = new Intent(MenuActivity.this, RegisterResponseActivity.class);
//                intentMat.putExtra("O_USER_ID", strUserId);
//                intentMat.putExtra("O_USER_NAME", strUserName);
//                intentMat.putExtra("Ip", strIp);
//                intentMat.putExtra("TOP_MENU_DESC", btbBO .getText().toString());
//                startActivity(intentMat);
//            }
//        });
//
//        btnTP.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intentMat = new Intent(MenuActivity.this, RegisterDefomationActivity.class);
//                intentMat.putExtra("O_USER_ID", strUserId);
//                intentMat.putExtra("O_USER_NAME", strUserName);
//                intentMat.putExtra("Ip", strIp);
//                intentMat.putExtra("TOP_MENU_DESC", btnTP .getText().toString());
//                startActivity(intentMat);
//            }
//        });
//        btnAg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intentMat = new Intent(MenuActivity.this, RegisterAgingActivity.class);
//                intentMat.putExtra("O_USER_ID", strUserId);
//                intentMat.putExtra("O_USER_NAME", strUserName);
//                intentMat.putExtra("Ip", strIp);
//                intentMat.putExtra("TOP_MENU_DESC", btnAg .getText().toString());
//                startActivity(intentMat);
//            }
//        });
//
//        btnPr.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intentMat = new Intent(MenuActivity.this, RegisterProcessActivity.class);
//                intentMat.putExtra("O_USER_ID", strUserId);
//                intentMat.putExtra("O_USER_NAME", strUserName);
//                intentMat.putExtra("Ip", strIp);
//                intentMat.putExtra("TOP_MENU_DESC", btnPr .getText().toString());
//                startActivity(intentMat);
//            }
//        });
//        btnSr.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intentMat = new Intent(MenuActivity.this, SearchActivity.class);
//                intentMat.putExtra("O_USER_ID", strUserId);
//                intentMat.putExtra("O_USER_NAME", strUserName);
//                intentMat.putExtra("Ip", strIp);
//                intentMat.putExtra("TOP_MENU_DESC", btnSr .getText().toString());
//                startActivity(intentMat);
//            }
//        });



        logOut.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MenuActivity.this);
                alert.setTitle("로그아웃");
                alert.setMessage("로그아웃 하시겠습니까?");
                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences.Editor editor = appData.edit();
                        editor.remove("chkauto");
                        Intent intentHome = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intentHome);
                        finish();
                    }

                });
                alert.show();

                return false;
            }
        });

        appOut.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MenuActivity.this);
                alert.setTitle("종료");
                alert.setMessage("어플리케이션을 종료 하시겠습니까?");
                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ActivityCompat.finishAffinity(MenuActivity.this);
                        System.exit(0);

                    }

                });
                alert.show();

                return false;
            }
        });


        //종합 상황판
      /*  btnTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  //sendMessage(new JSONArray(arrayListTokens), "h system utility", "test utility", "h system", "h system");
                Intent intentMat = new Intent(MenuActivity.this, TotalMultiScreenActivity.class);
                intentMat.putExtra("Sabeon", strSabeon);
                intentMat.putExtra("UserName", strUserName);
                intentMat.putExtra("Ip", strIp);
                startActivity(intentMat);
            }
        });*/

        //LOG
 /*       btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMat = new Intent(MenuActivity.this, LogActivity.class);
                intentMat.putExtra("Sabeon", strSabeon);
                intentMat.putExtra("UserName", strUserName);
                intentMat.putExtra("Ip", strIp);
                startActivity(intentMat);
            }
        });

        //Token
        btnToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMat = new Intent(MenuActivity.this, TokenActivity.class);
                intentMat.putExtra("Sabeon", strSabeon);
                intentMat.putExtra("UserName", strUserName);
                intentMat.putExtra("Ip", strIp);
                startActivity(intentMat);
            }
        });*/

        //버튼 로그아웃
        tvId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MenuActivity.this);
                alert.setTitle("로그아웃");
                alert.setMessage("로그아웃 하시겠습니까?");
                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences.Editor editor = appData.edit();
                        editor.remove("chkauto");
                        editor.remove("pw");
                        editor.commit();
                        Intent intentHome = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intentHome);
                        finish();
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

    public void moveActivity(String TopMenuId , String TopMenuDesc){

        Intent intentMat = new Intent();

        if(TopMenuId.equals("10000")){ //계량
            intentMat = new Intent(MenuActivity.this, RegisterWeighingWorkActivity.class);

        }else if(TopMenuId.equals("10001")){ //배합
            intentMat = new Intent(MenuActivity.this, CombinationWorkActivity.class);

        }else if(TopMenuId.equals("10002")){ //연육
            intentMat = new Intent(MenuActivity.this, SurimiWeighingWorkActivity.class);

        }else if(TopMenuId.equals("10003")){ //조정
            intentMat = new Intent(MenuActivity.this, RegisterAdjustmentActivity.class);

        }else if(TopMenuId.equals("10004")){ //충진
            intentMat = new Intent(MenuActivity.this, RegisterPackingActivity.class);

        }else if(TopMenuId.equals("10006")){ //반응
            intentMat = new Intent(MenuActivity.this, RegisterResponseActivity.class);

        }else if(TopMenuId.equals("10007")){ //탈포
            intentMat = new Intent(MenuActivity.this, RegisterDefomationActivity.class);

        }else if(TopMenuId.equals("10008")){ //aging
            intentMat = new Intent(MenuActivity.this, RegisterAgingActivity.class);

        }else if(TopMenuId.equals("10009")){ //실적처리
            intentMat = new Intent(MenuActivity.this, RegisterProcessActivity.class);

        }else if(TopMenuId.equals("10010")){ //작업등록조회
            intentMat = new Intent(MenuActivity.this, SearchActivity.class);

        }


      intentMat.putExtra("O_USER_ID", strUserId);
      intentMat.putExtra("O_USER_NAME", strUserName);
      intentMat.putExtra("Ip", strIp);
      intentMat.putExtra("TOP_MENU_DESC", TopMenuDesc);
      startActivity(intentMat);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    protected class DB_Menu_Header extends AsyncTask<String, Void, String>
    {
        final DbMenuListAdapter dbMenuListAdapter = new DbMenuListAdapter();
        protected  String doInBackground(String... urls)
        {
            StringBuffer jsonHtml = new StringBuffer();

            //서버로 보낼 데이터 설정
            String search_title = "W_SOB_ID=" + urls[1]
                    + "&W_ORG_ID=" + urls[2]
                    + "&W_USER_ID=" + urls[3]
                    ;

            try
            {
                //String ip = context.getApplicationContext().getResources().getString(R.string.ip);

                URL obj = new URL("http://" + urls[0] + "/TAIYO/DBMenu.jsp"); //주소 지정

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

        @Override
        protected void onPostExecute(String result) {
            try{
                JSONObject RESULT = new JSONObject(result);
                JSONArray arr = RESULT.getJSONArray("RESULT");

                Menu menu =  mNavigationView.getMenu();

                ItemId = menu.findItem(R.id.user_id);
                ItemId.setTitle(strUserId);

                MenuItem ItemName = menu.findItem(R.id.user_name);

                for(int j=0; j< arr.length(); j++){

                JSONObject obj = arr.getJSONObject(j);

                    if(obj.getString("AUTHORITY_FLAG").equals("Y"))
                    {
                        dbMenuListAdapter.addItem(obj.getString("TOP_NAME"),obj.getString("AUTHORITY_FLAG"),obj.getString("TOP_SEQ"),obj.getString("USER_ID"),
                                obj.getString("TOP_MENU_ID"));

                    }
                }
                overridePendingTransition(R.anim.silde_in_down, R.anim.silde_out_down);
                lvSearch.setAdapter(dbMenuListAdapter);

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private List<String> generateMenuButtons() {
        List<String> buttons = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            buttons.add("메뉴 버튼 " + i);
        }
        return buttons;
    }


    protected class DB_Menu_Detail extends AsyncTask<String, Void, String>
    {
        protected  String doInBackground(String... urls)
        {
            String URL = "http://" + strIp + "/DRK/MenuDetail.jsp"; //자신의 웹서버 주소를 저장합니다.
            DefaultHttpClient client = new DefaultHttpClient();//HttpClient 통신을 합니다.

            try
            {
                HttpPost post = new HttpPost(URL+ "?W_USER_ID=" + urls[0]);

                HttpResponse response = client.execute(post);
                BufferedReader bufreader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"));

                String line = null;
                String result = "";

                while ((line = bufreader.readLine()) != null) {
                    result += line;
                }


                //tvLog.append();
                return result;

            }catch (Exception e)
            {
                e.printStackTrace();
            }
            return ""; //결과값 리턴
        }

        @Override
        protected void onPostExecute(String result) {
            try{
                JSONObject RESULT = new JSONObject(result);
                JSONArray arr = RESULT.getJSONArray("RESULT");


                for(int i=0; i< arr.length(); i++)
                {
                    JSONObject j_ob = arr.getJSONObject(i);
                    if(j_ob.getString("TOP_NAME").equals("MOBF0009"))
                    {
                        if(j_ob.getString("AUTHORITY_FLAG").equals("Y"))
                        {

                        }
                        else
                        {
                          //  btnWait.setEnabled(false);
                           // btnWait.setBackgroundColor(Color.GRAY);
                        }
                    }

                    if(j_ob.getString("TOP_NAME").equals("MOBF0010"))
                    {
                        if(j_ob.getString("AUTHORITY_FLAG").equals("Y"))
                        {
                        }
                        else
                        {
                            //btnMerger.setEnabled(false);
                           // btnMerger.setBackgroundColor(Color.GRAY);
                        }
                    }


                    if(j_ob.getString("TOP_NAME").equals("MOBF0011"))
                    {
                        if(j_ob.getString("AUTHORITY_FLAG").equals("Y"))
                        {
                        }
                        else
                        {
                           // btnMobf0007.setEnabled(false);
                           // btnMobf0007.setBackgroundColor(Color.GRAY);
                        }
                    }

                }


            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
