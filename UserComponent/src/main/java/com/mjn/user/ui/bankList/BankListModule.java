package com.mjn.user.ui.bankList;

import com.bing.lan.comm.rx.OnDataChangerListener;
import com.mjn.libs.api.ApiManager;
import com.mjn.libs.api.ResponseResult;
import com.mjn.libs.base.MainLibActivityModule;
import com.mjn.libs.comm.bean.IBankCard;
import com.mjn.libs.comm.bean.IBankCardList;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

import static com.mjn.libs.cons.RequestActionCons.ACTION_SET_DEFAULT_BANKCARD;
import static com.mjn.libs.cons.RequestActionCons.ACTION_UPDATE_BANKCARD_LIST;

/**
 * @author 蓝兵
 */
public class BankListModule extends MainLibActivityModule
        implements IBankListContract.IBankListModule {

    @Override
    public void loadData(int action, OnDataChangerListener listener, Object... parameter) {
        super.loadData(action, listener, parameter);

        switch (action) {
            case ACTION_UPDATE_BANKCARD_LIST:
                bankCardList(action, listener, parameter);
                break;
            case ACTION_SET_DEFAULT_BANKCARD:
                setDefaultBankCard(action, listener, parameter);
                break;
        }
    }

    private void setDefaultBankCard(int action, OnDataChangerListener listener, Object[] parameter) {
        Map<String, String> params = new HashMap<>();
        checkNotEmptyAdd(params, (String) parameter[0], "cardId");
        params = handlerRequestParams(params);

        Observable<ResponseResult<IBankCard>> observable = ApiManager.getInstance()
                .getApiService()
                .setDefaultBankCard(params);

        subscribe(observable, action, listener, "设置默认银行卡..");
    }

    private void bankCardList(int action, OnDataChangerListener listener, Object[] parameter) {
        Map<String, String> params = new HashMap<>();
        params = handlerRequestParams(params);

        Observable<ResponseResult<IBankCardList>> observable = ApiManager.getInstance()
                .getApiService()
                .findBankCards(params);

        subscribe(observable, action, listener, "获取银行卡列表..");
    }
}
