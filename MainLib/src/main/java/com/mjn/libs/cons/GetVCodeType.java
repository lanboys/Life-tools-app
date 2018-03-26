package com.mjn.libs.cons;

/**
 * 获取验证码的类型
 */
public enum GetVCodeType {

    /**
     * 1,注册,2登录,3找回密码,4删除店铺,5支付密码,6认证,18添加银行卡，19修改银行卡
     */
    REGISTER("REGISTER"),
    PAY("PAY"),
    FIND_PASSWORD("FORGOT"),

    LOGIN("2"),
    DELETE_SHOP("4"),
    PAY_PASSWORD("5"),
    SHOP_AUTH("6"),
    REALNAME_BANK("10"),
    ADD_BANK_CARD("18"),
    MODIFY_BANK_CARD("19");

    private final String mType;

    GetVCodeType(String type) {
        this.mType = type;
    }

    public String getType() {
        return mType;
    }

}
