package com.supcon.mes.middleware.constant;

import android.os.Environment;

import java.io.File;

/**
 * Created by wangshizhan on 2018/4/28.
 * Email:wangshizhan@supcon.com
 */

public interface Constant {

    String FILE_PATH = Environment.getExternalStorageDirectory() + File.separator + "eam" + File.separator;
    String CRASH_PATH = FILE_PATH + "log" + File.separator;
    String XJ_PATH = FILE_PATH + "xj" + File.separator;
    String QX_PATH = FILE_PATH + "quexian" + File.separator;
    String YH_PATH = FILE_PATH + "yh" + File.separator;
    String XJ_BASE_PATH = FILE_PATH + "xj_base" + File.separator;
    String EAM_BASE_PATH = FILE_PATH + "eam_base" + File.separator;
    String IMAGE_SAVE_PATH = FILE_PATH + "pics" + File.separator;   //在线单据下载的缺陷图片存放位置
    String IMAGE_SAVE_YHPATH = YH_PATH + "pics" + File.separator;   //缺陷拍摄的图片位置
    String IMAGE_SAVE_XJPATH = XJ_PATH + "pics" + File.separator;   //巡检拍摄的图片路径
    String CID = "cid";
    String CNAME = "cname";
    String XJ_GUIDE_IMGPATH = FILE_PATH + "eamInspectionGuideImage" + File.separator; //设备巡检指导图片路径

    interface CAMERA_ACT {
        int ACT_GALLERY = 0;
        int ACT_CAMERA = 1;
        int ACT_CROP = 2;
        int ACT_PERMISSION = 3;
    }

    interface Controller {
        String ROLE = "ROLE";
        String WORK_FLOW = "WORK_FLOW";
        String LINK = "LINK";

        String REPAIR_STAFF = "REPAIR_STAFF";
        String ACCEPTANCE = "ACCEPTANCE";
        String LUBRICATE_OIL = "LUBRICATE_OIL";
        String SPARE_PART = "SPARE_PART";
        String SUBMIT = "SUBMIT";
    }


    interface Router {

        String MAIN = "main";
        String LOGIN = "login";
        String SETTING = "setting";
        String WORK_SETTING = "workSetting";
        String WEB = "web";
        String IMAGE_VIEW = "IMAGE_VIEW";
        String ABOUT = "ABOUT";


        String XJGL_LIST = "XJGL_LIST";


        String YXJL_LIST = "YXJLList";
        String YXJL_EDIT = "YXJL_EDIT";
        String YXJL_LOCAL = "YXJL_LOCAL";
        String YXJL_VIEW = "YXJL_VIEW";
        String YXJL_DEVICE = "YXJLDevice";
        String YXJL_DEVICE_VIEW = "YXJL_DEVICE_VIEW";

        String ADD_DEVICE = "AddDevice";

        String QXGL_LIST = "QXGLList";
        String QXGL_EDIT = "QXGL_EDIT";         //缺陷管理编辑页面
        String QXGL_APPOVE = "QXGL_APPROVE";    //缺陷管理审批页面，可以编辑
        String QXGL_LOACL = "QXGL_LOACL";       //缺陷管理本地离线编辑
        String QXGL_VIEW = "QXGL_VIEW";         //缺陷管理查看页面

        String WXGD_LIST = "WXGDList";  //维修工单列表
        String WXGD_DISPATCHER = "WXGD_DISPATCHER";//维修工单派单
        String WXGD_RECEIVE = "WXGD_RECEIVE";//维修工单接单
        String WXGD_EXECUTE = "WXGD_EXECUTE";//维修工单执行
        String WXGD_ACCEPTANCE = "WXGD_ACCEPTANCE";//维修工单验收
        String WXGD_WARN = "WXGD_WARN";//预警工单pt

