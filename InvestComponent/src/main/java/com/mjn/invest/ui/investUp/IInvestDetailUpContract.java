package com.mjn.invest.ui.investUp;

import com.mjn.libs.api.ResponseListDataResult;
import com.mjn.libs.base.IMainLibFragmentContract;
import com.mjn.libs.comm.bean.IProduct;

/**
 * @author 蓝兵
 */
public interface IInvestDetailUpContract {

    interface IInvestDetailUpView extends IMainLibFragmentContract.IMainLibFragmentView<IInvestDetailUpPresenter> {

        void updateSuccess(   ResponseListDataResult<IProduct> listDataResult);
    }

    interface IInvestDetailUpPresenter extends
            IMainLibFragmentContract.IMainLibFragmentPresenter<IInvestDetailUpView, IInvestDetailUpModule> {

        void updateInvestDetail(String productId);
    }

    interface IInvestDetailUpModule extends IMainLibFragmentContract.IMainLibFragmentModule {

    }
}
