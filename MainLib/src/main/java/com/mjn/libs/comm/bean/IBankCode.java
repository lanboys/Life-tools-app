package com.mjn.libs.comm.bean;

import java.io.Serializable;

/**
 * @auther zhuwengang
 * @tiem 2017/11/29
 */

public class IBankCode implements Serializable {
    private Integer bankCodeId;
    private String bankName;
    private String bankCode;

    public IBankCode() {
    }

    public void setBankCodeId(Integer bankCodeId) {
        this.bankCodeId = bankCodeId;
    }

    public Integer getBankCodeId() {
        return this.bankCodeId;
    }

    public void setBankName(String bankName) {
        if (bankName != null) {
            this.bankName = bankName;
        }
    }

    public String getBankName() {
        return this.bankName;
    }

    public void setBankCode(String bankCode) {
        if (bankCode != null) {
            this.bankCode = bankCode;
        }
    }

    public String getBankCode() {
        return this.bankCode;
    }
}

