package com.bing.lan.comm.encrypt;

import android.support.annotation.NonNull;

import com.bing.lan.comm.utils.LogUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * @author 蓝兵
 * @time 2017/2/9  9:14
 */
public class MD5Util {

    protected static final LogUtil log = LogUtil.getLogUtil(MD5Util.class, LogUtil.LOG_VERBOSE);

    public static String MD5(String str) {
        String key;
        try {
            // 通过MD5加密 先获得数字摘要器
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] strBytes = str.getBytes();
            log.d("MD5(): " + Arrays.toString(strBytes));
            digest.update(strBytes);
            byte[] bytes = digest.digest();
            log.d("MD5(): " + Arrays.toString(bytes));
            key = bytesToHexString(bytes);
        } catch (NoSuchAlgorithmException e) {
            log.e("MD5():  ", e);
            key = String.valueOf(str.hashCode());
        }
        log.i("MD5(): key " + key);
        return key;
    }

    //将这个byte[]的每个元素 转换为字符串
    private static String bytesToHexString(@NonNull byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        log.d("bytesToHexString(): " + bytes.length);
        log.d("bytesToHexString(): " + Arrays.toString(bytes));
        for (byte bt : bytes) {
            // http://blog.csdn.net/scyatcs/article/details/16887807
            int i = 0xFF & bt;//???
            // http://blog.csdn.net/lan_bing2013/article/details/52864635
            String hex = Integer.toHexString(i); //将int 转换成 16 进制字符串 如: int(0011 0010)---> String("32")
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
            log.i("bytesToHexString(): " + sb);
        }
        return sb.toString();
    }
}
