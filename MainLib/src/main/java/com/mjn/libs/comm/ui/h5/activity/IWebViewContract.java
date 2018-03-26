package com.mjn.libs.comm.ui.h5.activity;

import com.mjn.libs.base.IMainLibActivityContract;

/**
 * @author 蓝兵
 */
public interface IWebViewContract {

    interface IWebViewView
            extends IMainLibActivityContract.IMainLibActivityView<IWebViewPresenter> {

    }

    interface IWebViewPresenter
            extends IMainLibActivityContract.IMainLibActivityPresenter<IWebViewView, IWebViewModule> {

    }

    interface IWebViewModule
            extends IMainLibActivityContract.IMainLibActivityModule {

    }
}
