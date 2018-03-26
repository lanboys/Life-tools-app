package com.bing.lan.comm.mvp.activity;

import com.bing.lan.comm.mvp.IBaseContract.IBaseModule;
import com.bing.lan.comm.mvp.IBaseContract.IBasePresenter;
import com.bing.lan.comm.mvp.IBaseContract.IBaseView;

public interface IBaseActivityContract {

    interface IBaseActivityView<T extends IBaseActivityPresenter> extends IBaseView<T> {

        void finish();
    }

    interface IBaseActivityPresenter<T extends IBaseActivityView, M extends IBaseActivityModule>
            extends IBasePresenter<T, M> {

    }

    interface IBaseActivityModule extends IBaseModule {

    }
}
