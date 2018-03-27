package com.mjn.invest.ui.investUp;

import com.bing.lan.comm.rx.OnDataChangerListener;
import com.mjn.libs.api.ApiManager;
import com.mjn.libs.api.ResponseResult;
import com.mjn.libs.base.MainLibFragmentModule;
import com.mjn.libs.comm.bean.IProduct;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

import static com.mjn.libs.base.MainLibActivityModule.handlerRequestParams;

/**
 * @author 蓝兵
 */
public class InvestDetailUpModule extends MainLibFragmentModule
        implements IInvestDetailUpContract.IInvestDetailUpModule {

    @Override
    public void loadData(int action, OnDataChangerListener listener, Object... parameter) {

        Map<String, String> params = new HashMap<>();
        checkNotEmptyAdd(params, (String) parameter[0], "productId");
        params = handlerRequestParams(params);

        Observable<ResponseResult<IProduct>> observable = ApiManager.getInstance()
                .getApiService()
                .proDetailInfo(params);

        subscribe(observable, action, listener, "投资详情..");
    }
}
