package com.mjn.invest.ui.investList;

import com.mjn.libs.api.ResponseListDataResult;
import com.mjn.libs.base.IMainLibFragmentContract;
import com.mjn.libs.comm.bean.IProduct;

/**
 * @author 蓝兵
 */
public interface IInvestListContract {

    interface IInvestListView extends IMainLibFragmentContract.IMainLibFragmentView<IInvestListPresenter> {

        void updateSuccess(ResponseListDataResult<IProduct> listDataResult);

        void loadMoreSuccess(ResponseListDataResult<IProduct> listDataResult);
    }

    interface IInvestListPresenter extends
            IMainLibFragmentContract.IMainLibFragmentPresenter<IInvestListView, IInvestListModule> {

        void update();

        void loadMore(long page);
    }

    interface IInvestListModule extends IMainLibFragmentContract.IMainLibFragmentModule {

    }
}
