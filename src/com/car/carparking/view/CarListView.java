package com.car.carparking.view;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.car.carparking.R;
import com.car.carparking.R.id;

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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

public class CarListView extends Activity implements View.OnClickListener{
	private AlertDialog mAlertDialog;
	private EditText mAddressEditText;
	private TextView mCountView;
	
	private DateFormat mDateFormat;
	
	private Spinner mSpinner;
    private ArrayAdapter<String> mCarProvinceArray;
    private String[] mCarProvince;
    private String mCurrentProvince;
	
	ListView mItemList;
    ItemListAdapter mItemListAdapter;
    private  ArrayList<Map<String,Object>>  mArrayList=  new ArrayList<Map<String,Object>>();
    
    public String[] mItemName;
	public int mCount;
	
	

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
        case id.action_auto:
            break;
        case id.action_manual:
        	showAddEdit();
            break;
        }
        return false;
    }

    private void initView(){
        //Intent intent = getIntent();
        setContentView(R.layout.itemview);
        mItemList = (ListView)findViewById(R.id.listview);
        mCountView = (TextView)findViewById(R.id.listcount);
        initListView();
        initListDate();
    }
    
    
    private void initListView() {
    	mItemListAdapter=new ItemListAdapter(
                this,
                mArrayList,
                R.layout.itemlistadapt
                );
        mItemList.setAdapter(mItemListAdapter);
        mItemList.setOnItemClickListener(new OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            // TODO Auto-generated method stub
        	Map<String,Object> map = (Map<String,Object>)mItemListAdapter.getItem(arg2);
        	Intent intent = new Intent();
            intent.setClassName("com.car.carparking","com.car.carparking.view.CheckoutView");
            intent.putExtra("name", map.get("name").toString());
            intent.putExtra("datetime", map.get("datetime").toString());
            startActivityForResult(intent, 0);
        }
         
    });
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
	    }
	}

    private void initListDate(){         
    	String str = mDateFormat.format(System.currentTimeMillis()); 
    	mArrayList.clear();
//        for(int i=0;i< 5;i++){
//        	Map map = new HashMap<String, Object>();
//            map.put("name", "À’FMG888");
//            map.put("datetime", str);
//            mArrayList.add(map);
//        }
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
	                                            map.put("name", mSpinner.getSelectedItem() + add);
	                                            map.put("datetime", mDateFormat.format(System.currentTimeMillis()));
	                                            mArrayList.add(map);
	                                            mItemListAdapter.notifyDataSetChanged();
	                                            mCountView.setText(""+ mArrayList.size());
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
       //
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
}
