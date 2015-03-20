package com.car.carparking.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.car.carparking.R;
import com.car.carparking.db.Configuration;
import com.car.carparking.db.DBSQLiteHelper;
import com.car.carparking.module.Account;
import com.car.carparking.module.AccountManager;

import java.sql.Timestamp;

/**
 * Created by Home on 2015/3/15.
 */
public class AdminHomeView extends Activity implements View.OnClickListener{
    private Button mBtnAccount;
    private Button mBtnLocation;
    private Button mBtnParkStatus;
    private Button mBtnClearAll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminhome);
        TitleView tv = (TitleView) findViewById(R.id.titleview);
        tv.setTitle(R.string.management);
        mBtnAccount = (Button) findViewById(R.id.btn_manage_account);
        mBtnAccount.setOnClickListener(this);
        mBtnLocation = (Button) findViewById(R.id.btn_manage_location);
        mBtnLocation.setOnClickListener(this);
        mBtnParkStatus = (Button) findViewById(R.id.btn_park_status);
        mBtnParkStatus.setOnClickListener(this);
        mBtnClearAll = (Button) findViewById(R.id.btn_clear);
        mBtnClearAll.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == mBtnAccount) {
            Intent intent = new Intent();
            intent.setClassName("com.car.carparking", "com.car.carparking.view.AccountManagementView");
            startActivity(intent);
        }else if (view == mBtnLocation){
            Intent intent = new Intent();
            intent.setClassName("com.car.carparking", "com.car.carparking.view.LocationManagementView");
            startActivity(intent);
        }else if (view == mBtnParkStatus){
            Intent intent = new Intent();
            intent.setClassName("com.car.carparking", "com.car.carparking.view.CarparkStatusView");
            startActivity(intent);
        }else if (view == mBtnClearAll){
            new AlertDialog.Builder(this).
                    setTitle(getString(R.string.manage_clear))
                    .setNegativeButton(getString(R.string.cancel), null)
                    .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    DBSQLiteHelper dbhelper = new DBSQLiteHelper(AdminHomeView.this);
                                    dbhelper.deleteDB(Configuration.DB_CARPARK_NAME);
                                }
                            }
                    ).show();
        }
    }
}
