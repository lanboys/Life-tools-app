package com.bing.lan.comm.app;

public class AppConfig {

    /**
     * sharePreference 文件名
     */
    public static final String SHARED_PREFER_FILE = "shared_prefer_file";
    /**
     * 缓存过期时间
     */
    public static final long SAFE_TIMER = 10000;

    /**
     * 无图模式
     */
    public static final String SETTING_NO_IMAGE = "no_image";
    /**
     * 日间/夜间模式存储的键值
     */
    public static final String DAY_NIGHT_MODE = "day_night_mode";
    /**
     * 日志前缀
     */
    public static final String TAG_PREFIX = "jzk-->";
    public static final String DB_NAME = "fm.db";
    public static final String UPLOAD_ERROR_FILE = "Upload[file]";
    public static final String UPLOAD_IMAGE_FILE = "Upload[file]";
    /**
     * 日志控制选项
     */
    public static boolean LOG_DEBUG = AppUtil.LOG_DEBUG;
}
