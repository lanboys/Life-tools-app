package com.mjn.invest.ui.payOrder;

import com.mjn.libs.api.ResponseListDataResult;
import com.mjn.libs.base.MainLibActivityPresenter;
import com.mjn.libs.comm.bean.Order;
import com.mjn.libs.comm.bean.OrderBean;
import com.mjn.libs.comm.bean.PayInfo;

import static com.mjn.libs.cons.RequestActionCons.ACTION_GET_PAY_ORDER_VCODE;
import static com.mjn.libs.cons.RequestActionCons.ACTION_GET_RE_PAY_ORDER_VCODE;
import static com.mjn.libs.cons.RequestActionCons.ACTION_PAY_ORDER;
import static com.mjn.libs.cons.RequestActionCons.ACTION_UPDATE_ORDER_INFO;

/**
 * @author 蓝兵
 */
public class PayOrderPresenter
        extends MainLibActivityPresenter<IPayOrderContract.IPayOrderView, IPayOrderContract.IPayOrderModule>
        implements IPayOrderContract.IPayOrderPresenter {

    @Override
    public void onStart(Object... params) {
    }

    @Override
    public void sendPayVcode(String projectId, String couponId, String bankId, String amount) {
        showProgressDialog("正在获取..");
        requestData(ACTION_GET_PAY_ORDER_VCODE, projectId, couponId, bankId, amount);
    }

    @Override
    public void reSendPayVcode(String orderId) {
        showProgressDialog("正在获取..");
        requestData(ACTION_GET_RE_PAY_ORDER_VCODE, orderId);
    }

    @Override
    public void payOrder(String orderId, String vcode) {
        showProgressDialog("正在加载..");
        requestData(ACTION_PAY_ORDER, orderId, vcode);
    }

    @Override
    public void getOrderInfo(String projectId, String money) {
        showProgressDialog("正在加载..");
        requestData(ACTION_UPDATE_ORDER_INFO, projectId, money);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onSuccess(int action, Object data) {
        super.onSuccess(action, data);

        switch (action) {
            case ACTION_UPDATE_ORDER_INFO:
                mView.updateSuccess((ResponseListDataResult<PayInfo>) data);
                break;
            case ACTION_GET_PAY_ORDER_VCODE:
                mView.sendVcodeSuccess((ResponseListDataResult<OrderBean>) data);
                break;
            case ACTION_GET_RE_PAY_ORDER_VCODE:
                mView.reSendVcodeSuccess((ResponseListDataResult<Order>) data);
                break;
            case ACTION_PAY_ORDER:
                mView.payOrderSuccess((ResponseListDataResult<OrderBean>) data);
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
