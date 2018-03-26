package com.mjn.discover.ui.discover;

import com.mjn.libs.base.IMainLibFragmentContract;

/**
 * @author 蓝兵
 */
public interface IDiscoverContract {

    interface IDiscoverView extends IMainLibFragmentContract.IMainLibFragmentView<IDiscoverPresenter> {

    }

    interface IDiscoverPresenter extends
            IMainLibFragmentContract.IMainLibFragmentPresenter<IDiscoverView, IDiscoverModule> {

    }

    interface IDiscoverModule extends IMainLibFragmentContract.IMainLibFragmentModule {

    }
}
