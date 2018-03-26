package com.mjn.libs.utils;

import android.text.TextUtils;
import android.util.Base64;

import com.mjn.libs.cons.MConstant;
import com.mjn.libs.cons.SP_Constant;

public class AppUtil {

    public static boolean checkPwd(String pwd) {
        if (pwd.matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,12}$")) {
            return true;
        }
        return false;
    }


    public static int getPatternRetryTime() {
        int time = 0;
        try {
            String timeStr = SPUtil.getInstance().getString(SP_Constant.PATTERN_RETRYTIME);
            if (!TextUtils.isEmpty(timeStr)) {
                AES aes = new AES(MConstant.AES_KEY);
                String aesStr = new String(aes.decrypt(Base64.decode(timeStr.getBytes(), Base64.NO_WRAP)));
                time = Integer.valueOf(aesStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }

    public static void savePatternRetryTime(int time) {
        try {
            AES aes = new AES(MConstant.AES_KEY);
            String encodeStr = Base64.encodeToString(aes.encrypt(String.valueOf(time).getBytes()), Base64.NO_WRAP);
            SPUtil.getInstance().putString(SP_Constant.PATTERN_RETRYTIME, encodeStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
