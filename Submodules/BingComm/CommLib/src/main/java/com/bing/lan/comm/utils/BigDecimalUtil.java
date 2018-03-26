package com.bing.lan.comm.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by win7 on 2017/5/4.
 */
public class BigDecimalUtil {

    public static void print() {
        System.out.println(Long.MAX_VALUE);//最大数:9223372036854775807
        System.out.println(Long.MIN_VALUE);//最小数:-9223372036854775808
        System.out.println(Double.MAX_VALUE);//最大数:1.7976931348623157E308
        System.out.println(Double.MIN_VALUE);//最小数:4.9E-324
    }

    /**
     * Math.floor()
     * 通过该函数计算后的返回值是舍去小数点后的数值
     * 如：Math.floor(3.2)返回3
     * Math.floor(3.9)返回3
     * Math.floor(3.0)返回3
     * <p>
     * Math.ceil()
     * ceil函数只要小数点非0，将返回整数部分+1
     * 如：Math.ceil(3.2)返回4
     * Math.ceil(3.9)返回4
     * Math.ceil(3.0)返回3
     * <p>
     * double a = 12.111;
     * System.out.println(String.format("%.2f", a-0.005));
     * <p>
     * Double number = 1.226;
     * BigDecimal bd = new BigDecimal(number);
     * BigDecimal setScale = bd.setScale(2, bd.ROUND_DOWN);
     * System.out.println(setScale);
     * <p>
     * DecimalFormat df=new DecimalFormat(".##");
     * double d=1252.2545;
     * d=Double.valueOf(df.format(d));
     * System.out.println(d);
     */

    protected static final LogUtil log = LogUtil.getLogUtil(BigDecimalUtil.class, LogUtil.LOG_VERBOSE);

    public static String doubleStringFormat(String dou) {

        Double aDouble = null;
        try {
            aDouble = Double.valueOf(dou);
        } catch (Exception e) {
            log.e("doubleStr2String():  " + e.getLocalizedMessage());
        }

        if (aDouble == null) {
            return dou;
        }
        return double2String(aDouble);
    }

    //返回的是String类型的数据
    public static String double2String(double dou) {
        DecimalFormat df = new DecimalFormat(",##0.00");//会四舍五入
        String douStr = df.format(dou);
        return douStr;
    }

    public static String double2String1(double dou) {
        DecimalFormat df = new DecimalFormat(",###0.00");//会四舍五入
        String douStr = df.format(dou);
        return douStr;
    }

    public static String double2String2(double dou) {
        DecimalFormat df = new DecimalFormat("###0");//会四舍五入
        String douStr = df.format(dou);
        return douStr;
    }

    public static Double roundDouble(double dou, int i) {
        Double d = null;
        try {
            double factor = Math.pow(10, i);
            d = Math.floor(dou * factor + 0.5) / factor;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return d;
    }

    /**
     * 提供精确的加法运算。
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */

    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */

    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */

    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }
}
