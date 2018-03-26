package com.mjn.libs.comm.ui.h5.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.bing.lan.comm.app.AppUtil;
import com.mjn.libs.R;
import com.mjn.libs.base.MainLibFragment;
import com.mjn.libs.comm.ui.h5.BaseWebChromeClient;
import com.mjn.libs.comm.ui.h5.BaseWebViewClient;
import com.mjn.libs.comm.ui.h5.Html5WebView;

import java.util.HashMap;

/**
 * @author 蓝兵
 */
public class Html5Fragment extends MainLibFragment<IHtml5Contract.IHtml5Presenter>
        implements IHtml5Contract.IHtml5View {

    private String mUrl;

    private FrameLayout mLayout;
    private SeekBar mSeekBar;
    private Html5WebView mWebView;

    public Html5Fragment() {

    }

    //Bundle args = new Bundle();
    //    args.putString("fragment标题", title);
    //    fragment.setArguments(args);

    public static Html5Fragment newInstance(String title) {
        Html5Fragment fragment = new Html5Fragment();
        fragment.setTitle(title);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_html5;
    }

    @Override
    protected IHtml5Contract.IHtml5Presenter initPresenter() {
        Html5Presenter presenter = new Html5Presenter();
        presenter.setModule(new Html5Module());
        presenter.onAttachView(this);
        return presenter;
    }

    @Override
    protected void initViewAndData(Intent intent, Bundle bundle) {
        mLayout = (FrameLayout) mContentView.findViewById(R.id.web_layout);
        mSeekBar = (SeekBar) mContentView.findViewById(R.id.web_sbr);

        // 创建 WebView
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mWebView = new Html5WebView(AppUtil.getAppContext());
        mWebView.setLayoutParams(params);
        mLayout.addView(mWebView);
        mWebView.getSettings().setUserAgentString("baogongyoucai");

        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.setWebChromeClient(new Html5WebChromeClient());

        mWebView.addJavascriptInterface(new WebViewJavascriptBridge(handler, this), "WebViewJavascriptBridge");

        //LoadingDialog.showLoading(getActivity());

        //  js代码调用
        //  var androidObjectName = Android_Object_JsObject;
        //
        //  var callAndroid1 = function () {
        //      androidObjectName.helloAndroid("我是来自js的代码")
        //  };
        //mWebView.addJavascriptInterface(new JsObject(), "Android_Object_JsObject");
        //mWebView.addJavascriptInterface(this, "Android_Object_Html5Activity");

        //String jsmethod = "javascript:javaCallJS(\'我是来自安卓的补充文字\')";
        ////   android 4.4 开始使用evaluateJavascript调用js函数 ValueCallback获得调用js函数的返回值
        ////   4.4 之前还得通过 JavascriptInterface 来回调
        //mWebView.evaluateJavascript(jsmethod, new ValueCallback<String>() {
        //
        //    @Override
        //    public void onReceiveValue(String value) {
        //        Log.d("MainActivity", "回调  onReceiveValue value=" + value);
        //    }
        //});
    }

    public void setWebViewParams(Bundle bundle) {
        if (bundle != null) {
            String url = bundle.getString("url", "");
            String data = bundle.getString("data", "");
            title = bundle.getString("title", "");
            log.i("===== url ======: " + url);
            log.i("===== data ======: " + data);
            log.i("===== title ======: " + title);
            if (TextUtils.isEmpty(title)) {
                title = "加载中...";
            }
            if (!TextUtils.isEmpty(url)) {
                if (url.toLowerCase().startsWith("mcyd")) {
                    new WebBackNative().parseUrl(Uri.parse(url));
                } else {
                    synCookies(url);
                    mWebView.loadUrl(url);
                }
            } else if (!TextUtils.isEmpty(data)) {
                mWebView.loadDataWithBaseURL(null, data, "text/html", "UTF-8", null);
            }
        }
    }

    /**
     * 同步设置cookie,webview初始化设置就可以
     *
     * @param url
     */
    public void synCookies(String url) {
        String tempUrl = url.substring(0, (url.indexOf("/", 7) == -1 ? url.length() : url.indexOf("/", 7)));
        CookieSyncManager.createInstance(mWebView.getContext());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();//移除
        //cookieManager.setCookie(tempUrl, AppSpDataUtil.getInstance().getCookies());//cookies是在HttpClient中获得的cookie
        // TODO: 2018/3/22
        CookieSyncManager.getInstance().sync();
    }

    @Override
    protected void readyStartPresenter() {

    }

    //@JavascriptInterface
    //public void beInvokedByJavascript(String msg) {
    //    Log.i("beInvokedByJavascript", "beInvokedByJavascript: " + msg);
    //    Toast.makeText(Html5Activity.this, "beInvokedByJavascript" + msg, Toast.LENGTH_SHORT).show();
    //}

    @Override
    public void onDestroy() {
        // 销毁 WebView
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();

            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }

    private long mOldTime;

    /**
     * 点击“返回键”，返回上一层
     * 双击“返回键”，返回到最开始进来时的网页
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - mOldTime < 1500) {
                mWebView.clearHistory();
                mWebView.loadUrl(mUrl);
            } else if (mWebView.canGoBack()) {
                mWebView.goBack();
            } else {
                getActivity().finish();
            }
            mOldTime = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    /**
     * 自定义客户端
     * 可以拿到H5返回的标题
     */
    class MyWebViewClient extends BaseWebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //if (url.toLowerCase().startsWith(AppConfig.scheme)) {
            //    AppConfig.mainActivity.webBackNative.parseUrl(Uri.parse(url));
            //} else if (url.toLowerCase().startsWith("tel")) {
            //    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
            //    getActivity().startActivity(intent);
            //} else if (url.toLowerCase().startsWith("http") || url.toLowerCase().startsWith("https")) {
            //    view.loadUrl(url);
            //} else if (url.toLowerCase().startsWith("alipays://")) {
            //    if (checkInstall("com.eg.android.AlipayGphone")) {
            //        Intent intent = new Intent();
            //        intent.setAction(Intent.ACTION_VIEW);
            //        intent.setData(Uri.parse(url));
            //        startActivity(intent);
            //    }
            //} else if (url.toLowerCase().startsWith("weixin://")) {
            //    if (checkInstall("com.tencent.mm")) {
            //        Intent intent = new Intent();
            //        intent.setAction(Intent.ACTION_VIEW);
            //        intent.setData(Uri.parse(url));
            //        startActivity(intent);
            //    }
            //}
            return true;
        }

        /**
         * 加载完成，能拿到跳转的url和标题
         *
         * @param view
         * @param url
         */
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            // 拿到标题
            title = view.getTitle();
            //if (headView != null) {
            //    headView.setTitle(title);
            //}
            //LoadingDialog.hideLoading();
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            //if (headView != null) {
            //    headView.setTitle(title);
            //}
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

            String data = "网络错误,请稍后重试";
            view.loadUrl("javascript:document.body.innerHTML=\"" + data + "\"");
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }
    }

    // 继承 WebView 里面实现的基类
    class Html5WebChromeClient extends BaseWebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            // 顶部显示网页加载进度
            mSeekBar.setProgress(newProgress);
            Log.i("Html5WebChromeClient", "newProgress: " + newProgress);
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
            //Tools.showDialog("提示", message, "取消", "确定", new Tools.CustomAlertBtnCallback() {
            //    @Override
            //    public void ensure() {
            //        result.confirm();
            //    }
            //
            //    @Override
            //    public void cancel() {
            //        result.cancel();
            //    }
            //});
            return true;
        }

        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue,
                final JsPromptResult result) {
            //Tools.showDialog("提示", message, "取消", "确定", new Tools.CustomAlertBtnCallback() {
            //    @Override
            //    public void ensure() {
            //        result.confirm();
            //    }
            //
            //    @Override
            //    public void cancel() {
            //        result.cancel();
            //    }
            //});
            return false;
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            titleMap.put(view.getUrl(), title);
        }
    }

    /**
     * 标题
     */
    private String title;
    private HashMap<String, String> titleMap = new HashMap<>();
    /**
     * js交互后的返回执行条件
     */
    protected boolean isJsBrdge;

    /**
     * 分享id
     */
    protected static final int SHARE_ID = 1745;
    protected static final int TO_HOME = 1746;
    protected static final int CLOSE_PAGE = 1747;
    protected static final int TO_BANKCARD = 1748;
    protected static final int TO_SHARE = 1749;
    protected static final int TO_INVEST = 1750;
    protected static final int TO_DISCOVER = 1751;
    protected static final int TO_USER = 1752;
    protected static final int TO_TABMAIN = 1753;
    protected static final int RISK_RATING = 1754;

    /**
     * app中H5跳转到app固定的界面
     * 1.首页
     * 2.投资页
     * 3.发现页
     * 4.账户页
     * 5.账户中心
     * 6.登录
     * 7.我的红包
     * 8.我的加息券
     * 9.我的积分
     */
    protected void skipToTable(int id) {
        //if (!AppSpDataUtil.getInstance().isLogined()) {
        //    Tools.pushScreen(Login.class, null);
        //    return;
        //}
        switch (id) {
            case 1:
                Message message = new Message();
                message.what = TO_TABMAIN;
                handler.sendMessage(message);
                break;
            case 2:
                Message message2 = new Message();
                message2.what = TO_INVEST;
                handler.sendMessage(message2);
                break;
            case 3:
                Message message3 = new Message();
                message3.what = TO_DISCOVER;
                handler.sendMessage(message3);
                break;
            case 4:
                Message message4 = new Message();
                message4.what = TO_USER;
                handler.sendMessage(message4);
                break;
            //case 5:
            //    Tools.pushScreen(MyAccount.class, null);
            //    break;
            //case 6:
            //
            //    break;
            //case 7:
            //    Tools.pushScreen(RedPackage.class, null);
            //    break;
            //case 8:
            //    Tools.pushScreen(MyCoupon.class, null);
            //    break;
            //case 9:
            //    Tools.pushScreen(MyScore.class, null);
            //    break;
        }
    }

    /**
     * 获取链接
     *
     * @return
     */
    private String getShareUrl(String shareUrl) {
        //return shareUrl + "?invest_code=" + AppSpDataUtil.getInstance().getUserBean().getInviteCode() +
        //        "&resource=" + Tools.getAppChannel() + "&extra_info=";
        // TODO: 2018/3/22 解开注释
        return null;
    }

    /**
     * 获取链接,不拼接参数
     *
     * @return
     */
    private String gettoShareUrl(String shareUrl) {
        return shareUrl;
    }

    /**
     * Js交互后回调
     */
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //if (msg.what == SHARE_ID) {
            //    if (msg.obj != null) {
            //        String temp[] = (String[]) msg.obj;
            //        share(temp[0], temp[1], temp[2], temp[3]);
            //    }
            //} else if (msg.what == TO_SHARE) {
            //    if (msg.obj != null) {
            //        String temp[] = (String[]) msg.obj;
            //        toShare(temp[0], temp[1], temp[2], temp[3]);
            //    }
            //} else if (msg.what == TO_HOME) {
            //    Bundle bundle = new Bundle();
            //    bundle.putString("iswebtotab", "locker");
            //    Tools.replaceRootPushScreen(TabMain.class, bundle);
            //    //                TabMain.getInstance().changeTab(TabMain.TAB_INDEX_HOME);
            //} else if (msg.what == CLOSE_PAGE) {
            //    Tools.pullScreen();
            //} else if (msg.what == TO_TABMAIN) {
            //    Bundle bundle = new Bundle();
            //    bundle.putString("H5TOTAB", "h5totab");
            //    Tools.replaceRootPushScreen(TabMain.class, bundle);
            //    TabMain.getInstance().changeTab(0);
            //} else if (msg.what == TO_INVEST) {
            //    Bundle bundle2 = new Bundle();
            //    bundle2.putString("H5TOTAB", "h5totab");
            //    Tools.replaceRootPushScreen(TabMain.class, bundle2);
            //    TabMain.getInstance().changeTab(1);
            //} else if (msg.what == TO_DISCOVER) {
            //    Bundle bundle3 = new Bundle();
            //    bundle3.putString("H5TOTAB", "h5totab");
            //    Tools.replaceRootPushScreen(TabMain.class, bundle3);
            //    TabMain.getInstance().changeTab(2);
            //} else if (msg.what == TO_USER) {
            //    Bundle bundle4 = new Bundle();
            //    bundle4.putString("H5TOTAB", "h5totab");
            //    Tools.replaceRootPushScreen(TabMain.class, bundle4);
            //    TabMain.getInstance().changeTab(3);
            //} else if (msg.what == RISK_RATING) {
            //    // 发送广播：
            //    Intent broadIntent = new Intent();
            //    broadIntent.setAction("broadRefreshUser");
            //    broadIntent.putExtra("message", "refreshUserInfo");
            //    getActivity().sendBroadcast(broadIntent);
            //    Tools.pullScreen();
            //}
            ////            else if (msg.what == TO_BANKCARD) {
            ////                Intent intent = new Intent(getContext(),
            ////                        BankCardScanActivity.class);
            ////                intent.putExtra(Util.KEY_ISDEBUGE, false);
            ////                intent.putExtra(Util.KEY_ISALLCARD, true);
            ////                startActivityForResult(intent, TO_BANKCARD);
            ////            }
            //else {
            //    if (msg.obj != null) {
            //        HybridOfWebview.this.headView.setTitle(msg.obj.toString());
            //    }
            //}
        }
    };
}
