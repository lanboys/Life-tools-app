package com.mjn.libs.api.interceptor;

import android.support.annotation.NonNull;

import com.bing.lan.comm.app.AppUtil;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 配置公共参数
 */
public class CommParaInterceptor implements Interceptor {

    private static final String USER_AGENT_HEADER_NAME = "User-Agent";
    private static final String userAgentHeaderValue = "baogongyoucai";

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request originalRequest = chain.request();
        HttpUrl modifiedUrl = getNewHttpUrl(originalRequest.url());

        Request request = originalRequest.newBuilder()
                .url(modifiedUrl)
                //.removeHeader(USER_AGENT_HEADER_NAME)
                //.addHeader(USER_AGENT_HEADER_NAME, userAgentHeaderValue)
                .build();

        return chain.proceed(request);
    }

    @NonNull
    private HttpUrl getNewHttpUrl(HttpUrl oldUrl) {

        HttpUrl.Builder builder = oldUrl.newBuilder();
        // 参数
        //.addEncodedQueryParameter("v", "2")
        builder = builder.addQueryParameter("version", AppUtil.getVersionName() /*"1.3.4"*/);

        HttpUrl httpUrl = builder.build();
        return httpUrl;

        //HttpUrl newUrl = oldUrl.newBuilder()
        //        // 参数
        //        .addQueryParameter("version", AppUtil.getVersionName())
        //        //.addEncodedQueryParameter("v", "2")
        //        .build();
        //return newUrl;
    }
}
