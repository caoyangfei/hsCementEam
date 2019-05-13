package com.supcon.mes.module_wxgd.ui;

import android.annotation.SuppressLint;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.app.annotation.BindByTag;
import com.app.annotation.Presenter;
import com.app.annotation.apt.Router;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.supcon.common.com_http.util.RxSchedulers;
import com.supcon.common.view.base.activity.BaseRefreshRecyclerActivity;
import com.supcon.common.view.base.adapter.IListAdapter;
import com.supcon.common.view.listener.OnItemChildViewClickListener;
import com.supcon.common.view.listener.OnRefreshPageListener;
import com.supcon.common.view.util.LogUtil;
import com.supcon.common.view.view.loader.base.OnLoaderFinishListener;
import com.supcon.mes.mbap.adapter.RecyclerEmptyAdapter;
import com.supcon.mes.mbap.beans.FilterBean;
import com.supcon.mes.mbap.beans.LoginEvent;
import com.supcon.mes.mbap.beans.WorkFlowEntity;
import com.supcon.mes.mbap.utils.SpaceItemDecoration;
import com.supcon.mes.mbap.utils.StatusBarUtils;
import com.supcon.mes.mbap.view.CustomDialog;
import com.supcon.mes.mbap.view.CustomFilterView;
import com.supcon.mes.mbap.view.CustomHorizontalSearchTitleBar;
import com.supcon.mes.mbap.view.CustomSearchView;
import com.supcon.mes.middleware.EamApplication;
import com.supcon.mes.middleware.constant.Constant;
import com.supcon.mes.middleware.controller.RoleController;
import com.supcon.mes.middleware.model.bean.BapResultEntity;
import com.supcon.mes.middleware.model.bean.RoleEntity;
import com.supcon.mes.middleware.model.event.LoginValidEvent;
import com.supcon.mes.middleware.model.event.RefreshEvent;
import com.supcon.mes.middleware.util.EmptyViewHelper;
import com.supcon.mes.middleware.util.ErrorMsgHelper;
import com.supcon.mes.middleware.util.SnackbarHelper;
import com.supcon.mes.middleware.util.SystemCodeManager;
import com.supcon.mes.module_wxgd.R;
import com.supcon.mes.module_wxgd.controller.WXGDSubmitController;
import com.supcon.mes.module_wxgd.model.api.WXGDListAPI;
import com.supcon.mes.middleware.model.bean.WXGDEntity;
import com.supcon.mes.module_wxgd.model.bean.WXGDListEntity;
import com.supcon.mes.module_wxgd.model.contract.WXGDListContract;
import com.supcon.mes.module_wxgd.presenter.WXGDListPresenter;
import com.supcon.mes.module_wxgd.ui.adapter.WXGDListAdapter;
import com.supcon.mes.module_wxgd.util.FilterHelper;
import com.supcon.mes.module_wxgd.util.WXGDMapManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

/**
 * WXGDListActivity 维修工单列表
 * created by zhangwenshuai1 2018/8/9
 */

@Router(value = Constant.Router.WXGD_LIST)
@Presenter(value = {WXGDListPresenter.class})
public class WXGDListActivity extends BaseRefreshRecyclerActivity<WXGDEntity> implements WXGDListContract.View, WXGDSubmitController.OnSubmitResultListener {

    @BindByTag("contentView")
    RecyclerView contentView;

    @BindByTag("leftBtn")
    AppCompatImageButton leftBtn;

    @BindByTag("rightBtn")
    AppCompatImageButton rightBtn;

    @BindByTag("titleText")
    TextView titleText;

    @BindByTag("customSearchView")
    CustomSearchView customSearchView;

    @BindByTag("searchTitleBar")
    CustomHorizontalSearchTitleBar searchTitleBar;

    @BindByTag("listWorkSourceFilter")
    CustomFilterView<FilterBean> listWorkSourceFilter;

    @BindByTag("listRepairTypeFilter")
    CustomFilterView<FilterBean> listRepairTypeFilter;

    @BindByTag("listPriorityFilter")
    CustomFilterView<FilterBean> listPriorityFilter;

