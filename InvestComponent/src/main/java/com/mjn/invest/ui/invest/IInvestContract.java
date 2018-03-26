package com.mjn.invest.ui.invest;

import com.mjn.libs.base.IMainLibFragmentContract;

/**
 * @author 蓝兵
 */
public interface IInvestContract {

    interface IInvestView extends IMainLibFragmentContract.IMainLibFragmentView<IInvestPresenter> {

    }

    interface IInvestPresenter extends
            IMainLibFragmentContract.IMainLibFragmentPresenter<IInvestView, IInvestModule> {

    }

    interface IInvestModule extends IMainLibFragmentContract.IMainLibFragmentModule {

    }
}
