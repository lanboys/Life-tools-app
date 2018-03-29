package com.mjn.libs.cons;

/**
 * Created by 蓝兵 on 2018/3/22.
 */

public class RequestActionCons implements IConstants {

    public static final int ACTION_GET_VCODE = 4444;
    public static final int ACTION_GET_VCODE_FORGET = 4442;
    public static final int ACTION_CHECK_VCODE = 4443;

    public static final int ACTION_LOGIN = 1000;
    public static final int ACTION_REGISTER = 1001;

    public static final int ACTION_UPDATE_HOME = 1010;

    public static final int ACTION_UPDATE_INVEST = 1020;
    public static final int ACTION_LOAD_MORE_INVEST = 1021;

    public static final int ACTION_UPDATE_INVEST_DETAIL = 1031;

    public static final int ACTION_UPDATE_ORDER_INFO = 1041;
    public static final int ACTION_GET_PAY_ORDER_VCODE = 1042;
    public static final int ACTION_GET_RE_PAY_ORDER_VCODE = 1043;
    public static final int ACTION_PAY_ORDER = 1044;

    public static final int ACTION_UPDATE_BANKCARD_LIST = 1051;
    public static final int ACTION_SET_DEFAULT_BANKCARD = 1052;
}
