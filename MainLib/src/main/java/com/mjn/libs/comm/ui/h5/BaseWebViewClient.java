package com.mjn.libs.comm.ui.h5;

import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.HashMap;
import java.util.Set;

/**
 * 实现一个基础的 WebViewClient ，如果有更多的需要，直接继承它
 */
public class BaseWebViewClient extends WebViewClient {

    /**
     * 多页面在同一个WebView中打开，就是不新建activity或者调用系统浏览器打开
     *
     * @param webView WebView
     * @param url     WebView 内地址
     * @return true  WebView 不会自动加载 可以自动手动 WebView.loadUrl(url) 进行加载  false WebView 会自动加载
     */
    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url) {
        //http://blog.csdn.net/carson_ho/article/details/64904691
        // 步骤2：根据协议的参数，判断是否是所需要的url
        // 一般根据scheme（协议格式） & authority（协议名）判断（前两个参数）
        //假定传入进来的 url = "js://webview?arg1=111&arg2=222"（同时也是约定好的需要拦截的）

        Uri uri = Uri.parse(url);
        // 如果url的协议 = 预先约定的 js 协议
        // 就解析往下解析参数
        if ("js".equals(uri.getScheme())) {
            // 如果 authority  = 预先约定协议里的 webview，即代表都符合约定的协议
            // 所以拦截url,下面JS开始调用Android需要的方法
            if ("webview".equals(uri.getAuthority())) {
                //  步骤3：
                // 执行JS所需要调用的逻辑
                System.out.println("js调用了Android的方法2");
                // 可以在协议上带有参数并传递到Android上
                HashMap<String, String> params = new HashMap<>();
                Set<String> collection = uri.getQueryParameterNames();
            }
        } else {
            webView.loadUrl(url);
        }

        return true;
    }
}