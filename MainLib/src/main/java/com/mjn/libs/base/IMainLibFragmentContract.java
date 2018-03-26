package com.mjn.libs.base;

import com.bing.lan.comm.mvp.fragment.IBaseFragmentContract;

public interface IMainLibFragmentContract {

    interface IMainLibFragmentView<T extends IMainLibFragmentPresenter>
            extends IBaseFragmentContract.IBaseFragmentView<T> {

    }

    interface IMainLibFragmentPresenter<T extends IMainLibFragmentView,
            M extends IMainLibFragmentModule>
            extends IBaseFragmentContract.IBaseFragmentPresenter<T, M> {

    }

    interface IMainLibFragmentModule extends IBaseFragmentContract.IBaseFragmentModule {

    }
}
