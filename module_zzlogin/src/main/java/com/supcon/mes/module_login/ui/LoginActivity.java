package com.supcon.mes.module_login.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.app.annotation.BindByTag;
import com.app.annotation.Presenter;
import com.app.annotation.apt.Router;
import com.bluetron.rxretrohttp.exception.ApiException;
import com.bluetron.zhizhi.domain.event.login.LogOutEventSDK;
import com.bluetron.zhizhi.domain.event.login.LoginEventSDK;
import com.bluetron.zhizhi.home.presentation.HomeSDK;
import com.bluetron.zhizhi.login.presentation.LoginUserSDK;
import com.jakewharton.rxbinding2.view.RxView;
import com.jdjz.coresdk14.ZZApp;
import com.supcon.common.view.base.activity.BasePresenterActivity;
import com.supcon.common.view.util.LogUtil;
import com.supcon.common.view.util.SharedPreferencesUtils;
import com.supcon.common.view.util.ToastUtils;
import com.supcon.mes.mbap.MBapApp;
import com.supcon.mes.mbap.MBapConstant;
import com.supcon.mes.mbap.beans.LoginEvent;
import com.supcon.mes.mbap.utils.PatternUtil;
import com.supcon.mes.mbap.utils.StatusBarUtils;
import com.supcon.mes.mbap.view.CustomEditText;
import com.supcon.mes.mbap.view.CustomTextView;
import com.supcon.mes.middleware.EamApplication;
import com.supcon.mes.middleware.constant.Constant;
import com.supcon.mes.middleware.constant.DataModule;
import com.supcon.mes.middleware.model.bean.AccountInfo;
import com.supcon.mes.middleware.model.bean.AccountInfoDao;
import com.supcon.mes.middleware.model.bean.BapResultEntity;
import com.supcon.mes.middleware.model.bean.ModuleAuthorization;
import com.supcon.mes.middleware.model.bean.ModuleAuthorizationListEntity;
import com.supcon.mes.middleware.util.ChannelUtil;
import com.supcon.mes.middleware.util.ErrorMsgHelper;
import com.supcon.mes.middleware.util.SnackbarHelper;
import com.supcon.mes.middleware.util.Util;
import com.supcon.mes.module_login.BuildConfig;
import com.supcon.mes.module_login.IntentRouter;
import com.supcon.mes.module_login.R;
import com.supcon.mes.module_login.model.api.LoginAPI;
import com.supcon.mes.module_login.model.api.MineAPI;
import com.supcon.mes.module_login.model.api.ZhiZhiUrlQueryAPI;
import com.supcon.mes.module_login.model.bean.LicenseEntity;
import com.supcon.mes.module_login.model.bean.LoginEntity;
import com.supcon.mes.module_login.model.contract.LoginContract;
import com.supcon.mes.module_login.model.contract.MineContract;
import com.supcon.mes.module_login.model.contract.ZhiZhiUrlQueryContract;
import com.supcon.mes.module_login.presenter.LoginPresenter;
import com.supcon.mes.module_login.presenter.MinePresenter;
import com.supcon.mes.module_login.presenter.ZhiZhiUrlQueryPresenter;
import com.supcon.mes.module_login.service.HeartBeatService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wangshizhan on 2017/8/11.
 */

@Router(Constant.Router.LOGIN)
@Presenter(value = {LoginPresenter.class, ZhiZhiUrlQueryPresenter.class, MinePresenter.class})
public class LoginActivity extends BasePresenterActivity implements LoginContract.View , ZhiZhiUrlQueryContract.View, MineContract.View {


    @BindByTag("usernameInput")
    CustomEditText usernameInput;

    @BindByTag("pwdInput")
    CustomEditText pwdInput;

    @BindByTag("loginLogo")
    ImageView loginLogo;

    @BindByTag("loginBg")
    RelativeLayout loginBg;

    @BindByTag("buildVersion")
    CustomTextView buildVersion;

    private boolean isFirstIn = false;
    private boolean loginInvalid;

