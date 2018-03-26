package com.bing.lan.comm.utils;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by win7 on 2017/5/6.
 * * 过滤用户输入只能为金额格式
 */

public class CashierInputFilterUtil implements InputFilter {

    Pattern mPattern;

    //输入的最大金额
    private static final Double MAX_VALUE = Double.MAX_VALUE;
    //小数点后的位数
    private final int POINTER_LENGTH;

    private static final String POINTER = ".";

    private static final String ZERO = "0";

    public CashierInputFilterUtil() {
        this(2);//默认2位
    }

    public CashierInputFilterUtil(int pointLength) {
        POINTER_LENGTH = pointLength;
        mPattern = Pattern.compile("([0-9]|\\.)*");
    }

    protected final LogUtil log = LogUtil.getLogUtil(getClass(), LogUtil.LOG_VERBOSE);

    /**
     * @param source 新输入的字符串
     * @param start  新输入的字符串起始下标，一般为0
     * @param end    新输入的字符串终点下标，一般为source长度-1
     * @param dest   输入之前文本框内容
     * @param dstart 原内容起始坐标，一般为0
     * @param dend   原内容终点坐标，一般为dest长度-1
     * @return 输入内容
     */
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

        String newText = source.toString();
        String oldText = dest.toString();

        log.e("filter()  oldText: " + oldText + "  newText: " + newText + ",      oldText: " + oldText + ", dstart: "
                + dstart + ", dend: " + dend + ",      newText: " + newText + ", start: " + start + ", end: " + end);

        //验证删除等按键
        if (TextUtils.isEmpty(newText)) {
            return "";
        }

        //限制复制粘贴
        if (newText.length() > 1) {
            return "";
        }

        CharSequence oldCharSequence = dest.subSequence(dstart, dend);
        //log.i("filter(): oldCharSequence: " + oldCharSequence);

        Matcher matcher = mPattern.matcher(source);
        //已经输入小数点的情况下，只能输入数字
        if (oldText.contains(POINTER)) {
            if (!matcher.matches()) {
                return "";
            } else {
                if (POINTER.equals(source.toString())) {  //只能输入一个小数点
                    return "";
                }
            }

            //验证小数点精度，保证小数点后只能输入两位
            int index = oldText.indexOf(POINTER);

            //小数点位数达标后，并且在小数点后面输入
            if (oldText.length() - index > POINTER_LENGTH && dstart > index) {
                return oldCharSequence;
            }

            int length = dend - index;
            if (length > POINTER_LENGTH) {
                return oldCharSequence;
            }
        } else {

            if (!matcher.matches()) {
                return "";
            } else {

                //首位不能输入小数点
                if ((POINTER.equals(source.toString())) && TextUtils.isEmpty(oldText)) {
                    return "";

                    //如果首位输入0，接下来只能输入小数点
                } else if (!POINTER.equals(source.toString()) && ZERO.equals(oldText)) {
                    return "";
                }
            }
        }

        //有数值后首位 不能输入 0
        if (ZERO.equals(newText) && dstart == 0 && oldText.length() > 0) {
            return "";
        }

        //验证输入金额的大小,有bug ----- 中间插入数值
        double sumText = Double.parseDouble(oldText + newText);
        if (sumText > MAX_VALUE) {
            return oldCharSequence;
        }

        return oldCharSequence + newText;
    }
}