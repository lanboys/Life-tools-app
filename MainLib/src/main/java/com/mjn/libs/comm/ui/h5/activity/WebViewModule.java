package com.mjn.libs.comm.ui.h5.activity;

import com.bing.lan.comm.rx.OnDataChangerListener;
import com.mjn.libs.base.MainLibActivityModule;

/**
 * @author 蓝兵
 */
public class WebViewModule extends MainLibActivityModule
        implements IWebViewContract.IWebViewModule {

    @Override
    public void loadData(int action, OnDataChangerListener listener, Object... parameter) {
        super.loadData(action, listener, parameter);
    }
}
