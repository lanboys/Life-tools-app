package com.mjn.invest.ui.investDetail;

import com.mjn.libs.base.MainLibActivityPresenter;

/**
 * @author 蓝兵
 */
public class InvestDetailPresenter
        extends MainLibActivityPresenter<IInvestDetailContract.IInvestDetailView, IInvestDetailContract.IInvestDetailModule>
        implements IInvestDetailContract.IInvestDetailPresenter {

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
