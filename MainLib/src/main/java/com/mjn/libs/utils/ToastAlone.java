package com.mjn.libs.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

public class ToastAlone extends Toast {

    private static Toast mToast = null;

    public ToastAlone(Context context) {
        super(context);
    }

    public static void show(String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        if (null == mToast) {
            mToast = Toast.makeText(AppConfig.context, text, Toast.LENGTH_SHORT);
        }
        mToast.setText(text);
        mToast.show();
    }

    public static void show(int textRid) {
        if (null == mToast) {
            mToast = Toast.makeText(AppConfig.context, textRid, Toast.LENGTH_SHORT);
        }
        mToast.setText(textRid);
        mToast.show();
    }

}