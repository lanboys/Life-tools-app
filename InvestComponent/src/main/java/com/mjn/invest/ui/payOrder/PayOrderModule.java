package com.mjn.invest.ui.payOrder;

import com.bing.lan.comm.rx.OnDataChangerListener;
import com.mjn.libs.api.ApiManager;
import com.mjn.libs.api.ResponseResult;
import com.mjn.libs.base.MainLibActivityModule;
import com.mjn.libs.comm.bean.Order;
import com.mjn.libs.comm.bean.OrderBean;
import com.mjn.libs.comm.bean.PayInfo;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

import static com.mjn.libs.cons.RequestActionCons.ACTION_GET_PAY_ORDER_VCODE;
import static com.mjn.libs.cons.RequestActionCons.ACTION_GET_RE_PAY_ORDER_VCODE;
import static com.mjn.libs.cons.RequestActionCons.ACTION_PAY_ORDER;
import static com.mjn.libs.cons.RequestActionCons.ACTION_UPDATE_ORDER_INFO;

/**
 * @author 蓝兵
 */
public class PayOrderModule extends MainLibActivityModule
        implements IPayOrderContract.IPayOrderModule {

    @Override
    public void loadData(int action, OnDataChangerListener listener, Object... parameter) {
        super.loadData(action, listener, parameter);

        switch (action) {
            case ACTION_UPDATE_ORDER_INFO:
                getOrderInfo(action, listener, parameter);
                break;
            case ACTION_GET_PAY_ORDER_VCODE:
                getOrderVcode(action, listener, parameter);
                break;
            case ACTION_GET_RE_PAY_ORDER_VCODE:
                getReOrderVcode(action, listener, parameter);
                break;
            case ACTION_PAY_ORDER:
                payOrder(action, listener, parameter);
                break;
        }
    }

    private void payOrder(int action, OnDataChangerListener listener, Object[] parameter) {
        Map<String, String> params = new HashMap<>();
        checkNotNullAdd(params, (String) parameter[0], "orderId");
        checkNotNullAdd(params, (String) parameter[1], "smsCode");
        params = handlerRequestParams(params);

        Observable<ResponseResult<OrderBean>> observable = ApiManager.getInstance()
                .getApiService()
                .payOrder(params);

        subscribe(observable, action, listener, "支付订单..");
    }

    private void getOrderVcode(int action, OnDataChangerListener listener, Object[] parameter) {
        Map<String, String> params = new HashMap<>();
        checkNotNullAdd(params, (String) parameter[0], "productId");
        checkNotNullAdd(params, (String) parameter[1], "couponId");
        checkNotNullAdd(params, (String) parameter[2], "bankId");
        checkNotNullAdd(params, (String) parameter[3], "amount");
        params = handlerRequestParams(params);

        Observable<ResponseResult<OrderBean>> observable = ApiManager.getInstance()
                .getApiService()
                .getPayOrderVcode(params);

        subscribe(observable, action, listener, "发支付验证码..");
    }

    private void getReOrderVcode(int action, OnDataChangerListener listener, Object[] parameter) {
        Map<String, String> params = new HashMap<>();
        checkNotNullAdd(params, (String) parameter[0], "orderId");
        params = handlerRequestParams(params);

        Observable<ResponseResult<Order>> observable = ApiManager.getInstance()
                .getApiService()
                .getRePayOrderVcode(params);

        subscribe(observable, action, listener, "重复发支付验证码..");
    }

    private void getOrderInfo(int action, OnDataChangerListener listener, Object[] parameter) {
        Map<String, String> params = new HashMap<>();
        checkNotNullAdd(params, (String) parameter[0], "productId");
        checkNotNullAdd(params, (String) parameter[1], "amount");
        params = handlerRequestParams(params);

        Observable<ResponseResult<PayInfo>> observable = ApiManager.getInstance()
                .getApiService()
                .payOrderInfo(params);

        subscribe(observable, action, listener, "优惠券，红包..");
    }
}
