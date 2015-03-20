package com.car.carparking.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.car.carparking.R;
import com.car.carparking.module.Account;
import com.car.carparking.module.AccountManager;
import com.car.carparking.module.Location;
import com.car.carparking.module.LocationManager;

import java.util.*;

/**
 * Created by Home on 2015/3/16.
 */
public class LocationManagementView extends Activity implements View.OnClickListener{
    Button mBtnAddLoc;
    ItemListAdapter mAdapter;
    List<Account> mAccountList;
    ArrayList<Map<String,Object>> mDataList;
    int mAccountSelectIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locationmanagement);
        TitleView tv = (TitleView) findViewById(R.id.titleview);
        tv.setTitle(R.string.manage_location);
        initView();
        mBtnAddLoc = (Button) findViewById(R.id.button_add_location);
        mBtnAddLoc.setOnClickListener(this);
    }
    private void updateDataList(ArrayList<Map<String,Object>> datalist){
        if (datalist == null) {
            return;
        }
        //clear list first
        datalist.clear();
        //get account from account manager
        LocationManager lm = LocationManager.getInstance(this);
        List<Location> locations = lm.getAllLocation();
        if (locations != null) {
            //init datalist
            Iterator<Location> iter = locations.iterator();
            while (iter.hasNext()) {
                Location location = iter.next();
                Map map = new HashMap<String, Object>();
                map.put("name", location.getName());
                AccountManager am = AccountManager.getInstance(this);
                Account account = am.getAccountById(location.getAccount());
                if (account != null) {
                    map.put("datetime", account.getName());
                }
                map.put("loc", location);
                datalist.add(map);
            }
        }
        Iterator<Map<String,Object>> iter = datalist.iterator();
        while(iter.hasNext()) {
            Map<String,Object> map = iter.next();
            Log.d("dongbin", "name:" + map.get("name").toString());
        }
    }
    private void initView(){
        AccountManager am = AccountManager.getInstance(this);
        mAccountList  = am.getAccountList();
        final String namelist[] = new String[mAccountList.size()];
        for (int i=0;i<mAccountList.size();i++) {
            namelist[i] = mAccountList.get(i).getName();
        }

        mDataList = new ArrayList<Map<String,Object>>();
        updateDataList(mDataList);
        ListView lv = (ListView) findViewById(R.id.listview_location);
        mAdapter = new ItemListAdapter(
                this,
                mDataList,
                R.layout.itemlistadapt
        );
        lv.setAdapter(mAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                Map<String, Object> map = (Map<String, Object>) mAdapter.getItem(arg2);
                final Location loc = (Location)map.get("loc");
                new AlertDialog.Builder(LocationManagementView.this).
                        setTitle(getString(R.string.location_input_account_hint))
                        .setSingleChoiceItems(namelist, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mAccountSelectIndex = i;
                            }
                        })
                        .setNegativeButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Log.d("dongbin", "select " + mAccountSelectIndex);
                                        Account account = mAccountList.get(mAccountSelectIndex);
                                        account.setLocation(loc.getName());
                                        AccountManager.getInstance(LocationManagementView.this).updateAccount(account);
                                        loc.setAccount(account.getId());
                                        LocationManager.getInstance(LocationManagementView.this).updateLocation(loc);
                                        updateDataList(mDataList);
                                        mAdapter.notifyDataSetChanged();
                                    }
                                }
                        )
                        .setPositiveButton(getString(R.string.cancel), null)
                        .show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        if (view == mBtnAddLoc) {
            final EditText inputLocation = new EditText(this);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.location_input_hint))
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setView(inputLocation)
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.cancel), null)
                    .setNegativeButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            String name = inputLocation.getText().toString();
                            Location location = new Location(0,name,0);
                            LocationManager lm = LocationManager.getInstance(LocationManagementView.this);
                            if (lm.addLocation(location)) {
                                updateDataList(mDataList);
                                mAdapter.notifyDataSetChanged();
                            }
                        }
                    });
            builder.show();
        }
    }
}
