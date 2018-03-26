package com.bing.lan.comm.api.interceptor;

import android.content.Context;
import android.text.TextUtils;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

/**
 * 将 Cookie 添加到 Header
 */
public class AddCookiesInterceptor extends CookiesInterceptor {

    public AddCookiesInterceptor(Context context) {
        super(context);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        String cookie = getCookie(request.url().toString(), request.url().host());
        if (!TextUtils.isEmpty(cookie)) {
            builder.addHeader("Cookie", cookie);
            log.i("添加 Cookie " + cookie);
        } else {
            log.i("没有 Cookie,不做添加操作");
        }

        Response response = chain.proceed(builder.build());
        return response;
    }
}
