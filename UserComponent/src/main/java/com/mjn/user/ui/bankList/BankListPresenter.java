package com.mjn.user.ui.bankList;

import com.mjn.libs.api.ResponseListDataResult;
import com.mjn.libs.base.MainLibActivityPresenter;
import com.mjn.libs.comm.bean.IBankCard;
import com.mjn.libs.comm.bean.IBankCardList;

import static com.mjn.libs.cons.RequestActionCons.ACTION_SET_DEFAULT_BANKCARD;
import static com.mjn.libs.cons.RequestActionCons.ACTION_UPDATE_BANKCARD_LIST;

/**
 * @author 蓝兵
 */
public class BankListPresenter
        extends MainLibActivityPresenter<IBankListContract.IBankListView, IBankListContract.IBankListModule>
        implements IBankListContract.IBankListPresenter {

    @Override
    public void onStart(Object... params) {
    }

    @Override
    public void updateBankCardList() {
        showProgressDialog("正在加载..");
        requestData(ACTION_UPDATE_BANKCARD_LIST);
    }

    @Override
    public void setDefaultBankCard(String cardId) {
        showProgressDialog("正在保存..");
        requestData(ACTION_SET_DEFAULT_BANKCARD, cardId);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onSuccess(int action, Object data) {
        super.onSuccess(action, data);
        switch (action) {
            case ACTION_UPDATE_BANKCARD_LIST:
                mView.updateBankListSuccess((ResponseListDataResult<IBankCardList>) data);
                break;
            case ACTION_SET_DEFAULT_BANKCARD:
                mView.setDefaultBankCardSuccess((ResponseListDataResult<IBankCard>) data);
                break;
        }
    }

    @Override
    public void onError(int action, Throwable e) {
        super.onError(action, e);
    }

    @Override
    public void onCompleted(int action) {
        super.onCompleted(action);
    }
}
