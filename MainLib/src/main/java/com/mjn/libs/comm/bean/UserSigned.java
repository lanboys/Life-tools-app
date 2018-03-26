package com.mjn.libs.comm.bean;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @auther zhuwengang
 * @tiem 2017/11/29
 */

public class UserSigned implements Serializable {
    private Date month;
    private int todaySignStatus;
    private int signedTotalOfMonth;
    private int awardScore;
    private String awardRecordUrl;
    private List<UserSignDateInfo> monthSingDetail;

    public UserSigned() {
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public Date getMonth() {
        return this.month;
    }

    public void setTodaySignStatus(int todaySignStatus) {
        this.todaySignStatus = todaySignStatus;
    }

    public int getTodaySignStatus() {
        return this.todaySignStatus;
    }

    public void setSignedTotalOfMonth(int signedTotalOfMonth) {
        this.signedTotalOfMonth = signedTotalOfMonth;
    }

    public int getSignedTotalOfMonth() {
        return this.signedTotalOfMonth;
    }

    public void setAwardScore(int awardScore) {
        this.awardScore = awardScore;
    }

    public int getAwardScore() {
        return this.awardScore;
    }

    public void setAwardRecordUrl(String awardRecordUrl) {
        if (awardRecordUrl != null) {
            this.awardRecordUrl = awardRecordUrl;
        }
    }

    public String getAwardRecordUrl() {
        return this.awardRecordUrl;
    }

    public void setMonthSingDetail(List<UserSignDateInfo> monthSingDetail) {
        this.monthSingDetail = monthSingDetail;
    }

    public List<UserSignDateInfo> getMonthSingDetail() {
        return this.monthSingDetail;
    }
}