    private WXGDListAdapter wxgdListAdapter;
    private RecyclerEmptyAdapter mRecyclerEmptyAdapter;

    Map<String, Object> queryParam = new HashMap<>();
    private WXGDSubmitController wxgdSubmitController;
    private RoleController roleController;


    @Override
    protected IListAdapter createAdapter() {
        wxgdListAdapter = new WXGDListAdapter(context);
        return wxgdListAdapter;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.ac_wxgd_list;
    }

    @Override
    protected void onInit() {
        super.onInit();

        refreshListController.setAutoPullDownRefresh(true);
        refreshListController.setPullDownRefreshEnabled(true);
        refreshListController.setLoadMoreEnable(true);

        EventBus.getDefault().register(this);
    }

    @Override
    protected void initView() {
        super.initView();

        StatusBarUtils.setWindowStatusBarColor(this, R.color.themeColor);
        contentView.setLayoutManager(new LinearLayoutManager(this));
        contentView.addItemDecoration(new SpaceItemDecoration(10));

        customSearchView.setHint("搜索");
        searchTitleBar.disableRightBtn();

        initFilter();
        initEmpty();
    }

    @Override
    protected void onRegisterController() {
        super.onRegisterController();
        roleController = new RoleController();
        wxgdSubmitController = new WXGDSubmitController(this);
        roleController.queryRoleList(EamApplication.getUserName());
        registerController(Constant.Controller.ROLE, roleController);
        registerController(Constant.Controller.SUBMIT, wxgdSubmitController);
    }

