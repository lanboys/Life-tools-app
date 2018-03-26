package com.bing.lan.comm.utils;

import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/8/30.
 */

public class EditTextUtil {
    /**
     * editText输入小数点后面几位
     *
     * @param view editText
     */
    public static void inputPiont(EditText view) {
        //输入的最大金额
        view.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER);//设置输入类型
        view.addTextChangedListener(new MyWatcher(Integer.MAX_VALUE,2));
//        InputFilter[] inputFilters = {new CashierInputFilter()};
//        view.setFilters(inputFilters);
    }

    public static class MyWatcher implements TextWatcher {
        private int beforeDot;
        private int afterDot;

        /**
         * 构造器
         *
         * @param beforeDot 小数点前位数  不限制输入-1
         * @param afterDot  小数点后位数  不限制输入-1
         */
        public MyWatcher(int beforeDot, int afterDot) {
            this.beforeDot = beforeDot;
            this.afterDot = afterDot;
        }


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            judge(s);
        }

        private void judge(Editable editable) {
            String temp = editable.toString();
            int posDot = temp.indexOf(".");
            //直接输入小数点的情况
            if (posDot == 0) {
                editable.insert(0, "0");
                return;
            }
            //连续输入0
            if (temp.equals("00")) {
                editable.delete(1, 2);
                return;
            }
            //输入"08" 等类似情况
            if (temp.startsWith("0") && temp.length() > 1 && (posDot == -1 || posDot > 1)) {
                editable.delete(0, 1);
                return;
            }

            //不包含小数点 不限制小数点前位数
            if (posDot < 0 && beforeDot == -1) {
                //do nothing 仅仅为了理解逻辑而已
                return;
            } else if (posDot < 0 && beforeDot != -1) {
                //不包含小数点 限制小数点前位数
                if (temp.length() <= beforeDot) {
                    //do nothing 仅仅为了理解逻辑而已
                } else {
                    editable.delete(beforeDot, beforeDot + 1);
                }
                return;
            }

            //如果包含小数点 限制小数点后位数
            if (temp.length() - posDot - 1 > afterDot && afterDot != -1) {
                editable.delete(posDot + afterDot + 1, posDot + afterDot + 2);//删除小数点后多余位数
            }
        }
    }


    public static class CashierInputFilter implements InputFilter {
        Pattern mPattern;

        //输入的最大金额
        private static final int MAX_VALUE = Integer.MAX_VALUE;
        //小数点后的位数
        private static final int POINTER_LENGTH = 2;

        private static final String POINTER = ".";

        private static final String ZERO = "0";

        public CashierInputFilter() {
            mPattern = Pattern.compile("([0-9]|\\.)*");
        }

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
            String sourceText = source.toString();
            String destText = dest.toString();

            //验证删除等按键
            if (TextUtils.isEmpty(sourceText)) {
                return "";
            }

            Matcher matcher = mPattern.matcher(source);
            //已经输入小数点的情况下，只能输入数字
            if (destText.contains(POINTER)) {
                if (!matcher.matches()) {
                    return "";
                } else {
                    if (POINTER.equals(source.toString())) {  //只能输入一个小数点
                        return "";
                    }
                }

                //验证小数点精度，保证小数点后只能输入两位
                int index = destText.indexOf(POINTER);
                int length = dend - index;

                if (length > POINTER_LENGTH) {
                    return dest.subSequence(dstart, dend);
                } else if (length > 0 && length <= POINTER_LENGTH) {
                    if (destText.substring(index, destText.length() - 1).length() >= 2) {
                        return "";
                    }
                }

            } else {
                /**
                 * 没有输入小数点的情况下，只能输入小数点和数字
                 * 1. 首位不能输入小数点
                 * 2. 如果首位输入0，则接下来只能输入小数点了
                 */
                if (!matcher.matches()) {
                    return "";
                } else {
                    if ((POINTER.equals(source.toString())) && TextUtils.isEmpty(destText)) {  //首位不能输入小数点
                        return "";
                    } else if (!POINTER.equals(source.toString()) && ZERO.equals(destText)) { //如果首位输入0，接下来只能输入小数点
                        return "";
                    }
                }
            }

            //验证输入金额的大小
            double sumText = Double.parseDouble(destText + sourceText);
            if (sumText > MAX_VALUE) {
                return dest.subSequence(dstart, dend);
            }
            return dest.subSequence(dstart, dend) + sourceText;
        }
    }
}
