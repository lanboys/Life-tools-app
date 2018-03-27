package com.mjn.invest.ui.invest;

import com.bing.lan.comm.rx.OnDataChangerListener;
import com.mjn.libs.api.ApiManager;
import com.mjn.libs.api.ResponseResult;
import com.mjn.libs.base.MainLibFragmentModule;
import com.mjn.libs.comm.bean.IProduct;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

import static com.mjn.libs.base.MainLibActivityModule.handlerRequestParams;
import static com.mjn.libs.cons.RequestActionCons.ACTION_LOAD_MORE_INVEST;
import static com.mjn.libs.cons.RequestActionCons.ACTION_UPDATE_INVEST;

/**
 * @author 蓝兵
 */
public class InvestModule extends MainLibFragmentModule
        implements IInvestContract.IInvestModule {

    @Override
    public void loadData(int action, OnDataChangerListener listener, Object... parameter) {

        switch (action) {
            case ACTION_UPDATE_INVEST:
            case ACTION_LOAD_MORE_INVEST:
                update(action, listener, parameter);
                break;
        }
    }

    private void update(int action, OnDataChangerListener listener, Object[] parameter) {
        Map<String, String> params = new HashMap<>();
        checkNotNullAdd(params, (String) parameter[0], "categoryId");
        checkNotNullAdd(params, (String) parameter[1], "financialPeriod");
        checkNotNullAdd(params, (String) parameter[2], "page");
        checkNotNullAdd(params, (String) parameter[3], "size");
        params = handlerRequestParams(params);

        Observable<ResponseResult<IProduct>> observable = ApiManager.getInstance()
                .getApiService()
                .investList(params);

        subscribe(observable, action, listener, "投资列表..");
    }
}
