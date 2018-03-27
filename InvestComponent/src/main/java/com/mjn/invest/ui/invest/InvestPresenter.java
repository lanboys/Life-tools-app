package com.mjn.invest.ui.invest;

import com.mjn.libs.api.ResponseListDataResult;
import com.mjn.libs.base.MainLibFragmentPresenter;
import com.mjn.libs.comm.bean.IProduct;

import static com.mjn.libs.cons.IConstants.LIST_PAGE_SIZE;
import static com.mjn.libs.cons.RequestActionCons.ACTION_LOAD_MORE_INVEST;
import static com.mjn.libs.cons.RequestActionCons.ACTION_UPDATE_INVEST;

/**
 * @author 蓝兵
 */
public class InvestPresenter extends
        MainLibFragmentPresenter<IInvestContract.IInvestView, IInvestContract.IInvestModule>
        implements IInvestContract.IInvestPresenter {

    @Override
    public void onStart(Object... params) {
        showProgressDialog("正在加载..");
        update();
    }

    @Override
    public void update() {
        requestData(ACTION_UPDATE_INVEST, "2", "0", "1", LIST_PAGE_SIZE);
    }

    @Override
    public void loadMore(long page) {
        requestData(ACTION_LOAD_MORE_INVEST, "2", "0", String.valueOf(page), LIST_PAGE_SIZE);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onSuccess(int action, Object data) {
        super.onSuccess(action, data);
        switch (action) {
            case ACTION_UPDATE_INVEST:
                mView.updateSuccess((ResponseListDataResult<IProduct>) data);
                break;
            case ACTION_LOAD_MORE_INVEST:
                mView.loadMoreSuccess((ResponseListDataResult<IProduct>) data);
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
