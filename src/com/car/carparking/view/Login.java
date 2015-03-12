package com.car.carparking.view;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.content.Intent;

import com.car.carparking.R;

public class Login extends Activity implements View.OnClickListener{
    /**
     * Called when the activity is first created.
     */
	private ImageButton mLoginButton;
	private EditText mUserText;
	private EditText mPwdText;
	static private int INVALID_NULL = -1;
	static private int RET_OK = 0;
	static private int INVALID_PWD = 1;
	static private int INVALID_USER =2;
	private TitleView mTitleView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mLoginButton = (ImageButton)findViewById(R.id.logimgButton);
        mLoginButton.setOnClickListener( this);
        mUserText = (EditText)findViewById(R.id.username);
        mPwdText = (EditText)findViewById(R.id.password);
        mTitleView = (TitleView)findViewById(R.id.titleview);
        mTitleView.setTitle(R.string.app_name);
    }
    
    @Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
    	if(view==mLoginButton){ 
    		int ret = checkLoginInfo();
    		if(ret==INVALID_NULL){
    			Toast.makeText(getApplicationContext(), "username and password can't be empty", Toast.LENGTH_LONG).show();
    	    }else if(ret==RET_OK){
    	    	Intent intent = new Intent();
                intent.setClassName("com.car.carparking","com.car.carparking.view.CarListView");
                startActivity(intent); 
                Login.this.finish();
    		}else if(ret==INVALID_PWD){
    			Toast.makeText(getApplicationContext(), "Invalid password", Toast.LENGTH_LONG).show();
    		}else{
    			Toast.makeText(getApplicationContext(), "Invalid username", Toast.LENGTH_LONG).show();
    		}
    	}
	}
    private int checkLoginInfo() {
        String user = mUserText.getText().toString();
        String pwd = mPwdText.getText().toString();
        if(user==null || pwd==null || user.equals("")|| pwd.equals("")){
            return INVALID_NULL;
        }
        int ret = RET_OK;
        if (user.equals("car") && pwd.equals("1111")) {
            return RET_OK;
        }else if (!user.equals("car")) {
            return INVALID_USER;
        }else if(!pwd.equals("1111")) {
            return INVALID_PWD;
        }
    	return INVALID_NULL;
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
}
