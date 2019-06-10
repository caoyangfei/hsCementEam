package com.supcon.mes.module_yhgl.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.app.annotation.BindByTag;
import com.app.annotation.Controller;
import com.app.annotation.Presenter;
import com.app.annotation.apt.Router;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.supcon.common.com_http.BaseEntity;
import com.supcon.common.view.base.activity.BaseRefreshActivity;
import com.supcon.common.view.listener.OnChildViewClickListener;
import com.supcon.common.view.util.LogUtil;
import com.supcon.common.view.util.ToastUtils;
import com.supcon.common.view.view.picker.SinglePicker;
import com.supcon.mes.mbap.MBapApp;
import com.supcon.mes.mbap.beans.GalleryBean;
import com.supcon.mes.mbap.beans.WorkFlowEntity;
import com.supcon.mes.mbap.beans.WorkFlowVar;
import com.supcon.mes.mbap.utils.DateUtil;
import com.supcon.mes.mbap.utils.GsonUtil;
import com.supcon.mes.mbap.utils.StatusBarUtils;
import com.supcon.mes.mbap.utils.controllers.DatePickController;
import com.supcon.mes.mbap.utils.controllers.SinglePickController;
import com.supcon.mes.mbap.view.CustomDialog;
import com.supcon.mes.mbap.view.CustomEditText;
import com.supcon.mes.mbap.view.CustomGalleryView;
import com.supcon.mes.mbap.view.CustomSpinner;
import com.supcon.mes.mbap.view.CustomTextView;
import com.supcon.mes.mbap.view.CustomVerticalDateView;
import com.supcon.mes.mbap.view.CustomVerticalEditText;
import com.supcon.mes.mbap.view.CustomVerticalSpinner;
import com.supcon.mes.mbap.view.CustomVerticalTextView;
import com.supcon.mes.mbap.view.CustomWorkFlowView;
import com.supcon.mes.middleware.EamApplication;
import com.supcon.mes.middleware.constant.Constant;
import com.supcon.mes.middleware.controller.LinkController;
import com.supcon.mes.middleware.controller.OnlineCameraController;
import com.supcon.mes.middleware.model.bean.Area;
import com.supcon.mes.middleware.model.bean.AreaDao;
import com.supcon.mes.middleware.model.bean.BapResultEntity;
import com.supcon.mes.middleware.model.bean.CommonDeviceEntity;
import com.supcon.mes.middleware.model.bean.EamType;
import com.supcon.mes.middleware.model.bean.RepairGroupEntity;
import com.supcon.mes.middleware.model.bean.RepairGroupEntityDao;
import com.supcon.mes.middleware.model.bean.Staff;
import com.supcon.mes.middleware.model.bean.SystemCodeEntity;
import com.supcon.mes.middleware.model.bean.UserInfo;
import com.supcon.mes.middleware.model.bean.WXGDEam;
import com.supcon.mes.middleware.model.bean.YHEntity;
import com.supcon.mes.middleware.model.event.CommonSearchEvent;
import com.supcon.mes.middleware.model.event.ImageDeleteEvent;
import com.supcon.mes.middleware.model.event.RefreshEvent;
import com.supcon.mes.middleware.util.ErrorMsgHelper;
import com.supcon.mes.middleware.util.SnackbarHelper;
import com.supcon.mes.middleware.util.SystemCodeManager;
import com.supcon.mes.middleware.util.Util;
import com.supcon.mes.module_yhgl.IntentRouter;
import com.supcon.mes.module_yhgl.R;
import com.supcon.mes.module_yhgl.controller.LubricateOilsController;
import com.supcon.mes.module_yhgl.controller.MaintenanceController;
import com.supcon.mes.module_yhgl.controller.RepairStaffController;
import com.supcon.mes.module_yhgl.controller.SparePartController;
import com.supcon.mes.module_yhgl.model.api.YHSubmitAPI;
import com.supcon.mes.module_yhgl.model.contract.YHSubmitContract;
import com.supcon.mes.module_yhgl.model.dto.LubricateOilsEntityDto;
import com.supcon.mes.module_yhgl.model.dto.MaintainDto;
import com.supcon.mes.module_yhgl.model.dto.RepairStaffDto;
import com.supcon.mes.module_yhgl.model.dto.SparePartEntityDto;
import com.supcon.mes.module_yhgl.model.event.ListEvent;
import com.supcon.mes.module_yhgl.model.event.LubricateOilsEvent;
import com.supcon.mes.module_yhgl.model.event.MaintenanceEvent;
import com.supcon.mes.module_yhgl.model.event.RepairStaffEvent;
import com.supcon.mes.module_yhgl.model.event.SparePartEvent;
import com.supcon.mes.module_yhgl.presenter.YHSubmitPresenter;
import com.supcon.mes.module_yhgl.util.FieldHepler;
import com.supcon.mes.module_yhgl.util.YHGLMapManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.supcon.mes.middleware.constant.Constant.IntentKey.DEPLOYMENT_ID;

/**
 * Created by wangshizhan on 2018/8/21
 * Email:wangshizhan@supcom.com
 */
@Router(Constant.Router.YH_EDIT)
@Presenter(YHSubmitPresenter.class)
@Controller(value = {SparePartController.class, LubricateOilsController.class, RepairStaffController.class, MaintenanceController.class, OnlineCameraController.class})
public class YHEditActivity extends BaseRefreshActivity implements YHSubmitContract.View {

    @BindByTag("leftBtn")
    ImageButton leftBtn;

    @BindByTag("titleText")
    TextView titleText;

