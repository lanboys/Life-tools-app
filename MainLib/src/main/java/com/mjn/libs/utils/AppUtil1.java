package com.mjn.libs.utils;

public class AppUtil1 {

    public static boolean checkPwd(String pwd) {
        if (pwd.matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,12}$")) {
            return true;
        }
        return false;
    }
}
