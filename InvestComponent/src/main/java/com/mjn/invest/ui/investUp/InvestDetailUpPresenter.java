package com.mjn.invest.ui.investUp;

import com.mjn.libs.api.ResponseListDataResult;
import com.mjn.libs.base.MainLibFragmentPresenter;
import com.mjn.libs.comm.bean.IProduct;

import static com.mjn.libs.cons.RequestActionCons.ACTION_UPDATE_INVEST_DETAIL;

/**
 * @author 蓝兵
 */
public class InvestDetailUpPresenter extends
        MainLibFragmentPresenter<IInvestDetailUpContract.IInvestDetailUpView,
                IInvestDetailUpContract.IInvestDetailUpModule>
        implements IInvestDetailUpContract.IInvestDetailUpPresenter {

    @Override
    public void onStart(Object... params) {

    }

    @Override
    public void updateInvestDetail(String productId) {
        showProgressDialog("正在加载..");
        requestData(ACTION_UPDATE_INVEST_DETAIL, productId);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onSuccess(int action, Object data) {
        super.onSuccess(action, data);
        switch (action) {
            case ACTION_UPDATE_INVEST_DETAIL:
                ResponseListDataResult<IProduct> listDataResult = (ResponseListDataResult<IProduct>) data;
                mView.updateSuccess(listDataResult);
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