    @BindByTag("yhEditFindTime")
    CustomVerticalDateView yhEditFindTime;

    @BindByTag("yhEditFindStaff")
    CustomVerticalTextView yhEditFindStaff;

    @BindByTag("yhEditArea")
    CustomVerticalSpinner yhEditArea;

    @BindByTag("yhEditPriority")
    CustomVerticalSpinner yhEditPriority;

    @BindByTag("yhEditEamCode")
    CustomVerticalTextView yhEditEamCode;

    @BindByTag("yhEditEamName")
    CustomVerticalTextView yhEditEamName;

    @BindByTag("yhEditEamModel")
    CustomTextView yhEditEamModel;

    @BindByTag("yhEditType")
    CustomSpinner yhEditType;

    @BindByTag("yhEditWXType")
    CustomVerticalSpinner yhEditWXType;

    @BindByTag("yhEditWXGroup")
    CustomVerticalSpinner yhEditWXGroup;

    @BindByTag("yhEditDescription")
    CustomVerticalEditText yhEditDescription;

    @BindByTag("yhGalleryView")
    CustomGalleryView yhGalleryView;

    @BindByTag("yhEditMemo")
    CustomVerticalEditText yhEditMemo;

    @BindByTag("yhEditCommentInput")
    CustomEditText yhEditCommentInput;

    @BindByTag("yhEditTransition")
    CustomWorkFlowView yhEditTransition;

    private YHEntity mYHEntity, mOriginalEntity;

    private SinglePickController mSinglePickController;
    private DatePickController mDatePickController;

    private Map<String, SystemCodeEntity> wxTypes;
    private Map<String, SystemCodeEntity> yhTypes;
    private Map<String, SystemCodeEntity> yhPriorities;
    private Map<String, Area> mAreas;
    private Map<String, RepairGroupEntity> mRepairGroups;

    //    private AttachmentController mAttachmentController;
    private LinkController mLinkController;
    private List<GalleryBean> pics = new ArrayList<>();
    private boolean isCancel = false;
    private long deploymentId;
    private boolean hits;

    private SparePartController mSparePartController;
    private LubricateOilsController mLubricateOilsController;
    private RepairStaffController mRepairStaffController;
    private MaintenanceController maintenanceController;
    //dataGrid删除数据id
    private List<Long> dgDeletedIds_sparePart = new ArrayList<>();
    private List<Long> dgDeletedIds_lubricateOils = new ArrayList<>();
    private List<Long> dgDeletedIds_repairStaff = new ArrayList<>();
    private List<Long> dgDeletedIds_maintenance = new ArrayList<>();

    //表体修改前的列表数据
    private String lubricateOilsListStr;
    private String repairStaffListStr;
    private String sparePartListStr;
    private String maintenanceListStr;
    private YHEntity oldYHEntity;

    @Subscribe
    public void onReceiveImageDeleteEvent(ImageDeleteEvent imageDeleteEvent) {
        getController(OnlineCameraController.class).deleteGalleryBean(yhGalleryView.getGalleryAdapter().getList().get(imageDeleteEvent.getPos()), imageDeleteEvent.getPos());
//        yhGalleryView.deletePic(imageDeleteEvent.getPos());
        EventBus.getDefault().post(new RefreshEvent());
    }

    @Override
    protected int getLayoutID() {
        return R.layout.ac_yh_edit;
    }

    @Override
    protected void onInit() {
        super.onInit();
        EventBus.getDefault().register(this);
        refreshController.setAutoPullDownRefresh(false);
        refreshController.setPullDownRefreshEnabled(false);
        mYHEntity = (YHEntity) getIntent().getSerializableExtra(Constant.IntentKey.YHGL_ENTITY);
        oldYHEntity = GsonUtil.gsonToBean(mYHEntity.toString(), YHEntity.class);

        if (null != mYHEntity.eamID && !mYHEntity.eamID.checkNil()) {
            yhEditArea.setEditable(false);
        }
        deploymentId = getIntent().getLongExtra(DEPLOYMENT_ID, 0L);
        mOriginalEntity = GsonUtil.gsonToBean(mYHEntity.toString(), YHEntity.class);

        mSparePartController = getController(SparePartController.class);
        mSparePartController.setEditable(true);
        mLubricateOilsController = getController(LubricateOilsController.class);
        mLubricateOilsController.setEditable(true);
        mRepairStaffController = getController(RepairStaffController.class);
        mRepairStaffController.setEditable(true);
        maintenanceController = getController(MaintenanceController.class);
        maintenanceController.setEditable(true);

        initSinglePickController();
        initDatePickController();
//        initAttachmentController();
    }

//    private void initAttachmentController() {
//        mAttachmentController = new AttachmentController();
//    }

    private void initDatePickController() {
        mDatePickController = new DatePickController(this);
        mDatePickController.textSize(18);
        mDatePickController.setCycleDisable(false);
        mDatePickController.setCanceledOnTouchOutside(true);
    }

