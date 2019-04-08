package com.supcon.mes.module_olxj.model.network;

import com.app.annotation.apt.ApiFactory;
import com.supcon.mes.middleware.model.bean.BapResultEntity;
import com.supcon.mes.middleware.model.bean.CommonBAPListEntity;
import com.supcon.mes.middleware.model.bean.CommonEntity;
import com.supcon.mes.middleware.model.bean.FastQueryCondEntity;
import com.supcon.mes.middleware.model.bean.ResultEntity;
import com.supcon.mes.module_olxj.model.bean.OLXJAreaEntity;
import com.supcon.mes.module_olxj.model.bean.OLXJGroupEntity;
import com.supcon.mes.module_olxj.model.bean.OLXJTaskEntity;
import com.supcon.mes.module_olxj.model.bean.OLXJWorkItemEntity;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by wangshizhan on 2019/3/29
 * Email:wangshizhan@supcom.com
 */
@ApiFactory(name = "OLXJClient")
public interface OLXJNetworkAPI {

    /**
     * 获取路线列表
     * @return
     */
    @GET("/mobileEAM/workGroup/workGroup/workGroupList-query.action?page.pageSize=500&page.maxPageSize=500&page.pageNo=1")
    Flowable<CommonBAPListEntity<OLXJGroupEntity>> queryWorkGroupList();



    /**
     * 获取已下发任务列表
     * @param queryParam  页数相关key
     * @param fastQueryCondEntity 已下发快速查询条件
     * @return
     */
    @GET("/mobileEAM/potrolTaskNew/potrolTaskWF/potrolTaskList-query.action?1=1&permissionCode=mobileEAM_1.0.0_potrolTaskNew_potrolTaskList&page.pageSize=500&page.maxPageSize=500&page.pageNo=1")
    Flowable<CommonBAPListEntity<OLXJTaskEntity>> queryPotrolTaskList(@QueryMap Map<String, Object> queryParam, @Query("fastQueryCond") FastQueryCondEntity fastQueryCondEntity);

    /**
     * 获取已下发临时任务列表
     * @param queryParam  页数相关key
     * @param fastQueryCondEntity
     * @return
     */
    @GET("/mobileEAM/potrolTaskNew/potrolTaskWF/tempList-query.action?1=1&permissionCode=mobileEAM_1.0.0_potrolTaskNew_tempList&page.pageSize=500&page.maxPageSize=500&page.pageNo=1")
    Flowable<CommonBAPListEntity<OLXJTaskEntity>> queryPotrolTempTaskList(@QueryMap Map<String, Object> queryParam, @Query("fastQueryCond") FastQueryCondEntity fastQueryCondEntity);



    /**
     * 获取任务包含的作业项
     * @param queryParam  页数相关key
     * @return
     */
    @GET("/mobileEAM/potrolTaskNew/potrolTaskWF/data-dg1489026162123.action?datagridCode=mobileEAM_1.0.0_potrolTaskNew_tempViewdg1489026162123&rt=json")
    Flowable<CommonBAPListEntity<OLXJWorkItemEntity>> queryWorkList(@QueryMap Map<String, Object> queryParam);


    /**
     * 1、添加临时巡检任务作业项参照列表
     * 2、路线查询区域和作业项
     */

    @GET("/mobileEAM/work/workItem/addworkItem-query.action?&permissionCode=mobileEAM_1.0.0_work_addworkItem&crossCompanyFlag=")
    Flowable<CommonBAPListEntity<OLXJWorkItemEntity>> queryWorkListRef(@QueryMap Map<String, Object> queryParam);

    /**
     * 创建临时任务
     * @return
     */
    @Multipart
    @POST("/mobileEAM/potrolTaskNew/potrolTaskWF/tempEdit/submit.action?__pc__=c3RhcnQzMTF0ZW1wV0Z8dGVtcFdG&_bapFieldPermissonModelCode_=mobileEAM_1.0.0_potrolTaskNew_PotrolTaskWF&_bapFieldPermissonModelName_=PotrolTaskWF&superEdit=false")
    Flowable<BapResultEntity> createTempTask(@PartMap Map<String, RequestBody> requestBodyMap);

    /**
     * 创建临时任务
     * @return
     */
    @GET("/mobileEAM/MobileInterfaceForAndroidAction/createTempPotrolTask.action")
    Flowable<CommonEntity<String>> createTempTaskNew(@QueryMap Map<String, Object> map);

    /**
     * 更新巡检任务状态，下载解析完成时调用
     *
     * @param staffId   用户id
     * @param taskIdStr 下载完成的巡检任务id
     * @return
     */
    @GET("/public/potrolTaskNew/potrolTaskWF/updateStatus.action")
    Flowable<ResultEntity> updateStatus(@Query("staffId") long staffId, @Query("taskIdStr") String taskIdStr);

    /**
     * 批量取消任务
     * @param taskIDs
     * @param changeState 1、未下发 LinkState/01 2、已下发 LinkState/02 3、已完成 LinkState/03 4、已取消 LinkState/04
     * @return
     */
    @GET("/mobileEAM/potrolTaskNew/potrolTaskWF/cancelTask.action")
    Flowable<ResultEntity>  cancelTask(@Query("taskIDs") String taskIDs, @Query("changeState") String changeState);

    /**
     * 结束/终止任务
     * @param taskIDs
     * @param endReason
     * @param isFinish
     * @return
     */
    @GET("/mobileEAM/MobileInterfaceForAndroidAction/endTask.action")
    Flowable<ResultEntity>  endTask(@Query("taskIDs") String taskIDs, @Query("endReason") String endReason, @Query("type") boolean isFinish);

    /**
     * 获取区域列表
     */
    @GET("/mobileEAM/work/work/workPart-query.action?valueType=mobileEAM001/02&&permissionCode=mobileEAM_1.0.0_work_workLayout&page.pageSize=20&page.maxPageSize=500")
    Flowable<CommonBAPListEntity<OLXJAreaEntity>> queryOLXJArea(@Query("workGroupID") long workGroupID, @Query("page.pageNo") int pageNo);


    /**
     * 区域上传数据
     * @param file    压缩包body
     * @param zipFile 压缩包名字
     */
    @Multipart
    @POST("/mobileEAM/MobileInterfaceForAndroidAction/submitPotrolTaskByWork.action")
    Flowable<ResultEntity> submitPotrolTaskByWork(@Part List<MultipartBody.Part> file, @Query("zipFile") String zipFile);

}
