package com.mjn.libs.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import static android.content.Context.MODE_PRIVATE;

public class SPUtil {

    private final static String NAME = "com.mars.android.baselib.sputil";
    private static SharedPreferences saveInfo;
    private static SharedPreferences.Editor saveEditor;

    private static SPUtil instance;

    private SPUtil() {
        if (saveInfo == null) {
            saveInfo = AppConfig.context.getSharedPreferences(NAME, MODE_PRIVATE);
            saveEditor = saveInfo.edit();
        }
    }

    public static SPUtil getInstance() {
        if (instance == null) {
            instance = new SPUtil();
        }
        return instance;
    }

    public boolean putString(String key, String value) {
        saveEditor.putString(key, value);
        return saveEditor.commit();
    }

    public boolean putBoolean(String key, boolean value) {
        saveEditor.putBoolean(key, value);
        return saveEditor.commit();
    }

    public boolean putInt(String key, int value) {
        saveEditor.putInt(key, value);
        return saveEditor.commit();
    }

    public int getInt(String key) {
        return saveInfo.getInt(key, 0);
    }

    public int getInt(String key, int defaultValue) {
        return saveInfo.getInt(key, defaultValue);
    }

    public String getString(String key) {
        return saveInfo.getString(key, "");
    }

    public String getString(String key, String defaultValue) {
        return saveInfo.getString(key, defaultValue);
    }

    public boolean getBoolean(String key) {
        return saveInfo.getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean value) {
        return saveInfo.getBoolean(key, value);
    }


    /**
     * 存放实体类以及任意类型
     *
     * @param context 上下文对象
     * @param key
     * @param obj
     */
    public void putBean(Context context, String key, Object obj) {
        if (obj instanceof Serializable) {// obj必须实现Serializable接口，否则会出问题
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(obj);
                String string64 = new String(Base64.encode(baos.toByteArray(),
                        0));
                saveEditor.putString(key, string64).commit();
//                SharedPreferences.Editor editor = AppConfig.context.getSharedPreferences(NAME, MODE_PRIVATE).edit();
//                editor.putString(key, string64).commit();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            throw new IllegalArgumentException(
                    "the obj must implement Serializble");
        }
    }

    public Object getBean(Context context, String key) {
        Object obj = null;
        try {
            String base64 = AppConfig.context.getSharedPreferences(NAME, MODE_PRIVATE).getString(key, "");

            if (base64.equals("")) {
                return null;
            }
            byte[] base64Bytes = Base64.decode(base64.getBytes(), 1);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            obj = ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

}
