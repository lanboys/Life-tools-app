package com.mjn.libs.base;

import com.bing.lan.comm.mvp.fragment.BaseFragmentPresenter;

/**
 * @author 蓝兵
 */
public abstract class MainLibFragmentPresenter<
        T extends IMainLibFragmentContract.IMainLibFragmentView,
        V extends IMainLibFragmentContract.IMainLibFragmentModule>
        extends BaseFragmentPresenter<T, V>
        implements IMainLibFragmentContract.IMainLibFragmentPresenter<T, V> {


}
