package com.car.carparking.module;

import java.sql.Timestamp;

/**
 * Created by dong_bin on 15-2-2.
 */
public class Account {
    /**
     * Account id
     */
    private int mID;

    /**
     * Account name
     */
    private String mName;

    /**
     * Account password
     */
    private String mPassword;

    /**
     * Account position
     */
    private String mLocation;

    /**
     * Account expired time
     */
    private Timestamp mExpiration;

    /**
     * Constructor to create an account
     *
     * @param name    The name of account, normally is username
     * @param password    The password for this account
     * @param location  The location where account is located
     */
    public Account(int id, String name, String password, String location, Timestamp expiration) {
        mID = id;
        mName = name;
        mPassword = password;
        mLocation = location;
        mExpiration = expiration;
    }

    /**
     * get account id
     *
     * @return id
     */
    public int getId() {
        return mID;
    }

    /**
     * get account name
     *
     * @return name
     */
    public String getName() {
        return mName;
    }

    /**
     * get account password
     *
     * @return pass
     */
    public String getPass() {
        return mPassword;
    }

    /**
     * get account location
     *
     * @return order list
     */
    public String getLocation() {
        return mLocation;
    }

    /**
     * get account expire time
     *
     * @return timestamp
     */
    public Timestamp getExpiration() {
        return mExpiration;
    }

    public boolean setLocation(String location) {
        mLocation = location;
        return true;
    }

    public boolean isAdmin() {
        if ( mName != null && mName.equals("admin")) {
            return true;
        }
        return false;
    }
}
