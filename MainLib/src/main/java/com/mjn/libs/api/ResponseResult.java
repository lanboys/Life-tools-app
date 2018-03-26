package com.mjn.libs.api;

import java.io.Serializable;

/**
 * Created by 蓝兵 on 2018/3/22.
 */

public class ResponseResult<T> implements Serializable {

    public static final String REQUEST_CODE_SUCCESS = "0";

    private static final long serialVersionUID = 1L;
    private ResponseListDataResult<T> data;
    private String code;
    private String message;
    private Long servicetime = Long.valueOf(System.currentTimeMillis());
    private String debugmessage;

    public ResponseResult() {
    }

    public ResponseResult(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseListDataResult<T> getData() {
        return this.data;
    }

    public void setData(ResponseListDataResult<T> data) {
        this.data = data;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getServicetime() {
        return this.servicetime;
    }

    public void setServicetime(Long servicetime) {
        this.servicetime = servicetime;
    }

    public String getDebugmessage() {
        return this.debugmessage;
    }

    public void setDebugmessage(String debugmessage) {
        this.debugmessage = debugmessage;
    }
}
