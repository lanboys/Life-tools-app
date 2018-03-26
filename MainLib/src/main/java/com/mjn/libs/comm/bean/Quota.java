package com.mjn.libs.comm.bean;

import java.io.Serializable;

/**
 * @auther zhuwengang
 * @tiem 2017/11/29
 */

public class Quota implements Serializable {
    private String bankCode;
    private String bankName;
    private String once;
    private String oneDay;

    public Quota() {
    }

    public void setBankCode(String bankCode) {
        if (bankCode != null) {
            this.bankCode = bankCode;
        }
    }

    public String getBankCode() {
        return this.bankCode;
    }

    public void setBankName(String bankName) {
        if (bankName != null) {
            this.bankName = bankName;
        }
    }

    public String getBankName() {
        return this.bankName;
    }

    public void setOnce(String once) {
        if (once != null) {
            this.once = once;
        }
    }

    public String getOnce() {
        return this.once;
    }

    public void setOneDay(String oneDay) {
        if (oneDay != null) {
            this.oneDay = oneDay;
        }
    }

    public String getOneDay() {
        return this.oneDay;
    }
}