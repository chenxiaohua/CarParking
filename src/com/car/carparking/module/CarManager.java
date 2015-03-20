package com.car.carparking.module;

import android.content.Context;
import com.car.carparking.db.CarparkDaoFactory;
import com.car.carparking.db.ICarparkDao;

import java.util.List;

/**
 * Created by dong_bin on 15-2-2.
 */
public class CarManager {

    private static CarManager sCm = null;
    private ICarparkDao mCarparkDao;

    private CarManager(Context context)
    {
        mCarparkDao = CarparkDaoFactory.getICarparkDaoInstance(context);
    }

    public static CarManager getInstance(Context context)
    {
        if (sCm == null) {
            sCm = new CarManager(context);
        }
        return sCm;
    }
    public boolean addCar(Car car){
        return mCarparkDao.addCar(car);
    }

    public boolean delCar(Car car){
        return mCarparkDao.deleteCar(car);
    }

    public List<Car> getAllCars(String location){
        return mCarparkDao.getAllCars(location);
    }
}
