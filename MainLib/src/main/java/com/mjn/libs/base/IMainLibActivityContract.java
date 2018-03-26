package com.mjn.libs.base;

import com.bing.lan.comm.mvp.activity.IBaseActivityContract;

public interface IMainLibActivityContract {

    interface IMainLibActivityView<T extends IMainLibActivityPresenter> extends IBaseActivityContract.IBaseActivityView<T> {

    }

    interface IMainLibActivityPresenter<T extends IMainLibActivityView, M extends IMainLibActivityModule>
            extends IBaseActivityContract.IBaseActivityPresenter<T, M> {

    }

    interface IMainLibActivityModule extends IBaseActivityContract.IBaseActivityModule {

    }
}
