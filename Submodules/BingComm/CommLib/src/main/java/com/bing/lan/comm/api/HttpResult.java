package com.bing.lan.comm.api;

import com.google.gson.Gson;

/**
 * 统一处理返回数据
 */
public class HttpResult<T> {

    public static final int HTTP_CODE_SUCCESS = 200;

    private int errorCode;

    @Deprecated
    private int code;

    private String msg;

    private String status;

    private T data;

    public static <T> HttpResult<T> objectFromData(String str) {

        return new Gson().fromJson(str, HttpResult.class);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Deprecated
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "HttpResult{" +
                "errorCode=" + errorCode +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", status='" + status + '\'' +
                ", data=" + data +
                '}';
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