    private int loginLogoId = 0;
    private int loginBgId = 0;

    @Override
    protected int getLayoutID() {
        return R.layout.ac_login;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //无title
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //全屏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onInit() {
        super.onInit();
        EventBus.getDefault().register(this);
        loginInvalid = getIntent().getBooleanExtra(Constant.IntentKey.LOGIN_INVALID, false);
        isFirstIn = getIntent().getBooleanExtra(Constant.IntentKey.FIRST_LOGIN, false);
        loginLogoId = getIntent().getIntExtra(Constant.IntentKey.LOGIN_LOGO_ID, 0);
        loginBgId = getIntent().getIntExtra(Constant.IntentKey.LOGIN_BG_ID, 0);

//        String channel = ChannelUtil.getUMengChannel();
//        String port = " ";
//        String ip  = SharedPreferencesUtils.getParam(getApplicationContext(), MBapConstant.SPKey.IP, "");
//        LogUtil.d("channel:"+channel+" ip:"+ip);
//        if(TextUtils.isEmpty(ip)){
//
//            if (channel.equals("hongshi")) {
//                ip = "218.75.97.170";
//                port = "8181";
//            }
//            else if(channel.equals("hailuo")){
//                ip = "60.167.69.246";
//                port = "8099";
//            }
//            else if(channel.equals("dev")){
//                ip = "192.168.90.215";
//                port = "8080";
//            }
//            else{
//                ip = "192.168.90.134";
//                port = "8080";
//            }
//            EamApplication.setIp(ip);
//            EamApplication.setPort(port);
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initListener() {
        super.initListener();
        RxView.clicks(findViewById(R.id.loginBtn))
                .throttleFirst(2, TimeUnit.SECONDS)   //两秒钟之内只取一个点击事件，防抖操作
                .subscribe(o -> {
                    //如果返回结果为false说明存在格式上的错误
                    if (!checkInput()) {
                        return;
                    }

                    if (TextUtils.isEmpty(SharedPreferencesUtils.getParam(context, MBapConstant.SPKey.IP, ""))) {
                        SnackbarHelper.showError(rootView, "必须设置服务器IP地址!");
                        return;
                    }

                    onLoading("正在登陆...");
                    if(!TextUtils.isEmpty(ZZApp.getZzUrl())){
                        doZhiZhiLogin();
                    }
                    else{
                        presenterRouter.create(ZhiZhiUrlQueryAPI.class).getZhizhiUrl();
                    }

                });


        RxView.clicks(findViewById(R.id.loginSettingLayout))
                .throttleFirst(2, TimeUnit.SECONDS)   //两秒钟之内只取一个点击事件，防抖操作
                .subscribe(o -> IntentRouter.go(this, Constant.Router.SETTING)
                );
    }

    @Override
    protected void initView() {
        super.initView();
        setSwipeBackEnable(false);
        StatusBarUtils.setWindowStatusBarColor(this, R.color.transparent);


        usernameInput.setInputType(InputType.TYPE_CLASS_TEXT);
        pwdInput.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        pwdInput.editText().setTransformationMethod(PasswordTransformationMethod.getInstance());
        usernameInput.setInput(MBapApp.getUserName());
        pwdInput.setInput(MBapApp.getPassword());

        if (loginLogoId != 0)
            loginLogo.setImageResource(loginLogoId);

        if (loginBgId != 0) {
            loginBg.setBackgroundResource(loginBgId);
        }

        StringBuilder versionName = new StringBuilder();
        versionName.append(" V");
        versionName.append(BuildConfig.VERSION_NAME);
        versionName.append(BuildConfig.DEBUG ? "(debug)" : "");

        buildVersion.setValue(versionName.toString());
    }

    public void doZhiZhiLogin() {
        String ip = SharedPreferencesUtils.getParam(ZZApp.getAppContext(), Constant.ZZ.IP, "");
        String port = SharedPreferencesUtils.getParam(ZZApp.getAppContext(), Constant.ZZ.PORT, "");
        String url = SharedPreferencesUtils.getParam(context, Constant.ZZ.URL, "");
        String userName = usernameInput.getContent();
        String pwd = pwdInput.getContent();

        if(TextUtils.isEmpty(url)){
            LogUtil.d("zhizhi login ip:" + ip + " port:" + port + " userName:" + userName + " pwd:" + pwd);
            LoginUserSDK.getInstance().userLogin(TextUtils.isEmpty(userName) ? "admin" : userName, TextUtils.isEmpty(pwd) ? "Supos1304@" : pwd, "http://" + ip + ":" + port + "/");
        }
        else{
            LogUtil.d("zhizhi login url:" + url + " userName:" + userName + " pwd:" + pwd);

            LoginUserSDK.getInstance().userLogin(TextUtils.isEmpty(userName) ? "admin" : userName, TextUtils.isEmpty(pwd) ? "Supos1304@" : pwd, url+"/");

        }
    }

    //登录,登出,获取minappist时出错返回的内容和code
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(ApiException ex) {
        ToastUtils.show(context, ex.getCode() + " " + ex.getMessage());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(LoginEventSDK ex) {
        LogUtil.d("supos平台登录成功！");
        HomeSDK.getInstance().getMinppListSDK();
        String token1 = LoginUserSDK.getInstance().getOSToken();
        LogUtil.e("zhizhi token1:" + token1);
//        ToastUtils.show(context, "zhizhi token:"+token1);
        presenterRouter.create(LoginAPI.class).dologinWithToken(usernameInput.getInput().trim(), pwdInput.getInput().trim(), token1);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(LogOutEventSDK ex) {
        LogUtil.d("supos平台登出成功");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (loginInvalid)
            finish();
        else {
            finish();

            EamApplication.exitApp();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();


        if (loginInvalid) {
            SnackbarHelper.showError(rootView, "登陆已失效，请重新登陆！");
        }


        HeartBeatService.stopLoginLoop(this);
    }

    @SuppressWarnings("all")
    private boolean checkUsername() {

        return TextUtils.isEmpty(usernameInput.getInput().trim());

    }

    @SuppressWarnings("all")
    private boolean checkPwd() {

        return TextUtils.isEmpty(pwdInput.getInput().trim());

    }

    private boolean checkInput() {
        if (TextUtils.isEmpty(usernameInput.getInput().trim())) {
            SnackbarHelper.showError(rootView, "用户名不允许为空!");
            return false;
        }
        if (!PatternUtil.checkUserName(usernameInput.getInput().trim()/*,"^[A-Za-z0-9\\u4e00-\\u9fa5]+$"*/)) {
            SnackbarHelper.showError(rootView, "用户名/密码不合法！");
            return false;
        }
        //以上只单纯检测纯格式方面的错误
        if (TextUtils.isEmpty(pwdInput.getInput().trim())) {
            SnackbarHelper.showError(rootView, "密码不允许为空!");
            return false;
        }
        //密码方面并没有特别的限制,所以这里并不特备进行验证,只进行是否为空的验证

        return true;
    }

    @Override
    public void dologinSuccess(LoginEntity userEntity) {


        //如果登陆成功, 取出用户必备信息
        if (!loginInvalid) {
            //在线模式使用心跳防止session过期
            if (!SharedPreferencesUtils.getParam(this, MBapConstant.SPKey.OFFLINE_ENABLE, false)) {
                HeartBeatService.startLoginLoop(this);
            }

            onLoading("正在检查授权...");
            StringBuilder sb = new StringBuilder("");
            sb.append(Constant.ModuleAuthorization.mobileEAM).append(",").append(Constant.ModuleAuthorization.BEAM2);
            presenterRouter.create(LoginAPI.class).getLicenseInfo(sb.toString());

        } else {
            //跳转到主页

            loaderController.showMsgAndclose("登陆成功！", true, 200, () -> {
                LoginActivity.this.finish();
                Flowable.timer(650, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(v -> {
                            EventBus.getDefault().post(new LoginEvent());
                        });
            });
        }


    }

    @Override
    public void dologinFailed(String errMsg) {
        onLoadFailed(ErrorMsgHelper.msgParse(errMsg));

    }

    @Override
    public void dologinWithTokenSuccess(LoginEntity entity) {
        dologinSuccess(entity);
    }

    @Override
    public void dologinWithTokenFailed(String errorMsg) {
        dologinFailed(ErrorMsgHelper.msgParse(errorMsg));

    }

    private void doLogout(){
        presenterRouter.create(MineAPI.class).logout();
    }

    @Override
    public void getLicenseInfoSuccess(ModuleAuthorizationListEntity entity) {

        //将授权信息放入缓存
        for (ModuleAuthorization moduleAuthorization : entity.result) {

            switch (moduleAuthorization.moduleCode) {
                case Constant.ModuleAuthorization.mobileEAM:
                    SharedPreferencesUtils.setParam(context, Constant.ModuleAuthorization.mobileEAM, true/*moduleAuthorization.isAuthorized*/);
                    break;
                case Constant.ModuleAuthorization.BEAM2:
                    SharedPreferencesUtils.setParam(context, Constant.ModuleAuthorization.BEAM2, true/*moduleAuthorization.isAuthorized*/);
                    break;
                default:
                    break;
            }
        }

        //存入数据库
        EamApplication.dao().getModuleAuthorizationDao().insertOrReplaceInTx(entity.result);

        onLoading("正在获取用户信息...");
        presenterRouter.create(LoginAPI.class).getAccountInfo(usernameInput.getInput());

    }

    @Override
    public void getLicenseInfoFailed(String errorMsg) {
        onLoadFailed(ErrorMsgHelper.msgParse(errorMsg));
        doLogout();
    }

    @Override
    public void getAccountInfoSuccess() {
//        downloadBase();
        //跳转到主页
        goMain();
    }

    @Override
    public void getAccountInfoFailed(String errorMsg) {
        if (errorMsg != null && errorMsg.contains("403")) {
            errorMsg = "获取用户信息失败，请检查用户查看权限！";
        }

        onLoadFailed(ErrorMsgHelper.msgParse(errorMsg));
        doLogout();
    }


    private void goMain() {
        MBapApp.setIsLogin(true);
        MBapApp.setUserName(usernameInput.getInput().trim());
        MBapApp.setPassword(pwdInput.getInput().trim());

        onLoadSuccessAndExit("登陆成功！", () -> {

            //跳转到主页
            if (isFirstIn) {
                IntentRouter.go(context, Constant.Router.MAIN);
                isFirstIn = false;
            }
            LoginActivity.this.finish();
        });

    }

    private void downloadBase() {
        List<String> downloadModules = new ArrayList<>();
//        downloadModules.add(DataModule.EAM_BASE.getModuelName());
//        downloadModules.add(DataModule.XJ_BASE.getModuelName());
        downloadModules.add(DataModule.EAM_DEVICE.getModuelName());
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(Constant.IntentKey.DOWNLOAD_MODULES, (ArrayList<String>) downloadModules);
        bundle.putBoolean(Constant.IntentKey.DOWNLOAD_VISIBLE, false);
        IntentRouter.go(context, Constant.Router.SJXZ, bundle);
    }

    @Override
    public void getZhizhiUrlSuccess(BapResultEntity entity) {
        if(!TextUtils.isEmpty(entity.zhizhiUrl)){
            ZZApp.setZzUrl(entity.zhizhiUrl);

        }

        doZhiZhiLogin();
    }

    @Override
    public void getZhizhiUrlFailed(String errorMsg) {
        onLoadFailed(ErrorMsgHelper.msgParse(errorMsg));
    }

    @Override
    public void logoutSuccess() {
        LogUtil.d("登出成功");
    }

    @Override
    public void logoutFailed(String errorMsg) {
        LogUtil.d("登出失败");
    }
}
