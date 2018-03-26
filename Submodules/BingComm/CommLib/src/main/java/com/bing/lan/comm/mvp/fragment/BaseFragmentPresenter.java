package com.bing.lan.comm.mvp.fragment;

import android.os.Bundle;

import com.bing.lan.comm.mvp.BasePresenter;

/**
 * @author 蓝兵
 * @time 2017/1/12  18:54
 */
public abstract class BaseFragmentPresenter<
        T extends IBaseFragmentContract.IBaseFragmentView,
        V extends IBaseFragmentContract.IBaseFragmentModule>
        extends BasePresenter<T, V>
        implements IBaseFragmentContract.IBaseFragmentPresenter<T, V> {

    @Override
    public void reStartUpdate() {
        onStart();
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public void setParams(Bundle bundle) {

    }

    @Override
    public void stopUpdate() {
        mModule.releaseTask();
    }
}
