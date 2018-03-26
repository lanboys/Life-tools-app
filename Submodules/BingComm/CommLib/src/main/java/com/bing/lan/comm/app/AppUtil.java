package com.bing.lan.comm.app;

import android.app.Activity;
import android.app.AppOpsManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.os.Process;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.RawRes;
import android.support.v4.content.FileProvider;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.bing.lan.comm.utils.FileUtil;
import com.bing.lan.comm.utils.LogUtil;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class AppUtil {

    static boolean LOG_DEBUG = false;
    // Android监听连续点击次数代码实现
    private static long[] mHits;
    /* global application parameter */
    private static Context sContext;
    private static Handler sHandler;
    // private static Gson sGson;
    private static int sThreadId;
    private static Application sApplication;
    private static Resources sResources;
    /* global cache */
    private static Map<String, Object> sCacheMap;
    private static File sPicassoCacheFile;
    /* log */
    private static LogUtil log = LogUtil.getLogUtil(AppUtil.class, LogUtil.LOG_VERBOSE);

    public static void initGlobal(Application application, Context appCtx, boolean isDebug) {
        LOG_DEBUG = isDebug;

        sApplication = application;
        sContext = appCtx;
        // sGson = new Gson();
        sHandler = new Handler();
        sThreadId = Process.myTid();
        sCacheMap = new HashMap<>();
        sResources = sContext.getResources();
        // realm init
        //RealmManager.initRealm(sContext);
        //ImageLoader init
        //ImageLoader.init(application);
    }

    //    public static Object getGlobal(String key, Object defaultValue) {
    //        Object va = sCacheMap.get(key);
    //        if (va == null)
    //            return defaultValue;
    //        return va;
    //    }

    public static void putGlobal(String key, Object value) {
        sCacheMap.put(key, value);
    }

    public static boolean hitClick(int time, int hit) {
        if (mHits == null) {
            mHits = new long[hit];
        }
        System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
        mHits[mHits.length - 1] = SystemClock.uptimeMillis();

        log.i("hitClick----------------(): " + Arrays.toString(mHits));
        log.i("hitClick----------------(): " + (mHits[mHits.length - 1] - mHits[0]));

        if (mHits[mHits.length - 1] - mHits[0] < time) {
            mHits = new long[hit];
            return true;
        } else {
            return false;
        }
    }

    public static <T> T getGlobal(String key) {
        return getGlobal(key, null);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getGlobal(String key, T defaultValue) {
        T va = (T) sCacheMap.get(key);
        if (va == null)
            return defaultValue;
        return va;
    }

    public static DisplayMetrics getDisplayMetrics() {
        return getAppRes().getDisplayMetrics();
    }

    public static String formatByteSize(long size) {
        return Formatter.formatFileSize(getAppContext(), size);
    }

    public static Context getAppContext() {
        return sContext;
    }

    public static Application getApplication() {
        return sApplication;
    }

    public static int getMainThreadId() {
        return sThreadId;
    }

    public static Handler getMainHandler() {
        return sHandler;
    }

    // public static Gson getGson() {
    //     return sGson;
    // }

    public static Resources getAppRes() {
        return sResources;
    }

    public static AssetManager getAssets() {
        return sResources.getAssets();
    }

    public static String getPackageName() {
        return getAppContext().getPackageName();
    }

    public static String getCacheDir() {
        return FileUtil.getCacheDir();
    }

    public static String getString(int strId) {
        return sResources.getString(strId);
    }

    public static InputStream openRawResource(@RawRes int id) {
        return sResources.openRawResource(id);
    }

    public static String getString(int strId, String format) {
        return sResources.getString(strId, format);
    }

    public static String[] getStrArr(int arrId) {
        return sResources.getStringArray(arrId);
    }

    public static int[] getIntArr(int arrId) {
        return sResources.getIntArray(arrId);
    }

    public static int getInteger(int intId) {
        return sResources.getInteger(intId);
    }

    public static int getColor(int colorId) {
        return sResources.getColor(colorId);
    }

    /**
     * @param dimenId 单位是dp eg: <dimen name="height">140dp</dimen>
     * @return 返回的是 px
     */
    public static float getDimension(int dimenId) {
        return sResources.getDimension(dimenId);
    }

    public static int getIntByRandom() {

        return new Random(50).nextInt(30);
    }

    public static boolean getBooleanByRandom() {

        return getIntByRandom() % 2 == 0;
    }

    public static void startActivity(Context c, Class<? extends Activity> clazz,
            boolean isFinish, boolean addFlags) {

        Intent intent = new Intent(c, clazz);
        startActivity(c, intent, isFinish, addFlags);
    }

    public static void startActivity(Context c, Intent intent,
            boolean isFinish, boolean addFlags) {
        if (addFlags) {
            intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        }
        c.startActivity(intent);
        if (isFinish) {
            ((Activity) c).finish();
        }
    }

    /**
     * 安全执行任务
     *
     * @param runnable
     */
    public static void postTaskSafe(Runnable runnable) {
        postTaskSafeDelay(runnable, 0);
    }

    public static void postTaskSafeDelay(Runnable runnable, long delayMillis) {
        int currId = Process.myTid();
        if (currId == getMainThreadId() && delayMillis == 0) {
            runnable.run();
        } else {
            getMainHandler().postDelayed(runnable, delayMillis);
        }
    }

    public static int sp2px(float spValue) {
        final float fontScale = getAppRes().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int dp2px(int dip) {
        // denstity*dip=px;
        // float density = Resources.getSystem().getDisplayMetrics().density;
        float density = getAppRes().getDisplayMetrics().density;
        return (int) (dip * density + .5f);
    }

    public static int px2dp(int px) {
        // denstity*dip=px;
        // float density = Resources.getSystem().getDisplayMetrics().density;
        float density = getAppRes().getDisplayMetrics().density;
        return (int) (px / density + .5f);
    }

    //获得状态栏的高度
    public static int getStatusHeight() {
        int statusHeight = -1;
        try {
            Class clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = getAppContext().getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            log.e("getStatusHeight():  " + e.getLocalizedMessage());
        }
        return statusHeight;
    }

    public static void RunApp(String packageName) {
        PackageInfo pi;
        try {
            pi = getAppContext().getPackageManager().getPackageInfo(packageName, 0);
            Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
            // resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            resolveIntent.setPackage(pi.packageName);
            PackageManager pManager = getAppContext().getPackageManager();
            List<ResolveInfo> apps = pManager.queryIntentActivities(resolveIntent, 0);

            ResolveInfo ri = apps.iterator().next();
            if (ri != null) {
                packageName = ri.activityInfo.packageName;
                String className = ri.activityInfo.name;

                Intent intent = new Intent(Intent.ACTION_MAIN);
                // intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                ComponentName cn = new ComponentName(packageName, className);

                intent.setComponent(cn);
                getAppContext().startActivity(intent);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Intent createShareIntent(String shareTitle,
            String detailTitle,
            String msgText,
            File img) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        if (img == null || TextUtils.isEmpty(img.getAbsolutePath())) {
            intent.setType("text/plain");
        } else {
            Uri u = Uri.fromFile(img);
            intent.putExtra(Intent.EXTRA_STREAM, u);
            intent.setType("image/*");
        }
        intent.putExtra(Intent.EXTRA_SUBJECT, detailTitle);
        intent.putExtra(Intent.EXTRA_TEXT, msgText);
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        return Intent.createChooser(intent, shareTitle);
    }

    /**
     * 获取屏幕的高度
     */
    public static int getScreenH() {

        // DisplayMetrics metrics = new DisplayMetrics();
        //获取屏幕的显示信息
        // Display display = activity.getWindowManager().getDefaultDisplay();

        // 第一种方法
        // display.getMetrics(metrics);//数据在metrics中

        // 第二种方法
        // int screenWidth = display.getWidth();
        // int screenHeight = display.getHeight();

        // 第三种方法
        DisplayMetrics metrics = getAppContext().getResources().getDisplayMetrics();
        return metrics.heightPixels;
    }

    /**
     * 获取屏幕的宽度
     */
    public static int getScreenW() {
        DisplayMetrics metrics = getAppContext().getResources().getDisplayMetrics();
        return metrics.widthPixels;
    }

    /**
     * 获取系统版本；如4.x.x
     */
    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取硬件厂商
     */
    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * 获取手机型号
     */
    public static String getPhoneModel() {
        return Build.MODEL;
    }

    /**
     * 获取sdk版本
     */
    public static int getPhoneSDKVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取mac地址
     * Error:Error: The WIFI_SERVICE must be looked up on the Application context or memory will leak
     * on devices < Android N. Try changing to .getApplicationContext() [WifiManagerLeak]
     */
    public static String getMacAddress() {
        WifiManager wifi = (WifiManager) getAppContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }

    /**
     * 获取本机imei号，imei为空时用mav地址代替
     */
    public static String getPhoneIMEI() {
        Context context = getAppContext();
        TelephonyManager telephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = telephonyMgr.getDeviceId();
        if (imei == null || imei.trim().equals("0") || imei.trim().equals("")) {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            if (info == null) {
                imei = "NO IMEI No MAC";
            } else {
                imei = "MAC" + info.getMacAddress().replaceAll(":", "");
            }
        }
        return imei;
    }

    /**
     * 获取系统所有app信息
     */
    public static List<AppInfoBean> getAllAppInfo(/*Context context*/) {
        Context context = getAppContext();

        List<AppInfoBean> appInfoBeens = new ArrayList<>();
        //获取包管理器
        PackageManager packageManager = context.getPackageManager();
        //获取安装包的信息
        List<PackageInfo> infos = packageManager.getInstalledPackages(0);

        //遍历
        for (PackageInfo info : infos) {
            AppInfoBean appInfoBean = new AppInfoBean();

            //获取包名
            String packageName = info.packageName;
            appInfoBean.setAppPackageName(packageName);

            //获取app名称
            String label = info.applicationInfo.loadLabel(packageManager).toString();
            appInfoBean.setAppName(label);

            //获取app图标
            Drawable icon = info.applicationInfo.loadIcon(packageManager);
            appInfoBean.setAppIcon(icon);

            //获取app大小
            String path = info.applicationInfo.sourceDir;
            long size = new File(path).length();
            appInfoBean.setAppSize(size);

            //用户标志
            int flags = info.applicationInfo.flags;
            //系统标志
            int flaSystem = ApplicationInfo.FLAG_SYSTEM;

            if ((flags & flaSystem) == 1) {
                //系统软件
                appInfoBean.setUserApp(false);
            } else {
                //用户软件
                appInfoBean.setUserApp(true);
            }
            //安装位置标志
            int flagExternalStorage = ApplicationInfo.FLAG_EXTERNAL_STORAGE;

            if ((flags & flagExternalStorage) == 1) {
                //说明安装在sd卡
                appInfoBean.setSDApp(true);
            } else {
                //说明安装在内存
                appInfoBean.setSDApp(false);
            }
            appInfoBeens.add(appInfoBean);
        }

        return appInfoBeens;
    }

    /**
     * 启动app详情界面
     *
     * @param context
     */
    public static void goToDetailApp(Context context) {
       /* <action android:name="android.settings.APPLICATION_DETAILS_SETTINGS" />
        <category android:name="android.intent.category.DEFAULT" />
        <ta android:scheme="package" />*/
        Intent intent = new Intent();
        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        context.startActivity(intent);
    }

    /**
     * 打开分享界面
     *
     * @param activity
     * @param shareText 分享时显示的文本内容
     */
    public static void shareApp(Activity activity, String shareText) {
       /* <action android:name="android.intent.action.SEND" />
        <category android:name="android.intent.category.DEFAULT" />
        <data android:mimeType="text/plain" />*/
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SEND");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, shareText);
        activity.startActivity(intent);
    }

    /**
     * 开启其他app
     *
     * @param activity
     * @param apkPackagename
     */
    public static void startApp(Activity activity, String apkPackagename) {
        PackageManager pm = activity.getPackageManager();
        Intent intent = pm.getLaunchIntentForPackage(apkPackagename);
        if (intent != null) {
            activity.startActivity(intent);
        } else {
            Toast.makeText(activity, "该软件无法启动", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 卸载软件
     *
     * @param activity
     * @param apkPackagename
     */
    public static void uninstalllApp(Activity activity, String apkPackagename) {
       /* <action android:name="android.intent.action.VIEW" />
        <action android:name="android.intent.action.DELETE" />
        <category android:name="android.intent.category.DEFAULT" />
        <data android:scheme="package" />*/

        List<AppInfoBean> allAppInfo = getAllAppInfo();
        boolean isExit = false;
        AppInfoBean apkInfoBean = null;
        for (AppInfoBean appInfoBean : allAppInfo) {
            String apkPackagename1 = appInfoBean.getAppPackageName();
            if (apkPackagename1.equals(apkPackagename)) {
                apkInfoBean = appInfoBean;
                isExit = true;
            }
        }
        if (!isExit) {
            Toast.makeText(activity, "不存在该软件,无法卸载", Toast.LENGTH_SHORT).show();
            return;
        }

        if (apkInfoBean.isUserApp()) {

            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setAction("android.intent.action.DELETE");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.setData(Uri.parse("package:" + apkPackagename));
            activity.startActivity(intent);
        } else {
            Toast.makeText(activity, "系统软件root后才能卸载", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 回到launcher界面,即桌面
     *
     * @param activity
     */

    public static void startLauncher(Activity activity) {

        // <action android:name="android.intent.action.MAIN" />
        // <category android:name="android.intent.category.HOME" />
        // <category android:name="android.intent.category.DEFAULT" />
        // <category android:name="android.intent.category.MONKEY"/>

        Intent intent = new Intent();
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addCategory("android.intent.category.MONKEY");
        activity.startActivity(intent);
    }

    /**
     * 隐藏app图标
     *
     * @param activity
     */
    public static void hideAppIcon(Activity activity) {
        activity.getPackageManager().setComponentEnabledSetting(activity.getComponentName(),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }

    /**
     * 在桌面生成快捷方式
     * <p>
     * -- 获取在桌面创建快捷方式的权限--
     * <uses-permission android:name="com.android.launcher.permission.INSTALL_SHORTCUT"/>
     *
     * @param activity
     */

    public static void createShortCut(Activity activity, Bitmap iconBitmap, String appName, String action) {

        //在桌面创建快捷方式的意图
        Intent intent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        // 传递的图片
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON, iconBitmap);
        // 软件的名称
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, appName);
        // 点击这个快捷方式的意图(隐式意图)
        Intent shortCutIntent = new Intent();
        shortCutIntent.setAction(action);
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortCutIntent);
        //发送广播给桌面,创建快捷方式
        activity.sendBroadcast(intent);
    }

    /**
     * 获取应用程序名称
     *
     * @param context
     * @return
     */
    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("AppUtil", "getAppName: " + e.getLocalizedMessage());
        }
        return null;
    }

    /**
     * 获取应用程序版本名称信息
     *
     * @return 当前应用的版本名称
     */
    public static String getVersionName() {
        try {
            PackageManager packageManager = getAppContext().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(getAppContext().getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            log.e("getVersionName: ", e);
        }
        return "";
    }

    /**
     * 获取应用程序版本号信息
     *
     * @return 当前应用的版本名称
     */
    public static int getVersionCode() {
        try {
            PackageManager packageManager = getAppContext().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(getAppContext().getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            log.e("getVersionCode: ", e);
        }
        return 0;
    }

    public static boolean isInstalled(String packageName) {
        return isInstalled(getAppContext(), packageName);
    }

    /**
     * 判断包是否安装
     *
     * @param context
     * @param packageName
     * @return
     */

    public static boolean isInstalled(Context context, String packageName) {
        PackageManager manager = context.getPackageManager();
        try {
            manager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            log.e("isInstalled: 是否已安装app ", e);
            return false;
        }
    }

    /**
     * 安装应用程序
     *
     * @param context
     * @param apkFile
     */
    public static void installApp(Context context, File apkFile, String authority) {

        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
            Uri uri;
            if (Build.VERSION.SDK_INT >= 24) {
                uri = FileProvider.getUriForFile(context, authority, apkFile);
            } else {
                uri = Uri.fromFile(apkFile);
            }
            // http://blog.csdn.net/y505772146/article/details/55255344
            // https://stackoverflow.com/questions/5503487/android-failed-to-open-zip-archive
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            context.startActivity(intent);
        } catch (Exception e) {
            log.e("installApp():  " + e.getLocalizedMessage());
        }

        // Intent intent = new Intent(Intent.ACTION_VIEW);
        // intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        // intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        // context.startActivity(intent);
    }

    /**
     * 打开应用程序
     *
     * @param context
     * @param packageName
     */
    public static void openApp(Context context, String packageName) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        context.startActivity(intent);
    }

    public static boolean isNotificationEnabled(Context context) {

        try {
            //http://blog.csdn.net/jijiaxin1989/article/details/54142324
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
                ApplicationInfo appInfo = context.getApplicationInfo();
                String pkg = context.getApplicationContext().getPackageName();
                int uid = appInfo.uid;

                Class appOpsClass = Class.forName(AppOpsManager.class.getName());
                Method checkOpNoThrowMethod = appOpsClass.getMethod("checkOpNoThrow", Integer.TYPE, Integer.TYPE, String.class);
                Field opPostNotificationValue = appOpsClass.getDeclaredField("OP_POST_NOTIFICATION");

                int value = (Integer) opPostNotificationValue.get(Integer.class);
                return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 启动app详情界面
     */
    public static void goToSetting(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.BASE) {
            // 进入设置系统应用权限界面
            Intent intent = new Intent(Settings.ACTION_SETTINGS);
            context.startActivity(intent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {// 运行系统在5.x环境使用
            //Intent intent = new Intent(Settings.ACTION_SETTINGS);
            Intent intent = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);

            context.startActivity(intent);
        }
    }

    /**
     * 安装apk
     *
     * @param activity
     * @param requestCode
     * @param apkFile     安装包路径,用来生成uri
     */
    private void installApk(Activity activity, int requestCode, File apkFile) {
        //android系统里面要求系统做什么事
        //都是通过意图来表达(Intent)
               /* <action android:name="android.intent.action.VIEW" />
                <category android:name="android.intent.category.DEFAULT" />
                <data android:scheme="content" />
                <data android:scheme="file" />
                <data android:mimeType="application/vnd.android.package-archive" />*/
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");

        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        // startActivity(intent);//这种方法启动安装界面,安装完成后,将不会自动进入下一个界面,造成假死界面
        activity.startActivityForResult(intent, requestCode);
    }

    public static class AppInfoBean {

        private Drawable appIcon;
        private String appName;
        private String appPackageName;

        private long appSize;
        private boolean isUserApp;
        private boolean isSDApp;

        public AppInfoBean() {
        }

        public Drawable getAppIcon() {
            return appIcon;
        }

        public void setAppIcon(Drawable appIcon) {
            this.appIcon = appIcon;
        }

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

        public String getAppPackageName() {
            return appPackageName;
        }

        public void setAppPackageName(String appPackageName) {
            this.appPackageName = appPackageName;
        }

        public long getAppSize() {
            return appSize;
        }

        public void setAppSize(long appSize) {
            this.appSize = appSize;
        }

        public boolean isSDApp() {
            return isSDApp;
        }

        public void setSDApp(boolean isSDApp) {
            this.isSDApp = isSDApp;
        }

        public boolean isUserApp() {
            return isUserApp;
        }

        public void setUserApp(boolean isUserApp) {
            this.isUserApp = isUserApp;
        }
    }
}
