package com.mjn.libs.cons;

/**
 * Created by 蓝兵 on 2018/3/21.
 */

public class UIRouterCons implements IConstants {

    //UIRouter.getInstance().openUri(
    //        getActivity(),
    //        UI_ROUTER_SCHEME + INVEST_HOST + INVEST_DETAIL_ROUTE_NODE_PATH,
    //        bundle);

    //AutowiredService.Factory.getInstance().create().autowire(this);

    public static final String UI_ROUTER_SCHEME = "DDComp://";

    public static final String USER_HOST = "user";
    public static final String INVEST_HOST = "invest";
    public static final String HOME_HOST = "home";
    public static final String APP_HOST = "app";

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


    //    优惠券 选择页面 ui路由信息
    public static final String USER_DISCOUNT_ROUTE_NODE_PATH = "/discount";
    public static final String USER_DISCOUNT_ROUTE_NODE_DESC = "选择优惠页面";

    public static final String USER_DISCOUNT_AUTOWIRED_MONEY = "money";
    public static final String USER_DISCOUNT_AUTOWIRED_MONEY_DESC = "投资金额";

    public static final String USER_AUTOWIRED_PAY_INFO = "payInfo";
    public static final String USER_AUTOWIRED_PAY_INFO_DESC = "支付信息对象,转为字符串再传";

    //  银行卡 ui路由信息
    public static final String USER_BANKLIST_ROUTE_NODE_PATH = "/backList";
    public static final String USER_BANKLIST_ROUTE_NODE_DESC = "银行卡列表";

    public static final String USER_BANKLIST_AUTOWIRED_IS_PAYORDER = "isPayOrder";
    public static final String USER_BANKLIST_AUTOWIRED_IS_PAYORDER_DESC = "PayOrder 页面进来 传 1, 否则传 0";

    //// 账户UI路由信息
    //public static final String USER_ACCOUNT_INFORMATION = "userAccount";
    //public static final String USER_ACCOUNT_INFORMATION_DESC = "用户账户";

}
