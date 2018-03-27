package com.mjn.libs.base;

import com.bing.lan.comm.mvp.BasePresenter;
import com.bing.lan.comm.mvp.fragment.BaseFragmentModule;
import com.mjn.libs.api.ResponseListDataResult;
import com.mjn.libs.api.ResponseResult;

import java.util.List;

/**
 * @author 蓝兵
 */
public abstract class MainLibFragmentModule extends BaseFragmentModule
        implements IMainLibFragmentContract.IMainLibFragmentModule {

    @Override
    public void onSuccess(int action, Object data) {
        if (presenter != null) {
            ResponseResult<Object> result = (ResponseResult<Object>) data;
            if (result == null) {
                onError(action, new BasePresenter.MvpHttpException("ResponseResult 为空"));
                return;
            }

            String code = result.getCode();
            String message = result.getMessage();
            String debugmessage = result.getDebugmessage();
            Long servicetime = result.getServicetime();

            // 响应码不正确
            if (!ResponseResult.REQUEST_CODE_SUCCESS.equals(code)) {
                if (com.bing.lan.comm.app.AppConfig.LOG_DEBUG) {// 测试环境显示
                    if (debugmessage != null && debugmessage.length() > 35) {
                        debugmessage = debugmessage.substring(0, 35);
                    }
                    onError(action, new BasePresenter.MvpHttpException("\nhttp响应code不匹配, \ncode: "
                            + code + ", \nmessage: " + message + ", \ndebugMessage: " + debugmessage));
                } else {
                    onError(action, new BasePresenter.MvpHttpException(message));
                }
                return;
            }

            ResponseListDataResult<Object> resultData = result.getData();
            //响应码正确, 数据为空
            if (resultData == null) {
                if (com.bing.lan.comm.app.AppConfig.LOG_DEBUG) {// 测试环境显示
                    onError(action, new BasePresenter.MvpHttpException("ResponseListDataResult 为空"));
                } else {
                    onError(action, new BasePresenter.MvpHttpException("数据异常,请稍后再试.."));
                }
                return;
            }

            List<Object> list = resultData.getList();
            //响应码正确, 数据列表为空
            if (list == null || list.isEmpty()) {
                if (com.bing.lan.comm.app.AppConfig.LOG_DEBUG) {// 测试环境显示
                    onError(action, new BasePresenter.MvpHttpException("ResponseListDataResult 数据列表为空"));
                } else {
                    onError(action, new BasePresenter.MvpHttpException("数据异常,请稍后再试.."));
                }
                return;
            }

            resultData.setCode(code);
            resultData.setMessage(message);
            resultData.setDebugmessage(debugmessage);
            resultData.setServicetime(servicetime);
            presenter.onSuccess(action, resultData);
        }
    }
}