        String WXGD_REPAIR_STAFF_LIST = "WXGD_REPAIR_STAFF_LIST";//维修工单维修人员
        String WXGD_ACCEPTANCE_LIST = "WXGD_ACCEPTANCE_LIST";//维修工单验收
        String WXGD_SPARE_PART_LIST = "WXGD_SPARE_PART_LIST";//维修工单备件
        String WXGD_LUBRICATE_OIL_LIST = "WXGD_LUBRICATE_OIL_LIST";//维修工单润滑油
        String WXGD_MAINTENANCE_STAFF_LIST = "WXGD_MAINTENANCE_STAFF_LIST";//维修工单润维保

        String YHGL_SPARE_PART_LIST = "WXGD_SPARE_PART_LIST";//隐患管理备件
        String YHGL_LUBRICATE_OIL_LIST = "WXGD_LUBRICATE_OIL_LIST";//隐患管理润滑油
        String YHGL_REPAIR_STAFF_LIST = "YHGL_REPAIR_STAFF_LIST";//隐患管理润维修人员
        String YHGL_MAINTENANCE_STAFF_LIST = "YHGL_MAINTENANCE_STAFF_LIST";//隐患管理润维保

        String SJSC = "SJSC";
        String SJXZ = "SJXZ";

        String RH = "RH";
        String BY = "BY";

        String SBDA_ONLINE_LIST = "SBDA_ONLINE_LIST";            //设备档案在线列表页面
        String SBDA_ONLINE_VIEW = "SBDA_ONLINE_VIEW";            //设备档案在线详细信息界面
        String SCORE = "SCORE";            //评分
        String SPARE_PART_LEDGER = "SPARE_PART_LEDGER";            //备件台账

        String SBDA_LIST = "SBDAList";            //设备档案列表页面
        String SBDA_VIEW = "SBDAListView";    //设备档案列表点击进入详细信息界面
        //        String SBDA_SEARCH_STAFF = "SDBASearchContact"; //搜索联系人列表
        String COMMON_SEARCH = "COMMON_SEARCH";

        //参照
        String SPARE_PART_REF = "SPARE_PART_REF";//备件参照选择列表
        String LUBRICATE_REF = "LUBRICATE_REF";//润滑参照选择列表
        String MAINTAIN_REF = "MAINTAIN_REF";//维保参照选择列表

        String SBDA_SEARCH_DEPARTMENT = "SBDA_SEARCH_DEPARTMENT";
        String COMMON_FILETER_SEARCH = "CommonFilterSearchActivity";

        String XJITEM_LIST = "XJITEM_LIST";
        String XJITEM_LIST_UNHANDLED = "XJITEM_LIST_UNHANDLED";
        String XJITEM_LIST_HANDLED = "XJITEM_LIST_HANDLED";
        String HSCEMENT_XJITEM_LIST = "HSCEMENT_XJITEM_LIST";

        String XJ_QXGL_LIST = "XJ_QXGL_LIST";

        String YH_LIST = "YH_LIST";
        String OFFLINE_YH_LIST = "OFFLINE_YH_LIST";
        String YH_EDIT = "YH_EDIT";
        String OFFLINE_YH_EDIT = "OFFLINE_YH_EDIT";
        String YH_VIEW = "YH_VIEW";

        String SEARCH_DEPARTMENT = "SEARCH_DEPARTMENT";

        String TD = "TD";
        String SD = "SD";

        String BJSQ_LIST = "BJSQ_LIST";
        String XJLX_LIST = "XJLX_LIST";
        String XJQY_LIST = "XJQY_LIST";
        String XJBB = "XJBB";

        String LSXJ_LIST = "LSXJ_LIST";
        String JHXJ_LIST = "JHXJ_LIST";
        String JHXJ_LX_LIST = "JHXJ_LX_LIST";
        String OLXJ_WORK_LIST_UNHANDLED = "OLXJ_WORK_LIST_UNHANDLED";
        String OLXJ_WORK_LIST_HANDLED = "OLXJ_WORK_LIST_HANDLED";

