package com.bing.lan.comm.api.interceptor;

import com.bing.lan.comm.utils.LogUtil;
import com.bing.lan.comm.utils.NetworkUtil;
import com.bing.lan.comm.app.AppUtil;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CacheControlInterceptor implements Interceptor {

    protected final LogUtil log = LogUtil.getLogUtil(getClass(), LogUtil.LOG_VERBOSE);

    @Override
    public Response intercept(Chain chain) throws IOException {

        boolean networkAvailable = NetworkUtil.isNetworkAvailable(AppUtil.getAppContext());
        Request request = chain.request();

        //没网强制从缓存读取
        if (!networkAvailable) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
            log.i("intercept(): 没网强制从缓存读取");
        }

        Response response = chain.proceed(request);

        // cache mechanism
        //if (networkAvailable) {
        //
        //    //    //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
        //    //    String cacheControl = request.cacheControl().toString();
        //    //
        //    //    log.i("intercept() cacheControl: " + cacheControl);
        //
        //    // 有网络时, 不缓存, 最大保存时长为0
        //    int maxAge = 0;
        //    response.newBuilder()
        //            .header("Cache-Control", "public, max-age=" + maxAge)
        //            .removeHeader("Pragma")
        //            .build();
        //} else {
        //    // 无网络时，设置超时为4周
        //    int maxStale = 60 * 60 * 24 * 28;
        //    response.newBuilder()
        //            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
        //            .removeHeader("Pragma")
        //            .build();
        //}
        return response;
    }
}
