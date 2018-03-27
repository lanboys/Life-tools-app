package com.mjn.libs.base;

import android.text.TextUtils;
import android.util.Log;

import com.bing.lan.comm.mvp.BasePresenter;
import com.bing.lan.comm.mvp.activity.BaseActivityModule;
import com.bing.lan.comm.utils.LogUtil;
import com.mjn.libs.api.ResponseListDataResult;
import com.mjn.libs.api.ResponseResult;
import com.mjn.libs.db.DataSaveManager;
import com.mjn.libs.utils.AppConfig;
import com.mjn.libs.utils.Tools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 蓝兵
 */

public abstract class MainLibActivityModule extends BaseActivityModule
        implements IMainLibActivityContract.IMainLibActivityModule {

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
                    if (debugmessage != null && debugmessage.length() > 50) {
                        debugmessage = debugmessage.substring(0, 50);
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

    protected static final LogUtil log1 = LogUtil.getLogUtil(MainLibActivityModule.class, LogUtil.LOG_VERBOSE);

    public static Map<String, String> handlerRequestParams(Map<String, String> params) {
        Map<String, String> signParams = new HashMap<>();
        signParams.putAll(params);
        try {
            if (AppConfig.updateTimestamp == 0) {
                String tempTime = DataSaveManager.getInstance().read("updateTimestamp");
                if (!TextUtils.isEmpty(tempTime)) {
                    AppConfig.updateTimestamp = Long.parseLong(tempTime);
                }
            }
            long timestamp = (System.currentTimeMillis() - AppConfig.updateTimestamp) / 1000;

            // 签名后的参数
            return getSignParams(signParams, timestamp);
        } catch (Exception e) {
            log1.e("handlerRequestParams(): 加密错误 " + e);
        }
        return new HashMap<>();
    }

    /**
     * 获取组装签名数据后的参数
     *
     * @param params    原始协议参数
     * @param timestamp 时间戳
     * @return
     */
    public static Map<String, String> getSignParams(Map<String, String> params, long timestamp) {
        Map<String, String> signParams = new HashMap<String, String>();
        signParams.putAll(params);
        try {
            signParams.put(AppConfig.KEY_TIMESTAMP_STRING, String.valueOf(timestamp));
            signParams.put(AppConfig.KEY_CHANNEL_ID, Tools.getAppChannel());
            signParams.put(AppConfig.KEY_VERSION, Tools.getAppVersion());
            signParams.put(AppConfig.KEY_DEVICE_TYPE, AppConfig.DEVICE_TYPE);
            signParams.put(AppConfig.KEY_IP, AppConfig.IP);
            signParams.put(AppConfig.KEY_LATITUDE, AppConfig.LATITUDE);
            signParams.put(AppConfig.KEY_LONGITUDE, AppConfig.LONGITUDE);
            signParams.put(AppConfig.KEY_DEVICE_ID, Tools.getDeviceId());

            signParams.put(AppConfig.KEY_SIGN, Tools.getSign(signParams, "21b35fa480505dbae4a50668182e6008"));
            Log.e("md5 sign = ", Tools.getSign(signParams, "21b35fa480505dbae4a50668182e6008"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return signParams;
    }

    /**
     * 构造post请求url
     *
     * @param url        原始url
     * @param signParams 签名后参数
     * @return
     */
    protected static String getPostUrl(String url, Map<String, String> signParams) {
        StringBuffer strbuf = new StringBuffer();
        strbuf.append(url);
        Tools.Log("网络请求url:" + strbuf.toString());
        return strbuf.toString();
    }

    /**
     * 构造Get请求url
     *
     * @param url
     * @param signParams
     * @return
     */
    protected static String getGetUrl(String url, Map<String, String> signParams) {
        StringBuilder strbuf = new StringBuilder();
        for (Map.Entry<String, String> paramEntry : signParams.entrySet()) {
            if (strbuf.length() > 0) {
                strbuf.append("&");
            }
            strbuf.append(paramEntry.getKey()).append("=").append(paramEntry.getValue());
        }
        strbuf.insert(0, "?").insert(0, url);
        Tools.Log("网络请求url:" + strbuf.toString());
        return strbuf.toString();
    }
}
