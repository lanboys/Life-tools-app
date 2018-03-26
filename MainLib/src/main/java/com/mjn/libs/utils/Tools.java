package com.mjn.libs.utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.SlidingDrawer;
import android.widget.TextView;

import com.bing.lan.comm.mvp.fragment.BaseFragment;
import com.mjn.libs.R;
import com.umeng.analytics.AnalyticsConfig;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static com.mjn.libs.base.MainLibActivityModule.getSignParams;

public class Tools {

    /**
     * 创建Alert框
     */
    private static Builder builder;
    /**
     * 提示框
     */
    public static Dialog dialog;

    /**
     * toast提示信息
     *
     * @param message
     */
    public static final void toastShow(String message) {
        ToastAlone.show(message);
    }

    /**
     * 日志信息打印
     *
     * @param message
     */
    public static final void Log(String message) {
        if (AppConfig.DEBUG) {
            Log.i("MCYD", message);
        }
    }

    /**
     * 获取md5密码
     *
     * @param pwd
     * @return
     */
    public static final String getMd5Pwd(String pwd) {
        //        StringBuffer strbuf = new StringBuffer();
        //        String temp = toMd5("sp_".getBytes());
        //        strbuf.append(temp.substring(0, 12));
        //        strbuf.append(toMd5(pwd.getBytes()));
        //        strbuf.append(temp.substring(temp.length() - 4, temp.length()));
        return toMd5(pwd.getBytes());
    }

    /**
     * 设置根页面
     *
     * @param name
     */
    public static void setRootScreen(final String name) {
        AppConfig.fragmentManager.addOnBackStackChangedListener(new OnBackStackChangedListener() {

            @Override
            public void onBackStackChanged() {
                if (AppConfig.fragmentManager.getBackStackEntryCount() == 0) {
                    AppConfig.CurrentRootScreenName = name;
                }
                if (AppConfig.fragmentManager.getBackStackEntryCount() == 1) {
                    AppConfig.CurrentRootScreenName = AppConfig.fragmentManager.getBackStackEntryAt(0).getName();
                }
            }
        });
    }

    /**
     * 获取时间戳变体
     *
     * @param nowTimestamp
     * @return
     * @throws ParseException
     */
    public static String getMogoTimestamp(long nowTimestamp) throws ParseException {
        //		Date mogoDate = new Date();
        //		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //		formatter.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        //		mogoDate = formatter.parse("2013-09-22 18:20:06");
        long time = 1379845206000l;
        return String.valueOf(nowTimestamp - time);
    }

