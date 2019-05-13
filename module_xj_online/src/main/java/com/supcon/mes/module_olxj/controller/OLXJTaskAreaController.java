package com.supcon.mes.module_olxj.controller;

import android.annotation.SuppressLint;
import android.content.Context;

import com.app.annotation.Presenter;
import com.supcon.common.view.base.controller.BaseDataController;
import com.supcon.common.view.util.LogUtil;
import com.supcon.mes.middleware.constant.Constant;
import com.supcon.mes.middleware.model.bean.CommonBAPListEntity;
import com.supcon.mes.middleware.model.listener.OnSuccessListener;
import com.supcon.mes.middleware.util.ErrorMsgHelper;
import com.supcon.mes.module_olxj.model.api.OLXJAreaAPI;
import com.supcon.mes.module_olxj.model.api.OLXJWorkListAPI;
import com.supcon.mes.module_olxj.model.bean.OLXJAreaEntity;
import com.supcon.mes.module_olxj.model.bean.OLXJTaskEntity;
import com.supcon.mes.module_olxj.model.bean.OLXJWorkItemEntity;
import com.supcon.mes.module_olxj.model.bean.OLXJWorkListEntity;
import com.supcon.mes.module_olxj.model.contract.OLXJAreaContract;
import com.supcon.mes.module_olxj.model.contract.OLXJWorkListContract;
import com.supcon.mes.module_olxj.presenter.OLXJAreaListPresenter;
import com.supcon.mes.module_olxj.presenter.OLXJWorkItemPresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wangshizhan on 2019/4/1
 * Email:wangshizhan@supcom.com
 */
@Presenter(value = {OLXJWorkItemPresenter.class, OLXJAreaListPresenter.class})
public class OLXJTaskAreaController extends BaseDataController implements OLXJWorkListContract.View, OLXJAreaContract.View{

    private OLXJTaskEntity mTaskEntity;
    private int currentPage = 1, cuttentAreaPage = 1;
    private List<OLXJWorkItemEntity> mOLXJWorkItemEntities = new ArrayList<>();
    private List<OLXJAreaEntity> mAreaEntities = new ArrayList<>();
    private Map<Long, OLXJAreaEntity> mAreaEntityMap = new LinkedHashMap<>();
    private OnSuccessListener<Boolean> mOnSuccessListener;

    public OLXJTaskAreaController(Context context) {
        super(context);
    }

    @Override
    public void onInit() {
        super.onInit();
        mTaskEntity = (OLXJTaskEntity) getIntent().getSerializableExtra(Constant.IntentKey.XJ_TASK_ENTITY);
        if(mTaskEntity == null ){
            throw new IllegalArgumentException("no mTaskEntity, please check it!");
        }
    }

    @Override
    public void initData() {
        super.initData();

        getWorkData();
        getAreaData();
    }

    public void getData(OLXJTaskEntity taskEntity, OnSuccessListener<Boolean> listener){
        mTaskEntity = taskEntity;
        if(mTaskEntity == null ){
            throw new IllegalArgumentException("no mTaskEntity, please check it!");
        }
        mOnSuccessListener = listener;
        mOLXJWorkItemEntities.clear();
        mAreaEntities.clear();
        mAreaEntityMap.clear();
        currentPage = 1;
        cuttentAreaPage = 1;
        getAreaData();
    }

    private void getWorkData(){

        presenterRouter.create(OLXJWorkListAPI.class).getWorkItemList(mTaskEntity.id, currentPage);
    }

    private void getAreaData(){
        presenterRouter.create(OLXJAreaAPI.class).getOJXJAreaList(mTaskEntity.workGroupID.id, cuttentAreaPage);
    }

    @Override
    public void getXJWorkItemListSuccess(OLXJWorkListEntity entity) {

    }

    @Override
    public void getXJWorkItemListFailed(String errorMsg) {

    }

    @Override
    public void getWorkItemListSuccess(CommonBAPListEntity entity) {

        if(entity.result!=null){
            mOLXJWorkItemEntities.addAll(entity.result);
        }

        if(entity.hasNext){
            currentPage ++;
            getWorkData();
        }
        else{
            updateArea();
        }
    }

    @SuppressLint("CheckResult")
    private void updateArea() {
        Flowable.fromIterable(mOLXJWorkItemEntities)
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<OLXJWorkItemEntity>() {
                    @Override
                    public void accept(OLXJWorkItemEntity olxjWorkItemEntity) throws Exception {
                        OLXJAreaEntity areaEntity = mAreaEntityMap.get(olxjWorkItemEntity.workID.id);
                        if (areaEntity != null) {
                            areaEntity.workItemEntities.add(olxjWorkItemEntity);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {

                        for(int i = mAreaEntities.size()-1; i>= 0 ; i--){
                            if(mAreaEntities.get(i).workItemEntities.size() == 0){
                                mAreaEntities.remove(i);
                            }
                            else {
//                                Collections.sort(mAreaEntities.get(i).workItemEntities);
                            }
                        }

                        if(mOnSuccessListener!=null){
                            mOnSuccessListener.onSuccess(true);
                        }
                    }
                });
    }

    @SuppressLint("CheckResult")
    private void initArea(List<OLXJAreaEntity> areaEntities) {

        Flowable.fromIterable(areaEntities)
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<OLXJAreaEntity>() {
                    @Override
                    public void accept(OLXJAreaEntity areaEntity) throws Exception {
                        mAreaEntityMap.put(areaEntity.id, areaEntity);
                    }
                });


    }

    @Override
    public void getWorkItemListFailed(String errorMsg) {
        LogUtil.e(ErrorMsgHelper.msgParse(errorMsg));
    }

    @Override
    public void getWorkItemListRefSuccess(CommonBAPListEntity entity) {

    }

    @Override
    public void getWorkItemListRefFailed(String errorMsg) {

    }

    @Override
    public void getOJXJAreaListSuccess(CommonBAPListEntity entity) {

        if(entity.result!=null){
            mAreaEntities.addAll(entity.result);
        }

        if(entity.hasNext){
            cuttentAreaPage ++;
            getAreaData();
        }
        else{
            initArea(mAreaEntities);
            getWorkData();
        }
    }

    @Override
    public void getOJXJAreaListFailed(String errorMsg) {
        LogUtil.e(ErrorMsgHelper.msgParse(errorMsg));
    }

    public List<OLXJAreaEntity> getAreaEntities() {
        return mAreaEntities;
    }

    public List<OLXJWorkItemEntity> getOLXJWorkItemEntities() {
        return mOLXJWorkItemEntities;
    }
}
