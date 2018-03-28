package com.mjn.invest.ui.investDown;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mjn.invest.R;
import com.mjn.libs.base.MainLibFragment;
import com.mjn.libs.view.CustomWebview;

/**
 * @author 蓝兵
 */
public class InvestDetailDownFragment extends MainLibFragment<IInvestDetailDownContract.IInvestDetailDownPresenter>
        implements IInvestDetailDownContract.IInvestDetailDownView {

    private CustomWebview webView;
    /**
     * 是否已经加载
     */
    private boolean isLoaded = false;

    public InvestDetailDownFragment() {

    }

    private String mUrl;

    @SuppressLint("ValidFragment")
    public InvestDetailDownFragment(String url) {
        this.mUrl = url;
    }

    public static InvestDetailDownFragment newInstance(String title) {
        InvestDetailDownFragment fragment = new InvestDetailDownFragment();
        fragment.setTitle(title);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_invest_detail_down;
    }

    @Override
    protected IInvestDetailDownContract.IInvestDetailDownPresenter initPresenter() {
        InvestDetailDownPresenter presenter = new InvestDetailDownPresenter();
        presenter.setModule(new InvestDetailDownModule());
        presenter.onAttachView(this);
        return presenter;
    }

    @Override
    protected void initViewAndData(Intent intent, Bundle arguments) {

        webView = mContentView.findViewById(R.id.invest_detail_web);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDefaultTextEncodingName("UTF-8");
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setUserAgentString("baogongyoucai");
        webView.requestFocus();
        webView.requestFocusFromTouch();
        webView.setWebViewClient(new MyWebViewClient());
    }

    @Override
    protected void readyStartPresenter() {

    }

    /**
     * 加载详情
     *
     * @param url
     */
    public void loadUrl(String url) {
        if (!TextUtils.isEmpty(url) /*&& !isLoaded*/) {
            webView.loadUrl(url);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadUrl(mUrl);
    }

    /**
     * 自定义客户端
     */
    class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            isLoaded = true;
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            String data = "网络错误,请稍后重试";
            view.loadUrl("javascript:document.body.innerHTML=\"" + data + "\"");
            isLoaded = false;
        }
    }
}
