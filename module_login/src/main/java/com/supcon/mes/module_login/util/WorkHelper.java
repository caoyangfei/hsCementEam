package com.supcon.mes.module_login.util;

import android.content.Context;

import com.supcon.common.view.util.SharedPreferencesUtils;
import com.supcon.mes.mbap.MBapApp;
import com.supcon.mes.mbap.utils.GsonUtil;
import com.supcon.mes.middleware.constant.Constant;
import com.supcon.mes.middleware.util.Util;
import com.supcon.mes.module_login.R;
import com.supcon.mes.module_login.model.bean.WorkInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangshizhan on 2017/8/16.
 * Email:wangshizhan@supcon.com
 */

public class WorkHelper {

    public static List<WorkInfo> getDefaultWorkList(Context context) {

        List<WorkInfo> list = new ArrayList<>();

        List<WorkInfo> works = null;

        JSONArray worksArray = null;
        try {
            worksArray = new JSONObject(Util.getJson(MBapApp.getAppContext(), "work.json")).getJSONArray("work");
            works = GsonUtil.jsonToList(worksArray.toString(), WorkInfo.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        if (works == null) {
            String cache = SharedPreferencesUtils.getParam(context, Constant.SPKey.WORKS, "");
            works = GsonUtil.jsonToList(cache, WorkInfo.class);
        }

        for (WorkInfo workInfo : works) {

            switch (workInfo.type) {


                case Constant.WorkType.XJGL:
                    workInfo.iconResId = R.drawable.ic_work_zygl_small;
                    workInfo.router = Constant.Router.XJGL_LIST;
                    break;
                case Constant.WorkType.JHXJ:
                    workInfo.iconResId = R.drawable.ic_work_jhxj;
                    workInfo.router = Constant.Router.JHXJ_LIST;
                    break;
                case Constant.WorkType.LSXJ:
                    workInfo.iconResId = R.drawable.ic_work_lsxj;
                    workInfo.router = Constant.Router.LSXJ_LIST;
                    break;
                case Constant.WorkType.BJSQ:
                    workInfo.iconResId = R.drawable.ic_work_bjsq2;
                    workInfo.router = Constant.Router.BJSQ_LIST;
                    break;
                case Constant.WorkType.YXJL:
                    workInfo.iconResId = R.drawable.ic_work_yxjl_small;
                    workInfo.router = Constant.Router.YXJL_LIST;
                    break;
//                    case Constant.WorkType.QXGL:
//                        object.put("iconResId", R.drawable.ic_work_qxgl_small);
//                        object.put("router", Constant.Router.QXGL_LIST);
//                        break;
                case Constant.WorkType.YHGL:
                    workInfo.iconResId = R.drawable.ic_work_yh;
                    workInfo.router = Constant.Router.YH_LIST;
                    break;
                case Constant.WorkType.WXGD:
                    workInfo.iconResId = R.drawable.ic_work_wxgd;
                    workInfo.router = Constant.Router.WXGD_LIST;
                    break;
                case Constant.WorkType.SJSC:
                    workInfo.iconResId = R.drawable.ic_work_sjsc_small;
                    workInfo.router = Constant.Router.SJSC;
                    break;
                case Constant.WorkType.SJXZ:
                    workInfo.iconResId = R.drawable.ic_work_sjxz_small;
                    workInfo.router = Constant.Router.SJXZ;
                    break;
                case Constant.WorkType.RH:
                    workInfo.iconResId = R.drawable.ic_work_rh;
                    workInfo.router = Constant.Router.RH;
                    break;
                case Constant.WorkType.BY:
                    workInfo.iconResId = R.drawable.ic_work_by;
                    workInfo.router = Constant.Router.BY;
                    break;
                case Constant.WorkType.LXYH:
                    workInfo.iconResId = R.drawable.ic_work_lxyh;
                    workInfo.router = Constant.Router.OFFLINE_YH_LIST;
                    break;
                case Constant.WorkType.TD:
                    workInfo.iconResId = R.drawable.ic_work_td;
                    workInfo.router = Constant.Router.TD;
                    break;
                case Constant.WorkType.SD:
                    workInfo.iconResId = R.drawable.ic_work_sd;
                    workInfo.router = Constant.Router.SD;
                    break;

                case Constant.WorkType.SBDA:

                    workInfo.iconResId = R.drawable.ic_data_sbda;
                    workInfo.router = Constant.Router.SBDA_LIST;
                    break;
                case Constant.WorkType.SBDA_ONLINE:

                    workInfo.iconResId = R.drawable.ic_data_sbda;
                    workInfo.router = Constant.Router.SBDA_ONLINE_LIST;
                    break;
                case Constant.WorkType.STOP_POLICE:

                    workInfo.iconResId = R.drawable.ic_data_sbda;
                    workInfo.router = Constant.Router.STOP_POLICE;
                    break;
                case Constant.WorkType.XJLX:

                    workInfo.iconResId = R.drawable.ic_data_xjlx;
                    workInfo.router = Constant.Router.XJLX_LIST;
                    break;
                case Constant.WorkType.XJQY:

                    workInfo.iconResId = R.drawable.ic_data_xjqy;
                    workInfo.router = Constant.Router.XJQY_LIST;
                    break;
                case Constant.WorkType.XJBB:

                    workInfo.iconResId = R.drawable.ic_data_xjbb;
                    workInfo.router = Constant.Router.XJBB;
                    break;
                default:

                    break;
            }
            if (workInfo.isOpen) {
                list.add(workInfo);
            }
        }

        SharedPreferencesUtils.setParam(MBapApp.getAppContext(), Constant.SPKey.WORKS, works.toString());

        return list;

    }
}