        String STOP_POLICE = "STOP_POLICE";//停机报警
        String SPARE_EARLY_WARN = "SPARE_EARLY_WARN";//备件更换预警
        String LUBRICATION_EARLY_WARN = "LUBRICATION_EARLY_WARN";//润滑预警
        String DAILY_LUBRICATION_EARLY_WARN = "DAILY_LUBRICATION_EARLY_WARN";//日常润滑预警
        String DAILY_LUBRICATION_EARLY_PART_WARN = "DAILY_LUBRICATION_EARLY_PART_WARN";//日常润滑部位
        String DAILY_LUBRICATION_EARLY_PART_ENSURE_WARN = "DAILY_LUBRICATION_EARLY_PART_ENSURE_WARN";//日常润滑部位确认
        String MAINTENANCE_EARLY_WARN = "MAINTENANCE_EARLY_WARN";//维保预警
        String DELAYDIALOG = "DELAYDIALOG";//延期弹出框
        String DELAY_RECORD = "DELAY_RECORD";//延期记录
        String GENERATE_WORK_DIALOG = "GENERATE_WORK_DIALOG";//派单弹出框


        String SCORE_EAM_LIST = "SCORE_EAM_LIST";//设备评分绩效列表
        String SCORE_EAM_PERFORMANCE = "SCORE_EAM_PERFORMANCE";//评分绩效
        String SCORE_INSPECTOR_STAFF_LIST = "SCORE_INSPECTOR_STAFF_LIST";//巡检人员列表
        String SCORE_MECHANIC_STAFF_LIST = "SCORE_MECHANIC_STAFF_LIST";//机修工列表
        String SCORE_INSPECTOR_STAFF_PERFORMANCE = "SCORE_INSPECTOR_STAFF_PERFORMANCE";//人员评分绩效
        String SCORE_MECHANIC_STAFF_PERFORMANCE = "SCORE_MECHANIC_STAFF_PERFORMANCE";//机修工评分绩效

        String EAM = "EAM";//设备搜索
        String STAFF = "STAFF";//人员搜索
    }


    interface IntentKey {
        String LOGIN_INVALID = "loginInvalid";
        String LOGIN_LOGO_ID = "LOGIN_LOGO_ID";
        String LOGIN_BG_ID = "LOGIN_BG_ID";
        String FIRST_LOGIN = "FIRST_LOGIN";
        String MODULE = "MODULE";
        String URL = "URL";
        String DEPLOYMENT_ID = "DEPLOYMENT_ID";

        String SBDA_ONLINE_EAMCODE = "SBDA_ONLINE_EAMCODE";
        String SBDA_ONLINE_EAMID = "SBDA_ONLINE_EAMID";
        String SBDA_ENTITY = "sbdaEntitiy";
        String SBDA_ENTITY_ID = "sbdaEntitiyId";
        String YXJL_ENTITY = "yxjlEntity";
        String QXGL_ENTITY = "qxglEntity";
        String YHGL_ENTITY = "yhglEntity";
        String DEVICE_ENTITY = "deviceEntity";

        String XJPATH_ENTITY = "XJPATH_ENTITY";
        String XJAREA_ENTITY = "XJAREA_ENTITY";
        String XJPATH_STATUS = "XJPATH_STATUS";

        String DOWNLOAD_MODULES = "DOWNLOAD_MODULES";
        String DOWNLOAD_VISIBLE = "DOWNLOAD_VISIBLE";

        String IS_MULTI = "IS_MULTI";
        String BASE_SEARCH_LIST = "BASE_SEARCH_LIST";

        String WXGD_ENTITY = "WXGD_ENTITY";
        String COMMON_SAERCH_MODE = "COMMON_SAERCH_MODE";//通用搜索界面搜索模式设置
        String COMMON_SEARCH_TAG = "COMMON_SEARCH_TAG";//通过tag来区别搜索请求从哪个组件发出的

