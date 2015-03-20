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
import com.car.carparking.module.*;

import java.util.*;

/**
 * Created by Home on 2015/3/16.
 */
public class CarparkStatusView extends Activity{
    ItemListAdapter mAdapter;
    ArrayList<Map<String,Object>> mDataList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carparkstatus);
        TitleView tv = (TitleView) findViewById(R.id.titleview);
        tv.setTitle(R.string.manage_park_status);
        initView();
    }
    private void updateDataList(ArrayList<Map<String,Object>> datalist){
        if (datalist == null) {
            return;
        }
        //clear list first
        datalist.clear();
        //get account from account manager
        LocationManager lm = LocationManager.getInstance(this);
        CarManager cm  = CarManager.getInstance(this);
        List<Location> locations = lm.getAllLocation();
        if (locations != null) {
            //init datalist
            Iterator<Location> iter = locations.iterator();
            while (iter.hasNext()) {
                Location location = iter.next();
                Map map = new HashMap<String, Object>();
                map.put("name", location.getName());
                List<Car> cars = cm.getAllCars(location.getName());
                map.put("datetime", String.valueOf(cars.size()));
                datalist.add(map);
            }
        }
    }
    private void initView(){
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
                Intent intent = new Intent();
                intent.putExtra("location", (String)map.get("name"));
                intent.putExtra("support_select", false);
                intent.setClassName("com.car.carparking", "com.car.carparking.view.CarListView");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
