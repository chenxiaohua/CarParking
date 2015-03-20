package com.car.carparking.module;


import java.sql.Timestamp;

/**
 * Created by dong_bin on 15-2-2.
 */
public class Car {
    /**
     * Car id
     */
    private int mID;

    /**
     * Car plate
     */
    private String mPlate;

    /**
     * Car park time
     */
    private Timestamp mParktime;

    /**
     * Car location
     */
    private String mLocation;

    /**
     * Constructor to create an account
     *
     * @param id    The id of car
     * @param plate    The plate of car
     * @param parktime    The timestamp when car is parked
     * @param location  The location where car is parked
     */
    public Car(int id, String plate, Timestamp parktime, String location) {
        mID = id;
        mPlate = plate;
        mParktime = parktime;
        mLocation = location;
    }

    /**
     * get car id
     *
     * @return id
     */
    public int getId() {
        return mID;
    }

    /**
     * get car plate
     *
     * @return plate
     */
    public String getPlate() {
        return mPlate;
    }

    /**
     * get Car park time
     *
     * @return parktime
     */
    public Timestamp getParktime() {
        return mParktime;
    }

    /**
     * get account location
     *
     * @return order list
     */
    public String getLocation() {
        return mLocation;
    }
}