    /**
     * @param
     * @return
     * @description 初始化过滤条件
     * @author zhangwenshuai1 2018/8/14
     */
    private void initFilter() {
//        listWorkSourceFilter.setCurrentItem(Constant.WxgdWorkSource_CN.allSource);
        listWorkSourceFilter.setData(FilterHelper.createWorkSourceList());
        listRepairTypeFilter.setData(FilterHelper.createRepairTypeList());
        listPriorityFilter.setData(FilterHelper.createPriorityList());
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initListener() {
        super.initListener();

        leftBtn.setOnClickListener(v -> finish());

        refreshListController.setOnRefreshPageListener(new OnRefreshPageListener() {
            @Override
            public void onRefresh(int pageIndex) {
                presenterRouter.create(WXGDListAPI.class).listWxgds(pageIndex, queryParam);
            }
        });

        listWorkSourceFilter.setFilterSelectChangedListener(filterBean -> {
            LogUtil.d("filterBean", filterBean.toString());

            generateFilterCondition(filterBean.name);
        });

        listRepairTypeFilter.setFilterSelectChangedListener(new CustomFilterView.FilterSelectChangedListener<FilterBean>() {
            @Override
            public void onFilterSelected(FilterBean filterBean) {
                if (filterBean.type == CustomFilterView.VIEW_TYPE_ALL){
                    queryParam.remove(Constant.BAPQuery.WXGD_REPAIR_TYPE);
                }else {
                    queryParam.put(Constant.BAPQuery.WXGD_REPAIR_TYPE, SystemCodeManager.getInstance().getSystemCodeEntityId(Constant.SystemCode.YH_WX_TYPE,filterBean.name));
                }
                doFilter();
            }
        });
        listPriorityFilter.setFilterSelectChangedListener(new CustomFilterView.FilterSelectChangedListener<FilterBean>() {
            @Override
            public void onFilterSelected(FilterBean filterBean) {
                if (filterBean.type == CustomFilterView.VIEW_TYPE_ALL){
                    queryParam.remove(Constant.BAPQuery.WXGD_PRIORITY);
                }else {
                    queryParam.put(Constant.BAPQuery.WXGD_PRIORITY,SystemCodeManager.getInstance().getSystemCodeEntityId(Constant.SystemCode.YH_PRIORITY,filterBean.name));
                }
                doFilter();
            }
        });

        RxTextView.textChanges(customSearchView.editText())
                .skipInitialValue()
                .subscribe(charSequence -> {
                    doSearchTableNo(charSequence.toString().trim());
                });

        searchTitleBar.setOnExpandListener(isExpand -> {

            if (isExpand) {
                customSearchView.setHint("搜索设备名称");
                customSearchView.setInputTextColor(R.color.hintColor);
            } else {
                customSearchView.setHint("搜索");
                customSearchView.setInputTextColor(R.color.black);
            }

        });

        wxgdListAdapter.setOnItemChildViewClickListener(new OnItemChildViewClickListener() {
            @Override
            public void onItemChildViewClick(View childView, int position, int action, Object obj) {
                String tag = String.valueOf(childView.getTag());
                WXGDEntity wxgdEntity = (WXGDEntity) obj;
                switch (tag) {
                    case "receiveBtn":
                        new CustomDialog(context)
                                .twoButtonAlertDialog("你确认接单么？")
                                .bindView(R.id.grayBtn, "取消")
                                .bindView(R.id.redBtn, "确认")
                                .bindClickListener(R.id.grayBtn, null, true)
                                .bindClickListener(R.id.redBtn, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        doSubmit(wxgdEntity);
                                    }
                                }, true)
                                .show();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * @param
     * @return
     * @description 接单
     * @author zhangwenshuai1 2018/9/13
     */
    private void doSubmit(WXGDEntity wxgdEntity) {
        onLoading("接单中...");
        //封装公共参数
        Map<String, Object> map = WXGDMapManager.createMap(wxgdEntity);

        //封装工作流
        map = generateWorkFlow(map);

        generateParamsDtoAndSubmit(map);
    }

    private Map<String, Object> generateWorkFlow(Map<String, Object> map) {
        WorkFlowEntity workFlowEntity = new WorkFlowEntity();
        List<WorkFlowEntity> workFlowEntities = new ArrayList<>();
        workFlowEntity.dec = "接单";
        workFlowEntity.type = "normal";
        workFlowEntity.outcome = "Link2163";
        workFlowEntities.add(workFlowEntity);
        map.put("operateType", Constant.Transition.SUBMIT);
        map.put("workFlowVar.outcomeMapJson", workFlowEntities.toString());
        map.put("workFlowVar.outcome", workFlowEntity.outcome);

        return map;
    }

    private void generateParamsDtoAndSubmit(Map<String, Object> map) {
        RoleEntity roleEntity = roleController.getRoleEntity(0);
        map.put("workRecord.createPositionId", roleEntity.role != null ? roleEntity.role.id : "");
        map.put("viewselect", "workReceiptEdit");
        map.put("workRecord.receiptInfo", "jiedan");
        map.put("datagridKey", "BEAM2_workList_workRecord_workReceiptEdit_datagrids");
        map.put("viewCode", "BEAM2_1.0.0_workList_workReceiptEdit");
        map.put("modelName", "WorkRecord");
        map.put("taskDescription", "BEAM2_1.0.0.work.task457");


        map.put("dg1531695879537ModelCode", "BEAM2_1.0.0_workList_AccceptanceCheck");
        map.put("dg1531695879537ListJson", new LinkedList().toString());

        map.put("dg1531695879443ModelCode", "BEAM2_1.0.0_workList_LubricateOil");
        map.put("dg1531695879443ListJson", new LinkedList().toString());

        map.put("dg1531695879084ModelCode", "BEAM2_1.0.0_workList_SparePart");
        map.put("dg1531695879084ListJson", new LinkedList().toString());

        map.put("dg1531695879365ModelCode", "BEAM2_1.0.0_workList_RepairStaff");
        map.put("dg1531695879365ListJson", new LinkedList().toString());
        wxgdSubmitController.doReceiveSubmit(map);
    }

    /**
     * @param
     * @return
     * @description 设备名称查询
     * @author zhangwenshuai1 2018/8/14
     */
    private void doSearchTableNo(String eamName) {
        queryParam.put(Constant.BAPQuery.EAM_NAME, eamName);
        refreshListController.refreshBegin();
//        if (queryParam.containsKey(Constant.BAPQuery.TABLE_NO) || queryParam.containsKey(Constant.BAPQuery.EAM_NAME)) {
//            queryParam.remove(Constant.BAPQuery.TABLE_NO);
//            queryParam.remove(Constant.BAPQuery.EAM_NAME);
//        }
//
//        if (Util.isContainChinese(tableNo)) {
//            queryParam.put(Constant.BAPQuery.EAM_NAME, tableNo);
//        } else {
//            queryParam.put(Constant.BAPQuery.TABLE_NO, tableNo);
//        }
//        refreshListController.refreshBegin();
    }

    /**
     * @param
     * @return
     * @description 封装工单来源过滤条件
     * @author zhangwenshuai1 2018/8/14
     */
    private void generateFilterCondition(String workSource) {
        String workSourceId = "";
        switch (workSource) {
            case Constant.WxgdWorkSource_CN.faultInfoSource:
                workSourceId = Constant.WxgdWorkSource.faultInfoSource;
                break;
            case Constant.WxgdWorkSource_CN.checkRepair:
                workSourceId = Constant.WxgdWorkSource.checkRepair;
                break;
            case Constant.WxgdWorkSource_CN.bigRepair:
                workSourceId = Constant.WxgdWorkSource.bigRepair;
                break;
            case Constant.WxgdWorkSource_CN.lubrication:
                workSourceId = Constant.WxgdWorkSource.lubrication;
                break;
            case Constant.WxgdWorkSource_CN.maintenance:
                workSourceId = Constant.WxgdWorkSource.maintenance;
                break;
            case Constant.WxgdWorkSource_CN.sparePart:
                workSourceId = Constant.WxgdWorkSource.sparePart;
                break;
            case Constant.WxgdWorkSource_CN.allSource:
                workSourceId = null;
                break;
            default:
                break;
        }

        queryParam.put(Constant.BAPQuery.WORK_SOURCE, workSourceId);
        doFilter();
    }

    /**
     * @param
     * @return
     * @description 过滤查询
     * @author zhangwenshuai1 2018/8/14
     */
    private void doFilter() {
        refreshListController.refreshBegin();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refresh(LoginEvent loginEvent) {
        LogUtil.w("---------------------------------","测试登录刷新...");
        refreshListController.refreshBegin();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refresh(LoginValidEvent loginValidEvent){
        LogUtil.w("---------------------------------","测试登录失效刷新...");
        Flowable.timer(500, TimeUnit.MILLISECONDS)
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        refreshListController.refreshBegin();
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(RefreshEvent refreshEvent) {
        refreshListController.refreshBegin();
    }

    @Override
    public void listWxgdsSuccess(WXGDListEntity entity) {
        refreshListController.refreshComplete(entity.result);
//        List<WXGDEntity> list = entity.result;
//        List<WXGDEntity> filterList = new ArrayList<>();
//        if (!TextUtils.isEmpty(tableNoFilter)) {
//            for (WXGDEntity wxgdEntity : list) {
//                if (wxgdEntity.tableNo.contains(tableNoFilter)) {
//                    filterList.add(wxgdEntity);
//                }
//            }
//            refreshListController.refreshComplete(filterList);
//        } else {
//            refreshListController.refreshComplete(list);
//        }

    }

    @Override
    public void listWxgdsFailed(String errorMsg) {
        SnackbarHelper.showError(rootView, errorMsg);
        refreshListController.refreshComplete();
    }

    /**
     * @param
     * @return
     * @description 初始化无数据
     * @author zhangwenshuai1 2018/8/9
     */
    private void initEmpty() {
        mRecyclerEmptyAdapter = new RecyclerEmptyAdapter(context);
        mRecyclerEmptyAdapter.addData(EmptyViewHelper.createEmptyEntity());
        refreshListController.setEmpterAdapter(mRecyclerEmptyAdapter);

    }

    @Override
    public void submitSuccess(BapResultEntity entity) {
        onLoadSuccessAndExit("接单成功", new OnLoaderFinishListener() {
            @Override
            public void onLoaderFinished() {
                refreshListController.refreshBegin();
            }
        });
    }

    @Override
    public void submitFailed(String errorMsg) {
        onLoadFailed(ErrorMsgHelper.msgParse(errorMsg));
    }
}
