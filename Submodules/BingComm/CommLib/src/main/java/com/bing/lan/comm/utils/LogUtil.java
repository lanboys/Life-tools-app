package com.bing.lan.comm.utils;

import android.util.Log;

import com.bing.lan.comm.app.AppConfig;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LogUtil  implements Serializable {

    public static final int LOG_VERBOSE = 1;
    public static final int LOG_INFO = 2;
    public static final int LOG_DEBUG = 3;
    public static final int LOG_WARN = 4;
    public static final int LOG_ERROR = 5;
    public static final int LOG_NONE = 6;

    /* 日期格式 */
    private static SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm:ss", Locale.CHINA);
    /* 全局默认log打印等级 LOG_ERROR */
    private static int GLOBAL_LOG_LEVEL = LOG_ERROR;
    /* 默认的前缀 */
    private static String TAG_PREFIX = AppConfig.TAG_PREFIX;
    /* current class log level */
    private int mLogLevel;
    /* current class log tag */
    private String tag;

    private LogUtil(Class clz, int level) {
        tag = TAG_PREFIX + clz.getSimpleName();
        mLogLevel = level;
    }

    public static void setTagPrefix(String tagPrefix) {
        TAG_PREFIX = tagPrefix;
    }

    /**
     * set global log level
     * 更改全局log等级
     */
    public static void setGlobalLogLevel(int level) {
        GLOBAL_LOG_LEVEL = level;
    }

    /**
     * log tag equal clz name and log level equal logLevel
     *
     * @param clz      xx.getClass() or xx.class
     * @param logLevel 设置打印log的等级
     */
    public static LogUtil getLogUtil(Class clz, int logLevel) {
        return new LogUtil(clz, logLevel);
    }

    /**
     * log tag equal clz name and log level equal GLOBAL_LOG_LEVEL
     */
    public static LogUtil getLogUtil(Class clz) {
        return getLogUtil(clz, GLOBAL_LOG_LEVEL);
    }

    private String getPriorityLetter(int level) {
        switch (level) {
            case LOG_VERBOSE:
                return "Verbose";
            case LOG_INFO:
                return "Info";
            case LOG_DEBUG:
                return "Debug";
            case LOG_WARN:
                return "Warning";
            case LOG_ERROR:
                return "Error";
            // case LOG_NONE:
            //     return "Not_Log";
        }
        return null;
    }

    private String getFormat(String msg, int level) {
        return String.format("%s %s: %s\n", formatter.format(new Date()), getPriorityLetter(level), msg);
    }

    /**
     * @param level 根据log等级 确定是否打印
     * @return true 打印
     */
    private boolean ensureLevel(int level) {
        //日志总开关
        if (!AppConfig.LOG_DEBUG) {
            return false;
        }
        //GLOBAL_LOG_LEVEL 以上等级一定打印
        //e.g.: mLogLevel = 1; level = 1; 2; 3; 4; 5; ... 打印
        //e.g.: mLogLevel = 2; level = 2; 3; 4; 5; ... 打印
        return (GLOBAL_LOG_LEVEL <= level || mLogLevel <= level);
    }

    private void verbose(String tag, String msg, boolean format) {
        if (!ensureLevel(LOG_VERBOSE))
            return;
        if (format)
            msg = getFormat(msg, LOG_VERBOSE);
        print(LOG_VERBOSE, tag, msg, null);
    }

    private void info(String tag, String msg, boolean format) {
        if (!ensureLevel(LOG_INFO))
            return;
        if (format)
            msg = getFormat(msg, LOG_INFO);
        print(LOG_INFO, tag, msg, null);
    }

    private void debug(String tag, String msg, boolean format) {
        if (!ensureLevel(LOG_DEBUG))
            return;
        if (format)
            msg = getFormat(msg, LOG_DEBUG);
        print(LOG_DEBUG, tag, msg, null);
    }

    private void warn(String tag, String msg, Throwable tr, boolean format) {
        if (!ensureLevel(LOG_WARN))
            return;
        if (format)
            msg = getFormat(msg, LOG_WARN);
        print(LOG_WARN, tag, msg, tr);
    }

    private void error(String tag, String msg, Throwable tr, boolean format) {
        if (!ensureLevel(LOG_ERROR))
            return;
        if (format)
            msg = getFormat(msg, LOG_ERROR);
        print(LOG_ERROR, tag, msg, tr);
    }

    private void print(int level, String tag, String msg, Throwable throwable) {
        switch (level) {
            case LOG_VERBOSE:
                //Log.v(tag, msg);
                Log.e(tag, msg);
                break;
            case LOG_INFO:
                //Log.i(tag, msg);
                Log.e(tag, msg);
                break;
            case LOG_DEBUG:
                //Log.d(tag, msg);
                Log.e(tag, msg);
                break;
            case LOG_WARN:
                //Log.w(tag, msg, throwable);
                Log.e(tag, msg, throwable);
                break;
            case LOG_ERROR:
                Log.e(tag, msg, throwable);
                break;
        }

        // switch (level) {
        //     case LOG_VERBOSE:
        //         Logger.t(tag).v(msg);
        //         break;
        //     case LOG_INFO:
        //         Logger.t(tag).i(msg);
        //         break;
        //     case LOG_DEBUG:
        //         Logger.t(tag).d(msg);
        //         break;
        //     case LOG_WARN:
        //         Logger.t(tag).w(msg, throwable);
        //         break;
        //     case LOG_ERROR:
        //         Logger.t(tag).e(throwable, msg);
        //         break;
        // }
    }

    ////////////log verbose/////////////
    public void v(String msg) {
        v(tag, msg);
    }

    private void v(String tag, String msg) {
        verbose(tag, msg, false);
    }

    ////////////log verbose/////////////
    public void vfmat(String msg) {
        vfmat(tag, msg);
    }

    private void vfmat(String tag, String msg) {
        verbose(tag, msg, true);
    }

    ////////////log info/////////////
    public void i(String msg) {
        i(tag, msg);
    }

    private void i(String tag, String msg) {
        info(tag, msg, false);
    }

    ////////////log info/////////////
    public void ifmat(String msg) {
        ifmat(tag, msg);
    }

    private void ifmat(String tag, String msg) {
        info(tag, msg, true);
    }

    ////////////log debug/////////////
    public void d(String msg) {
        d(tag, msg);
    }

    private void d(String tag, String msg) {
        debug(tag, msg, false);
    }

    ////////////log debug/////////////
    public void dfmat(String msg) {
        dfmat(tag, msg);
    }

    private void dfmat(String tag, String msg) {
        debug(tag, msg, true);
    }

    ////////////log warn/////////////
    public void w(String msg) {
        w(tag, msg);
    }

    public void w(String msg, Throwable tr) {
        w(tag, msg, tr);
    }

    private void w(String tag, String msg) {
        w(tag, msg, null);
    }

    private void w(String tag, String msg, Throwable tr) {
        warn(tag, msg, tr, false);
    }

    ////////////log warn/////////////
    private void wfmat(String tag, String msg) {
        wfmat(tag, msg, null);
    }

    public void wfmat(String msg) {
        wfmat(tag, msg);
    }

    public void wfmat(String msg, Throwable tr) {
        wfmat(tag, msg, tr);
    }

    private void wfmat(String tag, String msg, Throwable tr) {
        warn(tag, msg, tr, true);
    }

    ////////////log error/////////////
    public void e(String msg) {
        e(tag, msg, null);
    }

    public void e(String msg, Throwable tr) {
        e(tag, msg, tr);
    }

    private void e(String tag, String msg) {
        e(tag, msg, null);
    }

    private void e(String tag, String msg, Throwable tr) {
        error(tag, msg, tr, false);
    }

    ////////////log error/////////////
    public void efmat(String msg) {
        efmat(tag, msg, null);
    }

    public void efmat(String msg, Throwable tr) {
        efmat(tag, msg, tr);
    }

    private void efmat(String tag, String msg) {
        efmat(tag, msg, null);
    }

    private void efmat(String tag, String msg, Throwable tr) {
        error(tag, msg, tr, true);
    }
}
