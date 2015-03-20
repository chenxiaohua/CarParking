package com.car.carparking.module;

import android.content.Context;
import com.car.carparking.db.CarparkDaoFactory;
import com.car.carparking.db.ICarparkDao;

import java.util.List;

/**
 * Created by dong_bin on 15-2-2.
 */
public class LocationManager {

    private static final String TAG="LocationManager";
    private static LocationManager sLm = null;
    private ICarparkDao mCarparkDao;

    private LocationManager(Context context)
    {
        mCarparkDao = CarparkDaoFactory.getICarparkDaoInstance(context);
    }

    public static LocationManager getInstance(Context context)
    {
        if (sLm == null) {
            sLm = new LocationManager(context);
        }
        return sLm;
    }
    public boolean addLocation(Location loc){
        return mCarparkDao.addLocation(loc);
    }

    public boolean updateLocation(Location loc){
        return mCarparkDao.updateLocation(loc);
    }

    public List<Location> getAllLocation(){
        return mCarparkDao.getAllLocation();
    }
}
