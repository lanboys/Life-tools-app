package com.mjn.libs.base.test.fragment;

import com.mjn.libs.base.IMainLibFragmentContract;

/**
 * @author 蓝兵
 */
public interface IFragTestContract {

    interface IFragTestView extends IMainLibFragmentContract.IMainLibFragmentView<IFragTestPresenter> {

    }

    interface IFragTestPresenter extends
            IMainLibFragmentContract.IMainLibFragmentPresenter<IFragTestView, IFragTestModule> {

    }

    interface IFragTestModule extends IMainLibFragmentContract.IMainLibFragmentModule {

    }
}
