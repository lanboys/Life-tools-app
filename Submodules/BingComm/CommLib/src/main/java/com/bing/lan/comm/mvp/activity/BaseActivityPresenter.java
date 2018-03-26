package com.bing.lan.comm.mvp.activity;

import com.bing.lan.comm.mvp.BasePresenter;

/**
 * @author 蓝兵
 */
public abstract class BaseActivityPresenter<
        T extends IBaseActivityContract.IBaseActivityView,
        V extends IBaseActivityContract.IBaseActivityModule>
        extends BasePresenter<T, V>
        implements IBaseActivityContract.IBaseActivityPresenter<T, V> {

}
