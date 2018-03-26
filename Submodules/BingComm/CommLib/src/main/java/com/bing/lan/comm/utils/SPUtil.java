/**
 * 作者：artzok on 2016/9/6 12:14
 * 邮箱：artzok@163.com
 */
package com.bing.lan.comm.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.bing.lan.comm.app.AppConfig;
import com.bing.lan.comm.app.AppUtil;
import com.bing.lan.comm.encrypt.RxEncodeUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class SPUtil {

    private static SharedPreferences sp;

    private static SharedPreferences getSp() {
        if (sp == null)
            sp = AppUtil.getAppContext().getSharedPreferences(
                    AppConfig.SHARED_PREFER_FILE, Context.MODE_PRIVATE);
        return sp;
    }

    /**
     * get param of boolean from config.xml file
     *
     * @param key corresponding value's key
     * @return corresponding value
     */
    public static boolean getBoolean(@NonNull String key) {
        return getSp().getBoolean(key, false);
    }

    /**
     * set param of boolean to config.xml file
     *
     * @param key   corresponding value's key
     * @param value corresponding value
     */
    public static void putBoolean(@NonNull String key, boolean value) {
        getSp().edit().putBoolean(key, value).apply();
    }

    /**
     * get a string value of a certain key
     *
     * @param key someone certain key
     * @return except value or a null if not find
     */
    public static String getString(@NonNull String key) {
        return getSp().getString(key, "");
    }

    public static String getString(@NonNull String key, boolean isBase64Decode) {
        String value = getString(key);
        if (isBase64Decode) {
            value = RxEncodeUtils.base64Decode2String(value);
        }
        return value;
    }

    /**
     * store a string value of a certain key
     *
     * @param key   certain key
     * @param value correspond value
     */
    public static void putString(@NonNull String key, @NonNull String value) {
        getSp().edit().putString(key, value).apply();
    }

    public static void putString(@NonNull String key, @NonNull String value, boolean isBase64Encode) {
        if (isBase64Encode) {
            value = RxEncodeUtils.base64Decode2String(value);
        }
        putString(key, value);
    }

    /**
     * get a integer value of a certain key
     *
     * @param key someone certain key
     * @return except value or a 0 if not find
     */
    public static int getInt(@NonNull String key) {
        return getSp().getInt(key, 0);
    }

    /**
     * store a integer value of a certain key
     *
     * @param key   certain key
     * @param value correspond value
     */
    public static void putInt(@NonNull String key, int value) {
        getSp().edit().putInt(key, value).apply();
    }

    public static void clearAll() {
        getSp().edit().clear().apply();
    }

    /**
     * 保存List
     *
     * @param key
     * @param datalist
     */
    public static <T> void putList(String key, List<T> datalist) {
        if (null == datalist || datalist.size() <= 0) {
            getSp().edit().putString(key, "").apply();
        } else {
            Gson gson = new Gson();
            //转换成json数据，再保存
            String strJson = gson.toJson(datalist);
            getSp().edit().putString(key, strJson).apply();
        }
    }

    /**
     * 获取List
     *
     * @param key
     * @return
     */
    public static <T> List<T> getList(String key) {
        List<T> datalist = new ArrayList<T>();
        String strJson = getString(key);
        if (strJson.equals("")) {
            return datalist;
        }
        Gson gson = new Gson();
        datalist = gson.fromJson(strJson, new TypeToken<List<T>>() {
        }.getType());
        return datalist;
    }
}
