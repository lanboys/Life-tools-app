package com.bing.lan.comm.mvp.fragment;

import com.bing.lan.comm.mvp.BaseModule;
import com.bing.lan.comm.rx.OnDataChangerListener;

/**
 * @author 蓝兵
 * @email lan_bing2013@163.com
 * @time 2017/7/17  23:17
 */
public abstract class BaseFragmentModule extends BaseModule
        implements IBaseFragmentContract.IBaseFragmentModule {

    @Override
    public void loadData(int action, OnDataChangerListener listener, Object... parameter) {
    }
}