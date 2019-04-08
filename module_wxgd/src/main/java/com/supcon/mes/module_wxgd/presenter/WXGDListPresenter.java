package com.supcon.mes.module_wxgd.presenter;

import com.supcon.mes.middleware.model.bean.FastQueryCondEntity;
import com.supcon.mes.middleware.util.BAPQueryParamsHelper;
import com.supcon.mes.module_wxgd.model.bean.WXGDListEntity;
import com.supcon.mes.module_wxgd.model.contract.WXGDListContract;
import com.supcon.mes.module_wxgd.model.network.HttpClient;
import com.supcon.mes.module_wxgd.util.WXGDFastQueryCondHelper;

import java.util.HashMap;
import java.util.Map;

public class WXGDListPresenter extends WXGDListContract.Presenter {
    @Override
    public void listWxgds(int pageNum, Map<String, Object> queryParam) {

        FastQueryCondEntity fastQueryCondEntity = WXGDFastQueryCondHelper.createFastQueryCond(queryParam);

        fastQueryCondEntity.modelAlias = "workRecord";

        Map<String,Object> pageQueryParam = new HashMap<>();
        pageQueryParam.put("page.pageSize",20);
        pageQueryParam.put("page.maxPageSize",500);
        pageQueryParam.put("page.pageNo",pageNum);

        mCompositeSubscription.add(
                HttpClient.listWxgds(fastQueryCondEntity,pageQueryParam)
                .onErrorReturn(throwable -> {
                    WXGDListEntity wxgdListEntity = new WXGDListEntity();
                    wxgdListEntity.success = false;
                    wxgdListEntity.errMsg = throwable.toString();
                    return wxgdListEntity;
                }).subscribe(wxgdListEntity -> {
                    if (wxgdListEntity.errMsg == null){
                        getView().listWxgdsSuccess(wxgdListEntity);
                    }else {
                        getView().listWxgdsFailed(wxgdListEntity.errMsg);
                    }
                })
        );



    }
}
