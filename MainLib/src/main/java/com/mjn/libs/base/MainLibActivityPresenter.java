package com.mjn.libs.base;

import com.bing.lan.comm.mvp.activity.BaseActivityPresenter;

/**
 * @author 蓝兵
 */
public abstract class MainLibActivityPresenter<
        T extends IMainLibActivityContract.IMainLibActivityView,
        V extends IMainLibActivityContract.IMainLibActivityModule>
        extends BaseActivityPresenter<T, V>
        implements IMainLibActivityContract.IMainLibActivityPresenter<T, V> {

    @Override
    @SuppressWarnings("unchecked")
    public void onSuccess(int action, Object data) {
    }
}
