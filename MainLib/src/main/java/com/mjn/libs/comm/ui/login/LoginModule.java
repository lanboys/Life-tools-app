package com.mjn.libs.comm.ui.login;

import com.bing.lan.comm.rx.OnDataChangerListener;
import com.mjn.libs.api.ApiManager;
import com.mjn.libs.api.ResponseResult;
import com.mjn.libs.base.vcode.GetVcodeModule;
import com.mjn.libs.comm.bean.IUser;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

import static com.mjn.libs.cons.RequestActionCons.ACTION_LOGIN;

/**
 * @author 蓝兵
 */
public class LoginModule extends GetVcodeModule
        implements ILoginContract.ILoginModule {

    @Override
    public void loadData(int action, OnDataChangerListener listener, Object... parameter) {
        super.loadData(action, listener, parameter);

        switch (action) {
            case ACTION_LOGIN:
                login(action, listener, parameter);
                break;
        }
    }

    private void login(int action, OnDataChangerListener listener, Object[] parameter) {
        Map<String, String> params = new HashMap<>();
        checkNotEmptyAdd(params, (String) parameter[0], "mobile");
        checkNotEmptyAdd(params, (String) parameter[1], "password");
        params = handlerRequestParams(params);

        Observable<ResponseResult<IUser>> observable = ApiManager.getInstance()
                .getApiService()
                .login(params);

        subscribe(observable, action, listener, "登录..");
    }
}
