package com.mjn.libs.comm.bean;

import java.io.Serializable;

/**
 * @auther zhuwengang
 * @tiem 2017/11/29
 */

public class IProRecord implements Serializable {
    private Integer orderId;
    private String title;
    private Long investment;
    private Long finishIncome;
    private String status;
    private Long expectIncome;
    private Float annualYield;
    private String startDate;
    private String dueDate;
    private String buyDate;
    private String financialPeriod;
    private Integer remainTime;
    private String creditUrl;
    private Integer incomeMethod;
    private String agreementUrl;

    public IProRecord() {
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderId() {
        return this.orderId;
    }

    public void setTitle(String title) {
        if (title != null) {
            this.title = title;
        }
    }

    public String getTitle() {
        return this.title;
    }

    public void setInvestment(Long investment) {
        this.investment = investment;
    }

    public Long getInvestment() {
        return this.investment;
    }

    public void setFinishIncome(Long finishIncome) {
        this.finishIncome = finishIncome;
    }

    public Long getFinishIncome() {
        return this.finishIncome;
    }

    public void setStatus(String status) {
        if (status != null) {
            this.status = status;
        }
    }

    public String getStatus() {
        return this.status;
    }

    public void setExpectIncome(Long expectIncome) {
        this.expectIncome = expectIncome;
    }

    public Long getExpectIncome() {
        return this.expectIncome;
    }

    public void setAnnualYield(Float annualYield) {
        this.annualYield = annualYield;
    }

    public Float getAnnualYield() {
        return this.annualYield;
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

    public void setBuyDate(String buyDate) {
        if (buyDate != null) {
            this.buyDate = buyDate;
        }
    }

    public String getBuyDate() {
        return this.buyDate;
    }

    public void setFinancialPeriod(String financialPeriod) {
        if (financialPeriod != null) {
            this.financialPeriod = financialPeriod;
        }
    }

    public String getFinancialPeriod() {
        return this.financialPeriod;
    }

    public void setRemainTime(Integer remainTime) {
        this.remainTime = remainTime;
    }

    public Integer getRemainTime() {
        return this.remainTime;
    }

    public void setCreditUrl(String creditUrl) {
        if (creditUrl != null) {
            this.creditUrl = creditUrl;
        }
    }

    public String getCreditUrl() {
        return this.creditUrl;
    }

    public void setIncomeMethod(Integer incomeMethod) {
        this.incomeMethod = incomeMethod;
    }

    public Integer getIncomeMethod() {
        return this.incomeMethod;
    }

    public void setAgreementUrl(String agreementUrl) {
        if (agreementUrl != null) {
            this.agreementUrl = agreementUrl;
        }
    }

    public String getAgreementUrl() {
        return this.agreementUrl;
    }
}

