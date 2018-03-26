package com.mjn.libs.comm.ui.h5.fragment;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;

import static com.mjn.libs.comm.ui.h5.fragment.Html5Fragment.CLOSE_PAGE;
import static com.mjn.libs.comm.ui.h5.fragment.Html5Fragment.RISK_RATING;
import static com.mjn.libs.comm.ui.h5.fragment.Html5Fragment.SHARE_ID;
import static com.mjn.libs.comm.ui.h5.fragment.Html5Fragment.TO_BANKCARD;
import static com.mjn.libs.comm.ui.h5.fragment.Html5Fragment.TO_HOME;
import static com.mjn.libs.comm.ui.h5.fragment.Html5Fragment.TO_SHARE;

/**
 * Created by 蓝兵 on 2018/3/22.
 * js 交互方法
 */
public class WebViewJavascriptBridge {

    Handler handler;
    Html5Fragment html5Fragment;

    public WebViewJavascriptBridge(Handler handler, Html5Fragment html5Fragment) {
        this.handler = handler;
        this.html5Fragment = html5Fragment;
    }

    /**
     * 修改标题
     */
    @JavascriptInterface
    public void openAddPerson() {
        Message msg = new Message();
        msg.obj = "添加联系人";
        handler.sendMessage(msg);
        html5Fragment.isJsBrdge = true;
    }

    /**
     * 修改标题
     */
    @JavascriptInterface
    public void finishAddPerson() {
        html5Fragment.isJsBrdge = true;
    }

    /**
     * 修改标题
     */
    @JavascriptInterface
    public void toHome() {
        handler.sendMessage(handler.obtainMessage(TO_HOME));
    }

    /**
     * 修改标题
     */
    @JavascriptInterface
    public void selectBankCard() {
        handler.sendMessage(handler.obtainMessage(TO_BANKCARD));
    }

    @JavascriptInterface
    public void closePage() {
        handler.sendMessage(handler.obtainMessage(CLOSE_PAGE));
    }

    /**
     * 修改标题
     */
    @JavascriptInterface
    public void selectPerson() {
        html5Fragment.isJsBrdge = true;
    }

    /**
     * 分享：邀请好友，需要拼接 InvitorUserId
     *
     * @param shareUrl
     * @param shareImageUrl
     * @param shareTitle
     * @param shareContent
     */
    @JavascriptInterface
    public void inviteFriends(String shareUrl, String shareImageUrl, String shareTitle, String shareContent) {
        if (!TextUtils.isEmpty(shareUrl)) {
            Message msg = new Message();
            String temp[] = {shareTitle, shareContent, shareImageUrl, shareUrl};
            msg.obj = temp;
            msg.what = SHARE_ID;
            handler.sendMessage(msg);
        }
    }

    /**
     * 分享：不需要拼接参数
     *
     * @param shareUrl
     * @param shareImageUrl
     * @param shareTitle
     * @param shareContent
     */
    @JavascriptInterface
    public void toShare(String shareUrl, String shareImageUrl, String shareTitle, String shareContent) {
        if (!TextUtils.isEmpty(shareUrl)) {
            Message msg = new Message();
            String temp[] = {shareTitle, shareContent, shareImageUrl, shareUrl};
            msg.obj = temp;
            msg.what = TO_SHARE;
            handler.sendMessage(msg);
        }
    }

    /**
     * H5页面点击跳转到固定的界面
     * toViewController
     */
    @JavascriptInterface
    public void toViewController(int id) {
        html5Fragment.skipToTable(id);
    }

    /**
     * 风险评级回调，发动广播刷新用户信息接口
     */
    @JavascriptInterface
    public void riskRating() {
        // 发送通知：刷新用户信息的通知
        Message message = Message.obtain();
        message.what = RISK_RATING;
        handler.sendMessage(message);
    }
}
