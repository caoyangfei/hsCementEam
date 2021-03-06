package com.supcon.mes.module_login.model.network;

import com.app.annotation.apt.ApiFactory;
import com.supcon.mes.middleware.model.bean.BooleanEntity;
import com.supcon.mes.middleware.model.bean.CommonListEntity;
import com.supcon.mes.middleware.model.bean.ModuleAuthorizationListEntity;
import com.supcon.mes.middleware.model.bean.ResultEntity;
import com.supcon.mes.module_login.model.bean.HeartBeatEntity;
import com.supcon.mes.module_login.model.bean.LicenseEntity;
import com.supcon.mes.module_login.model.bean.LoginEntity;
import com.supcon.mes.module_login.model.bean.PendingNumEntity;
import com.supcon.mes.module_login.model.bean.WorkNumEntity;

import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by Administrator on 2016/3/23.
 */
@ApiFactory(name = "LoginHttpClient")
public interface ApiService {
    /**
     * 登陆
     * @param username 用户名
     * @param password 密码
     * @param map 默认参数
     * @return
     */
    @GET("/cas/mobile/logon")
    Flowable<LoginEntity> login(@Query("username") String username, @Query("password") String password, @QueryMap Map<String, Object> map);

    /**
     * 登出
     * @return
     */
    @GET("/cas/logout")
    Flowable<ResultEntity> logout();

    /**
     * 用户心跳接口
     * @return
     */
    @GET("/foundation/refreshSession.action")
    Flowable<HeartBeatEntity> heartbeat();

   /**
     * 获取授权
     * @return
     */
    @GET("/mobileEAM/MobileInterfaceForAndroidAction/getLicenseInfo.action")
    Flowable<LicenseEntity> getLicenseInfo();

    /**
     * 获取授权
     * @param modulePackageName 模块编码
     * @return
     */
    @GET("/mobile/mobileLicense/foundation/getLicenseInfo.action")
    Flowable<LicenseEntity> getLicenseInfo(@Query("modulePackageName") String modulePackageName);

    /**
     * 获取多模块授权
     * @param moduleCodes 模块编码
     * @return
     */
    @GET("/mobile/mobileLicense/foundation/getLicenseInfos.action")
    Flowable<ModuleAuthorizationListEntity> getLicenseInfos(@Query("moduleCodes") String moduleCodes);

    /**
     * 获取所有待办数量
     * @return
     */
    @GET("/foundation/workbench/pendingdata.action?groupby=process&beginDate=&endDate=")
    Flowable<String> getAllPendings();


    /**
     * 获取所有待办数量
     * @return
     */
    @GET
    Flowable<WorkNumEntity> getPendingsByModule(@Url String url);

    /**
     * 获取所有待办数量
     * @return
     */
    @GET("/services/public/foundation/user/time-data")
    Flowable<PendingNumEntity> getPendingNum(@Query("userId") long userId);
}