    private void initSinglePickController() {
        mSinglePickController = new SinglePickController<String>(this);
        mSinglePickController.textSize(18);
        mSinglePickController.setCanceledOnTouchOutside(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onRegisterController() {
        super.onRegisterController();
        mLinkController = new LinkController();
        registerController(Constant.Controller.LINK, mLinkController);
    }

    @Override
    protected void initView() {
        super.initView();
        StatusBarUtils.setWindowStatusBarColor(this, R.color.themeColor);
        titleText.setText("隐患编辑");
        yhEditFindStaff.setValue(mYHEntity.findStaffID != null ? mYHEntity.findStaffID.name : "");
        yhEditFindTime.setDate(DateUtil.dateTimeFormat(mYHEntity.findTime));
        yhEditPriority.setSpinner(mYHEntity.priority != null ? mYHEntity.priority.value : "");
        yhEditArea.setSpinner(mYHEntity.areaInstall != null ? mYHEntity.areaInstall.name : "");

        if (mYHEntity.eamID != null && !TextUtils.isEmpty(mYHEntity.eamID.name)) {
            yhEditEamCode.setValue(mYHEntity.eamID.code);
            yhEditEamName.setValue(mYHEntity.eamID.name);
            yhEditEamModel.setValue(mYHEntity.eamID.model);
        }

        yhEditType.setSpinner(mYHEntity.faultInfoType != null ? mYHEntity.faultInfoType.value : "");

        if (mYHEntity.repairType != null) {
            yhEditWXType.setSpinner(mYHEntity.repairType.value);
            if (Constant.YHWXType.JX_SYSCODE.equals(mYHEntity.repairType.id) || Constant.YHWXType.DX_SYSCODE.equals(mYHEntity.repairType.id)) {
                yhEditWXGroup.setEditable(false);
            }
        }
        yhEditWXGroup.setSpinner(mYHEntity.repiarGroup != null ? mYHEntity.repiarGroup.name : "");
        if (!TextUtils.isEmpty(mYHEntity.describe)) {
            yhEditDescription.setInput(mYHEntity.describe);
        }

        getController(OnlineCameraController.class).init(Constant.IMAGE_SAVE_YHPATH, Constant.PicType.YH_PIC);
        if (mYHEntity.attachmentEntities != null) {
            getController(OnlineCameraController.class).setPicData(mYHEntity.attachmentEntities);
        }


        if (!TextUtils.isEmpty(mYHEntity.remark)) {
            yhEditMemo.setInput(mYHEntity.remark);
        }
    }

    @SuppressLint("CheckResult")
    @SuppressWarnings("unchecked")
    @Override
    protected void initListener() {
        super.initListener();

        RxView.clicks(leftBtn)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(o -> onBackPressed());

        yhEditArea.setOnChildViewClickListener((childView, action, obj) -> {
            if (action == -1) {
                mYHEntity.areaInstall = null;
            } else {
                List<String> list = new ArrayList<>(mAreas.keySet());
                if (list.size() <= 0) {
                    ToastUtils.show(context, "区域位置列表数据为空,请重新加载页面！");
                    return;
                }
                mSinglePickController
                        .list(list)
                        .listener((SinglePicker.OnItemPickListener<String>) (index, item) -> {
                            Area area = mAreas.get(item);
                            mYHEntity.areaInstall = area;
                            yhEditArea.setSpinner(item);
                        })
                        .show(yhEditArea.getSpinnerValue());
            }
        });

        yhEditWXGroup.setOnChildViewClickListener((childView, action, obj) -> {
            if (action == -1) {
                mYHEntity.repiarGroup = null;
            } else {
                List<String> list = new ArrayList<>(mRepairGroups.keySet());
                if (list.size() <= 0) {
                    ToastUtils.show(context, "维修组列表数据为空,请重新加载页面！");
                    return;
                }
                mSinglePickController
                        .list(list)
                        .listener((SinglePicker.OnItemPickListener<String>) (index, item) -> {
                            RepairGroupEntity repairGroupEntity = mRepairGroups.get(item);
                            mYHEntity.repiarGroup = repairGroupEntity;
                            yhEditWXGroup.setSpinner(item);
                        })
                        .show(yhEditWXGroup.getSpinnerValue());
            }
        });

        yhEditType.setOnChildViewClickListener((childView, action, obj) -> {
            if (action == -1) {
                mYHEntity.faultInfoType = null;
            } else {
                List<String> list = new ArrayList<>(yhTypes.keySet());
                if (list.size() <= 0) {
                    ToastUtils.show(context, "隐患类型列表数据为空,请重新加载页面！");
                    return;
                }
                mSinglePickController
                        .list(list)
                        .listener((SinglePicker.OnItemPickListener<String>) (index, item) -> {
                            SystemCodeEntity yhType = yhTypes.get(item);
                            mYHEntity.faultInfoType = yhType;
                            yhEditType.setSpinner(item);
                        })
                        .show(yhEditType.getSpinnerValue());
            }
        });

        yhEditWXType.setOnChildViewClickListener((childView, action, obj) -> {
            if (action == -1) {
                mYHEntity.repairType = null;
                yhEditWXGroup.setEditable(true);
            } else {
                List<String> list = new ArrayList<>(wxTypes.keySet());
                if (list.size() <= 0) {
                    ToastUtils.show(context, "维修类型列表数据为空,请重新加载页面！");
                    return;
                }
                mSinglePickController
                        .list(list)
                        .listener((SinglePicker.OnItemPickListener<String>) (index, item) -> {
                            SystemCodeEntity wxType = wxTypes.get(item);
                            mYHEntity.repairType = wxType;
                            yhEditWXType.setSpinner(item);

                            //大修或检修时，维修组不可编辑，若有值清空
                            if (Constant.YHWXType.DX.equals(item) || Constant.YHWXType.JX.equals(item)) {
                                yhEditWXGroup.setEditable(false);
                                yhEditWXGroup.setSpinner(null);
                                mYHEntity.repiarGroup = null;
                            } else {
                                yhEditWXGroup.setEditable(true);
                            }
                        })
                        .show(yhEditWXType.getSpinnerValue());
            }
        });

        yhEditPriority.setOnChildViewClickListener((childView, action, obj) -> {
            if (action == -1) {
                mYHEntity.priority = null;
            } else {
                List<String> list = new ArrayList<>(yhPriorities.keySet());
                if (list.size() <= 0) {
                    ToastUtils.show(context, "优先级列表数据为空,请重新加载页面！");
                    return;
                }
                mSinglePickController
                        .list(list)
                        .listener((SinglePicker.OnItemPickListener<String>) (index, item) -> {
                            SystemCodeEntity yhPriority = yhPriorities.get(item);
                            mYHEntity.priority = yhPriority;
                            yhEditPriority.setSpinner(item);
                        })
                        .show(yhEditPriority.getSpinnerValue());
            }
        });

        yhEditFindTime.setOnChildViewClickListener((childView, action, obj) -> {
            if (action == -1) {
                mYHEntity.findTime = 0;
            } else {
                mDatePickController
                        .listener((year, month, day, hour, minute, second) -> {
                            LogUtil.i(year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second);
                            String dateStr = year + "-" + month + "-" + day + " " + hour + ":" + minute;
                            long select = DateUtil.dateFormat(dateStr, "yyyy-MM-dd HH:mm");

                            mYHEntity.findTime = select;
                            yhEditFindTime.setDate(DateUtil.dateTimeFormat(mYHEntity.findTime));

                        })
                        .show(mYHEntity.findTime);
            }
        });

        RxTextView.textChanges(yhEditMemo.editText())
                .skipInitialValue()
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribe(charSequence -> mYHEntity.remark = charSequence.toString());

        RxTextView.textChanges(yhEditDescription.editText())
                .skipInitialValue()
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribe(charSequence -> mYHEntity.describe = charSequence.toString());
/*        yhEditGalleryView.setOnChildViewClickListener((childView, action, obj) -> {

            int position = (int) obj;

            if (position == -1) {
//                startCamera();

                if (action == CustomGalleryView.ACTION_TAKE_PICTURE_FROM_CAMERA) {
                    getController(BaseCameraController.class).startCamera(Constant.YH_PATH, Constant.PicType.YH_PIC);
                }
                else if (action == CustomGalleryView.ACTION_TAKE_PICTURE_FROM_GALLERY) {
                    getController(BaseCameraController.class).startGallery(Constant.YH_PATH, Constant.PicType.YH_PIC);
                }

                return;
            }
            List<GalleryBean> galleryBeans = yhEditGalleryView.getGalleryAdapter().getList();
            GalleryBean galleryBean = galleryBeans.get(position);
            if (action == CustomGalleryView.ACTION_VIEW) {
                Bundle bundle = FaultPicHelper.genRequestBundle(context, childView, galleryBeans, position, true);
                getWindow().setWindowAnimations(R.style.fadeStyle);
                IntentRouter.go(context, Constant.Router.IMAGE_VIEW, bundle);
            } else if (action == CustomGalleryView.ACTION_DELETE) {

                new CustomDialog(context)
                        .twoButtonAlertDialog("是否删除图片?")
                        .bindView(R.id.grayBtn, "取消")
                        .bindView(R.id.redBtn, "确定")
                        .bindClickListener(R.id.grayBtn, v1 -> {
                        }, true)
                        .bindClickListener(R.id.redBtn, v3 -> {
                            yhEditGalleryView.deletePic(position);
                            deleteBitmap(galleryBean);
                        }, true)
                        .show();

            }

        });*/

        yhEditTransition.setOnChildViewClickListener(new OnChildViewClickListener() {
            @Override
            public void onChildViewClick(View childView, int action, Object obj) {
                WorkFlowVar workFlowVar = (WorkFlowVar) obj;
                if (workFlowVar == null) {
                    return;
                }
                if ("作废".equals(workFlowVar.dec)) {
                    isCancel = true;
                } else {

                    isCancel = false;

                }
                switch (action) {
                    case 0:  //保存
                        doSave();
                        break;
                    case 1:
                        if (checkBeforeSubmit()) {
                            doSubmit(workFlowVar);
                        }
                        break;
                    case 2:
                        if (checkBeforeSubmit()) {
                            doSubmit(workFlowVar);
                        }
                        break;
                    default:
                        break;
                }
            }
        });

//        yhEditTransition.setOnChildViewClickListener((childView, action, obj) -> {
//            String tag = (String) childView.getTag();
//            Transition transition = yhEditTransition.currentTransition();
//
//            if ("作废".equals(transition.transitionDesc)) {
//                isCancel = true;
//            } else {
//
//                isCancel = false;
//
//            }
//
//            switch (tag) {
//
//                case Constant.Transition.SUBMIT_BTN:
//                    if (checkBeforeSubmit()) {
//                        doSubmit(transition);
//                    }
//                    break;
//                case Constant.Transition.SAVE:
//
//                    doSave();
//
//                    break;
//
//                case Constant.Transition.ROUTER_BTN:
//                    if (mLinkController.getLinkEntities() == null) {
//                        iniTransition();
//                    }
//                    break;
//
//                default:
//
//                    break;
//
//            }
//
//        });


        yhEditEamCode.setOnChildViewClickListener((childView, action, obj) -> {
            if (action == -1) {
                mYHEntity.eamID = null;
                mYHEntity.areaInstall = null;
                yhEditArea.setEditable(true);
                yhEditArea.setSpinner(null);
                yhEditEamName.setValue(null);
                yhEditEamModel.setValue(null);
            } else {
                Bundle bundle = new Bundle();
                if (mYHEntity.areaInstall != null) {
                    bundle.putString(Constant.IntentKey.AREA_NAME, mYHEntity.areaInstall.name);
                }
                IntentRouter.go(this, Constant.Router.EAM, bundle);
//                Bundle bundle = new Bundle();
//                bundle.putString(Constant.IntentKey.MODULE, Module.Fault.name());
////                IntentRouter.go(context, Constant.Router.ADD_DEVICE, bundle);
//                bundle.putString(Constant.IntentKey.COMMON_SAERCH_MODE, Constant.CommonSearchMode.EAM);
//                bundle.putString(Constant.IntentKey.ENTITY_CODE, yhEditArea.getSpinnerValue());
//                IntentRouter.go(context, Constant.Router.COMMON_SEARCH, bundle);
            }
        });


        yhEditFindStaff.setOnChildViewClickListener((childView, action, obj) -> {

            if (action == -1) {
                mYHEntity.findStaffID = null;
            } else {
                Bundle bundle = new Bundle();
                bundle.putBoolean(Constant.IntentKey.IS_MULTI, false);
                IntentRouter.go(context, Constant.Router.COMMON_SEARCH, bundle);
            }
        });

/*        getController(BaseCameraController.class).setOnSuccessListener(new OnSuccessListener<File>() {
            @Override
            public void onSuccess(File result) {
                uploadLocalPic(result);
            }
        });*/
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void addDeviceEvent(CommonSearchEvent commonSearchEvent) {
        if (!(commonSearchEvent.commonSearchEntity instanceof EamType)) {
            return;
        }
        EamType eamType = (EamType) commonSearchEvent.commonSearchEntity;
        if (eamType != null) {
            yhEditEamName.setValue(eamType.name);
            yhEditEamCode.setValue(eamType.code);
            yhEditEamModel.setValue(eamType.model);
            WXGDEam wxgdEam = new WXGDEam();
            wxgdEam.name = eamType.name;
            wxgdEam.code = eamType.code;
            wxgdEam.model = eamType.model;
            wxgdEam.id = eamType.id;
            mYHEntity.eamID = wxgdEam;
            mSparePartController.upEam(wxgdEam);
            mLubricateOilsController.upEam(wxgdEam);
            maintenanceController.upEam(wxgdEam);

            //处理区域位置
            yhEditArea.setEditable(false);
            yhEditArea.setSpinner(eamType.getInstallPlace().name);
            mYHEntity.areaInstall = eamType.getInstallPlace();
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMaintenanceStaff(CommonSearchEvent commonSearchEvent) {
        if (!(commonSearchEvent.commonSearchEntity instanceof UserInfo)) {
            return;
        }
        UserInfo userInfo = (UserInfo) commonSearchEvent.commonSearchEntity;
        yhEditFindStaff.setValue(userInfo.staffName);
        mYHEntity.findStaffID = new Staff();
        mYHEntity.findStaffID.id = userInfo.staffId;
        mYHEntity.findStaffID.code = userInfo.staffCode;
        mYHEntity.findStaffID.name = userInfo.staffName;
    }

    /**
     * @param
     * @return
     * @description 接收原始数据
     * @author zhangwenshuai1 2018/10/11
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveList(ListEvent listEvent) {
        if ("lubricateOils".equals(listEvent.getFlag())) {
            lubricateOilsListStr = listEvent.getList().toString();
        } else if ("repairStaff".equals(listEvent.getFlag())) {
            repairStaffListStr = listEvent.getList().toString();
        } else if ("sparePart".equals(listEvent.getFlag())) {
            sparePartListStr = listEvent.getList().toString();
        } else if ("maintenance".equals(listEvent.getFlag())) {
            maintenanceListStr = listEvent.getList().toString();
        }
    }

    //更新备件
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshSparePart(SparePartEvent sparePartEvent) {
        mSparePartController.updateSparePartEntities(sparePartEvent.getList());
        if (sparePartEvent.getList().size() <= 0) {
            mSparePartController.clear();
        }
        dgDeletedIds_sparePart = sparePartEvent.getDgDeletedIds();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshLubricateOils(LubricateOilsEvent event) {
        mLubricateOilsController.updateLubricateOilsEntities(event.getList());
        if (event.getList().size() <= 0) {
            mLubricateOilsController.clear();
        }
        dgDeletedIds_lubricateOils = event.getDgDeletedIds();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshRepairStaff(RepairStaffEvent event) {
        mRepairStaffController.updateRepairStaffEntiies(event.getList());
        if (event.getList().size() <= 0) {
//            repairStaffListWidget.clear();
            mRepairStaffController.clear();
        }

        dgDeletedIds_repairStaff = event.getDgDeletedIds();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshRepairStaff(MaintenanceEvent event) {
        maintenanceController.updateMaintenanceEntities(event.getList());
        if (event.getList().size() <= 0) {
            maintenanceController.clear();
        }
        dgDeletedIds_maintenance = event.getDgDeletedIds();
    }


    @Override
    protected void initData() {
        super.initData();

        iniTransition();

        List<SystemCodeEntity> wxTypeEntities = SystemCodeManager.getInstance().getSystemCodeListByCode(Constant.SystemCode.YH_WX_TYPE);
        wxTypes = initEntities(wxTypeEntities);

        List<SystemCodeEntity> yhTypeEntities = SystemCodeManager.getInstance().getSystemCodeListByCode(Constant.SystemCode.QX_TYPE);
        yhTypes = initEntities(yhTypeEntities);

        List<SystemCodeEntity> yhPriorityEntity = SystemCodeManager.getInstance().getSystemCodeListByCode(Constant.SystemCode.YH_PRIORITY);
        yhPriorities = initEntities(yhPriorityEntity);

        List<Area> areaEntities = EamApplication.dao().getAreaDao().queryBuilder().where(AreaDao.Properties.Ip.eq(MBapApp.getIp())).list();
        mAreas = initEntities(areaEntities);

        List<RepairGroupEntity> repairGroupEntities =
                EamApplication.dao().getRepairGroupEntityDao().queryBuilder().where(RepairGroupEntityDao.Properties.IsUse.eq(Boolean.valueOf(true)), RepairGroupEntityDao.Properties.Ip.eq(EamApplication.getIp())).list();
        mRepairGroups = initEntities(repairGroupEntities);
    }

    private void iniTransition() {

        if (mYHEntity.pending == null) {
            mLinkController.initStartTransition(yhEditTransition, "faultInfoFW");
        } else {
            mLinkController.initPendingTransition(yhEditTransition, mYHEntity.pending.id);
        }
    }

    private <T extends BaseEntity> Map<String, T> initEntities(List<T> entities) {

        Map<String, T> map = new LinkedHashMap<>();


        for (T entity : entities) {

            String name = FieldHepler.getFieldValue(entity.getClass(), entity, "name");
            if (!TextUtils.isEmpty(name)) {
                map.put(name, entity);
                continue;
            }

            String value = FieldHepler.getFieldValue(entity.getClass(), entity, "value");
            if (!TextUtils.isEmpty(value)) {
                map.put(value, entity);
            }

        }

        return map;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().setWindowAnimations(R.style.activityAnimation);
    }

    @Override
    public void onBackPressed() {
        //退出前校验表单是否修改过
        //如果修改过, 提示是否保存
        if (isModified() || doCheckChange()) {
            new CustomDialog(context)
                    .twoButtonAlertDialog("页面已经被修改，是否要保存?")
                    .bindView(R.id.grayBtn, "保存")
                    .bindView(R.id.redBtn, "离开")
                    .bindClickListener(R.id.grayBtn, v -> doSave(), true)
                    .bindClickListener(R.id.redBtn, v3 -> {
                        EventBus.getDefault().post(new RefreshEvent());
                        //关闭页面
                        YHEditActivity.this.finish();
                    }, true)
                    .show();
        } else {
            EventBus.getDefault().post(new RefreshEvent());
            super.onBackPressed();
        }

    }

    /**
     * 提交之前校验必填字段
     *
     * @return
     */
    private boolean checkBeforeSubmit() {

        if (isCancel) {
            return true;
        }

        if (mYHEntity.findStaffID == null || null == mYHEntity.findStaffID.id) {
            SnackbarHelper.showError(rootView, "请填写发现人！");
            return false;
        }
        if (mYHEntity.findTime == 0) {
            SnackbarHelper.showError(rootView, "请填写发现时间！");
            return false;
        }

        if (mYHEntity.priority == null) {
            SnackbarHelper.showError(rootView, "请选择优先级！");
            return false;
        }

        //判断必填字段是否为空
        if (mYHEntity.eamID == null || mYHEntity.eamID.code == null) {
            SnackbarHelper.showError(rootView, "请选择设备");
            return false;
        }

        if (mYHEntity.faultInfoType == null) {
            SnackbarHelper.showError(rootView, "请选择隐患类型！");
            return false;
        }

        if (TextUtils.isEmpty(yhEditDescription.getInput())) {
            SnackbarHelper.showError(rootView, "请填写隐患现象！");
            return false;
        }

        if (mYHEntity.repairType == null) {
            SnackbarHelper.showError(rootView, "请选择维修类型！");
            return false;
        }

//        RoleEntity roleEntity = ((RoleController) getController(Constant.Controller.ROLE)).getRoleEntity(0);
//        if (roleEntity == null) {
//            SnackbarHelper.showError(rootView, "角色为空，请保存！");
//            return false;
//        }

//        if (TextUtils.isEmpty(yhEditTransition.currentTransition().outCome)) {
//            SnackbarHelper.showError(rootView, "请选择操作！");
//            return false;
//        }

        return true;
    }

    private void doSave() {
        hits = true;
        submit(null);
    }

    private void doSubmit(WorkFlowVar workFlowVar) {
        List<WorkFlowEntity> workFlowEntities = new ArrayList<>();
        WorkFlowEntity workFlowEntity = new WorkFlowEntity();
        workFlowEntity.dec = workFlowVar.dec;
        workFlowEntity.type = workFlowVar.outcomeMapJson.get(0).type;
        workFlowEntity.outcome = workFlowVar.outCome;
        workFlowEntities.add(workFlowEntity);

        submit(workFlowEntities);
    }

    private void submit(List<WorkFlowEntity> workFlowEntities) {
        WorkFlowEntity workFlowEntity = null;
        if (workFlowEntities != null && workFlowEntities.size() != 0) {
            workFlowEntity = workFlowEntities.get(0);
        } else {
            workFlowEntity = new WorkFlowEntity();
        }

        Map<String, Object> map = new HashMap<>();
        map.put("bap_validate_user_id", String.valueOf(EamApplication.getAccountInfo().userId));
        map.put("faultInfo.createStaffId", (mYHEntity.findStaffID == null || mYHEntity.findStaffID.checkNil()) ? "" : Util.strFormat2(mYHEntity.findStaffID.id));
        map.put("faultInfo.createTime", DateUtil.dateTimeFormat(mYHEntity.findTime));
        map.put("faultInfo.findStaffID.id", (mYHEntity.findStaffID == null || mYHEntity.findStaffID.checkNil()) ? "" : Util.strFormat2(mYHEntity.findStaffID.id));
        map.put("faultInfo.findTime", DateUtil.dateTimeFormat(mYHEntity.findTime));
        map.put("faultInfo.createPositionId", EamApplication.getAccountInfo().positionId);
        map.put("viewselect", "faultInfoEdit");
        map.put("id", mYHEntity.id != -1 ? mYHEntity.id : "");
        map.put("faultInfo.id", mYHEntity.id != -1 ? mYHEntity.id : "");

        if (mYHEntity.pending != null && mYHEntity.pending.id != null) {
            map.put("pendingId", Util.strFormat2(mYHEntity.pending.id));
            map.put("deploymentId", Util.strFormat2(mYHEntity.pending.deploymentId));
        } else {
            map.put("deploymentId", deploymentId);
            map.put("faultInfo.version", 1);
        }


        if (mYHEntity.eamID != null && mYHEntity.eamID.id != null) {
            map.put("faultInfo.eamID.id", Util.strFormat2(mYHEntity.eamID.id));
        } else {
            map.put("faultInfo.eamID.id", "");
        }

        if (mYHEntity.areaInstall != null && mYHEntity.areaInstall.id != 0) {
            map.put("faultInfo.areaInstall.id", Util.strFormat2(mYHEntity.areaInstall.id));
        } else {
            map.put("faultInfo.areaInstall.id", "");
        }
        if (mYHEntity.repiarGroup != null && mYHEntity.repiarGroup.id != null) {
            map.put("faultInfo.repiarGroup.id", Util.strFormat2(mYHEntity.repiarGroup.id));
        } else {
            map.put("faultInfo.repiarGroup.id", "");
        }

        if (mYHEntity.faultInfoType != null) {
            map.put("faultInfo.faultInfoType.id", Util.strFormat2(mYHEntity.faultInfoType.id));
            map.put("faultInfo.faultInfoType.value", Util.strFormat2(mYHEntity.faultInfoType.value));
        } else {
            map.put("faultInfo.faultInfoType.id", "");
            map.put("faultInfo.faultInfoType.value", "");
        }
        if (mYHEntity.priority != null) {
            map.put("faultInfo.priority.id", mYHEntity.priority.id);
        } else {
            map.put("faultInfo.priority.id", "");
        }

        if (mYHEntity.repairType != null) {
            map.put("faultInfo.repairType.id", mYHEntity.repairType.id);
        } else {
            map.put("faultInfo.repairType.id", "");
        }

        if (mYHEntity.describe != null)
            map.put("faultInfo.describe", mYHEntity.describe);
        if (mYHEntity.remark != null)
            map.put("faultInfo.remark", mYHEntity.remark);

        map.put("linkId", mYHEntity.tableInfoId != 0 ? mYHEntity.tableInfoId : "");
//        map.put("dlTableInfoId",                mYHEntity.tableInfoId);
        map.put("tableInfoId", mYHEntity.tableInfoId != 0 ? mYHEntity.tableInfoId : "");
        if (workFlowEntities != null) {//保存为空
            map.put("workFlowVar.outcomeMapJson", workFlowEntities);
            map.put("workFlowVar.dec", workFlowEntity.dec);
            map.put("workFlowVar.outcome", workFlowEntity.outcome);
            map.put("operateType", "submit");

            if (Constant.Transition.CANCEL_CN.equals(workFlowEntity.dec)) {
                map.put("workFlowVarStatus", Constant.Transition.CANCEL);
            }
        } else {
//            map.put("workFlowVar.dec", "");
//            map.put("workFlowVar.outcome", "");
            map.put("operateType", "save");
        }
        map.put("taskDescription", "BEAM2_1.0.0.faultInfoFW.task342");
        map.put("activityName", "task342");
        map.put("workFlowVar.comment", yhEditCommentInput.getInput());

        map.put("viewCode", "BEAM2_1.0.0_faultInfo_faultInfoEdit");

        map.put("modelName", "FaultInfo");
        map.put("datagridKey", "BEAM2_faultInfo_faultInfo_faultInfoEdit_datagrids");
        map.put("__file_upload", true);

        //标题数据
        List<SparePartEntityDto> sparePartEntityDtos = YHGLMapManager.translateSparePartDto(mSparePartController.getSparePartEntities());
        List<RepairStaffDto> repairStaffDtos = YHGLMapManager.translateStaffDto(mRepairStaffController.getRepairStaffEntities());
        List<LubricateOilsEntityDto> lubricateOilsEntityDtos = YHGLMapManager.translateLubricateOilsDto(mLubricateOilsController.getLubricateOilsEntities());
        List<MaintainDto> maintainDtos = YHGLMapManager.translateMaintainDto(maintenanceController.getMaintenanceEntities());

        map = YHGLMapManager.dgDeleted(map, dgDeletedIds_sparePart, "dg1557402465409");
        map.put("dg1557402465409ModelCode", "BEAM2_1.0.0_faultInfo_FaultSparePart");
        map.put("dg1557402465409ListJson", sparePartEntityDtos.toString());
        map.put("dgLists['dg1557402465409']", sparePartEntityDtos.toString());

        map = YHGLMapManager.dgDeleted(map, dgDeletedIds_repairStaff, "dg1557402325583");
        map.put("dg1557402325583ModelCode", "BEAM2_1.0.0_faultInfo_FaultRepairStaff");
        map.put("dg1557402325583ListJson", repairStaffDtos);
        map.put("dgLists['dg1557402325583']", repairStaffDtos);

        map = YHGLMapManager.dgDeleted(map, dgDeletedIds_lubricateOils, "dg1557455440578");
        map.put("dg1557455440578ModelCode", "BEAM2_1.0.0_faultInfo_FaultLubricateOils");
        map.put("dg1557455440578ListJson", lubricateOilsEntityDtos);
        map.put("dgLists['dg1557455440578']", lubricateOilsEntityDtos);

        map = YHGLMapManager.dgDeleted(map, dgDeletedIds_maintenance, "dg1557457043896");
        map.put("dg1557457043896ModelCode", "BEAM2_1.0.0_faultInfo_MaintainJwx");
        map.put("dg1557457043896ListJson", maintainDtos);
        map.put("dgLists['dg1557457043896']", maintainDtos);


        Map<String, Object> attachmentMap = new HashMap<>();

//        List<Map<String, Object>> files = new ArrayList<>();
//        for (GalleryBean galleryBean : pics) {
//            Map<String, Object> file = new HashMap<>();
//            file.put("file.filePath", galleryBean.url);
//            file.put("file.name", galleryBean.url.substring(galleryBean.url.lastIndexOf("\\") + 1));
//            file.put("file.memos", "pda隐患拍照上传");
//            file.put("file.fileType", "attachment");
//            files.add(file);
//        }
//
//        if (pics.size() != 0) {
//            attachmentMap.put("file.staffId", String.valueOf(EamApplication.getAccountInfo().staffId));
//            attachmentMap.put("file.type", "Table");
//            attachmentMap.put("files", files);
//            attachmentMap.put("linkId", String.valueOf(mYHEntity.tableInfoId));
//        }
        getController(OnlineCameraController.class).doSave(attachmentMap);
        if (attachmentMap.size() != 0) {
            attachmentMap.put("linkId", String.valueOf(mYHEntity.tableInfoId));
        }

        LogUtil.d(GsonUtil.gsonString(map));
        onLoading("单据处理中...");
        presenterRouter.create(YHSubmitAPI.class).doSubmit(map, attachmentMap, true);

    }

    @Override
    public boolean isModified() {
        return checkIsModified() || super.isModified();
    }

    /**
     * 判断是否填写过表单
     */
    private boolean checkIsModified() {
        if (!GsonUtil.gsonString(mYHEntity).equals(mOriginalEntity.toString())) {
            return true;
        }

        return !TextUtils.isEmpty(yhEditCommentInput.getInput());
    }

/*
    private void uploadLocalPic(File file) {
        onLoading("正在上传照片...");
        mAttachmentController.uploadAttachment(new OnSuccessListener<String>() {
            @Override
            public void onSuccess(String result) {
                GalleryBean galleryBean = new GalleryBean();
                galleryBean.localPath = file.getAbsolutePath();
                galleryBean.url = result;
                yhEditGalleryView.addGalleryBean(galleryBean);
                onLoadSuccess("照片上传成功！");
                pics.add(galleryBean);
            }
        }, file);
    }

    private void deleteBitmap(GalleryBean galleryBean) {
        if (pics.contains(galleryBean)) {
            pics.remove(galleryBean);
//            return;
        }

        String picPath = galleryBean.url;

        if (mYHEntity.attachmentEntities != null)
            for (AttachmentEntity attachmentEntity : mYHEntity.attachmentEntities) {
                if (attachmentEntity.path.equals(picPath)) {
                    mAttachmentController.deleteAttachment(result ->
                            SnackbarHelper.showMessage(rootView, "删除附件成功！"), attachmentEntity.id);
                }
            }
    }*/

    @Override
    public void doSubmitSuccess(BapResultEntity entity) {
        LogUtil.d("entity:" + entity);

        RefreshEvent refreshEvent = new RefreshEvent();
        if (isCancel) {
            refreshEvent.action = Constant.Transition.CANCEL;
            isCancel = false;
        }
        EventBus.getDefault().post(refreshEvent);

        if (hits) {
            hits = false;
            onLoadSuccessAndExit("保存成功！", this::finish);
        } else {
            onLoadSuccessAndExit("处理成功！", this::finish);
        }
    }

    @Override
    public void doSubmitFailed(String errorMsg) {
        LogUtil.e("errorMsg:" + errorMsg);
        hits = false;
        onLoadFailed(errorMsg);
        SnackbarHelper.showError(rootView, ErrorMsgHelper.msgParse(errorMsg));
    }

    //检查是否变化
    public boolean doCheckChange() {
        if (!oldYHEntity.toString().equals(mYHEntity.toString())) {
            return true;
        }
        if (!TextUtils.isEmpty(repairStaffListStr) && !repairStaffListStr.equals(mRepairStaffController.getRepairStaffEntities().toString())) {
            return true;
        }
        if (!TextUtils.isEmpty(lubricateOilsListStr) && !lubricateOilsListStr.equals(mLubricateOilsController.getLubricateOilsEntities().toString())) {
            return true;
        }
        if ((!TextUtils.isEmpty(sparePartListStr) && !sparePartListStr.equals(mSparePartController.getSparePartEntities().toString()))) {
            return true;
        }
        if ((!TextUtils.isEmpty(maintenanceListStr) && !maintenanceListStr.equals(maintenanceController.getMaintenanceEntities().toString()))) {
            return true;
        }
        return false;
    }
}