        String REPAIR_STAFF_ENTITIES = "REPAIR_STAFF_ENTITIES";
        String ACCEPTANCE_ENTITIES = "ACCEPTANCE_ENTITIES";
        String LUBRICATE_OIL_ENTITIES = "LUBRICATE_OIL_ENTITIES";
        String MAINTENANCE_ENTITIES = "MAINTENANCE_ENTITIES";
        String SPARE_PART_ENTITIES = "SPARE_PART_ENTITIES";
        String IS_EDITABLE = "IS_EDITABLE";
        String IS_ADD = "IS_ADD";
        String REPAIR_SUM = "REPAIR_SUM";  //工单执行次数

        String IS_ENTITY_CODE = "IS_ENTITY_CODE";//系统编码模式
        String IS_AREA = "IS_AREA";//区域模式
        String IS_STAFF = "IS_STAFF";//人员模式
        String IS_EAM = "IS_EAM"; // 设备模式

        String SEARCH_ENTITY_FLAG = "SEARCH_ENTITY_FLAG";
        String ENTITY_CODE = "ENTITY_CODE";

        String REC_CODE = "REC_CODE";

        String TABLE_STATUS = "TABLE_STATUS";//工单状态
        String TABLE_ACTION = "TABLE_ACTION";//工单视图action

        String PROPERTY = "PROPERTY"; // 数据库字段属性

        String LIST_ID = "LIST_ID"; // 工单ID
        String IS_SPARE_PART_REF = "IS_SPARE_PART_REF"; // 备件参照清单

        String EAM_ID = "EAM_ID"; // 设备ID
        String XJ_YHGL_ENTITY = "XJ_YHGL_ENTITY"; //巡检隐患
        String COMMON_SEARCH_DEFAULT_SEARCH_VALUE = "COMMON_SEARCH_DEFAULT_SEARCH_VALUE";

        String FILTER_SEARCH_TYPE = "FILTER_SEARCH_TYPE";
        String PARAM_MAP = "PARAM_MAP";
        String FILTER_SEARCH_PARAM = "FILTER_SEARCH_PARAM";


        String XJ_TASK_ID = "XJ_TASK_ID";
        String XJ_GROUP_ID = "XJ_GROUP_ID";
        String XJ_WORKLIST_VIEW_TYPE = "XJ_WORKLIST_VIEW_TYPE";

        String XJ_IS_TEMP = "XJ_IS_TEMP";
        String XJ_TASK_ENTITY = "XJ_TASK_ENTITY";
        String XJ_AREA_ENTITY = "XJ_AREA_ENTITY";
        String SPARE_PART_LEDGER_ID = "SPARE_PART_LEDGER_ID";

        String IS_XJ_FINISHED = "IS_XJ_FINISHED";

        String IS_FROM_PENDING = "IS_FROM_PENDING";
        String PENDING_ENTITY = "";

        String WARN_NEXT_TIME = "WARN_NEXT_TIME";//下次运行时间
        String WARN_SOURCE_TYPE = "WARN_SOURCE_TYPE";//延期来源  润滑BEAM062/01,备件BEAM062/03,维保BEAM062/02
        String WARN_SOURCE_IDS = "WARN_SOURCE_IDS";//延期ids
        String WARN_PEROID_TYPE = "WARN_PEROID_TYPE";//周期类型
        String WARN_SOURCE_URL = "WARN_SOURCE_URL";//URL

        String EAM_CODE = "EAM_CODE";//EAM_CODE
        String AREA_NAME = "AREA_NAME";//AREA_NAME
        String isEdit = "isEdit";//是否能编辑

        String SCORE_ENTITY = "SCORE_ENTITY";//评分记录
    }

    interface FilterSearchParam {
        String AREA_ID = "AREA_ID";
        String DEVICE_BLUR = "DEVICE_BLUR";
    }


    interface WorkType {//设备功能类型

