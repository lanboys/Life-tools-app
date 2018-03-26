package com.bing.lan.comm.utils.os;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Created by 蓝兵 on 2017/10/23.
 */

public class OsRomProperties {

    private static OsRomProperties ourInstance;

    public OsRomProperties() {
        properties = new Properties();
        try {
            properties.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static OsRomProperties getInstance() {
        if (ourInstance == null) {
            ourInstance = new OsRomProperties();
        }
        return ourInstance;
    }

    private Properties properties;

    public boolean containsKey(final Object key) {

        return properties.containsKey(key);
    }

    public boolean containsValue(final Object value) {

        return properties.containsValue(value);
    }

    public String getProperty(final String name) {

        return properties.getProperty(name);
    }

    public String getProperty(final String name, final String defaultValue) {

        return properties.getProperty(name, defaultValue);
    }

    public Set<Map.Entry<Object, Object>> entrySet() {

        return properties.entrySet();
    }

    public boolean isEmpty() {

        return properties.isEmpty();
    }

    public Enumeration keys() {

        return properties.keys();
    }

    public Set keySet() {

        return properties.keySet();
    }

    public int size() {

        return properties.size();
    }

    public Collection values() {

        return properties.values();
    }

    public String toJsonString() {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<Object, Object> entry : entrySet()) {
            builder.append("\"").append(entry.getKey()).append("\"").append(":").append("\"").append(entry.getValue()).append("\"").append(",");
        }
        String string = builder.toString();
        string = string.substring(0, string.length() - 1);
        return "OsRomProperties{" + string + '}';
    }
}
