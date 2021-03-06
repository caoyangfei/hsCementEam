package com.supcon.mes.module_sbda_online.presenter;

import android.text.TextUtils;

import com.supcon.mes.module_sbda_online.model.bean.LubriListEntity;
import com.supcon.mes.module_sbda_online.model.contract.LubriContract;
import com.supcon.mes.module_sbda_online.model.network.SBDAOnlineHttpClient;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yangfei.cao
 * @ClassName hongShiCementEam
 * @date 2019/4/1
 * ------------- Description -------------
 */
public class LubriPresenter extends LubriContract.Presenter {

    @Override
    public void lubriRecord(Long beamID, int page) {
        Map<String, Object> pageQueryParams = new HashMap<>();
        pageQueryParams.put("page.pageNo", page);
        pageQueryParams.put("page.pageSize", 500);
        pageQueryParams.put("page.maxPageSize", 500);
        mCompositeSubscription.add(SBDAOnlineHttpClient.lubriRecord(beamID, pageQueryParams)
                .onErrorReturn(throwable -> {
                    LubriListEntity lubriListEntity = new LubriListEntity();
                    lubriListEntity.errMsg = throwable.toString();
                    return lubriListEntity;
                }).subscribe(lubriListEntity -> {
                    if (TextUtils.isEmpty(lubriListEntity.errMsg)) {
                        getView().lubriRecordSuccess(lubriListEntity);
                    } else {
                        getView().lubriRecordFailed(lubriListEntity.errMsg);
                    }
                }));
    }
}
