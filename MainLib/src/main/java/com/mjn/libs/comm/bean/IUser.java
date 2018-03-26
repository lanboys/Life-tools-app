package com.mjn.libs.comm.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/10/17.
 */

public class IUser implements Serializable {

    private Integer userId;
    private String token;
    private String userName;
    private String idCard;
    private String realName;
    private String mobile;
    private String userLevel;
    private String authStatus;
    private Long financesAmount = Long.valueOf(0L);
    private Long inviteAmount = Long.valueOf(0L);
    private Long yesterdayIncome = Long.valueOf(0L);
    private Long availableAmount = Long.valueOf(0L);
    private Long accumulatedIncome = Long.valueOf(0L);
    private String ifBuyPro;
    private Date registerTime;
    private Long couponAmount;
    private String inviteCode;
    private Integer freeCashNum;
    private String invitedFriendsUrl; // 邀请好友
    // 新增资产总额
    private Long totalAmount = Long.valueOf(0L);
    // 新增累计收益
    private Long totalIncome = Long.valueOf(0L);
    // 是否跳转到风险评级
    private boolean ifEvaluation;

    public boolean isIfEvaluation() {
        return ifEvaluation;
    }

    public void setIfEvaluation(boolean ifEvaluation) {
        this.ifEvaluation = ifEvaluation;
    }

    public Long getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(Long totalIncome) {
        this.totalIncome = totalIncome;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getInvitedFriendsUrl() {
        return invitedFriendsUrl;
    }

    public void setInvitedFriendsUrl(String invitedFriendsUrl) {
        this.invitedFriendsUrl = invitedFriendsUrl;
    }

    public IUser() {
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setToken(String token) {
        if (token != null) {
            this.token = token;
        }
    }

    public String getToken() {
        return this.token;
    }

    public void setUserName(String userName) {
        if (userName != null) {
            this.userName = userName;
        }
    }

    public String getUserName() {
        return this.userName;
    }

    public void setIdCard(String idCard) {
        if (idCard != null) {
            this.idCard = idCard;
        }
    }

    public String getIdCard() {
        return this.idCard;
    }

    public void setRealName(String realName) {
        if (realName != null) {
            this.realName = realName;
        }
    }

    public String getRealName() {
        return this.realName;
    }

    public void setMobile(String mobile) {
        if (mobile != null) {
            this.mobile = mobile;
        }
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setUserLevel(String userLevel) {
        if (userLevel != null) {
            this.userLevel = userLevel;
        }
    }

    public String getUserLevel() {
        return this.userLevel;
    }

    public void setAuthStatus(String authStatus) {
        if (authStatus != null) {
            this.authStatus = authStatus;
        }
    }

    public String getAuthStatus() {
        return this.authStatus;
    }

    public void setFinancesAmount(Long financesAmount) {
        this.financesAmount = financesAmount;
    }

    public Long getFinancesAmount() {
        return this.financesAmount;
    }

    public void setInviteAmount(Long inviteAmount) {
        this.inviteAmount = inviteAmount;
    }

    public Long getInviteAmount() {
        return this.inviteAmount;
    }

    public void setYesterdayIncome(Long yesterdayIncome) {
        this.yesterdayIncome = yesterdayIncome;
    }

    public Long getYesterdayIncome() {
        return this.yesterdayIncome;
    }

    public void setAvailableAmount(Long availableAmount) {
        this.availableAmount = availableAmount;
    }

    public Long getAvailableAmount() {
        return this.availableAmount;
    }

    public void setAccumulatedIncome(Long accumulatedIncome) {
        this.accumulatedIncome = accumulatedIncome;
    }

    public Long getAccumulatedIncome() {
        return this.accumulatedIncome;
    }

    public void setIfBuyPro(String ifBuyPro) {
        if (ifBuyPro != null) {
            this.ifBuyPro = ifBuyPro;
        }
    }

    public String getIfBuyPro() {
        return this.ifBuyPro;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Date getRegisterTime() {
        return this.registerTime;
    }

    public void setCouponAmount(Long couponAmount) {
        this.couponAmount = couponAmount;
    }

    public Long getCouponAmount() {
        return this.couponAmount;
    }

    public void setInviteCode(String inviteCode) {
        if (inviteCode != null) {
            this.inviteCode = inviteCode;
        }
    }

    public String getInviteCode() {
        return this.inviteCode;
    }

    public void setFreeCashNum(Integer freeCashNum) {
        this.freeCashNum = freeCashNum;
    }

    public Integer getFreeCashNum() {
        return this.freeCashNum;
    }
}

