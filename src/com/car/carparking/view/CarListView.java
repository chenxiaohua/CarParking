package com.car.carparking.view;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.car.carparking.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import com.car.carparking.module.AccountManager;
import com.car.carparking.module.Car;
import com.car.carparking.module.CarManager;

public class CarListView extends Activity implements View.OnClickListener{
	private AlertDialog mAlertDialog;
	private EditText mAddressEditText;
	private TextView mCountView;
	
	private DateFormat mDateFormat;
	
	private Spinner mSpinner;
    private ArrayAdapter<String> mCarProvinceArray;
    private String[] mCarProvince;
    private String mCurrentProvince;
    private String mLocation;

	ListView mItemList;
    ItemListAdapter mItemListAdapter;
    private  ArrayList<Map<String,Object>>  mArrayList=  new ArrayList<Map<String,Object>>();
    
    public String[] mItemName;
	public int mCount;
	
	TitleView mTitleView;
	ImageView mMenu;
	
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        initView();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addcar, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
        case R.id.action_auto:
        	Intent intent = new Intent();
        	intent.setClassName("com.car.carparking","com.car.carparking.view.AutoScanCarPlate");
            startActivityForResult(intent, 1);
            break;
        case R.id.action_manual:
        	showAddEdit();
            break;
        }
        return false;
    }

    private void initView(){
        setContentView(R.layout.itemview);
        mItemList = (ListView)findViewById(R.id.listview);
        mCountView = (TextView)findViewById(R.id.listcount);
        mTitleView = (TitleView)findViewById(R.id.title_view);
        mTitleView.setMenuVisible(View.VISIBLE);

        mMenu = (ImageView)mTitleView.findViewById(R.id.menu);
        mMenu.setOnClickListener(this);

        initListData();
        initListView();
    }
    
    
    private void initListView() {
    	mItemListAdapter=new ItemListAdapter(
                this,
                mArrayList,
                R.layout.itemlistadapt
                );
        mItemList.setAdapter(mItemListAdapter);
        Intent intent = getIntent();
        boolean support_select = intent.getBooleanExtra("support_select", true);
        if (support_select) {
            mItemList.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    // TODO Auto-generated method stub
                    Map<String, Object> map = (Map<String, Object>) mItemListAdapter.getItem(arg2);
                    Intent intent = new Intent();
                    intent.setClassName("com.car.carparking", "com.car.carparking.view.CheckoutView");
                    intent.putExtra("name", map.get("name").toString());
                    intent.putExtra("datetime", map.get("datetime").toString());
                    startActivityForResult(intent, 0);
                }

            });
        }
        mItemListAdapter.notifyDataSetChanged();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
	    if (requestCode == 0) {
	        if (resultCode == RESULT_OK) {
	            if(intent!=null){
	            	String carname = intent.getStringExtra("name");
	            	if(carname!=null && !carname.equals("")&&mArrayList!=null&&mArrayList.size()>0){
		            	for(int i=0;i<mArrayList.size();i++){
		            		if(mArrayList.get(i).get("name").equals(carname)){
                                Car car = (Car)mArrayList.get(i).get("car");
                                CarManager.getInstance(this).delCar(car);
		            			mArrayList.remove(i);
		            			break;
		            		}
		            	}
		            	mItemListAdapter.notifyDataSetChanged();
		            	mCountView.setText(""+ mArrayList.size());
	            	}
	            }
	        } else if (resultCode == RESULT_CANCELED) {
	            // Handle cancel
	        }
	    }else if(requestCode == 1){
	    	if (resultCode == RESULT_OK) {
	            if(intent!=null){
	            	String carname = intent.getStringExtra("name");
	            	if(carname!=null && !carname.equals("")&&mArrayList!=null){
                        Timestamp ts = new Timestamp(System.currentTimeMillis());
	            		Map map = new HashMap<String, Object>();
                        map.put("name", carname);
                        map.put("datetime", mDateFormat.format(ts.getTime()));
                        Car car = new Car(0,carname,ts,mLocation);
                        map.put("car", car);
                        mArrayList.add(map);
		            	mItemListAdapter.notifyDataSetChanged();
		            	mCountView.setText(""+ mArrayList.size());
                        CarManager.getInstance(this).addCar(car);
	            	}
	            }
	        } else if (resultCode == RESULT_CANCELED) {
	            // Handle cancel
	        }
	    }
	}

    private void initListData(){
    	mArrayList.clear();
        Intent intent = getIntent();
        mLocation = intent.getStringExtra("location");
        List<Car> cars = CarManager.getInstance(this).getAllCars(mLocation);
        Log.d("dongbin", "get car number" + cars.size());
        if (cars != null) {
            Iterator<Car> iter = cars.iterator();
            while(iter.hasNext()) {
                Car car = iter.next();
                Log.d("dongbin", car.getId() + " " + car.getPlate());
                Map map = new HashMap<String, Object>();
                map.put("name", car.getPlate());
                map.put("datetime", mDateFormat.format(car.getParktime()));
                map.put("car",car);
                mArrayList.add(map);
            }
        }
        if(mCountView!=null){
        	mCountView.setText(""+ mArrayList.size());
        }
    }
    private void showAddEdit(){
		   LayoutInflater inflater=LayoutInflater.from(CarListView.this);
		   final View carview=inflater.inflate(R.layout.caraddress, null);
		   mAddressEditText = (EditText)carview.findViewById(R.id.carnum);
		   mSpinner = (Spinner)carview.findViewById(R.id.Spinner01);
		   mCarProvince = getResources().getStringArray(R.array.province_list);;
		   mCarProvinceArray = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,mCarProvince);
		   mCarProvinceArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		   mSpinner.setAdapter(mCarProvinceArray);
		   mSpinner.setOnItemSelectedListener(new SpinnerSelectedListener());
		   mSpinner.setSelection(1);
		   mSpinner.setVisibility(View.VISIBLE);
		   mAlertDialog = new AlertDialog.Builder(this)
		             .setTitle(R.string.action_input)
		             .setIcon(android.R.drawable.ic_dialog_info)
		             .setView(carview)
				     .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {  
	                                     @Override  
	                                     public void onClick(DialogInterface dialog, int which) {
	                                    	 String add = mAddressEditText.getText().toString();
	                                    	 if(add!=null&&!add.equals("")&&add.length()==6){
                                                 Map map = new HashMap<String, Object>();
                                                 String carplate = mSpinner.getSelectedItem() + add ;
                                                 map.put("name", carplate);
                                                 Timestamp ts = new Timestamp(System.currentTimeMillis());
                                                 map.put("datetime", mDateFormat.format(ts));
                                                 Car car = new Car(0, carplate,ts, mLocation);
                                                 map.put("car", car);
                                                 mArrayList.add(map);
                                                 mItemListAdapter.notifyDataSetChanged();
                                                 mCountView.setText(""+ mArrayList.size());
                                                 CarManager.getInstance(CarListView.this).addCar(car);
	                                    	 }
	                     	                 mAlertDialog.dismiss();
	                                     }  
	                 })
				     .setNegativeButton(getString(R.string.cancel), null)
				     .setCancelable(false)
				     .show();
    }
    class SpinnerSelectedListener implements OnItemSelectedListener{
    	 
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                long arg3) {
        	mCurrentProvince = mCarProvince[arg2];
        }
        public void onNothingSelected(AdapterView<?> arg0) {
        	//
        }
    }
    @Override
	public void onClick(View view) {
    	if(view==mMenu){
			 openOptionsMenu();
		 }
	}

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        initView();
    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setTitle(R.string.dialog_quit)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    	CarListView.this.finish();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //
                    }
                }).show();
    }
}
