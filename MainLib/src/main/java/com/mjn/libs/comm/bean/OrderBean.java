package com.mjn.libs.comm.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/14.
 */

public class OrderBean implements Serializable {


    /**
     * iProduct : {"productId":86,"categoryId":2,"title":"谢东测试标","annualYield":12,"addYield":2,"minInvestment":100000,"subjectAmount":150000000,"financialPeriod":"1个月","financialPeriodDay":30,"status":"PUBLISHING","joinDate":"2017-09-08","joinDateUnix":1504831251,"endDateUnix":1505490053,"remainAmount":148200000,"incomeMethod":"2","investmentRatio":1.2,"startDate":"购买立即计息","dueDate":"计息1个月后退出","riskLevel":"低","maxInvestment":10000000,"increaseInvestment":100000,"minRaiseAmount":0,"expectIncome":0,"isUseCoupon":"Y"}
     * paymentMethod : BankPay
     * orderId : 13
     * aomount : 100000
     * payStatus : SUCCESS
     * actualPaidAmount : 100000
     * success : true
     */

    private IProductBean iProduct;
    private String paymentMethod;
    private int orderId;
    private long aomount;
    private String payStatus;
    private long actualPaidAmount;
    private String phone;
    private boolean success;
    private String couponBatchId;
    private String couponType;

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public String getCouponBatchId() {
        return couponBatchId;
    }

    public void setCouponBatchId(String couponBatchId) {
        this.couponBatchId = couponBatchId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public IProductBean getIProduct() {
        return iProduct;
    }

    public void setIProduct(IProductBean iProduct) {
        this.iProduct = iProduct;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public long getAomount() {
        return aomount;
    }

    public void setAomount(long aomount) {
        this.aomount = aomount;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public long getActualPaidAmount() {
        return actualPaidAmount;
    }

    public void setActualPaidAmount(long actualPaidAmount) {
        this.actualPaidAmount = actualPaidAmount;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class IProductBean {
        /**
         * productId : 86
         * categoryId : 2
         * title : 谢东测试标
         * annualYield : 12.0
         * addYield : 2.0
         * minInvestment : 100000
         * subjectAmount : 150000000
         * financialPeriod : 1个月
         * financialPeriodDay : 30
         * status : PUBLISHING
         * joinDate : 2017-09-08
         * joinDateUnix : 1504831251
         * endDateUnix : 1505490053
         * remainAmount : 148200000
         * incomeMethod : 2
         * investmentRatio : 1.2
         * startDate : 购买立即计息
         * dueDate : 计息1个月后退出
         * riskLevel : 低
         * maxInvestment : 10000000
         * increaseInvestment : 100000
         * minRaiseAmount : 0
         * expectIncome : 0
         * isUseCoupon : Y
         */

        private int productId;
        private int categoryId;
        private String title;
        private double annualYield;
        private double addYield;
        private long minInvestment;
        private long subjectAmount;
        private String financialPeriod;
        private int financialPeriodDay;
        private String status;
        private String joinDate;
        private long joinDateUnix;
        private long endDateUnix;
        private long remainAmount;
        private String incomeMethod;
        private double investmentRatio;
        private String startDate;
        private String dueDate;
        private String riskLevel;
        private long maxInvestment;
        private long increaseInvestment;
        private long minRaiseAmount;
        private long expectIncome;
        private String isUseCoupon;

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public double getAnnualYield() {
            return annualYield;
        }

        public void setAnnualYield(double annualYield) {
            this.annualYield = annualYield;
        }

        public double getAddYield() {
            return addYield;
        }

        public void setAddYield(double addYield) {
            this.addYield = addYield;
        }

        public long getMinInvestment() {
            return minInvestment;
        }

        public void setMinInvestment(int minInvestment) {
            this.minInvestment = minInvestment;
        }

        public long getSubjectAmount() {
            return subjectAmount;
        }

        public void setSubjectAmount(int subjectAmount) {
            this.subjectAmount = subjectAmount;
        }

        public String getFinancialPeriod() {
            return financialPeriod;
        }

        public void setFinancialPeriod(String financialPeriod) {
            this.financialPeriod = financialPeriod;
        }

        public int getFinancialPeriodDay() {
            return financialPeriodDay;
        }

        public void setFinancialPeriodDay(int financialPeriodDay) {
            this.financialPeriodDay = financialPeriodDay;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getJoinDate() {
            return joinDate;
        }

        public void setJoinDate(String joinDate) {
            this.joinDate = joinDate;
        }

        public long getJoinDateUnix() {
            return joinDateUnix;
        }

        public void setJoinDateUnix(long joinDateUnix) {
            this.joinDateUnix = joinDateUnix;
        }

        public long getEndDateUnix() {
            return endDateUnix;
        }

        public void setEndDateUnix(long endDateUnix) {
            this.endDateUnix = endDateUnix;
        }

        public long getRemainAmount() {
            return remainAmount;
        }

        public void setRemainAmount(long remainAmount) {
            this.remainAmount = remainAmount;
        }

        public String getIncomeMethod() {
            return incomeMethod;
        }

        public void setIncomeMethod(String incomeMethod) {
            this.incomeMethod = incomeMethod;
        }

        public double getInvestmentRatio() {
            return investmentRatio;
        }

        public void setInvestmentRatio(double investmentRatio) {
            this.investmentRatio = investmentRatio;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getDueDate() {
            return dueDate;
        }

        public void setDueDate(String dueDate) {
            this.dueDate = dueDate;
        }

        public String getRiskLevel() {
            return riskLevel;
        }

        public void setRiskLevel(String riskLevel) {
            this.riskLevel = riskLevel;
        }

        public long getMaxInvestment() {
            return maxInvestment;
        }

        public void setMaxInvestment(long maxInvestment) {
            this.maxInvestment = maxInvestment;
        }

        public long getIncreaseInvestment() {
            return increaseInvestment;
        }

        public void setIncreaseInvestment(long increaseInvestment) {
            this.increaseInvestment = increaseInvestment;
        }

        public long getMinRaiseAmount() {
            return minRaiseAmount;
        }

        public void setMinRaiseAmount(long minRaiseAmount) {
            this.minRaiseAmount = minRaiseAmount;
        }

        public long getExpectIncome() {
            return expectIncome;
        }

        public void setExpectIncome(long expectIncome) {
            this.expectIncome = expectIncome;
        }

        public String getIsUseCoupon() {
            return isUseCoupon;
        }

        public void setIsUseCoupon(String isUseCoupon) {
            this.isUseCoupon = isUseCoupon;
        }
    }
}
