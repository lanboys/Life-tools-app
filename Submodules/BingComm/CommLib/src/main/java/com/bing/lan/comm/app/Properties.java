package com.bing.lan.comm.app;

/**
 * 应用属性类，用于存放一些与应用或系统相关的属性值
 */
public class Properties {

    public String platform;      // 平台 1 IOS, 2 安卓；

    public int screen_width;      // 屏幕宽度

    public int screen_height;     // 屏幕高度

    public String phone_model;       // 手机品牌

    public String phone_imei;       // 手机IMEI

    public String mac_address;       // mac 地址

    public String hardware_vendors;  // 硬件厂商

    public int appVersionCode;

    public String appVersionName;

    public int phone_sdk_version;       // Sdk 版本

    @Override
    public String toString() {
        return "\n Properties{" +
                "\n platform='" + platform + '\'' +
                ",\n screen_width=" + screen_width +
                ",\n screen_height=" + screen_height +
                ",\n phone_model='" + phone_model + '\'' +
                ",\n phone_imei='" + phone_imei + '\'' +
                ",\n mac_address='" + mac_address + '\'' +
                ",\n hardware_vendors='" + hardware_vendors + '\'' +
                ",\n appVersionCode=" + appVersionCode +
                ",\n appVersionName='" + appVersionName + '\'' +
                ",\n sdk_version='" + phone_sdk_version + '\'' +
                '}';
    }
}