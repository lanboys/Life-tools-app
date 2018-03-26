package com.bing.lan.comm.app;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.os.Bundle;

import com.bing.lan.comm.utils.LogUtil;

/**
 * Created by 蓝兵 on 2017/11/3.
 * <p>
 * http://blog.csdn.net/xx326664162/article/details/50727941
 * <p>
 * <meta-data android:name="UMENG_CHANNEL" android:value="dev"/>
 * <p>
 * String applicationMetaData = MetaDataUtil.getApplicationMetaData(this, "UMENG_CHANNEL");
 * Log.i(TAG, "applicationMetaData : " + applicationMetaData);
 * <p>
 * String flavor = BuildConfig.FLAVOR;//渠道名字
 */

public class MetaDataUtil {

    private static LogUtil log = LogUtil.getLogUtil(AppUtil.class, LogUtil.LOG_VERBOSE);

    public static String getApplicationMetaData(Application application, String name) {
        try {
            ApplicationInfo info = application.getPackageManager()
                    .getApplicationInfo(application.getPackageName(), PackageManager.GET_META_DATA);
            Bundle metaData = info.metaData;
            if (metaData != null) {
                return metaData.getString(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.e("getApplicationMetaData():  ", e);
        }
        return null;
    }

    public static String getActivityMetaData(Activity activity, String name) {
        try {
            ActivityInfo info = activity.getPackageManager()
                    .getActivityInfo(activity.getComponentName(), PackageManager.GET_META_DATA);
            Bundle metaData = info.metaData;
            if (metaData != null) {
                return metaData.getString(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.e("getActivityMetaData():  ", e);
        }
        return null;
    }

    public static String getServiceMetaData(Context context, Class<?> cls, String name) {
        try {
            ServiceInfo info = context.getPackageManager()
                    .getServiceInfo(new ComponentName(context, cls), PackageManager.GET_META_DATA);
            Bundle metaData = info.metaData;
            if (metaData != null) {
                return metaData.getString(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.e("getServiceMetaData():  ", e);
        }
        return null;
    }

    public static String getReceiverMetaData(Context context, Class<?> cls, String name) {
        try {
            ActivityInfo info = context.getPackageManager()
                    .getReceiverInfo(new ComponentName(context, cls), PackageManager.GET_META_DATA);
            Bundle metaData = info.metaData;
            if (metaData != null) {
                return metaData.getString(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.e("getReceiverMetaData():  ", e);
        }
        return null;
    }
}
