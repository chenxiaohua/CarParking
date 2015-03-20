package com.car.carparking.module;

import java.sql.Timestamp;

/**
 * Created by dong_bin on 15-2-2.
 */
public class Location {
    /**
     * Location id
     */
    private int mID;

    /**
     * Location name
     */
    private String mName;

    /**
     * Associate account
     */
    private int mAccount;

    /**
     * Constructor to create an account
     *
     * @param id    The id of location
     * @param name    The name of location
     * @param account  The account id associate with this location
     */
    public Location(int id, String name, int account) {
        mID = id;
        mName = name;
        mAccount = account;
    }

    /**
     * get location id
     *
     * @return id
     */
    public int getId() {
        return mID;
    }

    /**
     * get location name
     *
     * @return name
     */
    public String getName() {
        return mName;
    }

    /**
     * get location associate account
     *
     * @return pass
     */
    public int getAccount() {
        return mAccount;
    }

    public void setAccount(int accountid){
        mAccount = accountid;
    }
}
