package com.supcon.mes.module_wxgd.model.network;

import com.app.annotation.apt.ApiFactory;
import com.supcon.mes.middleware.model.bean.BapResultEntity;
import com.supcon.mes.middleware.model.bean.CommonListEntity;
import com.supcon.mes.middleware.model.bean.FastQueryCondEntity;
import com.supcon.mes.middleware.model.bean.ResultEntity;
import com.supcon.mes.module_wxgd.model.bean.AcceptanceCheckListEntity;
import com.supcon.mes.module_wxgd.model.bean.LubricateListEntity;
import com.supcon.mes.module_wxgd.model.bean.LubricateOilsListEntity;
import com.supcon.mes.module_wxgd.model.bean.MaintenanceListEntity;
import com.supcon.mes.module_wxgd.model.bean.RefProductListEntity;
import com.supcon.mes.module_wxgd.model.bean.RepairStaffListEntity;
import com.supcon.mes.module_wxgd.model.bean.SparePartJsonEntity;
import com.supcon.mes.module_wxgd.model.bean.SparePartListEntity;
import com.supcon.mes.module_wxgd.model.bean.SparePartRefListEntity;
import com.supcon.mes.module_wxgd.model.bean.StandingCropResultEntity;
import com.supcon.mes.module_wxgd.model.bean.WXGDListEntity;
import com.supcon.mes.module_wxgd.model.dto.SparePartJsonEntityDto;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.RequestBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * ApiService  网络接口
 * created by zhangwenshuai1 2018/8/8
 */
@ApiFactory(name = "HttpClient")
public interface ApiService {

    /**
     * @param
     * @return
     * @description 获取工单列表
     * @author zhangwenshuai1 2018/8/13
     */
    @GET("/BEAM2/workList/workRecord/workList-pending.action?1=1&permissionCode=BEAM2_1.0.0_workList_workList")
    Flowable<WXGDListEntity> listWxgds(@Query("fastQueryCond") FastQueryCondEntity fastQueryCondEntity, @QueryMap Map<String, Object> pageQueryMap);

    /**
     * 维修工单接单提交
     *
     * @param requestBodyMap 表单
     * @return
     */
    @POST("/BEAM2/workList/workRecord/workReceiptEdit/submit.action?__pc__=dGFzazQ1N3x3b3Jr")
    @Multipart
    Flowable<BapResultEntity> receiveSubmit(@PartMap Map<String, RequestBody> requestBodyMap);

    /**
     * 维修工单执行提交
     *
     * @param map
     * @return
     */
    @POST("/BEAM2/workList/workRecord/workExecuteEdit/submit.action?__pc__=dGFzazE4Mzh8d29yaw__")
    @Multipart
    Flowable<BapResultEntity> executeSubmit(@PartMap Map<String, RequestBody> map);


    /**
     * @param
     * @return
     * @description 获取维修工单维修人员
     * @author wnagshizhan 2018/8/28
     */
    @GET("/BEAM2/workList/workRecord/data-dg1531695961519.action")
    Flowable<RepairStaffListEntity> listRepairStaffs(@Query("workRecord.id") long id);

    /**
     * @param
     * @return
     * @description 获取维修工单备件
     * @author wnagshizhan 2018/8/28
     */
    @GET("/BEAM2/workList/workRecord/data-dg1531695961378.action")
    Flowable<SparePartListEntity> listSpareParts(@Query("workRecord.id") long id);


    /**
     * @param
     * @return
     * @description 获取备件物品列表
     * @author zhangwenshuai1 2018/8/13
     */
    @GET("/MESBasic/product/product/refProduct_kc-query.action?&permissionCode=MESBasic_1_product_refProductLayout_kc&crossCompanyFlag=false")
    Flowable<RefProductListEntity> listRefProduct(@Query("fastQueryCond") FastQueryCondEntity fastQueryCondEntity, @QueryMap Map<String, Object> pageQueryMap);

