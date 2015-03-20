package com.car.carparking.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.*;
import com.car.carparking.R;
import com.car.carparking.module.Account;
import com.car.carparking.module.AccountManager;

import java.sql.Timestamp;

/**
 * Created by Home on 2015/3/15.
 */
public class AccountAddView extends Activity implements View.OnClickListener{
    private static final long YEAR_MILLIS = (long)365*24*60*60*1000;
    private EditText mEtName;
    private EditText mEtPass;
    private EditText mEtTime;
    private Button mBtnConfirm;
    private CheckBox mCheckDisplayPass;
    private Button mBtnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accountadd);
        TitleView tv = (TitleView)findViewById(R.id.titleview);
        tv.setTitle(R.string.add_accont);
        mEtName = (EditText) findViewById(R.id.account_name);
        mEtPass = (EditText) findViewById(R.id.account_pass);
        mEtTime = (EditText) findViewById(R.id.account_time);
        mBtnConfirm = (Button) findViewById(R.id.button_confirm);
        mBtnCancel = (Button) findViewById(R.id.button_cancel);
        mBtnConfirm.setOnClickListener(this);
        mBtnCancel.setOnClickListener(this);
        mCheckDisplayPass = (CheckBox) findViewById(R.id.checkBox_display_pass);
        mCheckDisplayPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mEtPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    mEtPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    private void showDialog(String message, boolean finish) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (finish) {
            builder.setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            AccountAddView.this.finish();
                        }
                    });
        }else {
            builder.setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
        }
        builder.create().show();
    }

    @Override
    public void onClick(View view) {
        if (view == mBtnCancel) {
            AccountAddView.this.finish();
        }else if(view == mBtnConfirm) {
            String name = mEtName.getText().toString();
            if (name == null || name.equals("")) {
                //Toast.makeText(getApplicationContext(), R.string.login_namehit, Toast.LENGTH_LONG).show();
                showDialog(getString(R.string.login_namehit), false);
                return;
            }
            String pass = mEtPass.getText().toString();
            if (pass == null || pass.equals("")) {
                //Toast.makeText(getApplicationContext(), R.string.login_passwdhit, Toast.LENGTH_LONG).show();
                showDialog(getString(R.string.login_passwdhit), false);
                return;
            }
            String time = mEtTime.getText().toString();
            if (time == null || time.equals("")) {
                //Toast.makeText(getApplicationContext(), R.string.login_timehit, Toast.LENGTH_LONG).show();
                showDialog(getString(R.string.login_timehit), false);
                return;
            }
            int year = 0;
            try {
                year = Integer.valueOf(time);
            }catch (Exception e) {
                e.printStackTrace();
            }
            Account account = new Account(0,name,pass,null,new Timestamp(System.currentTimeMillis() + year*YEAR_MILLIS));
            AccountManager am = AccountManager.getInstance(AccountAddView.this);
            int ret = am.addAccount(account);
            if (ret == AccountManager.ACCOUNT_ADD_SUCCESS) {
                showDialog(getString(R.string.account_add_success), true);
            }else if (ret == AccountManager.ACCOUNT_ADD_FAIL_USED){
                showDialog(getString(R.string.account_add_used), false);
            }else if (ret == AccountManager.ACCOUNT_ADD_FAIL_UNKOWN){
                showDialog(getString(R.string.account_add_fail), true);
            }
        }
    }
}
