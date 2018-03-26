package com.mjn.libs.base.test.activity;

import com.mjn.libs.base.IMainLibActivityContract;

/**
 * @author 蓝兵
 */
public interface IActivityTestContract {

    interface IActivityTestView
            extends IMainLibActivityContract.IMainLibActivityView<IActivityTestPresenter> {

    }

    interface IActivityTestPresenter
            extends IMainLibActivityContract.IMainLibActivityPresenter<IActivityTestView, IActivityTestModule> {

    }

    interface IActivityTestModule
            extends IMainLibActivityContract.IMainLibActivityModule {

    }
}