        int SBDA = 1;  //设备档案
        int XJGL = 2;  //巡检管理
        int YXJL = 3;  //运行记录
        //        int QXGL = 4;  //缺陷管理
        int YHGL = 4;  //隐患管理
        int WXGD = 5;  //维修工单
        int RH = 6;  //缺陷管理
        int BY = 7;  //维修工单
        int LXYH = 8;//离线隐患
        int TD = 9;//停电
        int SD = 10;//送电

        int MORE = 99;  //更多
        int SJXZ = 11;  //数据下载
        int SJSC = 12;  //数据上传

        int JHXJ = 13;  //计划巡检
        int LSXJ = 14;  //临时巡检
        int BJSQ = 15;  //备件申请
        int XJLX = 16;  //巡检路线
        int XJQY = 17;  //巡检区域
        int XJBB = 18;  //巡检报表
        int SBDA_ONLINE = 19;  //设备档案
        int STOP_POLICE = 20;  //停机报警
        int DXJH = 22;
        int JXJH = 21;
        int SPARE_EARLY_WARN = 23;//备件更换预警
        int LUBRICATION_EARLY_WARN = 24;//润滑预警
        int DAILY_LUBRICATION_EARLY_WARN = 25;//日常润滑预警
        int MAINTENANCE_EARLY_WARN = 26;//维保预警
        int SCORE_EAM_LIST = 27;//设备评分
        int SCORE_INSPECTOR_PERSON_LIST = 28;//巡检人员评分
        int SCORE_MECHANIC_PERSON_LIST = 29;//巡检人员评分
    }

    interface SPKey {
        String WORKS = "works";
        String STAFF = "STAFF";

        String DEVICE_TOKEN = "DEVICE_TOKEN";

        String RUN_STATES_ON = "RUN_STATES_ON";
        String RUN_STATES_OFF = "RUN_STATES_OFF";
        String RECENT_DEVICES = "RECENT_DEVICES";
        String RECENT_DEVICES_STR = "RECENT_DEVICES_STR";

        //基本信息参数
        String FAULT_TYPE = "FAULT_TYPE";
        String DEAL_TYPE = "DEAL_TYPE";
        String RUNNING_STATE_PARAM = "RUNNING_STATE_PARAM";
        String STATE_TYPE = "STATE_TYPE";

        String IS_AUTO_REPAIR = "IS_AUTO_REPAIR";

        //下载信息缓存
        String DOWNLOAD_XJ_BASE = "DOWNLOAD_XJ_BASE";
        String DOWNLOAD_XJ = "DOWNLOAD_XJ";
        String DOWNLOAD_EAM_BASE = "DOWNLOAD_EAM_BASE";
        String DOWNLOAD_EAM_DEVICE = "DOWNLOAD_EAM_DEVICE";

        //上传信息缓存
        String UPLOAD_XJ = "UPLOAD_XJ";
        String UPLOAD_QX = "UPLOAD_QX";

        String UPLOAD_YH = "UPLOAD_YH";

        String EAM_DEVICE_NEED_DOWNLOAD = "EAM_DEVICE_NEED_DOWNLOAD";


        String JHXJ_TASK_CONTENT = "JHXJ_TASK_CONTENT";
        String LSXJ_TASK_CONTENT = "LSXJ_TASK_CONTENT";

        String JHXJ_TASK_ENTITY = "JHXJ_TASK_ENTITY";
        String LSXJ_TASK_ENTITY = "LSXJ_TASK_ENTITY";
    }


    interface Date {

        String TODAY = "今天";
        String YESTERDAY = "昨天";
        String TOMORROW = "明天";
        String THREEDAY = "后三天";
        String THREE_DAY = "三天内";
        String THIS_WEEK = "本周";
        String THIS_MONTH = "本月";
        String ALL = "全部";
    }

