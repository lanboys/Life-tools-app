package com.bing.lan.comm.mvp.activity;

import com.bing.lan.comm.mvp.BaseModule;
import com.bing.lan.comm.rx.OnDataChangerListener;

/**
 * @author 蓝兵
 * @email lan_bing2013@163.com
 * @time 2017/7/17  22:37
 */
public abstract class BaseActivityModule extends BaseModule
        implements IBaseActivityContract.IBaseActivityModule {

    @Override
    public void loadData(int action, OnDataChangerListener listener, Object... parameter) {
    }
}
