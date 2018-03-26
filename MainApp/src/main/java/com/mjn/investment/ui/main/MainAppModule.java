package com.mjn.investment.ui.main;

import com.bing.lan.comm.rx.OnDataChangerListener;
import com.mjn.libs.base.MainLibActivityModule;

/**
 * @author 蓝兵
 */
public class MainAppModule extends MainLibActivityModule
        implements IMainAppContract.IMainAppModule {

    @Override
    public void loadData(int action, OnDataChangerListener listener, Object... parameter) {
        super.loadData(action, listener, parameter);
    }
}
