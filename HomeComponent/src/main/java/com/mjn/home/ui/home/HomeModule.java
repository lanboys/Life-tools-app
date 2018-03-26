package com.mjn.home.ui.home;

import com.bing.lan.comm.rx.OnDataChangerListener;
import com.mjn.libs.api.ApiManager;
import com.mjn.libs.api.ResponseResult;
import com.mjn.libs.base.MainLibFragmentModule;
import com.mjn.libs.comm.bean.Home;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

import static com.mjn.libs.base.MainLibActivityModule.handlerRequestParams;
import static com.mjn.libs.cons.RequestActionCons.ACTION_UPDATE_HOME;

/**
 * @author 蓝兵
 */
public class HomeModule extends MainLibFragmentModule
        implements IHomeContract.IHomeModule {

    @Override
    public void loadData(int action, OnDataChangerListener listener, Object... parameter) {

        switch (action) {
            case ACTION_UPDATE_HOME:
                updateHome(action, listener, parameter);
                break;
        }
    }

    private void updateHome(int action, OnDataChangerListener listener, Object[] parameter) {
        Map<String, String> params = new HashMap<>();
        checkNotNullAdd(params, (String) parameter[0], "userId");
        params = handlerRequestParams(params);

        Observable<ResponseResult<Home>> observable = ApiManager.getInstance()
                .getApiService()
                .home(params);

        subscribe(observable, action, listener, "主页..");
    }
}
