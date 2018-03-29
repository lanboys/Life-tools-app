package com.mjn.libs.cons;

/**
 * Created by 蓝兵 on 2018/3/21.
 */

public class UIRouterCons implements IConstants {

    // 主项目 ui 路由信息
    public static final String MAIN_APP_ROUTE_NODE_PATH = "/mainApp";
    public static final String MAIN_APP_ROUTE_NODE_DESC = "首页";

    public static final String MAIN_APP_AUTOWIRED_NAME = "bookName";
    public static final String MAIN_APP_AUTOWIRED_DESC = "首页书名";

    //    投资详情页面 ui 路由信息
    public static final String INVEST_DETAIL_ROUTE_NODE_PATH = "/detail";
    public static final String INVEST_DETAIL_ROUTE_NODE_DESC = "投资详情页";

    public static final String INVEST_DETAIL_AUTOWIRED_PRODUCT_ID = "productId";
    public static final String INVEST_DETAIL_AUTOWIRED_PRODUCT_ID_DESC = "标的Id";

    public static final String INVEST_DETAIL_AUTOWIRED_PRODUCT_TITLE = "title";
    public static final String INVEST_DETAIL_AUTOWIRED_PRODUCT_TITLE_DESC = "标的标题";

    // 账户UI路由信息
    public static final String USER_ACCOUNT_INFORMATION = "userAccount";
    public static final String USER_ACCOUNT_INFORMATION_DESC = "用户账户";
}
