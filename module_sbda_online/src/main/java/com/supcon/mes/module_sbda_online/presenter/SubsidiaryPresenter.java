package com.supcon.mes.module_sbda_online.presenter;

import android.text.TextUtils;

import com.supcon.mes.module_sbda_online.model.bean.SubsidiaryListEntity;
import com.supcon.mes.module_sbda_online.model.contract.SubsidiaryContract;
import com.supcon.mes.module_sbda_online.model.network.SBDAOnlineHttpClient;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yangfei.cao
 * @ClassName hongShiCementEam
 * @date 2019/4/1
 * ------------- Description -------------
 */
public class SubsidiaryPresenter extends SubsidiaryContract.Presenter {
    @Override
    public void attachPart(Long beamID, int page) {
        Map<String, Object> pageQueryParams = new HashMap<>();
        pageQueryParams.put("page.pageNo", page);
        pageQueryParams.put("page.pageSize", 20);
        pageQueryParams.put("page.maxPageSize", 500);
        mCompositeSubscription.add(SBDAOnlineHttpClient.attachPart(beamID, pageQueryParams)
                .onErrorReturn(throwable -> {
                    SubsidiaryListEntity subsidiaryListEntity = new SubsidiaryListEntity();
                    subsidiaryListEntity.errMsg = throwable.toString();
                    return subsidiaryListEntity;
                }).subscribe(subsidiaryListEntity -> {
                    if (TextUtils.isEmpty(subsidiaryListEntity.errMsg)) {
                        getView().attachPartSuccess(subsidiaryListEntity);
                    } else {
                        getView().attachPartFailed(subsidiaryListEntity.errMsg);
                    }
                }));
    }
}
