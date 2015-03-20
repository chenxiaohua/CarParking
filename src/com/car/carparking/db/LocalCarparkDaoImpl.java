package com.car.carparking.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.car.carparking.module.Account;
import com.car.carparking.module.Car;
import com.car.carparking.module.Location;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Home on 2015/2/3.
 */
public class LocalCarparkDaoImpl implements ICarparkDao {
    private DBSQLiteHelper mDBHelper;

    public LocalCarparkDaoImpl(Context context) {
        mDBHelper = new DBSQLiteHelper(context);
    }

    @Override
    public boolean addAccount(Account account) {
        if (account == null) {
            return false;
        }
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        if (db == null) {
            return false;
        }
        boolean ret = false;
        ContentValues values = new ContentValues();
        values.put(Configuration.ACCOUNT_NAME, account.getName());
        values.put(Configuration.ACCOUNT_PASS, account.getPass());
        values.put(Configuration.ACCOUNT_LOCATION, account.getLocation());
        values.put(Configuration.ACCOUNT_EXPIRATION, account.getExpiration().getTime());
        //insert to db
        db.insert(Configuration.TABLE_ACCOUNT_NAME,null,values);
        //check if inserted
        String sort = Configuration.ACCOUNT_ID + " desc";
        Cursor cursor = db.query(Configuration.TABLE_ACCOUNT_NAME, null, null, null, null,
                null, sort);
        if (cursor != null) {
            if(cursor.moveToFirst()){
                ret=true;
            }
            cursor.close();
        }
        db.close();
        return ret;
    }

    @Override
    public boolean updateAccount(Account account) {
        if (account == null) {
            return false;
        }
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        if (db == null) {
            return false;
        }
        ContentValues values = new ContentValues();
        values.put(Configuration.ACCOUNT_NAME, account.getName());
        values.put(Configuration.ACCOUNT_PASS, account.getPass());
        values.put(Configuration.ACCOUNT_LOCATION, account.getLocation());
        values.put(Configuration.ACCOUNT_EXPIRATION, account.getExpiration().getTime());
        String select = Configuration.ACCOUNT_ID + "=?";
        String[] whereArgs = { String.valueOf(account.getId()) };
        int count = db.update(Configuration.TABLE_ACCOUNT_NAME,values,select,whereArgs);
        db.close();
        if (count != 1) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isAccountExist(String name) {
        if (name == null || name.equals(""))
            return false;

        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        if (db == null) {
            return false;
        }
        boolean ret = false;
        String[] columns={Configuration.ACCOUNT_NAME};   //only return name column
        String selection = Configuration.ACCOUNT_NAME+"='"+name+"'";
        Cursor cursor = db.query(Configuration.TABLE_ACCOUNT_NAME, columns, selection, null, null, null, null);
        if (cursor != null) {
            if(cursor.moveToFirst()){
                ret = true;
            }
            cursor.close();
        }
        db.close();
        return ret;
    }

    @Override
    public Account getAccountById(int id) {
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        if (db == null) {
            return null;
        }

        String selection = Configuration.ACCOUNT_ID + "='"+ id + "'";
        Cursor cursor = db.query(Configuration.TABLE_ACCOUNT_NAME, null, selection, null, null, null, null);
        Account ret = null;
        if (cursor != null) {
            if(cursor.moveToFirst()){
                int uid = cursor.getInt(cursor.getColumnIndexOrThrow(Configuration.ACCOUNT_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(Configuration.ACCOUNT_NAME));
                String password = cursor.getString(cursor.getColumnIndexOrThrow(Configuration.ACCOUNT_PASS));
                String location = cursor.getString(cursor.getColumnIndexOrThrow(Configuration.ACCOUNT_LOCATION));
                Timestamp expiration = new Timestamp(cursor.getLong(cursor.getColumnIndexOrThrow(Configuration.ACCOUNT_EXPIRATION)));
                ret = new Account(uid,name,password,location,expiration);
            }
            cursor.close();
        }
        db.close();
        return ret;
    }

    @Override
    public Account getAccountByName(String name) {
        if (name == null || name.equals(""))
            return null;

        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        if (db == null) {
            return null;
        }

        String selection = Configuration.ACCOUNT_NAME + "='"+ name + "'";
        Cursor cursor = db.query(Configuration.TABLE_ACCOUNT_NAME, null, selection, null, null, null, null);
        Account ret = null;
        if (cursor != null) {
            if(cursor.moveToFirst()){
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(Configuration.ACCOUNT_ID));
                String uname = cursor.getString(cursor.getColumnIndexOrThrow(Configuration.ACCOUNT_NAME));
                String password = cursor.getString(cursor.getColumnIndexOrThrow(Configuration.ACCOUNT_PASS));
                String location = cursor.getString(cursor.getColumnIndexOrThrow(Configuration.ACCOUNT_LOCATION));
                Timestamp expiration = new Timestamp(cursor.getLong(cursor.getColumnIndexOrThrow(Configuration.ACCOUNT_EXPIRATION)));
                ret = new Account(id,uname,password,location,expiration);
            }
            cursor.close();
        }
        db.close();
        return ret;
    }

    @Override
    public List<Account> getAllAcount() {
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        if (db == null) {
            return null;
        }
        List<Account> ret = new ArrayList<Account>();
        Cursor cursor = db.query(Configuration.TABLE_ACCOUNT_NAME, null, null, null, null, null, null);
        if (cursor != null) {
            for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
            {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(Configuration.ACCOUNT_ID));
                String uname = cursor.getString(cursor.getColumnIndexOrThrow(Configuration.ACCOUNT_NAME));
                String password = cursor.getString(cursor.getColumnIndexOrThrow(Configuration.ACCOUNT_PASS));
                String location = cursor.getString(cursor.getColumnIndexOrThrow(Configuration.ACCOUNT_LOCATION));
                Timestamp expiration = new Timestamp(cursor.getLong(cursor.getColumnIndexOrThrow(Configuration.ACCOUNT_EXPIRATION)));
                ret.add(new Account(id,uname,password,location,expiration));
            }
            cursor.close();
        }
        db.close();

        return ret;
    }

