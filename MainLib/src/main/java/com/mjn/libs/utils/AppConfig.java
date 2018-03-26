package com.mjn.libs.utils;

import android.content.Context;
import android.view.LayoutInflater;

import com.bing.lan.comm.app.AppUtil;
import com.bing.lan.comm.mvp.fragment.BaseFragment;
import com.mjn.libs.R;

import java.util.HashMap;
import java.util.Map;

public class AppConfig {

    /**
     * 调试开关
     */
    public static boolean DEBUG = false;
    /**
     * 屏幕宽高
     */
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    /**
     * 应用环境
     */
    public static Context context = AppUtil.getAppContext();
    /**
     * 应用主类实例
     */
    public static Context mainActivity= AppUtil.getAppContext();
    /**
     * fragment页面管理类实例
     */
    public static android.support.v4.app.FragmentManager fragmentManager;
    /**
     * 当前的根屏幕
     */
    public static String CurrentRootScreenName = "";
    /**
     * 当前屏幕
     */
    public static BaseFragment currentScreen;
    /**
     * 服务器和本地时间差
     */
    public static long updateTimestamp;
    /**
     * 设备相关信息
     */
    public static String DEVICE_TOKEN = "";//设备唯一标识
    public static final String DEVICE_TYPE = "android";//平台类型
    public static String IP = "";//ip
    public static String LATITUDE = "";//纬度
    public static String LONGITUDE = "";//经度
    /**
     * 接口请求公共参数key
     */
    public static final String KEY_CHANNEL_ID = "channelId";//渠道号
    public static final String KEY_DEVICE_TYPE = "deviceType";//平台类型
    public static final String KEY_VERSION = "version";//版本号
    public static final String KEY_IP = "ip";//ip
    public static final String KEY_TIMESTAMP_STRING = "timestamp";//时间戳
    public static final String KEY_SIGN = "sign";//签名
    public static final String KEY_LATITUDE = "latitude";//纬度
    public static final String KEY_LONGITUDE = "longitude";//经度
    public static final String SP_KEY_NOFIRSTINSTALL = "notfirstinstall";//首次安装
    public static final String KEY_DEVICE_ID = "device_id"; // 设备id
    /**
     * 手机操作系统
     */
    public static String DEVICE_OS = ""; // 手机操作系统
    /**
     * push计数
     */
    public static int PUSH_COUNTER_ID = 0;
    /**
     * 最新版本
     */
    public static String ANDROID_NEW_VERSION = "";
    /**
     * 版本更新log
     */
    public static String ANDROID_UPDATELOG = "";
    /**
     * http请求方式
     */
    public static final int HTTP_GET = 0;
    public static final int HTTP_POST = 1;
    public static final int HTTP_PUT = 2;
    /**
     * 请求url
     */
    public static String HTTP_HOST = "https://api.51qinglicai.com/api";
//        public static final String HTTP_HOST_TEST = "https://api.51qinglicai.com/api";
//    public static final String HTTP_HOST_TEST = "http://192.168.30.35:8081/api";
    public static String HTTP_HOST_TEST = "http://api.51qinglicai.com/api";

    public static final String H5Url = "https://h5.baogongyoucai.com";


    public static String scheme = "mcyd";//web跳转协议
    /**
     * 用来通过xml创建view实例
     */
    public static LayoutInflater INFLATER = null;
    /**
     * 首次安装启动应用程序
     */
    public static boolean isFirstInstall;
    /**
     * 首次启动应用程序
     */
    public static boolean isFirstBoot;
    /*************************************************************************
     * 存储用key值
     *************************************************************************/
    /**
     * 存储版本号的key
     */
    public static final String SAVE_KEY_VERSION = "version";
    /**
     * 接口请求成功
     */
    public static final String REQUEST_CODE_SUCCESS = "0";
    /**
     * 帮助中心
     */
    public static final String HELP_URL = "/webpage/help_center.html";
    /**
     * 帮助中心
     */
    public static final String SCOREHELP_URL = "/webpage/get_integral.html";
    public static final String SCORESTORE_URL = "/webpage/integral_mall.html";
    /**
     * 使用说明
     */
    public static final String USER_URL = "/webpage/coupon_use_explain.html";
    /**
     * 用户注册协议
     */
    public static final String AGREEMENTS_URL = "/webpage/register_agreement.html";
    /**
     * 合同服务协议
     */
    public static final String INSTALLMENT_URL = "/webpage/hetong.html";
    /**
     * 网络借贷风险和禁止性行为提示书
     */
    public static final String FANXIQIAN_URL = "/html/static/fanxiqian.html";

