package com.supcon.mes.middleware;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.supcon.common.view.util.SharedPreferencesUtils;
import com.supcon.mes.mbap.MBapApp;
import com.supcon.mes.mbap.MBapConstant;
import com.supcon.mes.mbap.utils.GsonUtil;
import com.supcon.mes.mbap.utils.cache.CacheUtil;
import com.supcon.mes.middleware.alone.AloneManager;
import com.supcon.mes.middleware.constant.Constant;
import com.supcon.mes.middleware.model.bean.AccountInfo;
import com.supcon.mes.middleware.model.bean.AccountInfoDao;
import com.supcon.mes.middleware.model.bean.DaoMaster;
import com.supcon.mes.middleware.model.bean.DaoSession;
import com.supcon.mes.middleware.model.bean.Staff;
import com.supcon.mes.middleware.util.CrashHandler;

import java.util.List;


/**
 * Created by wangshizhan on 2017/8/11.
 */

public class EamApplication extends MBapApp {

    private static AloneManager mAloneManager;
    private static DaoSession daoSession;
    private static AccountInfo accountInfo; //用户信息
    private static Staff me;

    public static AloneManager getAloneManager() {
        return mAloneManager;
    }

    public static AccountInfo getAccountInfo() {
        if (accountInfo == null) {
            accountInfo = dao().getAccountInfoDao().queryBuilder()
                    .where(AccountInfoDao.Properties.UserName.eq(TextUtils.isEmpty(getUserName())?"":getUserName().toLowerCase()), AccountInfoDao.Properties.Ip.eq(getIp()))
                    .unique();
        }

        if (accountInfo == null) {
            accountInfo = new AccountInfo();
        }
        return accountInfo;
    }

    public static void setAccountInfo(AccountInfo accountInfo) {
        EamApplication.accountInfo = accountInfo;
    }

    public static Staff me(){

        if(me == null){
            String staffStr = SharedPreferencesUtils.getParam(getAppContext(), Constant.SPKey.STAFF, "");
            me = GsonUtil.gsonToBean(staffStr, Staff.class);
        }

        return me;
    }

   public static void setStaff(Staff staff){
        me = staff;
       SharedPreferencesUtils.setParam(getAppContext(), Constant.SPKey.STAFF, staff.toString());
   }

    @Override
    public void onCreate() {
        super.onCreate();

        if (!BuildConfig.DEBUG) {
            CrashHandler.getInstance().init(this, Constant.CRASH_PATH);
        }
        if (isIsAlone()) {
            mAloneManager = new AloneManager("com.supcon.mes.hongShiCementEam");
            mAloneManager.setup();
            CacheUtil.init(mAloneManager.getHostContext());
        }
        setupDatabase();
        initRouter();
        SharedPreferencesUtils.setParam(getApplicationContext(), MBapConstant.SPKey.IP, "218.75.97.170");
        SharedPreferencesUtils.setParam(getApplicationContext(), MBapConstant.SPKey.PORT, "8181");
    }

    private void initRouter() {

        MainRouter.getInstance().setup();//初始化所有的IntentRouter

    }

    /**
     * 配置数据库
     */
    private void setupDatabase() {
        //创建数据库equipment.db"
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(isIsAlone() ? mAloneManager.getHostContext() : this, "equipment.db", null);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象
        DaoMaster daoMaster = new DaoMaster(db);
        //获取Dao对象管理者
        daoSession = daoMaster.newSession();
    }

    public static DaoSession dao() {

        return daoSession;
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    public static boolean offlineLogin(String userName, String pwd) {
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(pwd)) {
            return false;
        }

        AccountInfoDao accountInfoDao = dao().getAccountInfoDao();
        //先校验是否有同步过用户信息
        List<AccountInfo> userList = accountInfoDao.queryBuilder()
                .where(AccountInfoDao.Properties.UserName.eq(userName))
                .list();
        if (userList.size() == 0) {
//            SnackbarHelper.showError(rootView, "需要在线登录一次");
            return false;
        }

        //如果该用户在线登陆过，校验密码
        List<AccountInfo> list = accountInfoDao.queryBuilder()
                .where(AccountInfoDao.Properties.UserName.eq(userName),
                        AccountInfoDao.Properties.Password.eq(pwd))
                .list();

        if (list.size() == 0) {
//            SnackbarHelper.showError(rootView, "用户名或密码错误");
            return false;
        } else {
            //将用户信息保存到全局变量
            AccountInfo accountInfo = list.get(0);
            setAccountInfo(accountInfo);
            return true;
        }

    }
}