    private static Comparator<String> signComparator = new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            return o1.toLowerCase().compareTo(o2.toLowerCase());
        }
    };

    /**
     * 获取接口签名
     *
     * @return
     */
    public static String getSign(long timestamp) {
        StringBuffer strbuf = new StringBuffer();
        strbuf.append(timestamp);
        strbuf.append("&");
        strbuf.append("21b35fa480505dbae4a50668182e6008");

        byte[] temp = null;
        try {
            temp = strbuf.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
        return toMd5(temp);
    }

    /**
     * @param secret md5(用户密码)；或者是 md5（时间戳变体）
     * @return signValue
     * 公共请求参数md5加密，用于验签
     */
    public static String getSign(Map<String, String> tempMap, String secret) throws UnsupportedEncodingException {
        if (secret == null || tempMap == null) {
            return "";
        }
        if (tempMap.size() <= 0) {
            return "";
        }
        String timeStampValue = tempMap.get(AppConfig.KEY_TIMESTAMP_STRING);
        if (timeStampValue == null || timeStampValue != null && timeStampValue.length() <= 1) {
            return "";
        }
        Map<String, String> paramMap = new TreeMap<String, String>();
        paramMap.putAll(tempMap);
        StringBuilder paramStringBuffer = new StringBuilder();
        //升序操作，自定义排序导致验签失败
        //        Collections.sort(keyList, signComparator);
        // keySet()得到所有key的集合
        for (String key : paramMap.keySet()) {
            String value = paramMap.get(key);
            paramStringBuffer.append(key);
            paramStringBuffer.append("=");
            paramStringBuffer.append(value);
            paramStringBuffer.append("&");
            LogUtil.d("md5参数与值 排序后的参数：" + "key= " + key + " : " + "value= " + value);
        }
        if (!TextUtils.isEmpty(secret)) {
            paramStringBuffer.append("secret=");
            paramStringBuffer.append(secret);
        }
        byte[] temp = {0};
        temp = paramStringBuffer.toString().getBytes("UTF-8");
        Tools.Log("md5加密前字符的排序" + paramStringBuffer.toString());
        return toMd5(temp).toUpperCase();
    }

    /**
     * 渠道文件获取签名
     *
     * @param tempMap
     * @param secret
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getFileSign(Map<String, String> tempMap, String secret) throws UnsupportedEncodingException {
        if (secret == null || tempMap == null) {
            return "";
        }
        if (tempMap.size() <= 0) {
            return "";
        }
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.putAll(tempMap);
        String timeStampValue = paramMap.get("timestamp");
        if (timeStampValue == null || timeStampValue != null && timeStampValue.length() <= 1) {
            return "";
        }
        StringBuilder paramStringBuffer = new StringBuilder();
        List<String> keyList = new ArrayList<String>();
        //		keyList.add(SECRET_STRING);
        paramMap.put("secret", getSecret(secret));
        Set keysSet = paramMap.keySet();
        Iterator iterator = keysSet.iterator();
        while (iterator.hasNext()) {
            keyList.add(((String) iterator.next()));
        }

        //升序操作
        Collections.sort(keyList, signComparator);
        int timeMogoValue = getTimestampMogoIndex(timeStampValue);
        for (int i = 0; i < keyList.size(); i++) {
            paramStringBuffer.append(keyList.get(i));
            if (timeMogoValue == i || (timeMogoValue >= keyList.size() - 1 && i == keyList.size() - 1)) {
                paramStringBuffer.append("?");
            }
            paramStringBuffer.append("*");
            paramStringBuffer.append(paramMap.get(keyList.get(i)));
            if (i != keyList.size() - 1) {
                paramStringBuffer.append("?");
            }
        }
        byte[] temp = {0};
        temp = paramStringBuffer.toString().getBytes("UTF-8");
        return toMd5(temp).toUpperCase();
    }

    /**
     * 获取验签地址
     *
     * @param url
     * @return
     */
    public static String getSignUrl(String url) {
        String ref = url.substring(url.indexOf("?") + 1, url.length());

        StringBuffer signBuf = new StringBuffer();
        signBuf.append(ref);
        signBuf.append("&");
        signBuf.append("21b35fa480505dba");

        StringBuffer strbuf = new StringBuffer();
        strbuf.append(url);
        strbuf.append("&sign=");
        strbuf.append(Tools.toMd5(signBuf.toString().getBytes()));

        return strbuf.toString();
    }

    /**
     * 获取验签地址
     *
     * @param url
     * @return
     */
    public static String getSpecialSignUrl(String url) {
        //        StringBuffer signBuf = new StringBuffer();
        //        signBuf.append("uid=");
        //        signBuf.append(UserProperty.getInstance().getUserinfo().getUid());
        //        signBuf.append("&");
        //        signBuf.append("21b35fa480505dba");

        StringBuffer strbuf = new StringBuffer();
        //        strbuf.append(HTTP_HOST);
        //        strbuf.append("?");
        //        strbuf.append("uid=");
        //        strbuf.append(UserProperty.getInstance().getUserinfo().getUid());
        //        strbuf.append("&sign=");
        //        strbuf.append(Tools.toMd5(signBuf.toString().getBytes()));
        return strbuf.toString();
    }

    /**
     * md5加密param，生成secret
     *
     * @param param md5后的用户密码或者变体时间戳
     * @return
     */
    public static String getSecret(String param) throws UnsupportedEncodingException {
        String secret = "";
        if (param != null && !param.equalsIgnoreCase("")) {
            byte[] secretBytes = param.getBytes("UTF-8");
            if (secretBytes != null && secretBytes.length > 0) {
                secret = toMd5(secretBytes);
            }
        }
        return secret;
    }

    public static int getTimestampMogoIndex(String timestamp) {
        if (timestamp != null && timestamp.length() > 0) {
            char index = timestamp.charAt(timestamp.length() - 1);
            return Integer.parseInt(String.valueOf(index));
        } else {
            return -1;
        }
    }

    public static final char HEX_DIGITS[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f'};

    public static String toHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            sb.append(HEX_DIGITS[(bytes[i] & 0xf0) >>> 4]);
            sb.append(HEX_DIGITS[bytes[i] & 0x0f]);
        }
        return sb.toString();
    }

    /**
     * MD5 加密
     *
     * @param bytes
     * @return
     */
    public static String toMd5(byte[] bytes) {
        String result_md5 = "";
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(bytes);
            result_md5 = toHexString(algorithm.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result_md5;
    }

    /**
     * 获取一组动画序列
     *
     * @param ani
     * @return
     */
    public static Animation getGroupAni(Animation... ani) {
        if (ani == null || ani.length == 0) {
            return null;
        }
        AnimationSet aniSet = new AnimationSet(false);
        if (ani != null && ani.length > 0) {
            for (Animation animation : ani) {
                aniSet.addAnimation(animation);
            }
        }
        return aniSet;
    }

    /**
     * 获取平移动画
     *
     * @param durationMillis
     * @param startOffset
     * @param fromXDelta
     * @param toXDelta
     * @param fromYDelta
     * @param toYDelta
     * @param inter
     * @return
     */
    public static Animation getTranslateAni(long durationMillis, long startOffset, float fromXDelta, float toXDelta, float fromYDelta, float toYDelta, Interpolator inter) {
        TranslateAnimation ani = new TranslateAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta);
        ani.setDuration(durationMillis);
        ani.setFillAfter(true);
        ani.setInterpolator(inter);
        ani.setStartOffset(startOffset);
        return ani;
    }

    /**
     * 带物理效果的缩放
     *
     * @param duration
     * @param interpolator
     * @param from
     * @param to
     * @return
     */
    public static Animation getScaleAnimation(long duration, long startOffset, Interpolator interpolator, float from, float to) {
        Animation scaleAni = new ScaleAnimation(from, to, from, to, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAni.setDuration(duration);
        scaleAni.setInterpolator(interpolator);
        scaleAni.setStartOffset(startOffset);
        scaleAni.setFillAfter(true);
        return scaleAni;
    }

    /**
     * 获取透明度动画
     *
     * @param durationMillis
     * @param startOffset
     * @param fromAlpha
     * @param toAlpha
     * @return
     */
    public static Animation getAlphaAni(long durationMillis, long startOffset, float fromAlpha, float toAlpha) {
        AlphaAnimation alphaAni = new AlphaAnimation(fromAlpha, toAlpha);
        alphaAni.setDuration(durationMillis);
        alphaAni.setFillAfter(true);
        alphaAni.setStartOffset(startOffset);
        return alphaAni;
    }

    public static String substringBetween(String url, String key) {
        return Uri.parse(url).getQueryParameter(key);
    }

    public static float dp2px(Resources resources, float dp) {
        final float scale = resources.getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

    public static float sp2px(Resources resources, float sp) {
        final float scale = resources.getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

    /**
     * 显示带2个button的对话框
     *
     * @param title         标题
     * @param msg           内容
     * @param leftName      左按钮名称
     * @param rightName     右按钮名称
     * @param leftListener  左按钮事件
     * @param rightListener 右按钮事件
     */
    public static void showAlertDialog(String title, String msg,
            String leftName, String rightName, DialogInterface.OnClickListener leftListener,
            DialogInterface.OnClickListener rightListener) {
        builder = null;
        builder = new Builder(AppConfig.context);
        builder.setMessage(msg);
        builder.setTitle(title);
        builder.setPositiveButton(leftName, leftListener);
        builder.setNegativeButton(rightName, rightListener);
        final DialogInterface.OnClickListener cancelListener = leftListener;
        if (cancelListener != null) {
            builder.setOnCancelListener(new OnCancelListener() {

                @SuppressWarnings("static-access")
                public void onCancel(DialogInterface dialog) {
                    cancelListener.onClick(dialog, dialog.BUTTON_NEGATIVE);
                }
            });
        }
        if (dialog != null) {
            try {
                dialog.dismiss();
            } catch (Exception e) {
            }
        }

        dialog = builder.create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
    }

    /**
     * 显示带2个button的对话框
     *
     * @param title         标题
     * @param msg           内容
     * @param leftName      左按钮名称
     * @param rightName     右按钮名称
     * @param leftListener  左按钮事件
     * @param rightListener 右按钮事件
     */
    public static void showAlertDialogRightCancel(String title, String msg,
            String leftName, String rightName, DialogInterface.OnClickListener leftListener,
            DialogInterface.OnClickListener rightListener) {
        builder = null;
        builder = new Builder(AppConfig.context);
        builder.setMessage(msg);
        builder.setTitle(title);
        builder.setPositiveButton(leftName, leftListener);
        builder.setNegativeButton(rightName, rightListener);
        final DialogInterface.OnClickListener cancelListener = rightListener;
        if (cancelListener != null) {
            builder.setOnCancelListener(new OnCancelListener() {

                @SuppressWarnings("static-access")
                public void onCancel(DialogInterface dialog) {
                    cancelListener.onClick(dialog, dialog.BUTTON_NEGATIVE);
                }
            });
        }
        if (dialog != null) {
            try {
                dialog.dismiss();
            } catch (Exception e) {
            }
        }

        dialog = builder.create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
    }

    /**
     * 只设定提示内容的对话框
     *
     * @param msg
     */
    public static void showAlertDialog(String msg) {
        showAlertDialog("提示", msg, "确定", null, null);
    }

    /**
     * 可以设定标题和内容的对话框
     *
     * @param title
     * @param msg
     */
    public static void showAlertDialog(String title, String msg) {
        showAlertDialog(title, msg, "确定", null, null);
    }

    /**
     * 可以设定一个按钮事件的对话框
     *
     * @param msg     显示的内容
     * @param btnName 按钮名称
     */
    public static void showAlertDialog(String msg, String btnName, DialogInterface.OnClickListener btnOnclickListener) {
        showAlertDialog("提示", msg, null, btnName, null, btnOnclickListener);
    }

    /**
     * 显示带一个按钮并且可以设定cancel事件的对话框
     *
     * @param title          标题
     * @param msg            内容
     * @param leftName       按钮名称
     * @param leftListener   按钮事件
     * @param cancelListener 取消事件
     */
    public static void showAlertDialog(String title, String msg,
            String leftName, DialogInterface.OnClickListener leftListener,
            OnCancelListener cancelListener) {
        builder = null;
        builder = new Builder(AppConfig.context);
        builder.setMessage(msg);
        builder.setTitle(title);
        builder.setPositiveButton(leftName, leftListener);
        builder.setNegativeButton(null, null);
        builder.setOnCancelListener(cancelListener);

        if (dialog != null)
            dialog.dismiss();
        dialog = builder.show();
        dialog.setCanceledOnTouchOutside(false);
    }

    /**
     * 获取设备唯一标识
     *
     * @return
     */
    private static String deviceId;

    public static String getDeviceId() {
        try {
            if (TextUtils.isEmpty(deviceId)) {
                String imei = getIMEI();
                if (!TextUtils.isEmpty(imei)) {
                    deviceId = md5Encrypt(imei);
                } else {
                    String androidId = getAndroidId();
                    if (!TextUtils.isEmpty(androidId)) {
                        deviceId = md5Encrypt(androidId);
                    }
                }
            }
        } catch (Exception e) {
            deviceId = "Unknown";
        }
        return deviceId;
    }

    public static String getIMEI() {
        return com.bing.lan.comm.app.AppUtil.getPhoneIMEI();
    }

    public static String md5Encrypt(String string) {
        try {
            if (TextUtils.isEmpty(string)) {
                return null;
            }
            byte[] hash = null;
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
            StringBuilder hex = new StringBuilder(hash.length * 2);
            for (byte b : hash) {
                int i = (b & 0xFF);
                if (i < 0x10)
                    hex.append('0');
                hex.append(Integer.toHexString(i));
            }
            return hex.toString();
        } catch (Exception e) {
        }
        return null;
    }

    public static String getAndroidId() {
        try {
            return Settings.Secure.getString(AppConfig.context.getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 渠道文件内容取出索引
     */
    public static final int CHANNEL_TIMESTAMP = 0;//时间戳
    public static final int CHANNEL_SIGN = 1;//签名
    public static final int CHANNEL_SOURCEID = 2;//渠道号
    public static final int CHANNEL_PLATFORM = 3;//平台号
    public static final int CHANNEL_SUBVERSION = 4;//子版本号
    public static final int CHANNEL_SCHEME = 5;//跳转协议

    private static String appChannel;

    public static String getAppChannel() {
        if (TextUtils.isEmpty(appChannel)) {
            appChannel = AnalyticsConfig.getChannel(AppConfig.context);
            Log.i("appChannel=", appChannel);
        }
        return appChannel;
    }

    private static String appVersion;

    public static String getAppVersion() {
        if (TextUtils.isEmpty(appVersion)) {
            PackageManager manager = AppConfig.context.getPackageManager();
            try {
                PackageInfo info = manager.getPackageInfo(AppConfig.context.getPackageName(), 0);
                appVersion = info.versionName;
            } catch (Exception e) {
            }
        }
        return appVersion;
    }

    /**
     * 获取渠道文件内容
     *
     * @param zipFile
     * @param channelFile
     * @return
     */
    private static String[] getChannelFile(ZipFile zipFile, ZipEntry channelFile) {
        if (zipFile == null || channelFile == null) {
            return null;
        }
        String[] result = null;
        DataInputStream dIn = null;
        try {
            dIn = new DataInputStream(zipFile.getInputStream(channelFile));
            String temp = dIn.readUTF();
            dIn.close();
            if (temp != null) {
                char[] charArray = temp.toCharArray();
                for (int i = 0; i < charArray.length; i++) {
                    charArray[i] = (char) (charArray[i] ^ '8');
                }
                temp = new String(charArray);
                if (temp.contains("_")) {
                    result = temp.split("_");
                    if (result.length != 6) {
                        return null;
                    } else { //验证签名是否正确
                        Map<String, String> sinParams = new HashMap<String, String>();
                        sinParams.put("sourceId", result[CHANNEL_SOURCEID]);
                        sinParams.put("platform", result[CHANNEL_PLATFORM]);
                        sinParams.put("subVersion", result[CHANNEL_SUBVERSION]);
                        sinParams.put("scheme", result[CHANNEL_SCHEME]);
                        sinParams.put("timestamp", result[CHANNEL_TIMESTAMP]);
                        String sign = getFileSign(sinParams, "lecai_20140926");
                        sinParams = null;
                        if (!sign.equalsIgnoreCase(result[CHANNEL_SIGN])) {
                            return null;
                        } else {
                            return result;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (dIn != null) {
                try {
                    dIn.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 获取本机ip地址
     *
     * @return
     */
    public static String getLocalHostIp() {
        String ipaddress = "";
        try {
            Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces();
            // 遍历所用的网络接口
            while (en.hasMoreElements()) {
                NetworkInterface nif = en.nextElement();// 得到每一个网络接口绑定的所有ip
                Enumeration<InetAddress> inet = nif.getInetAddresses();
                // 遍历每一个接口绑定的所有ip
                while (inet.hasMoreElements()) {
                    InetAddress ip = inet.nextElement();

                    if (!ip.isLoopbackAddress() && ip instanceof Inet4Address) {
                        return ipaddress = ip.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return ipaddress;
    }

    /**
     * 判断是否符合手机号码
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobile(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9])|(14[7]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 动画绘制页面
     *
     * @param layoutId
     * @param baseFragment
     */
    public static final void aniInFragment(int layoutId, BaseFragment baseFragment) {
        FragmentTransaction transaction = AppConfig.fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fragment_slide_right_enter, R.anim.fragment_slide_right_exit);
        transaction.add(layoutId, baseFragment, "custom");
        transaction.addToBackStack("custom");
        transaction.commit();
    }

    /**
     * 动画隐藏页面
     *
     * @param baseFragment
     */
    public static final void aniOutFragment(BaseFragment baseFragment) {
        AppConfig.fragmentManager.popBackStackImmediate();
    }

    /**
     * 头像选择对话框
     */
    public static void chooseDialog() {
        //        final AlertDialog addPhotoDialog = new Builder(AppConfig.mainActivity).create();
        //        addPhotoDialog.show();
        //        View tempView = AppConfig.INFLATER.inflate(R.layout.select_photo, null);
        //        tempView.findViewById(R.id.takepht).setOnClickListener(new View.OnClickListener() { // 拍照
        //
        //            @Override
        //            public void onClick(View v) {
        //                selectPicFromCamera();// 照相
        //
        //                addPhotoDialog.dismiss();
        //            }
        //        });
        //        tempView.findViewById(R.id.openglry).setOnClickListener(new View.OnClickListener() { // 打开相册
        //
        //            @Override
        //            public void onClick(View v) {
        //                selectPicFromLocal(); // 相册
        //                addPhotoDialog.dismiss();
        //            }
        //        });
        //        tempView.findViewById(R.id.close_select_pht).setOnClickListener(new View.OnClickListener() { // 取消
        //
        //            @Override
        //            public void onClick(View v) {
        //                addPhotoDialog.dismiss();
        //            }
        //        });
        //        addPhotoDialog.setContentView(tempView);
        //        WindowManager windowManager = AppConfig.mainActivity.getWindowManager();
        //        Display display = windowManager.getDefaultDisplay();
        //        WindowManager.LayoutParams lp = addPhotoDialog.getWindow().getAttributes();
        //        lp.width = (int) (display.getWidth()); // 设置宽度
        //        addPhotoDialog.getWindow().setAttributes(lp);
        //
        //        Window window = addPhotoDialog.getWindow();
        //        window.setGravity(Gravity.BOTTOM);
        //        window.setWindowAnimations(R.style.bottom_dlg);
    }

    /**
     * 检测Sdcard是否存在
     *
     * @return
     */
    public static boolean isExitsSdcard() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            return true;
        else
            return false;
    }

    /**
     * 照相获取图片
     */
    public static void selectPicFromCamera() {
        if (!Tools.isExitsSdcard()) {
            String st = AppConfig.context.getResources().getString(R.string.sd_card_does_not_exist);
            Tools.toastShow(st);
            return;
        }

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //        if (takePictureIntent.resolveActivity(AppConfig.mainActivity.getPackageManager()) != null) {
        //            AppConfig.mainActivity.startActivityForResult(takePictureIntent, AppConfig.REQUEST_CODE_TAKE_PICTURE);
        //
        //        }
    }

    /**
     * 照相获取图片
     */
    public static void selectPicFromCamera(File file) {
        if (!Tools.isExitsSdcard()) {
            String st = AppConfig.context.getResources().getString(R.string.sd_card_does_not_exist);
            Tools.toastShow(st);
            return;
        }
        Intent imageCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        Uri uri = Uri.fromFile(file);
        imageCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        imageCaptureIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        //        AppConfig.mainActivity.startActivityForResult(imageCaptureIntent, AppConfig.REQUEST_CODE_TAKE_PICTURE_WITH_FILE);
    }

    /**
     * 从图库获取图片
     */
    public static void selectPicFromLocal() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
        } else {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        //        AppConfig.mainActivity.startActivityForResult(intent, AppConfig.REQUEST_PHOTO);
    }

    /**
     * 身份认证接口
     *
     * @param isIdCard   是否实名认证
     * @param params
     * @param imgData
     * @param idfront
     * @param idback
     * @param RequestURL
     * @return
     */
    public static String sendIdCheck(boolean isIdCard, byte[] imgData, byte[] idfront, byte[] idback, Map<String, String> params, String RequestURL) {
        String result = "";
        String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
        String CONTENT_TYPE = "multipart/form-data"; // 内容类型
        String PREFIX = "--", LINE_END = "\r\n";
        try {
            URL url = new URL(RequestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setDoInput(true); // 允许输入流
            conn.setDoOutput(true); // 允许输出流
            conn.setUseCaches(false); // 不允许使用缓存
            conn.setRequestMethod("POST"); // 请求方式
            conn.setRequestProperty("Charset", "utf-8"); // 设置编码
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Cookie", AppSpDataUtil.getInstance().getCookies());
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
            // 添加签名参数
            //            params.put("file", "img.jpg");
            long timestamp = (System.currentTimeMillis() - AppConfig.updateTimestamp) / 1000;
            Map<String, String> signParams = getSignParams(params, timestamp);
            Tools.Log("===sign:===" + signParams.get(AppConfig.KEY_SIGN));
            if (imgData != null) {
                /**
                 * 当文件不为空，把文件包装并且上传
                 */
                DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
                Set<String> keySet = signParams.keySet();
                for (Iterator<String> it = keySet.iterator(); it.hasNext(); ) {
                    String name = it.next();
                    String value = signParams.get(name);
                    dos.writeBytes("--" + BOUNDARY + "\r\n");
                    dos.writeBytes("Content-Disposition: form-data; name=\"" + name + "\"\r\n");
                    dos.writeBytes("\r\n");
                    dos.write(value.getBytes("utf-8"));
                    dos.writeBytes("\r\n");
                }
                //活体图片
                StringBuffer sb = new StringBuffer();
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINE_END);
                /**
                 * 这里重点注意： name里面的值为服务端需要key 只有这个key 才可以得到对应的文件
                 * filename是文件的名字，包含后缀名的 比如:abc.png
                 */

                sb.append("Content-Disposition: form-data; name=\"" + "file" + "\"; filename=\"" + "img.jpg" + "\""
                        + LINE_END);
                sb.append(LINE_END);
                dos.write(sb.toString().getBytes());
                dos.write(imgData);//写图片数据
                //                InputStream is = new FileInputStream(file);
                //                byte[] bytes = new byte[1024];
                //                int len = 0;
                //                while ((len = is.read(bytes)) != -1) {
                //                    dos.write(bytes, 0, len);
                //                }
                //                is.close();
                dos.write(LINE_END.getBytes());
                if (isIdCard) {
                    //身份证图片正面
                    sb.setLength(0);
                    sb.append(PREFIX);
                    sb.append(BOUNDARY);
                    sb.append(LINE_END);
                    sb.append("Content-Disposition: form-data; name=\"" + "idfront" + "\"; filename=\"" + "idfront.png" + "\"" + LINE_END);
                    sb.append(LINE_END);
                    dos.write(sb.toString().getBytes());
                    if (idfront != null) {
                        dos.write(idfront);//写图片数据
                    }
                    dos.write(LINE_END.getBytes());

                    //身份证图片反面
                    sb.setLength(0);
                    sb.append(PREFIX);
                    sb.append(BOUNDARY);
                    sb.append(LINE_END);
                    sb.append("Content-Disposition: form-data; name=\"" + "idback" + "\"; filename=\"" + "idback.png" + "\"" + LINE_END);
                    sb.append(LINE_END);
                    dos.write(sb.toString().getBytes());
                    if (idback != null) {
                        dos.write(idback);//写图片数据
                    }
                    dos.write(LINE_END.getBytes());
                }

                byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
                dos.write(end_data);
                dos.flush();
                dos.close();

                /**
                 * 获取响应码 200=成功 当响应成功，获取响应的流
                 */
                int res = conn.getResponseCode();
                if (res == 200) {
                    InputStream input = conn.getInputStream();
                    DataInputStream dataInputStream = new DataInputStream(input);
                    byte[] data = new byte[1024];
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    int len = 0;
                    while ((len = dataInputStream.read(data)) != -1) {
                        outputStream.write(data, 0, len);
                    }
                    result = new String(outputStream.toByteArray(), "utf-8");
                    outputStream.close();
                    dataInputStream.close();
                    input.close();
                    conn.disconnect();
                    Tools.Log(result);
                    return result;
                }
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return "";
    }

    /**
     * 保存图片
     *
     * @param imageName
     * @param bitmap
     * @return
     * @throws IOException
     */
    public static File saveImage(String imageName, Bitmap bitmap) throws IOException {
        return saveImage("img", imageName, bitmap);
    }

    public static File saveImage(String dir, String imageName, Bitmap bitmap) throws IOException {
        if (bitmap == null
                || !Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return null;
        }

        String sdCardDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        String path = sdCardDir + "/mjn/" + dir + "/";

        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }

        File image = new File(file, imageName);
        if (!image.exists()) {
            image.createNewFile();
        }
        FileOutputStream fout = new FileOutputStream(image);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fout);
        fout.flush();
        fout.close();
        fout = null;
        file = null;
        return image;
    }

    /**
     * 为保存图片或视频创建文件Uri
     */

    public static File getOutputMediaFileUri(int type) {

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return getOutputMediaFile(type);
        } else {
            return null;
        }
    }

    /**
     * 为保存图片或视频创建File
     */

    private static File getOutputMediaFile(int type) {

        //        File mediaStorageDir = null;

        //        if (type == AppConfig.MEDIA_TYPE_IMAGE) {
        //            mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "/mjn/img");
        //        } else if (type == AppConfig.MEDIA_TYPE_VIDEO) {
        //            mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES),
        //                    "/mjn");
        //        }
        //
        //        // 如果期望图片在应用程序卸载后还存在、且能被其它应用程序共享，
        //
        //        // 则此保存位置最合适
        //
        //        // 如果不存在的话，则创建存储目录
        //
        //        if (!mediaStorageDir.exists()) {
        //
        //            if (!mediaStorageDir.mkdirs()) {
        //
        //                return null;
        //
        //            }
        //        }
        //        // 创建媒体文件名
        //
        //        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        //
        //        File mediaFile;
        //
        //        if (type == AppConfig.MEDIA_TYPE_IMAGE) {
        //            mediaFile = new File(mediaStorageDir.getPath() + File.separator + System.currentTimeMillis() + ".png");
        //        } else if (type == AppConfig.MEDIA_TYPE_VIDEO) {
        //            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "VID_" + timeStamp + ".mp4");
        //        } else {
        //            return null;
        //        }

        //        return mediaFile;
        return null;
    }

    /**
     * 获取年月日日期格式
     *
     * @param time
     * @return
     */
    public static String getFullFormatDate(long time) {
        Date date = new Date();
        long oldTime = time;
        date.setTime(oldTime);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    public static String getFullFormatDate2(long time) {
        Date date = new Date();
        long oldTime = time;
        date.setTime(oldTime);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        return dateFormat.format(date);
    }

    public static String getFullFormatDate4(long time) {
        Date date = new Date();
        long oldTime = time;
        date.setTime(oldTime);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        return dateFormat.format(date);
    }

    /**
     * 获取年月格式
     * 签到改为   x年x月
     *
     * @param time
     * @return
     */
    public static String getFullFormatDate3(long time) {
        Date date = new Date();
        long oldTime = time;
        date.setTime(oldTime);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月");
        return dateFormat.format(date);
    }

    /**
     * 通过Java的反射技术来修改SlidingDrawer的TopOffset属性值
     *
     * @param sd
     * @param offset
     */
    public static void setScrollOutLength(SlidingDrawer sd, int offset) {
        try {
            Field field = sd.getClass().getDeclaredField("mTopOffset");
            field.setAccessible(true);
            field.set(sd, offset);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示自定义样式对话框
     *
     * @param title
     * @param msg
     * @param leftName
     * @param rightName
     */
    public static void showDialog(String title, String msg, String leftName, String rightName,
            final CustomAlertBtnCallback callback) {
        if (AppConfig.mainActivity == null)
            return;
        if (dialog != null) {
            try {
                dialog.dismiss();
            } catch (Exception e) {
            }
        }

        dialog = new Dialog(AppConfig.mainActivity, R.style.dialog);
        View view = AppConfig.INFLATER.inflate(R.layout.custom_alert, null);
        Button buttonEnsure = (Button) view.findViewById(R.id.button_ensure);
        Button buttonCancel = (Button) view.findViewById(R.id.button_cancel);
        TextView dialogTitle = (TextView) view.findViewById(R.id.textView_title);
        TextView line = (TextView) view.findViewById(R.id.dialog_btn_line);
        dialogTitle.setText(title);
        buttonEnsure.setText(rightName);
        if (rightName == null) {
            buttonEnsure.setVisibility(View.GONE);
            line.setVisibility(View.GONE);
        } else {
            buttonEnsure.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (callback != null) {
                        callback.ensure();
                    }
                }
            });
        }

        buttonCancel.setText(leftName);
        if (leftName == null) {
            buttonCancel.setVisibility(View.GONE);
            line.setVisibility(View.GONE);
        } else {
            buttonCancel.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (callback != null) {
                        callback.cancel();
                    }
                }
            });
        }
        ((TextView) view.findViewById(R.id.textView_message)).setText(msg);
        dialog.setContentView(view);
        dialog.setCancelable(true);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
    }

    /**
     * 对话框按钮点击回调
     */
    public interface CustomAlertBtnCallback {

        /**
         * 确认后加调
         */
        public void ensure();

        /**
         * 取消的回调
         */
        public void cancel();
    }

    public static String md5ByFile(File file) {
        String value = null;
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(byteBuffer);
            BigInteger bi = new BigInteger(1, md5.digest());
            value = bi.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }

    /**
     * 保留两位小数
     *
     * @param number
     * @return
     */
    @SuppressLint("NewApi")
    public static String getDecimal(double number) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        DecimalFormat formater = new DecimalFormat();
        formater.setMaximumFractionDigits(2);
        formater.setGroupingSize(0);
        formater.setRoundingMode(RoundingMode.DOWN);
        return decimalFormat.format(Double.parseDouble(formater.format(number)));
    }

    /**
     * 获取资源uri
     *
     * @param context
     * @param res
     * @return
     */
    public static Uri getResourceUri(Context context, int res) {
        try {
            Context packageContext = context.createPackageContext(context.getPackageName(),
                    Context.CONTEXT_RESTRICTED);
            Resources resources = packageContext.getResources();
            String appPkg = packageContext.getPackageName();
            String resPkg = resources.getResourcePackageName(res);
            String type = resources.getResourceTypeName(res);
            String name = resources.getResourceEntryName(res);

            Uri.Builder uriBuilder = new Uri.Builder();
            uriBuilder.scheme(ContentResolver.SCHEME_ANDROID_RESOURCE);
            uriBuilder.encodedAuthority(appPkg);
            uriBuilder.appendEncodedPath(type);
            if (!appPkg.equals(resPkg)) {
                uriBuilder.appendEncodedPath(resPkg + ":" + name);
            } else {
                uriBuilder.appendEncodedPath(name);
            }
            return uriBuilder.build();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 图片转成byte[]
     *
     * @param bm
     * @return
     */
    public static byte[] bitmap2Bytes(Bitmap bm) {
        if (bm == null) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * 从assets里获取文件
     *
     * @param fileName
     * @return
     */
    public static String getFromAssets(String fileName) {
        String result = "";
        try {
            InputStream in = AppConfig.mainActivity.getResources().getAssets().open(fileName);
            int lenght = in.available();
            byte[] buffer = new byte[lenght];
            in.read(buffer);
            result = new String(buffer, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
