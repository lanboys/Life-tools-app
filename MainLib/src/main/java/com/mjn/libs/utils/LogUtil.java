package com.mjn.libs.utils;

import android.util.Log;

public class LogUtil {

    public static boolean showLog = true;
    private static String TAG = "logUtil";


    public static void v(String msg) {
        v(TAG, msg);
    }

    public static void v(String tag, String msg) {
        if (showLog) {
            msg = formNull(msg);
            Log.v(tag, msg);
        }
    }

    public static void d(String msg) {
        d(TAG, msg);
    }

    public static void d(String tag, String msg) {
        if (showLog) {
            msg = formNull(msg);
            Log.d(tag, msg);
        }
    }

    public static void i(String msg) {
        i(TAG, msg);
    }

    public static void i(String tag, String msg) {
        if (showLog) {
            msg = formNull(msg);
            Log.i(tag, msg);
        }
    }

    public static void w(String msg) {
        w(TAG, msg);
    }

    public static void w(String tag, String msg) {
        if (showLog) {
            msg = formNull(msg);
            Log.w(tag, msg);
        }
    }

    public static void e(String msg) {
        e(TAG, msg);
    }

    public static void e(String tag, String msg) {
        if (showLog) {
            msg = formNull(msg);
            Log.e(tag, msg);
        }

    }

    private static String formNull(String value) {
        return value == null ? "null" : value;
    }

    public static void printeException(Throwable exception) {
        printeException(TAG, exception);
    }

    public static void printeException(String tag, Throwable exception) {
        if (showLog) {
            String msg = formNull(Log.getStackTraceString(exception));
            Log.e(tag, msg);
        }
    }

}
