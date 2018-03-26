package com.mjn.libs.comm.ui.h5.fragment;

import com.mjn.libs.base.IMainLibFragmentContract;

/**
 * @author 蓝兵
 */
public interface IHtml5Contract {

    interface IHtml5View extends IMainLibFragmentContract.IMainLibFragmentView<IHtml5Presenter> {

    }

    interface IHtml5Presenter extends
            IMainLibFragmentContract.IMainLibFragmentPresenter<IHtml5View, IHtml5Module> {

    }

    interface IHtml5Module extends IMainLibFragmentContract.IMainLibFragmentModule {

    }
}
