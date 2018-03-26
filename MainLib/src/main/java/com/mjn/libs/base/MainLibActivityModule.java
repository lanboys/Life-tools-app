package com.mjn.libs.base;

import android.text.TextUtils;
import android.util.Log;

import com.bing.lan.comm.mvp.activity.BaseActivityModule;
import com.bing.lan.comm.rx.OnDataChangerListener;
import com.bing.lan.comm.utils.LogUtil;
import com.mjn.libs.db.DataSaveManager;
import com.mjn.libs.utils.AppConfig;
import com.mjn.libs.utils.Tools;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 蓝兵
 */

public abstract class MainLibActivityModule extends BaseActivityModule
        implements IMainLibActivityContract.IMainLibActivityModule {

    @Override
    public void loadData(int action, OnDataChangerListener listener, Object... parameter) {
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
    protected String getPostUrl(String url, Map<String, String> signParams) {
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
    protected String getGetUrl(String url, Map<String, String> signParams) {
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
