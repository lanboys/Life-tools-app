package com.mjn.invest.ui.investDown;

import com.mjn.libs.base.IMainLibFragmentContract;

/**
 * @author 蓝兵
 */
public interface IInvestDetailDownContract {

    interface IInvestDetailDownView extends IMainLibFragmentContract.IMainLibFragmentView<IInvestDetailDownPresenter> {

    }

    interface IInvestDetailDownPresenter extends
            IMainLibFragmentContract.IMainLibFragmentPresenter<IInvestDetailDownView, IInvestDetailDownModule> {

    }

    interface IInvestDetailDownModule extends IMainLibFragmentContract.IMainLibFragmentModule {

    }
}
