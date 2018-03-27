package com.mjn.invest.ui.investDetail;

import com.mjn.libs.base.IMainLibActivityContract;

/**
 * @author 蓝兵
 */
public interface IInvestDetailContract {

    interface IInvestDetailView
            extends IMainLibActivityContract.IMainLibActivityView<IInvestDetailPresenter> {

    }

    interface IInvestDetailPresenter
            extends IMainLibActivityContract.IMainLibActivityPresenter<IInvestDetailView, IInvestDetailModule> {

    }

    interface IInvestDetailModule
            extends IMainLibActivityContract.IMainLibActivityModule {

    }
}
