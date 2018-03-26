package com.mjn.libs.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.mjn.libs.comm.bean.IUser;
import com.mjn.libs.db.DataSaveManager;

public class AppSpDataUtil extends BaseSpDataUtil {


    /**
     * 用户信息
     */
    private IUser userinfo;

    /**
     * 登录唯一标识
     */
    private String cookies = "";


    /**
     * 获取当前用户单例对象，如果
     *
     * @return
     */
    public static AppSpDataUtil getInstance() {
        return AppSpDataUtil.AppSpDataUtilLoader.INSTANCE;
    }

    /**
     * 私有构造器
     */
    private AppSpDataUtil() {
    }

    private static class AppSpDataUtilLoader {
        private final static AppSpDataUtil INSTANCE = new AppSpDataUtil();
    }

    public void saveUserBean(IUser data) {
        userinfo = data;
        BaseSpDataUtil.saveLocalJson(AppConfig.USERBEAN_CACHE, new Gson().toJson(data));
    }

    public IUser getUserBean() {
        if (userinfo == null) {
            String json = BaseSpDataUtil.getlocalJson(AppConfig.USERBEAN_CACHE);
            if (!TextUtils.isEmpty(json)) {
                userinfo = new Gson().fromJson(json, IUser.class);
            }
        }
        return userinfo;
    }

    /**
     * 判断用户是否登录
     *
     * @return true：标示已登录；false：标示未登录
     */
    public boolean isLogined() {
        getCookies();
        if (TextUtils.isEmpty(cookies)) {
            return false;
        }

        return true;
    }

    public String getCookies() {
        if (TextUtils.isEmpty(cookies)) {
            cookies = DataSaveManager.getInstance().read("locationcookies");
        }
        if (cookies == null) {
            cookies = "";
        }
        return cookies;
    }


    public void setCookies(String cookies) {
        if (cookies == null) {
            cookies = "";
        }
        this.cookies = cookies;
        // cookies存储到本地
        DataSaveManager.getInstance().save("locationcookies", cookies);
    }

    /**
     * 清除登录标记
     */
    public void clearUserInfo() {
        this.userinfo = null;
        setCookies("");
        AppConfig.DEVICE_TOKEN = "";
        DataSaveManager.getInstance().save("device_token", "");
        DataSaveManager.getInstance().save("client_type", "");
    }

    private String phone = "";

    public String getPhone() {
        if (TextUtils.isEmpty(phone)) {
            phone = DataSaveManager.getInstance().read("InputAccount");
        }
        if (phone == null) {
            phone = "";
        }
        return phone;
    }

    public void setPhone(String phone) {
        if (phone == null) {
            phone = "";
        }
        this.phone = phone;
        DataSaveManager.getInstance().save("InputAccount", phone);
    }
}
