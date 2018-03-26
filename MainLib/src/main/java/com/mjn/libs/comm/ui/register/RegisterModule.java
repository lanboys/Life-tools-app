package com.mjn.libs.comm.ui.register;

import com.bing.lan.comm.rx.OnDataChangerListener;
import com.mjn.libs.api.ApiManager;
import com.mjn.libs.api.ResponseResult;
import com.mjn.libs.base.vcode.GetVcodeModule;
import com.mjn.libs.comm.bean.UserBean;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

import static com.mjn.libs.cons.RequestActionCons.ACTION_REGISTER;

/**
 * @author 蓝兵
 */
public class RegisterModule extends GetVcodeModule
        implements IRegisterContract.IRegisterModule {

    @Override
    public void loadData(int action, OnDataChangerListener listener, Object... parameter) {
        super.loadData(action, listener, parameter);

        //处理验证码接口
        handleVcode(action, listener, parameter);

        switch (action) {
            case ACTION_REGISTER:
                register(action, listener, parameter);
                break;
        }
    }

    private void register(int action, OnDataChangerListener listener, Object[] parameter) {
        Map<String, String> params = new HashMap<>();
        checkNotEmptyAdd(params, (String) parameter[0], "mobile");
        checkNotEmptyAdd(params, (String) parameter[1], "password");
        checkNotEmptyAdd(params, (String) parameter[2], "smsCode");
        checkNotEmptyAdd(params, (String) parameter[3], "inviteCode");
        params = handlerRequestParams(params);

        Observable<ResponseResult<UserBean>> observable = ApiManager.getInstance()
                .getApiService()
                .register(params);

        subscribe(observable, action, listener, "注册..");
    }
}
