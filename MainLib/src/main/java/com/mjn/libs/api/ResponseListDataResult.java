package com.mjn.libs.api;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 蓝兵 on 2018/3/22.
 */

public class ResponseListDataResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    private long number;
    private long size;
    private long totalPages;
    private List<T> list;

    private String code;
    private String message;
    private Long servicetime = Long.valueOf(System.currentTimeMillis());
    private String debugmessage;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getServicetime() {
        return servicetime;
    }

    public void setServicetime(Long servicetime) {
        this.servicetime = servicetime;
    }

    public String getDebugmessage() {
        return debugmessage;
    }

    public void setDebugmessage(String debugmessage) {
        this.debugmessage = debugmessage;
    }

    public ResponseListDataResult() {
    }

    public long getNumber() {
        return this.number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public long getSize() {
        return this.size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getTotalPages() {
        return this.totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    public List<T> getList() {
        return this.list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