    /**
     * @param
     * @return
     * @description 获取润滑列表
     * @author zhangwenshuai1 2018/8/13
     */
    @GET("/BEAM/lubricateOil/lubricateOil/oilRef-query.action?&permissionCode=BEAM_1.0.0_lubricateOil_oilRef&crossCompanyFlag=false")
    Flowable<LubricateListEntity> listLubricate(@Query("fastQueryCond") FastQueryCondEntity fastQueryCondEntity, @QueryMap Map<String, Object> pageQueryMap);

    /**
     * @param
     * @return
     * @description 获取维修工单润滑油
     * @author wnagshizhan 2018/8/28
     */
    @GET("/BEAM2/workList/workRecord/data-dg1531695961550.action")
    Flowable<LubricateOilsListEntity> listLubricateOils(@Query("workRecord.id") long id);


    /**
     * @param
     * @return
     * @description 获取维修工单验收
     * @author wnagshizhan 2018/8/28
     */
    @GET("/BEAM2/workList/workRecord/data-dg1531695961597.action")
    Flowable<AcceptanceCheckListEntity> listAcceptances(@Query("workRecord.id") long id);

    /**
     * @param faultInfoId 隐患单ID  repairType 维修类型
     * @return
     * @description 转化为大修或检修
     * @author zhangwenshuai1 2018/9/3
     */
    @POST("/BEAM2/workList/workRecord/overhaul.action")
    Flowable<ResultEntity> translateRepair(@Query("faultInfoId") long faultInfoId, @Query("repairType") String repairType);

    /**
     * @param
     * @return
     * @description 派工
     * @author zhangwenshuai1 2018/9/11
     */
    @POST("/BEAM2/workList/workRecord/workEdit/submit.action?__pc__=dGFzazMzOHx3b3Jr&_bapFieldPermissonModelCode_=BEAM2_1.0.0_workList_WorkRecord&_bapFieldPermissonModelName_=WorkRecord&superEdit=false")
    @Multipart
    Flowable<BapResultEntity> doSubmitDispatch(@PartMap Map<String, RequestBody> map);


    /**
     * 维修工单暂停和激活
     *
     * @param map
     * @return
     */
    @GET("/BEAM2/workList/workRecord/stopOrActivate.action")
    Flowable<CommonListEntity> stopOrActivate(@QueryMap Map<String, Object> map);

    /**
     * @param
     * @return
     * @description 验收
     * @author zhangwenshuai1 2018/9/11
     */
    @POST("/BEAM2/workList/workRecord/workCheckEdit/submit.action?__pc__=dGFzazE5ODd8d29yaw__&_bapFieldPermissonModelCode_=BEAM2_1.0.0_workList_WorkRecord&_bapFieldPermissonModelName_=WorkRecord&superEdit=false")
    @Multipart
    Flowable<BapResultEntity> doAcceptChk(@PartMap Map<String, RequestBody> map);

    /**
     * @param productCode 备件编码
     * @return
     * @description 备件更新现存量
     * @author zhangwenshuai1 2018/10/10
     */
    @POST("/BEAM2/workList/workRecord/getNowSum.action")
    Flowable<CommonListEntity<StandingCropResultEntity>> updateStandingCrop(@Query("productCode") String productCode);

    /**
     * @description 备件参照列表查询
     * @param 
     * @return  
     * @author zhangwenshuai1 2018/10/23
     *
     */
    @POST("/BEAM/baseInfo/sparePart/sparePartRef-query.action?&permissio.0.0_baseInfo_sparePartRef&crossCompanyFlag=false")
    Flowable<SparePartRefListEntity> listSparePartsRef(@QueryMap Map<String,Object> queryMap, @Query("fastQueryCond") FastQueryCondEntity fastQueryCondEntity);

    /**
     * @description 备件生成领用出库单
     * @param
     * @return
     * @author zhangwenshuai1 2018/10/23
     *
     */
    @POST("/BEAM2/workList/sparePart/generateSparePartApply.action")
    Flowable<ResultEntity> generateSparePartApply(@Query("sparePartJsons") String listStr);

    /**
     * @param
     * @return
     * @description 隐患管理维保
     * @author wnagshizhan 2018/8/28
     */
    @GET("/BEAM2/workList/workRecord/data-dg1557994493235.action")
    Flowable<MaintenanceListEntity> listMaintenance(@Query("workRecord.id") long id);
}
