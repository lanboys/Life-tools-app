package com.bing.lan.comm.api;

import com.bing.lan.comm.utils.LogUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class HttpGetPostUtil {

    protected static final LogUtil log = LogUtil.getLogUtil(HttpGetPostUtil.class, LogUtil.LOG_VERBOSE);

    /**
     * @return 如果成功返回数据 则返回JSON字符串	如果失败 则返回""
     */
    public static String doGet(String urlPath) {
        try {
            log.d("doGet() urlPath: " + urlPath);
            //创建一个URL网址
            URL url = new URL(urlPath);
            //打开一个Http链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置请求方法
            conn.setRequestMethod("GET");
            //请求成功后要返回数据
            int responseCode = conn.getResponseCode();
            log.d("doGet() 响应码: " + responseCode);

            if (responseCode == 200) {
                InputStream is = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                //JSON语句
                return reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * @return 如果成功返回数据 则返回JSON字符串	如果失败 则返回""
     */
    public static String doPost(String urlPath, HashMap<String, String> paramsMap) {
        log.d("doPost() urlPath: " + urlPath + "   " + paramsMap);
        try {
            //创建一个URL网址
            URL url = new URL(urlPath);
            //打开一个Http链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置请求方法
            conn.setRequestMethod("POST");
            // 请求头是可以忽略的
            //	提交请求数据
            String params = buildParams(paramsMap);

            conn.setDoOutput(true);
            conn.getOutputStream().write(params.getBytes());
            //请求成功后要返回数据
            int responseCode = conn.getResponseCode();
            log.d("doPost() 响应码: " + responseCode);
            if (responseCode == 200) {
                InputStream is = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                //JSON语句
                return reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String buildParams(HashMap<String, String> paramsMap) {
        String params = "";
        for (Entry<String, String> entry : paramsMap.entrySet()) {
            params += "&" + entry.getKey() + "=" + entry.getValue();
        }
        params = params.substring(1);
        return params;
    }

    /**
     * 传递get参数对应的map集合,返回拼接之后的字符串信息
     * <p>
     * eg:   https://www.so.com/s ? +++++++ q=%E7  &  +++++++ src=srp  & +++++++ fr=brw_er  & +++++++ pid=8f883ac
     *
     * @param map
     * @return
     */
    public static String getUrlParamsByMap(Map<String, Object> map) {
        if (map == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            sb.append(entry.getKey() + "=" + entry.getValue());
            sb.append("&");
        }
        String s = sb.toString();
        if (s.endsWith("&")) {
            // 将尾部拼接的 "&" 去掉
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }
}