    interface Transition {    //工作流相关流程
        String SAVE = "save";       //保存\保存按钮
        String CANCEL = "cancel";   //作废
        String REJECT = "reject";   //驳回
        String SUBMIT = "submit";   //提交
        String SAVE_LOCAL = "saveLocal";    //保存到本地
        String SUBMIT_LOCAL = "submitLocal";    //提交到本地， 进行完整性检查
        String TRANSITION_MORE = "transitionMore";  //更多
        String SUBMIT_BTN = "submitBtn"; //提交按钮
        String ROUTER_BTN = "routerBtn"; //操作按钮
        String SAVE_BTN = "saveBtn";
        String CANCEL_CN = "作废";   //作废
        String REJECT_CN = "驳回";   //驳回
    }

    interface OperateType { //操作类型， 保存 / 提交
        String SAVE = "save";   //保存
        String SUBMIT = "submit";   //提交

        String EDIT = "edit";       //编辑
        String APPROVE = "approve"; //审批
    }

    interface XJPathStateType {
        String WAIT_CHECK_STATE = "待检";
        String PAST_CHECK_STATE = "已检";
    }

    interface TimeString {
        String START_TIME = " 00:00:00";
        String END_TIME = " 23:59:59";
    }

    interface PicType {
        String YHLR_PIC = "createRecord";
        String YHFC_PIC = "reviewRecord";
        String XJ_PIC = "xjRecord";
        String YH_PIC = "yhRecord";
    }

    interface RefreshAction {
        String XJ_WORK_END = "XJ_WORK_END";
        String XJ_WORK_REINPUT = "XJ_WORK_REINPUT";
    }

    interface BAPQuery {
        String VALUE = "VALUE";
        String BILL_STATE = "BILL_STATE";

        String BATCH_TEXT = "BATCH_TEXT";
        String BATCH_NUM = "BATCH_NUM";
        String ID = "ID";
        String NAME = "NAME";
        String SPECIFY = "SPECIFY";
        String PRODUCT_ID = "PRODUCT_ID";
        String PRODUCT_CODE = "PRODUCT_CODE";
        String PRODUCT_NAME = "PRODUCT_NAME";
        String PRODUCT_SPECIF = "PRODUCT_SPECIF";
        String WARE_CODE = "WARE_CODE";
        String WARE_NAME = "WARE_NAME";
        String PLACE_SET_CODE = "PLACE_SET_CODE";
        String PLACE_SET_NAME = "PLACE_SET_NAME";
        String TABLE_NO = "TABLE_NO";

        String ORDER_DATE = "ORDER_DATE";
        String FIND_TIME = "FIND_TIME";
        String YH_DATE_START = "YH_DATE_START";
        String YH_DATE_END = "YH_DATE_END";
        String YH_AREA = "YH_AREA";
        String REPAIR_TYPE = "REPAIR_TYPE";
        String PRIORITY = "PRIORITY";
        String STATUS = "STATUS";
        String FAULT_INFO_TYPE = "FAULT_INFO_TYPE";
        String EAM_NAME = "EAM_NAME";
        String LINK_STATE = "LINK_STATE";

        String XSCK_DATE_START = "XSCK_DATE_START";
        String XSCK_DATE_END = "XSCK_DATE_END";
        String OUT_STORAGE_DATE = "OUT_STORAGE_DATE";

        String TEXT = "TEXT";
        String SYSTEMCODE = "SYSTEMCODE";
        String BAPCODE = "BAPCODE";
        String BOOLEAN = "BOOLEAN";
        String LONG = "LONG";
        String DATETIME = "DATETIME";
        String DATE = "DATE";
        String TYPE_NORMAL = "0";
        String TYPE_JOIN = "2";
        String LIKE = "like";
        String BE = "=";
        String GE = ">=";
        String LE = "<=";
        String LIKE_OPT_BLUR = "%?%";
        String LIKE_OPT_Q = "?";

        String OPT_YH_AREA = "=includeCustSub#BEAM_AREAS";

