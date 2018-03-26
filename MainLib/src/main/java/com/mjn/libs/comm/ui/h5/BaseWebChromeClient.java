package com.mjn.libs.comm.ui.h5;

import android.graphics.Bitmap;
import android.os.Message;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * 实现一个基础的 WebChromeClient ，如果有更多的需要，直接继承它
 */
public class BaseWebChromeClient extends WebChromeClient {

    //=========HTML5定位==========================================================
    //需要先加入权限
    //<uses-permission android:name="android.permission.INTERNET"/>
    //<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
    //<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
    @Override
    public void onReceivedIcon(WebView view, Bitmap icon) {
        super.onReceivedIcon(view, icon);
    }

    @Override
    public void onGeolocationPermissionsHidePrompt() {
        super.onGeolocationPermissionsHidePrompt();
    }

    @Override
    public void onGeolocationPermissionsShowPrompt(final String origin, final GeolocationPermissions.Callback
            callback) {
        callback.invoke(origin, true, false);//注意个函数，第二个参数就是是否同意定位权限，第三个是是否希望内核记住
        super.onGeolocationPermissionsShowPrompt(origin, callback);
    }
    //=========HTML5定位==========================================================

    //=========多窗口的问题==========================================================
    @Override
    public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
        WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
        transport.setWebView(view);
        resultMsg.sendToTarget();
        return true;
    }
    //=========多窗口的问题==========================================================
}