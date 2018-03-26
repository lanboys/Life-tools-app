package com.mjn.home.ui;

import com.mjn.libs.base.IMainLibActivityContract;

/**
 * @author 蓝兵
 */
public interface IHomeComponentContract {

    interface IHomeComponentView
            extends IMainLibActivityContract.IMainLibActivityView<IHomeComponentPresenter> {

    }

    interface IHomeComponentPresenter
            extends IMainLibActivityContract.IMainLibActivityPresenter<IHomeComponentView, IHomeComponentModule> {

    }

    interface IHomeComponentModule
            extends IMainLibActivityContract.IMainLibActivityModule {

    }
}
