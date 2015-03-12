package com.car.carparking.view;

import java.text.DateFormat;
import java.text.ParseException;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

public class CheckoutView extends Activity implements View.OnClickListener{
	private TextView mCarId;
	private TextView mDateTime;
	private TextView mEndTime;
	private TextView mPriceStandard;
	private TextView mSubTotal;
	private DateFormat mDateFormat;
	private String mCarName;
	private String mStarttime;
	private String mEndtimeString;
	
	private Button mSubmit;
	private int mPriceItem = 10;
	private static int PER_HOUR = 0;
	private static int PER_TIEM = 1;
	private int mPriceType = PER_HOUR;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        initView();
    }

    private void initView(){
        Intent intent = getIntent();
        if(intent!=null){
        	mCarName = intent.getStringExtra("name");
        	mStarttime = intent.getStringExtra("datetime");
        }
        setContentView(R.layout.checkout);
        
    	mCarId = (TextView)findViewById(R.id.carname);
    	mCarId.setText(mCarName);
    	mDateTime = (TextView)findViewById(R.id.datetime);
    	mDateTime.setText(mStarttime);
    	mEndTime = (TextView)findViewById(R.id.endtime);
    	mEndtimeString = mDateFormat.format(System.currentTimeMillis());
    	mEndTime.setText(mEndtimeString);
    	mPriceStandard = (TextView)findViewById(R.id.price);
    	mSubTotal = (TextView)findViewById(R.id.totalprice);
    	if(mPriceType==PER_HOUR){
    		mPriceStandard.setText(mPriceItem + getString(R.string.price_houritem));
    		float hours=0f;
    		int realhours = 0;
    		 try {
    			 hours = (mDateFormat.parse(mEndtimeString).getTime() - mDateFormat.parse(mStarttime).getTime())/3600f/1000f;
    			 if(hours-(int)(hours) > 0.25f){
    				 realhours = (int)(hours) + 1;
    			 }else{
    				 realhours = (int)(hours);
    			 }
    		 }catch(ParseException e){
    			 e.printStackTrace();
    		 }
    		mSubTotal.setText(""+mPriceItem*realhours);
    	}else{
    		mPriceStandard.setText(mPriceItem + getString(R.string.price_item));
    		mSubTotal.setText(""+ mPriceItem);
    	}
    	mSubmit = (Button)findViewById(R.id.checkout);
    	mSubmit.setOnClickListener(this);
    }
    @Override
	public void onClick(View view) {
       if(view==mSubmit){
    	   Intent intent = new Intent(CheckoutView.this, CarListView.class);
    	   intent.putExtra("name", mCarName);
    	   setResult(RESULT_OK,intent);
    	   CheckoutView.this.finish();
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
}
