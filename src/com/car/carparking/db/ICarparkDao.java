package com.car.carparking.db;

import com.car.carparking.module.Account;
import com.car.carparking.module.Car;
import com.car.carparking.module.Location;

import java.util.List;

/**
 * Created by dong_bin on 15-2-2.
 */
public interface ICarparkDao {

    //Account related
    public boolean addAccount(Account account);

    public boolean updateAccount(Account account);

    public boolean isAccountExist(String name);

    public Account getAccountById(int id);

    public Account getAccountByName(String name);

    public List<Account> getAllAcount();

    //Location related
    public boolean addLocation(Location location);

    public boolean updateLocation(Location location);

    public Location getLocationByAccountId(int accountid);

    public List<Location> getAllLocation();

    //car related
    public boolean addCar(Car car);

    public boolean deleteCar(Car car);

    public List<Car> getAllCars(String location);
}
