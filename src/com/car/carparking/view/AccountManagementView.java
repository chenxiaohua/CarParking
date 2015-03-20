package com.car.carparking.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import com.car.carparking.R;
import com.car.carparking.module.Account;
import com.car.carparking.module.AccountManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Home on 2015/3/15.
 */
public class AccountManagementView extends Activity implements View.OnClickListener{
    Button mBtnAddAccount;
    ItemListAdapter mAdapter;
    ArrayList<Map<String,Object>> mDataList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accountmanagement);
        TitleView tv = (TitleView) findViewById(R.id.titleview);
        if (tv != null) {
            tv.setTitle(R.string.manage_account);
        }
        initView();
        mBtnAddAccount = (Button) findViewById(R.id.button_add_account);
        mBtnAddAccount.setOnClickListener(this);
    }
    private void updateDataList(ArrayList<Map<String,Object>> datalist){
        if (datalist == null) {
            return;
        }
        //clear list first
        datalist.clear();
        //get account from account manager
        AccountManager am = AccountManager.getInstance(this);
        List<Account> accounts = am.getAccountList();
        if (accounts != null) {
            //init datalist
            Iterator<Account> iter = accounts.iterator();
            while (iter.hasNext()) {
                Account account = iter.next();
                Map map = new HashMap<String, Object>();
                map.put("name", account.getName());
                DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                String extime = "";
                try {
                    extime = sdf.format(account.getExpiration());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //map.put("datetime", R.string.expiration_time + " " + extime);
                String loc = account.getLocation();
                if (loc!=null) {
                    map.put("datetime",loc);
                }else
                    map.put("datetime", extime);
                datalist.add(map);
            }
        }
    }
    private void initView(){
        mDataList = new ArrayList<Map<String,Object>>();
        updateDataList(mDataList);
        ListView lv = (ListView) findViewById(R.id.account_listview);
        mAdapter = new ItemListAdapter(
                this,
                mDataList,
                R.layout.itemlistadapt
        );
        lv.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateDataList(mDataList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        if (view == mBtnAddAccount) {
            Intent intent = new Intent();
            intent.setClassName("com.car.carparking", "com.car.carparking.view.AccountAddView");
            startActivity(intent);
        }
    }
}
