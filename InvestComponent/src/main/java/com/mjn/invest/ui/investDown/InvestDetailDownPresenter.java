package com.mjn.invest.ui.investDown;

import com.mjn.libs.base.MainLibFragmentPresenter;

/**
 * @author 蓝兵
 */
public class InvestDetailDownPresenter extends
        MainLibFragmentPresenter<IInvestDetailDownContract.IInvestDetailDownView, IInvestDetailDownContract.IInvestDetailDownModule>
        implements IInvestDetailDownContract.IInvestDetailDownPresenter {

    @Override
    public void onStart(Object... params) {

    }

    @Override
    @SuppressWarnings("unchecked")
    public void onSuccess(int action, Object data) {
        super.onSuccess(action, data);
        switch (action) {
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
