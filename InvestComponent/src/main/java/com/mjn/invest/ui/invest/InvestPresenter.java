package com.mjn.invest.ui.invest;

import com.mjn.libs.base.MainLibFragmentPresenter;

/**
 * @author 蓝兵
 */
public class InvestPresenter extends
        MainLibFragmentPresenter<IInvestContract.IInvestView, IInvestContract.IInvestModule>
        implements IInvestContract.IInvestPresenter {

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
