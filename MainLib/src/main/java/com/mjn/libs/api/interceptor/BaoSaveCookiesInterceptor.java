package com.mjn.libs.api.interceptor;

import android.content.Context;
import android.text.TextUtils;

import com.bing.lan.comm.api.interceptor.CookiesInterceptor;
import com.mjn.libs.utils.AppSpDataUtil;

import java.io.IOException;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

/**
 * 从 Response 中获取 set-cookie 字段的值，并保存在本地
 */
public class BaoSaveCookiesInterceptor extends CookiesInterceptor {

    public BaoSaveCookiesInterceptor(Context context) {
        super(context);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);

        if (!response.headers("set-cookie").isEmpty()) {
            List<String> cookies = response.headers("set-cookie");
            for (String cookie : cookies) {
                if (cookie.contains("token")) {
                    saveBaoToken(cookie);
                }
            }
        } else {
            log.i("没有Cookie,不做保存操作 ");
        }
        return response;
    }

    private void saveBaoToken(String cookie) {
        if (!TextUtils.isEmpty(cookie)
                && !AppSpDataUtil.getInstance().getCookies().contains(cookie)) {

            String temp = AppSpDataUtil.getInstance().getCookies();
            log.i("saveBaoToken()前: " + temp);
            temp += cookie + ";";
            log.i("saveBaoToken()前: " + temp);
            AppSpDataUtil.getInstance().setCookies(temp);
        }
    }
}
