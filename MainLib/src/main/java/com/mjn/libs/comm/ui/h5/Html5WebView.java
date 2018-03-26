package com.mjn.libs.comm.ui.h5;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.io.File;

public class Html5WebView extends WebView {

    private Context mContext;

    public Html5WebView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public Html5WebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Html5WebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        WebSettings mWebSettings = getSettings();
        mWebSettings.setSupportZoom(true);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setDefaultTextEncodingName("utf-8");
        mWebSettings.setLoadsImagesAutomatically(true);

        //调用JS方法.安卓版本大于17,加上注解 @JavascriptInterface
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setSupportMultipleWindows(true);
        //缓存数据
        saveData(mWebSettings);
        newWin(mWebSettings);
        setWebChromeClient(new BaseWebChromeClient());
        setWebViewClient(new BaseWebViewClient());
    }

    /**
     * 多窗口的问题
     */
    private void newWin(WebSettings mWebSettings) {
        //html中的_bank标签就是新建窗口打开，有时会打不开，需要加以下
        //然后 复写 WebChromeClient的onCreateWindow方法
        mWebSettings.setSupportMultipleWindows(false);
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
    }

    /**
     * HTML5数据存储
     */
    private void saveData(WebSettings mWebSettings) {
        //有时候网页需要自己保存一些关键数据,Android WebView 需要自己设置

        if (NetStatusUtil.isConnected(mContext)) {
            mWebSettings.setCacheMode(WebSettings.LOAD_DEFAULT);//根据cache-control决定是否从网络上取数据。
        } else {
            mWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//没网，则从本地获取，即离线加载
        }
        //File cacheDir = mContext.getCacheDir();
        File cacheDir = mContext.getExternalFilesDir("webview");

        if (cacheDir != null) {
            String appCachePath = cacheDir.getAbsolutePath();
            mWebSettings.setDomStorageEnabled(true);
            mWebSettings.setDatabaseEnabled(true);
            mWebSettings.setAppCacheEnabled(true);
            mWebSettings.setAppCachePath(appCachePath);
        }
    }
}

