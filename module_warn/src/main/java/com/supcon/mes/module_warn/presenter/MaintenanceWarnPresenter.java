package com.supcon.mes.module_warn.presenter;

import android.text.TextUtils;

import com.supcon.mes.middleware.model.bean.FastQueryCondEntity;
import com.supcon.mes.middleware.model.bean.JoinSubcondEntity;
import com.supcon.mes.middleware.util.BAPQueryParamsHelper;
import com.supcon.mes.module_warn.model.bean.MaintenanceWarnListEntity;
import com.supcon.mes.module_warn.model.contract.MaintenanceWarnContract;
import com.supcon.mes.module_warn.model.network.EarlyWarnHttpClient;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yangfei.cao
 * @ClassName hongShiCementEam
 * @date 2019/4/23
 * ------------- Description -------------
 *  维保
 */
public class MaintenanceWarnPresenter extends MaintenanceWarnContract.Presenter {

    @Override
    public void getMaintenance(Map<String, Object> params,int page) {

        FastQueryCondEntity fastQuery = BAPQueryParamsHelper.createSingleFastQueryCond(new HashMap<>());
        JoinSubcondEntity joinSubcondEntity = BAPQueryParamsHelper.crateJoinSubcondEntity(params, "EAM_BaseInfo,EAM_ID,BEAM_JWXITEMS,EAMID");
        fastQuery.subconds.add(joinSubcondEntity);
        fastQuery.modelAlias = "jWXItem";

        Map<String, Object> pageQueryParams = new HashMap<>();
        pageQueryParams.put("page.pageNo", page);
        pageQueryParams.put("page.maxPageSize", 500);
        mCompositeSubscription.add(EarlyWarnHttpClient.getMaintenance(fastQuery,pageQueryParams)
                .onErrorReturn(throwable -> {
                    MaintenanceWarnListEntity maintenanceWarnListEntity = new MaintenanceWarnListEntity();
                    maintenanceWarnListEntity.errMsg = throwable.toString();
                    return maintenanceWarnListEntity;
                }).subscribe(maintenanceWarnListEntity -> {
                    if (TextUtils.isEmpty(maintenanceWarnListEntity.errMsg)) {
                        getView().getMaintenanceSuccess(maintenanceWarnListEntity);
                    } else {
                        getView().getMaintenanceFailed(maintenanceWarnListEntity.errMsg);
                    }
                }));
    }
}
