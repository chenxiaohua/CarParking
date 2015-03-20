package com.car.carparking.module;

import android.content.Context;
import android.util.Log;
import com.car.carparking.db.CarparkDaoFactory;
import com.car.carparking.db.ICarparkDao;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by dong_bin on 15-2-2.
 */
public class AccountManager {

    private static final String TAG="AccountManager";
    private static AccountManager sAm = null;
    private ICarparkDao mCarparkDao;
    private Account mCurrentAccount;
    public static int LOGIN_SUCCESS=0;
    public static int ERROR_NO_ACCOUNT = -1;
    public static int ERROR_NAME_PASS_NOTMATCH = -2;
    public static int ACCOUNT_ADD_SUCCESS = 0;
    public static int ACCOUNT_ADD_FAIL_UNKOWN = -1;
    public static int ACCOUNT_ADD_FAIL_USED = -2;
    //public static int ERROR_CONNCTION_TIMEOUT = -3;

    private AccountManager(Context context)
    {
        mCarparkDao = CarparkDaoFactory.getICarparkDaoInstance(context);
    }

    public static AccountManager getInstance(Context context)
    {
        if (sAm == null) {
            sAm = new AccountManager(context);
        }
        return sAm;
    }

    public Account getCurrentAccount()
    {
        return mCurrentAccount;
    }

    public Account getAccountById(int id)
    {
        return mCarparkDao.getAccountById(id);
    }

    public List<Account> getAccountList(){
        return mCarparkDao.getAllAcount();
    }

    /**
     * Login account by username and password
     * @param name      username
     * @param password  password
     * @return 0 for login success, negative number for fail
     */
    public int login(String name, String password)
    {
        Log.d(TAG,"login(" + name + ", " + password + ")");
        //check input first
        if (name == null || name.equals("")){
            return ERROR_NO_ACCOUNT;
        }

        if (name.equals("admin") && password.equals("admin")) {
            mCurrentAccount = new Account(0, "admin", "admin", "", new Timestamp(0));
            return LOGIN_SUCCESS;
        }
        //check if account with name is exist
        if (!mCarparkDao.isAccountExist(name)){
            return ERROR_NO_ACCOUNT;
        }

        //check if name password match
        mCurrentAccount = mCarparkDao.getAccountByName(name);
        if (!mCurrentAccount.getPass().equals(password)) {
            return ERROR_NAME_PASS_NOTMATCH;
        }
        return LOGIN_SUCCESS;
    }

    public int addAccount(Account account)
    {
        if (account != null) {
            //check if account already in database
            if (mCarparkDao.isAccountExist(account.getName())){
                return ACCOUNT_ADD_FAIL_USED;
            }
            //add to database
            if (mCarparkDao.addAccount(account)){
                return ACCOUNT_ADD_SUCCESS;
            }
        }
        return ACCOUNT_ADD_FAIL_UNKOWN;
    }

    public boolean updateAccount(Account account)
    {
        if (account != null) {
            return mCarparkDao.updateAccount(account);
        }
        return false;
    }
}
