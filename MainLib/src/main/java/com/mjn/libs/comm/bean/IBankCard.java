package com.mjn.libs.comm.bean;

import java.io.Serializable;

/**
 * @auther zhuwengang
 * @tiem 2017/11/29
 */

public class IBankCard implements Serializable {
    private Integer bankCardId;
    private String bankName;
    private String cardNo;
    private String bankCode;
    private String isDefault;
    private String quota;
    private String bankBranch;

    public IBankCard() {
    }

    public void setBankCardId(Integer bankCardId) {
        this.bankCardId = bankCardId;
    }

    public Integer getBankCardId() {
        return this.bankCardId;
    }

    public void setBankName(String bankName) {
        if (bankName != null) {
            this.bankName = bankName;
        }
    }

    public String getBankName() {
        return this.bankName;
    }

    public void setCardNo(String cardNo) {
        if (cardNo != null) {
            this.cardNo = cardNo;
        }
    }

    public String getCardNo() {
        return this.cardNo;
    }

    public void setBankCode(String bankCode) {
        if (bankCode != null) {
            this.bankCode = bankCode;
        }
    }

    public String getBankCode() {
        return this.bankCode;
    }

    public void setIsDefault(String isDefault) {
        if (isDefault != null) {
            this.isDefault = isDefault;
        }
    }

    public String getIsDefault() {
        return this.isDefault;
    }

    public void setQuota(String quota) {
        if (quota != null) {
            this.quota = quota;
        }
    }

    public String getQuota() {
        return this.quota;
    }

    public void setBankBranch(String bankBranch) {
        if (bankBranch != null) {
            this.bankBranch = bankBranch;
        }
    }

    public String getBankBranch() {
        return this.bankBranch;
    }
}

