package com.mjn.user.ui.bankList;

import com.mjn.libs.api.ResponseListDataResult;
import com.mjn.libs.base.IMainLibActivityContract;
import com.mjn.libs.comm.bean.IBankCard;
import com.mjn.libs.comm.bean.IBankCardList;

/**
 * @author 蓝兵
 */
public interface IBankListContract {

    interface IBankListView
            extends IMainLibActivityContract.IMainLibActivityView<IBankListPresenter> {

        void updateBankListSuccess(ResponseListDataResult<IBankCardList> data);

        void setDefaultBankCardSuccess(ResponseListDataResult<IBankCard> data);
    }

    interface IBankListPresenter
            extends IMainLibActivityContract.IMainLibActivityPresenter<IBankListView, IBankListModule> {

        void updateBankCardList();

        void setDefaultBankCard(String cardId);
    }

    interface IBankListModule
            extends IMainLibActivityContract.IMainLibActivityModule {

    }
}
