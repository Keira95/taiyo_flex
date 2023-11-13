package com.erp.Taiyo.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.TextView;

import com.erp.Taiyo.R;
import com.erp.Taiyo.item.LuOillerIListtem;
import com.erp.Taiyo.adapter.LuOillerListAdapter;

import com.erp.Taiyo.item.LuMoveControlItem;
import com.erp.Taiyo.adapter.LuMoveControlAdapter;

import com.erp.Taiyo.item.LuWorkCenterItem;
import com.erp.Taiyo.adapter.LuWorkCenterAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class LuOillerDialog {
    private Context context;

    EditText etFilter;
    Button btnClose;
    ListView IvItem;
    String ip ,strSobId, strOrgId;


    public LuOillerDialog(Context context) {
        this.context = context;
    }

    // 호출할 다이얼로그 함수를 정의한다.
    public void call_Oiller(String ip, final TextView tvCode, final TextView tvName, final TextView tvId) {

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dialog = new Dialog(context);

        strSobId = "70";
        strOrgId ="701";
        // 액티비티의 타이틀바를 숨긴다.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dialog.setContentView(R.layout.dialog_lu_oiller);

        DisplayMetrics dm = dialog.getContext().getApplicationContext().getResources().getDisplayMetrics();
        WindowManager.LayoutParams wm = new WindowManager.LayoutParams();
        wm.copyFrom(dialog.getWindow().getAttributes());
        wm.width = dm.widthPixels - (dm.widthPixels / 10);
        wm.height = dm.heightPixels - (dm.widthPixels / 3);
        dialog.getWindow().setAttributes(wm);

        // 커스텀 다이얼로그를 노출한다.
        dialog.show();

        //LAYOUT
        etFilter = dialog.findViewById(R.id.et_filter);
        btnClose = dialog.findViewById(R.id.btn_close);
        IvItem = dialog.findViewById(R.id.lv_list);


        Lu_Oiller lu_oiller = new Lu_Oiller();
        lu_oiller.execute(ip.toString(), strSobId, strOrgId);


        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        IvItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LuOillerIListtem luItemListitem = (LuOillerIListtem) parent.getAdapter().getItem(position);

                tvCode.setText(luItemListitem.getStrItemCode());
                tvName.setText(luItemListitem.getStrItemDesc());
                tvId.setText(luItemListitem.getStrItemId());
                dialog.dismiss();
            }
        });

        etFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                ((LuOillerListAdapter) IvItem.getAdapter()).getFilter().filter(etFilter.getText().toString());
            }
        });


    }
    public void call_Workcetner(String ip, final TextView tvCode, final TextView tvName, final TextView tvId , final String UserId , final TextView  OperationId) {

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dialog = new Dialog(context);

        strSobId = "70";
        strOrgId ="701";
        // 액티비티의 타이틀바를 숨긴다.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dialog.setContentView(R.layout.dialog_lu_oiller);

        DisplayMetrics dm = dialog.getContext().getApplicationContext().getResources().getDisplayMetrics();
        WindowManager.LayoutParams wm = new WindowManager.LayoutParams();
        wm.copyFrom(dialog.getWindow().getAttributes());
        wm.width = dm.widthPixels - (dm.widthPixels / 10);
        wm.height = dm.heightPixels - (dm.widthPixels / 3);
        dialog.getWindow().setAttributes(wm);

        // 커스텀 다이얼로그를 노출한다.
        dialog.show();

        //LAYOUT
        etFilter = dialog.findViewById(R.id.et_filter);
        btnClose = dialog.findViewById(R.id.btn_close);
        IvItem = dialog.findViewById(R.id.lv_list);

        Lu_Workcenter   lu_workcenter = new Lu_Workcenter();
        lu_workcenter.execute(ip.toString(), strSobId,strOrgId, UserId , OperationId.getText().toString());



        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        IvItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LuWorkCenterItem luItemListitem = (LuWorkCenterItem) parent.getAdapter().getItem(position);

                tvCode.setText(luItemListitem.getStrWorkCode());
                tvName.setText(luItemListitem.getStrWorkDesc());
                tvId.setText(luItemListitem.getStrWorkId());
                dialog.dismiss();
            }
        });

        etFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                ((LuOillerListAdapter) IvItem.getAdapter()).getFilter().filter(etFilter.getText().toString());
            }
        });


    }

    public void call_Move_Trx(String ip, final TextView tvCode, final TextView tvName, final TextView tvId ,final String UserId ,final TextView tvMoveTrxType) {

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dialog = new Dialog(context);

        strSobId = "70";
        strOrgId ="701";
        // 액티비티의 타이틀바를 숨긴다.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dialog.setContentView(R.layout.dialog_lu_oiller);

        DisplayMetrics dm = dialog.getContext().getApplicationContext().getResources().getDisplayMetrics();
        WindowManager.LayoutParams wm = new WindowManager.LayoutParams();
        wm.copyFrom(dialog.getWindow().getAttributes());
        wm.width = dm.widthPixels - (dm.widthPixels / 10);
        wm.height = dm.heightPixels - (dm.widthPixels / 3);
        dialog.getWindow().setAttributes(wm);

        // 커스텀 다이얼로그를 노출한다.
        dialog.show();

        //LAYOUT
        etFilter = dialog.findViewById(R.id.et_filter);
        btnClose = dialog.findViewById(R.id.btn_close);
        IvItem = dialog.findViewById(R.id.lv_list);

        Lu_Move_Trx lu_move_trx = new Lu_Move_Trx();
        lu_move_trx.execute(ip.toString(), strSobId, strOrgId,UserId,tvMoveTrxType.getText().toString());



        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        IvItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LuMoveControlItem luItemListitem = (LuMoveControlItem) parent.getAdapter().getItem(position);

                tvCode.setText(luItemListitem.getStrMoveType());
                tvName.setText(luItemListitem.getStrMoveDesc());
                tvId.setText(luItemListitem.getStrMoveTypeId());
                dialog.dismiss();
            }
        });

        etFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                ((LuOillerListAdapter) IvItem.getAdapter()).getFilter().filter(etFilter.getText().toString());
            }
        });


    }





    /*아이템 룩업*/
    protected class Lu_Oiller extends AsyncTask<String, Void, String>
    {
        final LuOillerListAdapter luItemAdapter = new LuOillerListAdapter();
        protected  String doInBackground(String... urls)
        {
            StringBuffer jsonHtml = new StringBuffer();

            //서버로 보낼 데이터 설정
            String search_title = "W_SOB_ID=" + urls[1]
                    + "&W_ORG_ID=" + urls[2];


            try
            {  URL obj = new URL("http://" + urls[0] + "/TAIYO/LuOiler.jsp"); //주소 지정

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

                for(int i = 0; i < jarrayWorkLevel.length(); i++)
                {
                    JSONObject job = jarrayWorkLevel.getJSONObject(i); //JSON 오브젝트 파싱

                    LuOillerIListtem luItemListItem = new LuOillerIListtem();

                    luItemListItem.setStrItemCode(job.getString("ITEM_CODE"));
                    luItemListItem.setStrItemDesc(job.getString("ITEM_DESC"));
                    luItemListItem.setStrItemId(job.getString("INVENTORY_ITEM_ID"));
                    luItemListItem.setStrYuChkFlag(job.getString("YU_CHECK_FLAG"));

                    luItemAdapter.addItem(luItemListItem);

                }

                IvItem.setAdapter(luItemAdapter);

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


    protected class Lu_Workcenter extends AsyncTask<String, Void, String>
    {
        final LuWorkCenterAdapter luWorkCenterAdapter = new LuWorkCenterAdapter();
        protected  String doInBackground(String... urls)
        {
            StringBuffer jsonHtml = new StringBuffer();

            //서버로 보낼 데이터 설정
            String search_title = "W_SOB_ID=" + urls[1]
                    + "&W_ORG_ID=" + urls[2]
                    + "&P_USER_ID=" + urls[3]
                    + "&P_OPERATION_ID=" + urls[4]
                    ;


            try
            {  URL obj = new URL("http://" + urls[0] + "/TAIYO/LuWorkcenter.jsp"); //주소 지정

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

                for(int i = 0; i < jarrayWorkLevel.length(); i++)
                {
                    JSONObject job = jarrayWorkLevel.getJSONObject(i); //JSON 오브젝트 파싱

                    LuWorkCenterItem luWorkCenterItem = new LuWorkCenterItem();

                    luWorkCenterItem.setStrWorkCode(job.getString("WORKCENTER_CODE"));
                    luWorkCenterItem.setStrWorkDesc(job.getString("WORKCENTER_DESCRIPTION"));
                    luWorkCenterItem.setStrWorkId(job.getString("WORKCENTER_ID"));

                    luWorkCenterAdapter.addItem(luWorkCenterItem);
                }

                IvItem.setAdapter(luWorkCenterAdapter);

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

    protected class Lu_Move_Trx extends AsyncTask<String, Void, String>
    {
        final LuMoveControlAdapter luMoveControlAdapter = new LuMoveControlAdapter();
        protected  String doInBackground(String... urls)
        {
            StringBuffer jsonHtml = new StringBuffer();

            //서버로 보낼 데이터 설정
            String search_title = "W_SOB_ID=" + urls[1]
                    + "&W_ORG_ID=" + urls[2]
                    + "&P_USER_ID=" + urls[3]
                    + "&P_MOVE_TRX_TYPE=" + urls[4]
                    ;


            try
            {  URL obj = new URL("http://" + urls[0] + "/TAIYO/LuMoveTrxControl.jsp"); //주소 지정

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

                for(int i = 0; i < jarrayWorkLevel.length(); i++)
                {
                    JSONObject job = jarrayWorkLevel.getJSONObject(i); //JSON 오브젝트 파싱

                    LuMoveControlItem luMoveControlItem = new LuMoveControlItem();

                    luMoveControlItem.setStrMoveType(job.getString("MOVE_TRX_TYPE"));
                    luMoveControlItem.setStrMoveDesc(job.getString("DESCRIPTION"));
                    luMoveControlItem.setStrMoveTypeId(job.getString("MOVE_TRX_TYPE_ID"));

                    luMoveControlAdapter.addItem(luMoveControlItem);
                }

                IvItem.setAdapter(luMoveControlAdapter);

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
