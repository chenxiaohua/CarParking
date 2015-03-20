package com.car.carparking.db;

/**
 * Created by Home on 2015/3/14.
 */
public class Configuration {
    //database spec
    public static final String DB_CARPARK_NAME="carpark.db";
    public static final int DB_CARPARK_VERSION=1;

    //table account
    public static final String TABLE_ACCOUNT_NAME="account";
    public static final String ACCOUNT_ID="id";
    public static final String ACCOUNT_NAME="name";
    public static final String ACCOUNT_PASS="password";
    public static final String ACCOUNT_LOCATION="location";
    public static final String ACCOUNT_EXPIRATION="expiration";

    //table car
    public static final String TABLE_CAR_NAME="car";
    public static final String CAR_ID="id";
    public static final String CAR_PLATE="plate";
    public static final String CAR_PARKTIME="parktime";
    public static final String CAR_LOCATION="location";

    //table location
    public static final String TABLE_LOCATION_NAME="location";
    public static final String LOCATION_ID="id";
    public static final String LOCATION_NAME="name";
    public static final String LOCATION_ACCOUNT="account";
}
