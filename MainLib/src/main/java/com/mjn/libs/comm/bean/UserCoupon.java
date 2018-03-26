package com.mjn.libs.comm.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * @auther zhuwengang
 * @tiem 2017/11/29
 */

public class UserCoupon implements Serializable {
    private int userCouponId;
    private int userId;
    private String couponCode;
    private byte status;
    private String batchId;
    private String couponName;
    private long couponValue;
    private float addtionAmount;
    private Date expireDate;
    private Date effectiveDate;
    private String description;
    private long conditionMoney;
    private byte conditionMonth;
    private byte couponType;

    public UserCoupon() {
    }

    public void setUserCouponId(int userCouponId) {
        this.userCouponId = userCouponId;
    }

    public int getUserCouponId() {
        return this.userCouponId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setCouponCode(String couponCode) {
        if (couponCode != null) {
            this.couponCode = couponCode;
        }
    }

    public String getCouponCode() {
        return this.couponCode;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public byte getStatus() {
        return this.status;
    }

    public void setBatchId(String batchId) {
        if (batchId != null) {
            this.batchId = batchId;
        }
    }

    public String getBatchId() {
        return this.batchId;
    }

    public void setCouponName(String couponName) {
        if (couponName != null) {
            this.couponName = couponName;
        }
    }

    public String getCouponName() {
        return this.couponName;
    }

    public void setCouponValue(long couponValue) {
        this.couponValue = couponValue;
    }

    public long getCouponValue() {
        return this.couponValue;
    }

    public void setAddtionAmount(float addtionAmount) {
        this.addtionAmount = addtionAmount;
    }

    public float getAddtionAmount() {
        return this.addtionAmount;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public Date getExpireDate() {
        return this.expireDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getEffectiveDate() {
        return this.effectiveDate;
    }

    public void setDescription(String description) {
        if (description != null) {
            this.description = description;
        }
    }

    public String getDescription() {
        return this.description;
    }

    public void setConditionMoney(long conditionMoney) {
        this.conditionMoney = conditionMoney;
    }

    public long getConditionMoney() {
        return this.conditionMoney;
    }

    public void setConditionMonth(byte conditionMonth) {
        this.conditionMonth = conditionMonth;
    }

    public byte getConditionMonth() {
        return this.conditionMonth;
    }

    public void setCouponType(byte couponType) {
        this.couponType = couponType;
    }

    public byte getCouponType() {
        return this.couponType;
    }
}