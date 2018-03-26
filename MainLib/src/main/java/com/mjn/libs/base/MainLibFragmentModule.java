package com.mjn.libs.base;

import com.bing.lan.comm.mvp.BasePresenter;
import com.bing.lan.comm.mvp.fragment.BaseFragmentModule;
import com.mjn.libs.api.ResponseListDataResult;
import com.mjn.libs.api.ResponseResult;

/**
 * @author 蓝兵
 */
public abstract class MainLibFragmentModule extends BaseFragmentModule
        implements IMainLibFragmentContract.IMainLibFragmentModule {

    @Override
    public void onSuccess(int action, Object data) {
        if (presenter != null) {
            ResponseResult<Object> result = (ResponseResult<Object>) data;
            String code = result.getCode();
            String message = result.getMessage();
            String debugmessage = result.getDebugmessage();
            Long servicetime = result.getServicetime();

            ResponseListDataResult<Object> resultData = result.getData();
            if (ResponseResult.REQUEST_CODE_SUCCESS.equals(code) && resultData != null) {
                resultData.setCode(code);
                resultData.setMessage(message);
                resultData.setDebugmessage(debugmessage);
                resultData.setServicetime(servicetime);
                presenter.onSuccess(action, resultData);
            } else {
                onError(action, new BasePresenter.MvpHttpException(message + ", " + debugmessage));
            }
        }
    }
}