    @Override
    public boolean addLocation(Location location) {
        if (location == null) {
            return false;
        }
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        if (db == null) {
            return false;
        }
        boolean ret = false;
        ContentValues values = new ContentValues();
        values.put(Configuration.LOCATION_NAME, location.getName());
        values.put(Configuration.LOCATION_ID, location.getAccount());
        //insert to db
        db.insert(Configuration.TABLE_LOCATION_NAME,null,values);
        //check if inserted
        String sort = Configuration.LOCATION_ID + " desc";
        Cursor cursor = db.query(Configuration.TABLE_LOCATION_NAME, null, null, null, null,
                null, sort);
        if (cursor != null) {
            if(cursor.moveToFirst()){
                ret=true;
            }
            cursor.close();
        }
        db.close();
        return ret;
    }

    @Override
    public boolean updateLocation(Location location) {
        if (location == null) {
            return false;
        }
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        if (db == null) {
            return false;
        }
        ContentValues values = new ContentValues();
        values.put(Configuration.LOCATION_NAME, location.getName());
        values.put(Configuration.LOCATION_ACCOUNT, location.getAccount());
        String select = Configuration.LOCATION_ID + "=?";
        String[] whereArgs = { String.valueOf(location.getId()) };
        int count = db.update(Configuration.TABLE_LOCATION_NAME,values,select,whereArgs);
        db.close();
        if (count != 1) {
            return false;
        }
        return true;
    }

    @Override
    public Location getLocationByAccountId(int accountid) {
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        if (db == null) {
            return null;
        }

        String selection = Configuration.LOCATION_ID + "='"+ accountid + "'";
        Cursor cursor = db.query(Configuration.TABLE_LOCATION_NAME, null, selection, null, null, null, null);
        Location ret = null;
        if (cursor != null) {
            if(cursor.moveToFirst()){
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(Configuration.LOCATION_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(Configuration.LOCATION_NAME));
                int account = cursor.getInt(cursor.getColumnIndexOrThrow(Configuration.LOCATION_ACCOUNT));
                ret = new Location(id,name,account);
            }
            cursor.close();
        }
        db.close();
        return ret;
    }

    @Override
    public List<Location> getAllLocation() {
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        if (db == null) {
            return null;
        }
        List<Location> ret = new ArrayList<Location>();
        Cursor cursor = db.query(Configuration.TABLE_LOCATION_NAME, null, null, null, null, null, null);
        if (cursor != null) {
            for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
            {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(Configuration.LOCATION_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(Configuration.LOCATION_NAME));
                int accountid = cursor.getInt(cursor.getColumnIndexOrThrow(Configuration.LOCATION_ACCOUNT));
                ret.add(new Location(id,name,accountid));
            }
            cursor.close();
        }
        db.close();

        return ret;
    }

    @Override
    public boolean addCar(Car car) {
        if (car == null) {
            return false;
        }
        Log.d("dongbin", "add car: " + car.getPlate());
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        if (db == null) {
            return false;
        }
        boolean ret = false;
        ContentValues values = new ContentValues();
        values.put(Configuration.CAR_PLATE, car.getPlate());
        values.put(Configuration.CAR_LOCATION, car.getLocation());
        values.put(Configuration.CAR_PARKTIME, car.getParktime().getTime());
        //insert to db
        long id = db.insert(Configuration.TABLE_CAR_NAME,null,values);
        if (id != -1) {
            Log.d("dongbin", "car add success: " + id);
            ret = true;
        }
        db.close();
        return ret;
    }

    @Override
    public boolean deleteCar(Car car) {
        Log.d("dongbin", "del car");
        if (car == null) {
            return false;
        }
        Log.d("dongbin", "del car: " + car.getPlate());
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        if (db == null) {
            return false;
        }
        boolean ret = false;
        String select = Configuration.CAR_PLATE + "=?";
        String[] whereArgs = { car.getPlate() };
        int count = db.delete(Configuration.TABLE_CAR_NAME,select,whereArgs);
        if (count > 0) {
            Log.d("dongbin", "del car success " + count);
            ret = true;
        }
        db.close();
        return ret;
    }

    @Override
    public List<Car> getAllCars(String location) {
        Log.d("dongbin", "get all car in " + location);
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        if (db == null) {
            return null;
        }
        List<Car> ret = new ArrayList<Car>();
        String selection = Configuration.CAR_LOCATION + "='"+ location + "'";
        Cursor cursor = db.query(Configuration.TABLE_CAR_NAME, null, selection, null, null, null, null);
        if (cursor != null) {
            for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
            {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(Configuration.CAR_ID));
                String plate = cursor.getString(cursor.getColumnIndexOrThrow(Configuration.CAR_PLATE));
                Timestamp parktime = new Timestamp(cursor.getLong(cursor.getColumnIndexOrThrow(Configuration.CAR_PARKTIME)));
                String loc = cursor.getString(cursor.getColumnIndexOrThrow(Configuration.CAR_LOCATION));
                Log.d("dongbin", id + " " + plate);
                ret.add(new Car(id, plate,parktime,loc));
            }
            cursor.close();
        }
        db.close();

        return ret;
    }
}