        /*维修工单快速查询字段条件*/
        String WORK_SOURCE = "WORK_SOURCE";//工单来源
        String WXGD_REPAIR_TYPE = "WXGD_REPAIR_TYPE"; // 维修类型（维修工单列表查询）
        String WXGD_PRIORITY = "WXGD_PRIORITY"; // 优先级（维修工单列表查询）
        String WORK_STATE = "WORK_STATE"; // 工作流状态（维修工单列表查询）

        /*备件参照快速查询*/
        String BEAM_SPARE_PARTS = "BEAM_SPARE_PARTS";

        String EAM_EXACT_CODE = "EAM_EXACT_CODE";//精确设备编码
        String EAM_CODE = "EAM_CODE";//设备编码
        String EAM_STATE = "EAM_STATE";//设备状态
        String EAM_TYPE = "EAM_TYPE";//设备类型
        String EAM_AREA = "EAM_AREA";//区域类型
        String EAM_AREANAME = "EAM_AREANAME";//区域类型主设备
        String IS_MAIN_EQUIP = "IS_MAIN_EQUIP";//是否主设备

        String CONTENT = "CONTENT";//内容

        String OPEN_TIME_START = "OPEN_TIME_START";//时间
        String OPEN_TIME_STOP = "OPEN_TIME_STOP";//时间
        String OPEN_TIME = "OPEN_TIME";//时间
        String ON_OR_OFF = "ON_OR_OFF";//开关状态

        String sourceIds = "sourceIds";
        String sourceType = "sourceType";
        String delayDate = "delayDate";
        String delayReason = "delayReason";
        String peroidType = "peroidType";

        String startDate = "startDate";
        String endDate = "endDate";
        String repairGroupId = "repairGroupId";

        String SCORE_TIME_START = "SCORE_TIME_START";//时间
        String SCORE_TIME_STOP = "SCORE_TIME_STOP";//时间
        String SCORE_TIME = "SCORE_TIME";//时间

        String SCORE_DATA_START = "SCORE_DATA_START";//时间
        String SCORE_DATA_STOP = "SCORE_DATA_STOP";//时间
        String SCORE_DATA = "SCORE_DATA";//时间
    }


    interface SystemCode {
        //巡检系统编码
        String XJ_TYPE = "mobileEAM001";
        String REAL_VALUE = "realValue";
        String WILINK_STATE = "wiLinkState";
        String PASS_REASON = "passReason";
        String CART_REASON = "cartReason";
        String CARD_TYPE = "cardType";
        String MOBILE_EAM001 = "mobileEAM001";
        String MOBILE_EAM055 = "mobileEAM055";
        String MOBILE_EAM054 = "mobileEAM054";
        String LINK_STATE = "LinkState";

        //隐患系统编码
        String QX_TYPE = "BEAM029";
        String YH_STATE = "BEAM2004";
        String YH_WX_TYPE = "BEAM2005"; // 维修类型
        String YH_SOURCE = "BEAM2006";
        String YH_PRIORITY = "BEAM2007"; //优先级

        //维修工单
        String WXGD_SOURCE = "BEAM2003";// 工单来源
        String OIL_TYPE = "BEAM61"; // 加/换油
        String CHECK_RESULT = "BEAM033"; // 验收结论

    }

    /**
     * 维修工单单据状态
     */
    interface WxgdStatus {
        String DISPATCH = "BEAM049/01";
        String CONFIRM = "BEAM049/02";
        String IMPLEMENT = "BEAM049/03";
        String ACCEPTANCE = "BEAM049/04";
    }

    /**
     * 维修工单单据状态
     */
    interface WxgdStatus_CH {
        String DISPATCH = "待派工";
        String CONFIRM = "待接收";
        String IMPLEMENT = "待执行";
        String ACCEPTANCE = "待验收";
    }

    interface WxgdWorkSource {
        String patrolcheck = "BEAM2003/01";//巡检
        String lubrication = "BEAM2003/02";//润滑
        String maintenance = "BEAM2003/03";//维保
        String sparepart = "BEAM2003/04";//备件
        String other = "BEAM2003/05";//其它
    }

