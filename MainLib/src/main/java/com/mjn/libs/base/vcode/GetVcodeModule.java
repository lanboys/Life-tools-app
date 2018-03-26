package com.mjn.libs.base.vcode;

import com.bing.lan.comm.rx.OnDataChangerListener;
import com.mjn.libs.api.ApiManager;
import com.mjn.libs.api.ResponseResult;
import com.mjn.libs.base.MainLibActivityModule;
import com.mjn.libs.comm.bean.ISmsCode;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

import static com.mjn.libs.cons.RequestActionCons.ACTION_CHECK_VCODE;
import static com.mjn.libs.cons.RequestActionCons.ACTION_GET_VCODE;
import static com.mjn.libs.cons.RequestActionCons.ACTION_GET_VCODE_FORGET;

/**
 * @author 蓝兵
 */
public abstract class GetVcodeModule extends MainLibActivityModule
        implements IGetVcodeContract.IGetVcodeModule {

    protected void handleVcode(int action, OnDataChangerListener listener, Object... parameter) {
        switch (action) {
            case ACTION_GET_VCODE:
                getVcode(action, listener, parameter);
                break;
            case ACTION_GET_VCODE_FORGET:
                getForgetVcode(action, listener, parameter);
                break;
            case ACTION_CHECK_VCODE:
                checkVcode(action, listener, parameter);
                break;
        }
    }

    private void checkVcode(int action, OnDataChangerListener listener, Object... parameter) {
        //Observable<HttpResult<Object>> observable = ApiManager.getInstance()
        //        .getApiService()
        //        .checkVcode(
        //                (String) parameter[0],
        //                (String) parameter[1],
        //                (String) parameter[2]
        //        );
        //
        //subscribe(observable, action, listener, "检测 验证码");
    }

    private void getForgetVcode(int action, OnDataChangerListener listener, Object... parameter) {
        Map<String, String> params = getParams(parameter);

        Observable<ResponseResult<ISmsCode>> observable =
                ApiManager.getInstance()
                        .getApiService()
                        .getIDCardVcode(params);

        subscribe(observable, action, listener, "获取忘记密码 验证码");
    }

    private void getVcode(int action, OnDataChangerListener listener, Object... parameter) {
        Map<String, String> params = getParams(parameter);

        Observable<ResponseResult<Object>> observable =
                ApiManager.getInstance()
                        .getApiService()
                        .getVcode(params);

        subscribe(observable, action, listener, "获取 验证码");
    }

    private Map<String, String> getParams(Object[] parameter) {
        String mobile = (String) parameter[0];
        String smsType = (String) parameter[1];

        Map<String, String> params = new HashMap<>();
        checkNotEmptyAdd(params, mobile, "mobile");
        checkNotEmptyAdd(params, smsType, "smsType");
        params = handlerRequestParams(params);
        return params;
    }
}
