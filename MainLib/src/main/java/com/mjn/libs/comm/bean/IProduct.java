package com.mjn.libs.comm.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/16.
 */

public class IProduct implements Serializable, IHomeItemBean {

    private Integer productId;
    private Integer categoryId;
    private String title;
    private Float annualYield;
    private Float addYield;
    private Long minInvestment;
    private Long subjectAmount;
    private String financialPeriod;
    private Long financialPeriodDay;
    private String status;
    private String joinDate;
    private Integer joinDateUnix;
    private Integer endDateUnix;
    private Long remainAmount;
    private String incomeMethod;
    private Float investmentRatio;
    private String startDate;
    private String dueDate;
    private String riskLevel;
    private Long maxInvestment;
    private Long increaseInvestment;
    private Long minRaiseAmount;
    private Long expectIncome;
    private Integer couponNum;
    private String isUseCoupon;
    private String introduceUrl;

    public IProduct() {
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getProductId() {
        return this.productId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getCategoryId() {
        return this.categoryId;
    }

    public void setTitle(String title) {
        if (title != null) {
            this.title = title;
        }
    }

    public String getTitle() {
        return this.title;
    }

    public void setAnnualYield(Float annualYield) {
        this.annualYield = annualYield;
    }

    public Float getAnnualYield() {
        return this.annualYield;
    }

    public void setAddYield(Float addYield) {
        this.addYield = addYield;
    }

    public Float getAddYield() {
        return this.addYield;
    }

    public void setMinInvestment(Long minInvestment) {
        this.minInvestment = minInvestment;
    }

    public Long getMinInvestment() {
        return this.minInvestment;
    }

    public void setSubjectAmount(Long subjectAmount) {
        this.subjectAmount = subjectAmount;
    }

    public Long getSubjectAmount() {
        return this.subjectAmount;
    }

    public void setFinancialPeriod(String financialPeriod) {
        if (financialPeriod != null) {
            this.financialPeriod = financialPeriod;
        }
    }

    public String getFinancialPeriod() {
        return this.financialPeriod;
    }

    public void setFinancialPeriodDay(long financialPeriodDay) {
        this.financialPeriodDay = financialPeriodDay;
    }

    public long getFinancialPeriodDay() {
        return this.financialPeriodDay;
    }

    public void setStatus(String status) {
        if (status != null) {
            this.status = status;
        }
    }

    public String getStatus() {
        return this.status;
    }

    public void setJoinDate(String joinDate) {
        if (joinDate != null) {
            this.joinDate = joinDate;
        }
    }

    public String getJoinDate() {
        return this.joinDate;
    }

    public void setJoinDateUnix(Integer joinDateUnix) {
        this.joinDateUnix = joinDateUnix;
    }

    public Integer getJoinDateUnix() {
        return this.joinDateUnix;
    }

    public void setEndDateUnix(Integer endDateUnix) {
        this.endDateUnix = endDateUnix;
    }

    public Integer getEndDateUnix() {
        return this.endDateUnix;
    }

    public void setRemainAmount(Long remainAmount) {
        this.remainAmount = remainAmount;
    }

    public Long getRemainAmount() {
        return this.remainAmount;
    }

    public void setIncomeMethod(String incomeMethod) {
        if (incomeMethod != null) {
            this.incomeMethod = incomeMethod;
        }
    }

    public String getIncomeMethod() {
        return this.incomeMethod;
    }

    public void setInvestmentRatio(Float investmentRatio) {
        this.investmentRatio = investmentRatio;
    }

    public Float getInvestmentRatio() {
        return this.investmentRatio;
    }

    public void setStartDate(String startDate) {
        if (startDate != null) {
            this.startDate = startDate;
        }
    }

    public String getStartDate() {
        return this.startDate;
    }

    public void setDueDate(String dueDate) {
        if (dueDate != null) {
            this.dueDate = dueDate;
        }
    }

    public String getDueDate() {
        return this.dueDate;
    }

    public void setRiskLevel(String riskLevel) {
        if (riskLevel != null) {
            this.riskLevel = riskLevel;
        }
    }

    public String getRiskLevel() {
        return this.riskLevel;
    }

    public void setMaxInvestment(Long maxInvestment) {
        this.maxInvestment = maxInvestment;
    }

    public Long getMaxInvestment() {
        return this.maxInvestment;
    }

    public void setIncreaseInvestment(Long increaseInvestment) {
        this.increaseInvestment = increaseInvestment;
    }

    public Long getIncreaseInvestment() {
        return this.increaseInvestment;
    }

    public void setMinRaiseAmount(Long minRaiseAmount) {
        this.minRaiseAmount = minRaiseAmount;
    }

    public Long getMinRaiseAmount() {
        return this.minRaiseAmount;
    }

    public void setExpectIncome(Long expectIncome) {
        this.expectIncome = expectIncome;
    }

    public Long getExpectIncome() {
        return this.expectIncome;
    }

    public void setCouponNum(Integer couponNum) {
        this.couponNum = couponNum;
    }

    public Integer getCouponNum() {
        return this.couponNum;
    }

    public void setIsUseCoupon(String isUseCoupon) {
        if (isUseCoupon != null) {
            this.isUseCoupon = isUseCoupon;
        }
    }

    public String getIsUseCoupon() {
        return this.isUseCoupon;
    }

    public void setIntroduceUrl(String introduceUrl) {
        if (introduceUrl != null) {
            this.introduceUrl = introduceUrl;
        }
    }

    public String getIntroduceUrl() {
        return this.introduceUrl;
    }

    private int homeBeanType;

    @Override
    public void setHomeBeanType(@HomeBeanType int homeBeanType) {
        this.homeBeanType = homeBeanType;
    }

    @Override
    public int getHomeBeanType() {
        return homeBeanType;
    }
}

