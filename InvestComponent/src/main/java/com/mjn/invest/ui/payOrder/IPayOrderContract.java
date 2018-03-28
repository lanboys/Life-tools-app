package com.mjn.invest.ui.payOrder;

import com.mjn.libs.api.ResponseListDataResult;
import com.mjn.libs.base.IMainLibActivityContract;
import com.mjn.libs.comm.bean.Order;
import com.mjn.libs.comm.bean.OrderBean;
import com.mjn.libs.comm.bean.PayInfo;

/**
 * @author 蓝兵
 */
public interface IPayOrderContract {

    interface IPayOrderView
            extends IMainLibActivityContract.IMainLibActivityView<IPayOrderPresenter> {

        void updateSuccess(ResponseListDataResult<PayInfo> data);

        void sendVcodeSuccess(ResponseListDataResult<OrderBean> data);

        void reSendVcodeSuccess(ResponseListDataResult<Order> data);

        void payOrderSuccess(ResponseListDataResult<OrderBean> data);
    }

    interface IPayOrderPresenter
            extends IMainLibActivityContract.IMainLibActivityPresenter<IPayOrderView, IPayOrderModule> {

        /**
         * 重新获取验证码
         */
        void reSendPayVcode(String orderId);

        /**
         * 订单支付验证码
         */
        void sendPayVcode(String projectId, String couponId, String bankId, String amount);

        /**
         * 获取订单信息
         */
        void getOrderInfo(String projectId, String money);

        /**
         * 去支付
         */
        void payOrder(String orderId, String vcode);
    }

    interface IPayOrderModule
            extends IMainLibActivityContract.IMainLibActivityModule {

    }
}