    interface WxgdWorkSource_CN {
        String patrolcheck = "巡检";//巡检
        String lubrication = "润滑";//润滑
        String maintenance = "维保";//维保
        String sparepart = "备件";//备件
        String other = "其它";//其它

    }

    // 隐患维修类型
    interface YHWXType {

        String DX = "大修";
        String JX = "检修";
        String RC = "日常";

        String DX_SYSCODE = "BEAM2005/03";
        String JX_SYSCODE = "BEAM2005/02";
        String RC_SYSCODE = "BEAM2005/01";

    }

    interface CommonSearchMode {
        String STAFF = "STAFF";
        String AREA = "AREA";
        String DEPARTMENT = "DEPARTMENT";
        String SYSTEM_CODE = "SYSTEM_CODE";
        String INITIAL_VALUE = "INITIAL_VALUE";
        String EAM = "EAM";
    }

    //维修工单执行状态
    interface WxgdAction {

        String STOP = "BEAM2008/01";

        String ACTIVATE = "BEAM2008/02";
    }

    interface WxgdView {
        String DISPATCH_OPEN_URL = "/BEAM2/workList/workRecord/workEdit.action";//派单
        String RECEIVE_OPEN_URL = "/BEAM2/workList/workRecord/workReceiptEdit.action";//接单
        String EXECUTE_OPEN_URL = "/BEAM2/workList/workRecord/workExecuteEdit.action";//执行
        String STOP_OPEN_URL = "/BEAM2/workList/workRecord/workExecuteEdit.action";
        String ACCEPTANCE_OPEN_URL = "/BEAM2/workList/workRecord/workCheckEdit.action";//验收
    }

    /**
     * 周期类型
     */
    interface PeriodType {
        String TIME_FREQUENCY = "BEAM014/01"; // 时间频率
        String RUNTIME_LENGTH = "BEAM014/02"; // 运行时长
    }

    /**
     * 备件领用状态
     */
    interface SparePartUseStatus {
        String NO_USE = "BEAM2011/01"; // 未领用
        String USEING = "BEAM2011/02"; // 领用中
        String UESED = "BEAM2011/03"; // 已领用
        String PRE_USE = "BEAM2011/04"; // 预领用
    }

    /**
     * 模块授权
     */
    interface ModuleAuthorization {
        String BEAM2 = "BEAM2";       // 设备模块
        String mobileEAM = "mobileEAM";  // 移动设备巡检
        String WOM = "";        // 工单
    }

    interface WebUrl {

        String TD_LIST = "/BEAMEle/onOrOff/onoroff/eleOffList.action?__pc__=QkVBTUVsZV8xLjAuMF9vbk9yT2ZmX2VsZU9mZkxpc3Rfc2VsZnw_&workFlowMenuCode=BEAMEle_1.0.0_onOrOff_eleOnList&openType=page&clientType=mobile";
        String SD_LIST = "/BEAMEle/onOrOff/onoroff/eleOnList.action?__pc__=QkVBTUVsZV8xLjAuMF9vbk9yT2ZmX2VsZU9uTGlzdF9zZWxmfA__&workFlowMenuCode=BEAMEle_1.0.0_onOrOff_eleOffList&openType=page&clientType=mobile";
        String SCORE = "/BEAM/scoreStandard/soring/eamScoreView.action?clientType=mobile&eamID=";
        String XJ = "/mobileEAM/work/work/pointsList.action?taskId=";
    }

    interface ZZ {
        String IP = "IP";
        String PORT = "PORT";
    }

    interface AttrMap {
        String SCORE_FRACTION = "BEAM_1_0_0_scoreStandard_itemDetailList_LISTPT_ASSO_8395ad9a_dea0_4fb2_aad1_78cb7ef73cea";
        String SCORE_ITEM = "BEAM_1_0_0_scoreStandard_itemDetailList_LISTPT_ASSO_3e07e500_557f_4b41_9c6b_1396785b8113";
    }
}
