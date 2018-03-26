package com.bing.lan.comm.api.interceptor;

import android.content.Context;

import java.io.IOException;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

/**
 * 从 Response 中获取 set-cookie 字段的值，并保存在本地
 */
public class SaveCookiesInterceptor  extends CookiesInterceptor {

    public SaveCookiesInterceptor(Context context) {
        super(context);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);

        if (!response.headers("set-cookie").isEmpty()) {
            List<String> cookies = response.headers("set-cookie");
            String cookie = encodeCookie(cookies);
            saveCookie(request.url().toString(), request.url().host(), cookie);
        } else {
            log.i("没有Cookie,不做保存操作 ");
        }
        return response;
    }


}
