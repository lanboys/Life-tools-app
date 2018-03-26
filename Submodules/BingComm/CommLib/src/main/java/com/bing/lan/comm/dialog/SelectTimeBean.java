package com.bing.lan.comm.dialog;

/**
 * Author: yxhuang
 * Date: 2017/3/31
 * Email: yxhuang@gmail.com
 */

/**
 * 筛选中的时间日期
 */
public class SelectTimeBean {

    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;

    public String getTimeDetail() {
        return mYear + "-" + mMonth + "-" + mDay + " " + mHour + "-" + mMinute;
    }

    public int getYear() {
        return mYear;
    }

    public void setYear(int year) {
        mYear = year;
    }

    public int getMonth() {
        return mMonth;
    }

    public void setMonth(int month) {
        mMonth = month;
    }

    public int getDay() {
        return mDay;
    }

    public void setDay(int day) {
        mDay = day;
    }

    public int getHour() {
        return mHour;
    }

    public void setHour(int hour) {
        mHour = hour;
    }

    public int getMinute() {
        return mMinute;
    }

    public void setMinute(int minute) {
        mMinute = minute;
    }
}
