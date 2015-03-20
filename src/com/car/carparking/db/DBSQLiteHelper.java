package com.car.carparking.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Home on 2015/3/14.
 */
public class DBSQLiteHelper extends SQLiteOpenHelper{
    private Context mContext;

    public DBSQLiteHelper(Context context) {
        super(context, Configuration.DB_CARPARK_NAME, null, Configuration.DB_CARPARK_VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // create account table
        String table_account = "create table " + Configuration.TABLE_ACCOUNT_NAME + " ( "
                + Configuration.ACCOUNT_ID + " integer primary key autoincrement,"
                + Configuration.ACCOUNT_NAME + " text not null,"
                + Configuration.ACCOUNT_PASS + " text ,"
                + Configuration.ACCOUNT_LOCATION  + " text ,"
                + Configuration.ACCOUNT_EXPIRATION + " timestamp"
                + ");";
        sqLiteDatabase.execSQL(table_account);

        // create car table
        String table_car = "create table " + Configuration.TABLE_CAR_NAME + " ( "
                + Configuration.CAR_ID + " integer primary key autoincrement,"
                + Configuration.CAR_PLATE + " text not null,"
                + Configuration.CAR_PARKTIME + " timestamp default current_timestamp ,"
                + Configuration.CAR_LOCATION + " text not null"
                +");";
        sqLiteDatabase.execSQL(table_car);

        // create location table
        String table_location = "create table " + Configuration.TABLE_LOCATION_NAME+ " ( "
                + Configuration.LOCATION_ID + " integer primary key autoincrement,"
                + Configuration.LOCATION_NAME + " text not null,"
                + Configuration.LOCATION_ACCOUNT + " integer"
                +");";
        sqLiteDatabase.execSQL(table_location);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        if (i2 > i) {
            String upgrade_account = "DORP TABLE IF EXIST " + Configuration.TABLE_ACCOUNT_NAME;
            sqLiteDatabase.execSQL(upgrade_account);
            String upgrade_car = "DORP TABLE IF EXIST " + Configuration.TABLE_CAR_NAME;
            sqLiteDatabase.execSQL(upgrade_car);
            String upgrade_location = "DORP TABLE IF EXIST " + Configuration.TABLE_LOCATION_NAME;
            sqLiteDatabase.execSQL(upgrade_location);
            onCreate(sqLiteDatabase);
        }
    }

    public boolean deleteTabelByName(String tablename){
        SQLiteDatabase db;
        db = mContext.openOrCreateDatabase(Configuration.DB_CARPARK_NAME, Context.MODE_PRIVATE, null);
        if (db != null) {
            db.delete(tablename, null, null);
            db.close();
        }
        return true;
    }
    public boolean deleteDB(String dbname) {
        if (dbname == null || dbname.equals(""))
            return false;
        mContext.deleteDatabase(dbname);
        return true;
    }
}
