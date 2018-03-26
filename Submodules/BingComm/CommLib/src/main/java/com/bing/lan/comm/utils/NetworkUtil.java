package com.bing.lan.comm.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.Enumeration;

//添加权限       <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>

public class NetworkUtil {

    private static final int NET_CNNT_BAIDU_OK = 1; // NetworkAvailable
    private static final int NET_CNNT_BAIDU_TIMEOUT = 2; // no NetworkAvailable
    private static final int NET_NOT_PREPARE = 3; // Net no ready
    private static final int NET_ERROR = 4; //net error
    private static final int TIMEOUT = 3000; // TIMEOUT

    //没有网络连接
    private static final int NETWORK_NONE = 10;
    //wifi连接
    private static final int NETWORK_WIFI = 11;
    //手机网络数据连接类型
    private static final int NETWORK_2G = 12;
    private static final int NETWORK_3G = 13;
    private static final int NETWORK_4G = 14;
    private static final int NETWORK_MOBILE = 15;

    protected static final LogUtil log = LogUtil.getLogUtil(NetworkUtil.class, LogUtil.LOG_VERBOSE);

    /**
     * 返回当前网络状态
     */
    private static int getNetworkStateInt(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo networkinfo = connectivity.getActiveNetworkInfo();
                if (networkinfo != null) {
                    if (networkinfo.isAvailable() && networkinfo.isConnected()) {
                        if (!connectionNetwork())
                            return NET_CNNT_BAIDU_TIMEOUT;//超时
                        else
                            return NET_CNNT_BAIDU_OK;//可用
                    } else {
                        return NET_NOT_PREPARE;//未准备好
                    }
                }
            }
        } catch (Exception e) {
            log.e("getNetworkStateInt():  ", e);
        }
        return NET_ERROR;
    }

    public static String getNetworkState(Context context) {

        int networkStateInt = getNetworkStateInt(context);
        switch (networkStateInt) {
            case NET_CNNT_BAIDU_TIMEOUT:
                return "网络状态: 网络超时(百度测试)";
            case NET_CNNT_BAIDU_OK:
                return "网络状态: 网络可用(百度测试)";
            case NET_NOT_PREPARE:
                return "网络状态: 网络未准备好(百度测试)";
            case NET_ERROR:
                return "网络状态: 网络错误(百度测试)";
            default:
                return "网络状态: 未知状态";
        }
    }

    /**
     * 获取当前网络连接类型
     *
     * @param context
     * @return
     */
    private static int getNetworkTypeInt(Context context) {
        //获取系统的网络服务
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        //如果当前没有网络
        if (null == connManager)
            return NETWORK_NONE;

        //获取当前网络类型，如果为空，返回无网络
        NetworkInfo activeNetInfo = connManager.getActiveNetworkInfo();
        if (activeNetInfo == null || !activeNetInfo.isAvailable()) {
            return NETWORK_NONE;
        }

        // 判断是不是连接的是不是wifi
        NetworkInfo wifiInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (null != wifiInfo) {
            NetworkInfo.State state = wifiInfo.getState();
            if (null != state)
                if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
                    return NETWORK_WIFI;
                }
        }

        // 如果不是wifi，则判断当前连接的是运营商的哪种网络2g、3g、4g等
        NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (null != networkInfo) {
            NetworkInfo.State state = networkInfo.getState();
            String strSubTypeName = networkInfo.getSubtypeName();
            if (null != state)
                if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
                    switch (activeNetInfo.getSubtype()) {
                        //如果是2g类型
                        case TelephonyManager.NETWORK_TYPE_GPRS: // 联通2g
                        case TelephonyManager.NETWORK_TYPE_CDMA: // 电信2g
                        case TelephonyManager.NETWORK_TYPE_EDGE: // 移动2g
                        case TelephonyManager.NETWORK_TYPE_1xRTT:
                        case TelephonyManager.NETWORK_TYPE_IDEN:
                            return NETWORK_2G;
                        //如果是3g类型
                        case TelephonyManager.NETWORK_TYPE_EVDO_A: // 电信3g
                        case TelephonyManager.NETWORK_TYPE_UMTS:
                        case TelephonyManager.NETWORK_TYPE_EVDO_0:
                        case TelephonyManager.NETWORK_TYPE_HSDPA:
                        case TelephonyManager.NETWORK_TYPE_HSUPA:
                        case TelephonyManager.NETWORK_TYPE_HSPA:
                        case TelephonyManager.NETWORK_TYPE_EVDO_B:
                        case TelephonyManager.NETWORK_TYPE_EHRPD:
                        case TelephonyManager.NETWORK_TYPE_HSPAP:
                            return NETWORK_3G;
                        //如果是4g类型
                        case TelephonyManager.NETWORK_TYPE_LTE:
                            return NETWORK_4G;
                        default:
                            //中国移动 联通 电信 三种3G制式
                            if (strSubTypeName.equalsIgnoreCase("TD-SCDMA") || strSubTypeName.equalsIgnoreCase("WCDMA") || strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                                return NETWORK_3G;
                            } else {
                                return NETWORK_MOBILE;
                            }
                    }
                }
        }
        return NETWORK_NONE;
    }

    public static String getNetworkType(Context context) {

        int networkTypeInt = getNetworkTypeInt(context);
        switch (networkTypeInt) {
            case NETWORK_NONE:
                return "网络类型: 无网络";
            case NETWORK_WIFI:
                return "网络类型: wifi";
            case NETWORK_2G:
                return "网络类型: 2G";
            case NETWORK_3G:
                return "网络类型: 3G";
            case NETWORK_4G:
                return "网络类型: 4G";
            case NETWORK_MOBILE:
                return "网络类型: 未知网络";
            default:
                return "网络类型: 未知网络";
        }
    }

    /**
     * ping "http://www.baidu.com"
     *
     * @return
     */
    static private boolean connectionNetwork() {
        boolean result = false;
        HttpURLConnection httpUrl = null;
        try {
            httpUrl = (HttpURLConnection) new URL("http://www.baidu.com")
                    .openConnection();
            httpUrl.setConnectTimeout(TIMEOUT);
            httpUrl.connect();
            result = true;
        } catch (IOException e) {
        } finally {
            if (null != httpUrl) {
                httpUrl.disconnect();
            }
            httpUrl = null;
        }
        return result;
    }

    /**
     * check is3G
     *
     * @param context
     * @return boolean
     */
    public static boolean is3G(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            return true;
        }
        return false;
    }

    /**
     * isWifi
     *
     * @param context
     * @return boolean
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    /**
     * is2G
     *
     * @param context
     * @return boolean
     */
    public static boolean is2G(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && (activeNetInfo.getSubtype() == TelephonyManager.NETWORK_TYPE_EDGE
                || activeNetInfo.getSubtype() == TelephonyManager.NETWORK_TYPE_GPRS || activeNetInfo
                .getSubtype() == TelephonyManager.NETWORK_TYPE_CDMA)) {
            return true;
        }
        return false;
    }

    /**
     * is wifi on
     */
    public static boolean isWifiEnabled(Context context) {
        ConnectivityManager mgrConn = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        TelephonyManager mgrTel = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return ((mgrConn.getActiveNetworkInfo() != null && mgrConn
                .getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED) || mgrTel
                .getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS);
    }

    /**
     * check NetworkAvailable
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getApplicationContext().getSystemService(
                Context.CONNECTIVITY_SERVICE);
        if (null == manager)
            return false;
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        // if (null == networkInfo || !networkInfo.isAvailable())
        //     return false;
        // return true;

        return networkInfo != null && networkInfo.getState() == NetworkInfo.State.CONNECTED;
    }

    public static class NetWorkStatus {

        public boolean isNetworkAvailable = false;

        public String netWorkTip = "";
    }

    /**
     * getNetWorkTip
     */
    public static NetWorkStatus getNetWorkStatus(Context context) {

        NetWorkStatus netWorkStatus = new NetWorkStatus();
        netWorkStatus.netWorkTip = "当前没有网络连接,请确保你已经打开网络";//请检查网络状态
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();

        if (activeNetwork != null) {
            // connected to the internet
            if (activeNetwork.isConnected()) {
                netWorkStatus.isNetworkAvailable = true;
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                    // connected to wifi
                    netWorkStatus.netWorkTip = "当前WiFi连接可用";
                    log.i("getNetWorkTip(): 当前WiFi连接可用");
                } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    // connected to the mobile provider's data plan
                    netWorkStatus.netWorkTip = "当前移动网络连接可用";
                    log.i("getNetWorkTip(): 当前移动网络连接可用");
                }
            } else {
                log.e("getNetWorkTip(): 当前没有网络连接,请确保你已经打开网络");
            }
            log.i("getNetWorkTip(): TypeName：" + activeNetwork.getTypeName());
            log.i("getNetWorkTip(): SubtypeName：" + activeNetwork.getSubtypeName());
            log.i("getNetWorkTip(): State：" + activeNetwork.getState());
            log.i("getNetWorkTip(): DetailedState：" + activeNetwork.getDetailedState().name());
            log.i("getNetWorkTip(): ExtraInfo：" + activeNetwork.getExtraInfo());
            log.i("getNetWorkTip(): PhotoFlavour：" + activeNetwork.getType());
        } else {
            // not connected to the internet
            log.e("getNetWorkTip(): 当前没有网络连接,请确保你已经打开网络");
        }

        return netWorkStatus;
    }

    /**
     * getLocalIpAddress
     */
    public static String getLocalIpAddress() {
        String ret = "";
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        ret = inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException e) {
            log.e("getLocalIpAddress():  ", e);
        }
        return ret;
    }
}
