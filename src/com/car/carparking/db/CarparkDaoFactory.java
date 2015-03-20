package com.car.carparking.db;

import android.content.Context;

/**
 * Created by Home on 2015/2/3.
 */
public class CarparkDaoFactory {
    public static ICarparkDao getICarparkDaoInstance(Context context){
        return new LocalCarparkDaoImpl(context);
    }
}
