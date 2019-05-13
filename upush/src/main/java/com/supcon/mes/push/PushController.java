package com.supcon.mes.push;

import com.supcon.common.view.App;
import com.supcon.common.view.base.controller.BasePresenterController;
import com.supcon.mes.push.util.ManifestUtil;
import com.supcon.mes.push.util.PushHelper;

/**
 * Created by wangshizhan on 2019/4/30
 * Email:wangshizhan@supcom.com
 */
public class PushController extends BasePresenterController {

    @Override
    public void onInit() {
        super.onInit();
        String appKey =  ManifestUtil.getAppkeyByXML(App.getAppContext());
        String pushSecret =  ManifestUtil.getMessageSecretByXML(App.getAppContext());

        PushHelper.getInstance().init(App.getAppContext(), appKey, pushSecret);
    }
}
