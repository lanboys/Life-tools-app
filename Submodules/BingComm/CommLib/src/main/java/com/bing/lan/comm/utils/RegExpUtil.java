package com.bing.lan.comm.utils;

import android.support.annotation.NonNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExpUtil {

    // 校验 手机六位数的 验证码
    public static boolean checkVcode(String vcode) {
        String regex = "^[0-9]{6}$";
        return check(regex, vcode);
    }

    public static boolean checkReg(String text) {
        Pattern pattern = Pattern.compile("^/w+$*");
        Matcher m = pattern.matcher(text);
        return m.matches();
    }

    public static boolean checkUserName(String password) {
        String regex = "^[0-9a-zA-Z@]+$";// [0-9a-zA-Z@]*
        return check(regex, password);
    }

    //由数字和字母组成，并且要同时含有数字和字母，且长度要在6-36位之间。
    public static boolean checkSnCode(String snCode) {
        Pattern pattern = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,36}$");
        Matcher m = pattern.matcher(snCode);
        return m.matches();
    }

    public static boolean checkIdCard(String sfzhm) {
        Pattern patternSfzhm1 = Pattern.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{4}$");
        Pattern patternSfzhm2 = Pattern.compile("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$");

        Matcher matcherSfzhm1 = patternSfzhm1.matcher(sfzhm);
        Matcher matcherSfzhm2 = patternSfzhm2.matcher(sfzhm);

        if (sfzhm.equals("")) {
            //showToast("身份证号码不能为空！");
            return false;
        } else if (!(sfzhm.equals("")) && !matcherSfzhm1.find() && !matcherSfzhm2.find()) {
            // showToast("身份证号码格式不正确，请重新输入！");
            return false;
        }
        return true;
    }

    public static boolean checkIdCard1(String sfzhm) {
        Pattern patternSfzhm1 = Pattern.compile("^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)$");
        Matcher matcherSfzhm1 = patternSfzhm1.matcher(sfzhm);

        if (sfzhm.equals("")) {
            //showToast("身份证号码不能为空！");
            return false;
        } else if (!(sfzhm.equals("")) && !matcherSfzhm1.find()) {
            // showToast("身份证号码格式不正确，请重新输入！");
            return false;
        }
        return true;
    }

    private void check(String dzyx, String sjhm, String dlzh, String xm, String dlmm) {
        Pattern patternSjhm = Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher matcherSjhm = patternSjhm.matcher(sjhm);
        Pattern patternDzyx = Pattern
                .compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
        Matcher matcherDzyx = patternDzyx.matcher(dzyx);

        //if (xm.equals("")) {
        //    showToast("姓名不能为空！");
        //} else if (dlzh.equals("")) {
        //    showToast("登录账号不能为空！");
        //} else if (dlmm.equals("")) {
        //    showToast("登录密码不能为空！");
        //}
        //
        //else if (!(sjhm.equals("")) && !(matcherSjhm.find())) {
        //    showToast("手机号码格式不正确，请重新输入！");
        //} else if (!(dzyx.equals("")) && !((matcherDzyx.find()))) {
        //    showToast("电子邮箱格式不正确，请重新输入！");
        //}
    }

    public static boolean checkChineseName(String name) {

        //name.matches()

        //String regx = "(([\u4E00-\u9FA5]{2,7})|([a-zA-Z]{3,10}))";
        //System.out.println(Pattern.matches(regx, "我我我我我我我"));
        //System.out.println(Pattern.matches(regx, "aBcDefghij"));

        //if (!name.matches("[\u4e00-\u9fa5]{2,7}")) {
        //    System.out.println("只能输入2到7个汉字");
        //    return false;
        //} else
        return true;
    }

    public static boolean checkNickName(String nikeName) {
        String regex = "^[a-zA-Z0-9_\u4e00-\u9fa5]{5,20}$";
        return check(regex, nikeName);
    }

    public static boolean checkEmail(String email) {
        Pattern pattern = Pattern
                .compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
        Matcher m = pattern.matcher(email);
        return m.matches();
    }

    public static boolean checkQQ(String qq) {
        String regx = "^[1-9][0-9]{4,14}$";
        boolean result = qq.matches(regx);
        return result;
    }

    public static boolean checkPhoneNum(String phone) {
        //String regex = "^(1(([35][0-9])|([4][57])|[8][0-9]))\\d{8}$";
        //String regex = "^(1(([3578][0-9])|([4][57])|[8][0-9]))\\d{8}$";
        String regex = "^1[3|4|5|7|8]\\d{9}$";

        return check(regex, phone);
    }

    /**
     * 正则表达式:验证身份证
     */
    public static boolean checkCard(String card) {
        String regex = "(^\\d{15}$)|(^\\d{17}([0-9]|X|x)$)";
        return check(regex, card);
    }

    public static boolean checkIdentityNum(String identityNum) {
        Pattern pattern = Pattern.compile("^(\\d{14}\\w)|\\d{17}\\w$");
        Matcher matcher = pattern.matcher(identityNum);
        return matcher.matches();
    }

    /**
     * 验证固话号码
     */
    public static boolean checkTelephone(String telephone) {
        String regex = "^(0\\d{2}-\\d{8}(-\\d{1,4})?)|(0\\d{3}-\\d{7,8}(-\\d{1,4})?)$";
        return check(regex, telephone);
    }

    /**
     * 验证传真号码
     */
    public static boolean checkFax(String fax) {
        String regex = "^(0\\d{2}-\\d{8}(-\\d{1,4})?)|(0\\d{3}-\\d{7,8}(-\\d{1,4})?)$";
        return check(regex, fax);
    }

    /**
     * 验证非空
     */
    public static boolean checkNotEmpty(String notEmpty) {
        String regex = "^\\s*$";
        return !check(regex, notEmpty);
    }

    /**
     * 验证 支付密码
     */
    public static boolean checkPayPassword(@NonNull String password) {
        String regex = "^[0-9]{6,7}$";
        return check(regex, password);
    }

    /**
     * 验证 普通密码
     */
    public static boolean checkPassword(String password) {
        //String regex = "^(?=.*?[a-z])(?=.*?[0-9])[a-zA-Z0-9_]{6,16}$";
        //String regex = "^[0-9a-zA-Z_]{8,20}$";
        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,20}$";//ios的
        return check(regex, password);
    }

    /**
     * 5到20个数字，字母
     */
    public static boolean checkAccount(String password) {
        String regex = "^([0-9a-zA-Z]+){5,20}$";//ios的
        return check(regex, password);
    }

    private static boolean check(String regex, String str) {
        boolean flag = false;
        try {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(str);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }
}
