package com.mjn.libs.comm.ui.h5.fragment;

import android.net.Uri;

/**
 * 特殊协议跳转原生页面逻辑
 * Created by sld on 16/7/18.
 */
public class WebBackNative {

    /**
     * 跳转分类
     */
    private String target;
    /**
     * 用户中心跳转目标
     */
    public static final String BACK_TARGET_USER_CENTER = "usercenter";
    /**
     * 录单完成逻辑
     */
    public static final String COMPLETE = "complete";
    /**
     * 绑定银行卡
     */
    public static final String BIND_BANK = "BindingBank";

    /**
     * 解析url
     *
     * @param uri
     */
    public void parseUrl(Uri uri) {
        if (uri == null) {
            return;
        }
        target = uri.getQueryParameter("target");
        if (target == null) {
            target = "";
        } else if (target.equals(BACK_TARGET_USER_CENTER)) {
            String msg = uri.getQueryParameter("msg");
            backToView(msg);
        } else if (target.equals(COMPLETE)) {
            String url = "http" + uri.toString().substring(uri.toString().indexOf(":"));
            //Tools.Log(url);
            String pid = uri.getQueryParameter("pid");
            complete(url, pid);
        } else if (target.equals(BIND_BANK)) {
            bindBankOk();
        }
    }

    /**
     * 页面回调
     */
    private void backToView(String msg) {
        if (target.equals(BACK_TARGET_USER_CENTER)) {
            //Tools.replaceRootPushScreen(TabMain.class, null);
            //TabMain.getInstance().changeTab(TabMain.TAB_INDEX_USER);
            //if(!TextUtils.isEmpty(msg)) {
            //    Tools.showDialog("提示", msg, "确定", null, new Tools.CustomAlertBtnCallback() {
            //        @Override
            //        public void ensure() {
            //
            //        }
            //
            //        @Override
            //        public void cancel() {
            //
            //        }
            //    });
            //}
            // TODO: 2018/3/22 解开注释
        }
    }

    /**
     * 录单完成逻辑
     */
    private void complete(String url, String pid) {

    }

    /**
     * 银行卡绑定完成进入认证页面
     */
    private void bindBankOk() {

        //Tools.pushOntoAssignScreen(Auth.class, null, TabMain.class);
        // TODO: 2018/3/22 解开注释
    }
}
