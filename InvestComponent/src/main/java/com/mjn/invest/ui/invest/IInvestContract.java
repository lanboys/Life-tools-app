package com.mjn.invest.ui.invest;

import com.mjn.libs.api.ResponseListDataResult;
import com.mjn.libs.base.IMainLibFragmentContract;
import com.mjn.libs.comm.bean.IProduct;

/**
 * @author 蓝兵
 */
public interface IInvestContract {

    interface IInvestView extends IMainLibFragmentContract.IMainLibFragmentView<IInvestPresenter> {

        void updateSuccess(ResponseListDataResult<IProduct> listDataResult);

        void loadMoreSuccess(ResponseListDataResult<IProduct> listDataResult);
    }

    interface IInvestPresenter extends
            IMainLibFragmentContract.IMainLibFragmentPresenter<IInvestView, IInvestModule> {

        void update();

        void loadMore(long page);
    }

    interface IInvestModule extends IMainLibFragmentContract.IMainLibFragmentModule {

    }
}
