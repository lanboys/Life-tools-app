package com.mjn.home.ui;

import com.bing.lan.comm.rx.OnDataChangerListener;
import com.mjn.libs.base.MainLibActivityModule;

/**
 * @author 蓝兵
 */
public class HomeComponentModule extends MainLibActivityModule
        implements IHomeComponentContract.IHomeComponentModule {

    @Override
    public void loadData(int action, OnDataChangerListener listener, Object... parameter) {
        super.loadData(action, listener, parameter);
    }
}