    public static final String SAFT_URL = "/webpage/safe.html";

    // 平台简介
    public static final String PINGTAIJIANJIE = "http://h5.baogongyoucai.com/platformdescribe.html";
    /**
     * 首页布局类型
     */
    public static final int HOME_ITEM_TYPE_BANNER = 0;//首页广告
    public static final int HOME_ITEM_TYPE_BTN = 1;//按钮布局
    public static final int HOME_ITEM_TYPE_PRODUCT = 2;//首页标的
    public static final int HOME_ITEM_TYPE_BOTTOM = 3;//首页底部
    public static final int HOME_ITEM_TYPE_PREPRODUCT = 4;//首页底部
    public static final int HOME_ITEM_TYPE_BOTTOMGUIDE = 5;//首页底部
    public static final int HOME_ITEM_TYPE_HONGBAOQIANDAO = 6;//红包签到布局
    /**
     * 银行限额布局类型
     */
    public static final int BANK_LIMIT_TYPE_TITLE = 0;//限额标题栏
    public static final int BANK_LIMIT_TYPE_CONTEXT = 1;//内容布局
    /**
     * 投资页面布局类型
     */
    public static final int INVEST_TYPE_FAST = 0;//快速搜索栏
    public static final int INVEST_TYPE_ITEM = 1;//列表内容
    public static final int INVEST_TYPE_TOPITEM = 2;//顶部新手标布局

    /**
     * 红包选择布局类型
     */
    public static final int SELECT_TYPE_COUPON = 0;//优惠券选择
    public static final int SELECT_TYPE_RED = 1;//红包选择
    /**
     * 回款日历
     */
    public static final int GATHERING_TYPE_TITLE = 0;//日期
    public static final int GATHERING_TYPE_ITEM = 1;//记录详情

    public static final String BMOB_APPID_TEST = "bd39db5571aaf4527b03a7eb450c8bb2";
    public static String BMOB_APPID = "d7b56f3897e5c6e36223ca25cd8d54a2";
    /**
     * 银行图标容器
     */
    public static Map<String, Integer> iconMap = new HashMap<>();

    static {
        iconMap.put("ICBC", R.drawable.icon_bank_gs);//中国工商银行
        iconMap.put("CMB", R.drawable.icon_bank_zs);//招商银行
        iconMap.put("CCB", R.drawable.icon_bank_js);//中国建设银行
        iconMap.put("ABC", R.drawable.icon_bank_ny);//中国农业银行
        iconMap.put("BOC", R.drawable.icon_bank_zg);//中国银行
        iconMap.put("SPDB", R.drawable.icon_bank_pf);//上海浦东发展银行
        iconMap.put("BCOM", R.drawable.icon_bank_jt);//交通银行
        iconMap.put("CMBC", R.drawable.icon_bank_ms);//中国民生银行
        iconMap.put("GDB", R.drawable.icon_bank_gf);//广发银行
        iconMap.put("CITIC", R.drawable.icon_bank_zx);//中信银行
        iconMap.put("HXB", R.drawable.icon_bank_hx);//华夏银行
        iconMap.put("CIB", R.drawable.icon_bank_xy);//兴业银行
        iconMap.put("PSBC", R.drawable.icon_bank_yz);//中国邮政储蓄银行
        iconMap.put("CEB", R.drawable.icon_bank_gd);//中国光大银行
        iconMap.put("PAB", R.drawable.icon_bank_pa);//平安银行
        iconMap.put("SHB", R.drawable.icon_bank_sh);//上海银行
    }

    //udesk 配置
    /*public static String UDESK_DOMAIN = "xiangjiuna.udesk.cn";
    public static String UDESK_APPID = "9965bc2bac479315";
    public static String UDESK_APPKEY = "cedeaa9e4f3a1365bbf5e510788fe41d";*/
    public static String UDESK_DOMAIN = "anhuinana.udesk.cn";
    public static String UDESK_APPID = "ff2867f3e9687c4e";
    public static String UDESK_APPKEY = "0e7e828f471ef4268ef30bfd8a6c3ec3";

    // 序列化数据存储改为存json
    public static String USERBEAN_CACHE = "userInfo";
}
