package com.mjn.invest.ui.investDetail;

import com.bing.lan.comm.rx.OnDataChangerListener;
import com.mjn.libs.base.MainLibActivityModule;

/**
 * @author 蓝兵
 */
public class InvestDetailModule extends MainLibActivityModule
        implements IInvestDetailContract.IInvestDetailModule {

    @Override
    public void loadData(int action, OnDataChangerListener listener, Object... parameter) {
        super.loadData(action, listener, parameter);
    }
}
