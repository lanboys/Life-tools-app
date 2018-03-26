package com.bing.lan.comm.api.interceptor;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.bing.lan.comm.utils.LogUtil;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Response;

public abstract class CookiesInterceptor implements Interceptor {

    public static final String COOKIE_PREF = "cookie_perfs";
    protected final LogUtil log = LogUtil.getLogUtil(getClass(), LogUtil.LOG_VERBOSE);
    private final SharedPreferences mSp;

    public CookiesInterceptor(Context context) {
        mSp = context.getSharedPreferences(COOKIE_PREF, Context.MODE_PRIVATE);
    }

    @Override
    public abstract Response intercept(Chain chain) throws IOException;

    public String getCookie(String url, String host) {
        log.i("获取 Cookie() url: " + url);
        log.i("获取 Cookie() host: " + host);

        if (!TextUtils.isEmpty(host)) {
            return mSp.getString(host, "");
        }

        return null;
    }

    // 保存 cookie 到本地
    public void saveCookie(String url, String host, String cookies) {

        log.i("保存 Cookie() url: " + url);
        log.i("保存 Cookie() host: " + host);
        log.i("保存 Cookie() cookies: " + cookies);

        if (!TextUtils.isEmpty(host) && !TextUtils.isEmpty(cookies)) {
            SharedPreferences.Editor editor = mSp.edit();
            editor.putString(host, cookies);
            editor.apply();
        }
    }

    // 整合 cookie 为唯一字符串
    public String encodeCookie(List<String> cookies) {
        StringBuilder builder = new StringBuilder();
        Set<String> set = new HashSet<>();
        for (String cookie : cookies) {
            String[] arr = cookie.split(";");
            for (String s : arr) {
                if (set.contains(s)) {
                    continue;
                }
                set.add(s);
            }
        }

        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            String cookie = iterator.next();
            builder.append(cookie).append(";");
        }

        int last = builder.lastIndexOf(";");
        if (builder.length() - 1 == last) {
            builder.deleteCharAt(last);
        }

        return builder.toString();
    }
}
