package com.bing.lan.comm.app;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;

import com.bing.lan.comm.R;
import com.bing.lan.comm.refresh.header.JzkHeader;
import com.bing.lan.comm.utils.LogUtil;
import com.bing.lan.comm.utils.NetworkUtil;
import com.ganxin.library.LoadDataLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

/**
 * @author 蓝兵
 * @time 2017/5/9  18:26
 */
public abstract class BaseApplication extends Application {

    public static Properties mProperties;
    //1.创建一个静态的事件总线
    // public static Bus sBus;
    public static NetworkUtil.NetWorkStatus netWorkStatus = new NetworkUtil.NetWorkStatus();
    protected final LogUtil log = LogUtil.getLogUtil(getClass(), LogUtil.LOG_VERBOSE);
    //网络状态变化的广播
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            netWorkStatus = NetworkUtil.getNetWorkStatus(context);

            //if (AppUtil.checkNetwork(context)){
            //    isNetworkAvailable = true;
            //} else {
            //    isNetworkAvailable = false;
            //    Toast.makeText(context, "请检查网络状态", Toast.LENGTH_SHORT).show();
            //}
        }
    };

    //不加会导致报错 因为方法数太多了
    //  java.lang.NoClassDefFoundError: okhttp3.OkHttpClient$Builder
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //全局初始化
        AppUtil.initGlobal(this, getApplicationContext(), getLogDebug());
        //二维码
        // ZXingLibrary.initDisplayOpinion(this);

        ////otto
        // if (sBus == null) {
        //     //ANY是说该事件总线 在哪条线程中运行 无所谓
        //     sBus = new Bus(ThreadEnforcer.ANY);
        // }

        //图片加载
        // Fresco.initialize(this);

        //网络状态广播注册
        registerNetWorkReceiver();

        initProperties();

        // DownloadManager.init(ApiManager.getOkHttpClient(), this.getExternalFilesDir("download"));

        LoadDataLayout.getBuilder()
                .setLoadingText(getString(R.string.custom_loading_text))
                .setLoadingTextSize(16)
                .setLoadingTextColor(R.color.colorPrimary)
                //.setLoadingViewLayoutId(R.layout.custom_loading_view) //如果设置了自定义loading视图,上面三个方法失效
                .setEmptyImgId(R.drawable.ic_empty)
                .setErrorImgId(R.drawable.ic_error)
                .setNoNetWorkImgId(R.drawable.ic_no_network)
                .setEmptyImageVisible(true)
                .setErrorImageVisible(true)
                .setNoNetWorkImageVisible(true)
                .setEmptyText(getString(R.string.custom_empty_text))
                .setErrorText(getString(R.string.custom_error_text))
                .setNoNetWorkText(getString(R.string.custom_no_network_text))
                .setAllTipTextSize(16)
                .setAllTipTextColor(R.color.text_color_child)
                .setAllPageBackgroundColor(R.color.pageBackground)
                .setReloadBtnText(getString(R.string.custom_reloadBtn_text))
                .setReloadBtnTextSize(13)
                .setReloadBtnTextColor(R.color.text_color_child)
                //.setReloadBtnBackgroundResource(R.drawable.selector_btn_normal)
                .setReloadBtnVisible(false)
                .setReloadClickArea(LoadDataLayout.FULL);

        ClassicsHeader.REFRESH_HEADER_RELEASE = "松开,我会显示更多数据";

        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                JzkHeader jzkHeader = new JzkHeader(context);
                jzkHeader.setSpinnerStyle(SpinnerStyle.Scale);
                jzkHeader.setArrowResource(R.drawable.loading_rocket);
                jzkHeader.setProgressResource(R.drawable.loading_rocket);
                jzkHeader.setFinishDuration(320);//刷新完成 后 隐藏所用时间
                jzkHeader.setEnableLastTime(false);//隐藏上次刷新时间

                layout.setPrimaryColorsId(R.color.color_refreshLayout_background, R.color.color_refreshLayout_text);
                layout.setFooterHeight(80);
                layout.setHeaderHeight(80);
                layout.setReboundDuration(320);//设置回弹动画时长
                layout.setDisableContentWhenLoading(false);
                layout.setDisableContentWhenRefresh(false);
                layout.setEnableAutoLoadmore(false);

                return jzkHeader;
            }
        });

        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                return new ClassicsFooter(context);
            }
        });
    }

    protected abstract boolean getLogDebug();

    /**
     * 网络状态广播注册
     */
    private void registerNetWorkReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mBroadcastReceiver, intentFilter);
    }

    //public boolean isNetworkAvailable = false;

    private void initProperties() {
        mProperties = new Properties();
        try {
            mProperties.appVersionCode = AppUtil.getVersionCode();
            mProperties.appVersionName = AppUtil.getVersionName();
            mProperties.screen_height = AppUtil.getScreenH();
            mProperties.screen_width = AppUtil.getScreenW();
            mProperties.phone_model = AppUtil.getPhoneModel();
            mProperties.mac_address = AppUtil.getMacAddress();
            mProperties.hardware_vendors = AppUtil.getManufacturer();
            mProperties.phone_sdk_version = AppUtil.getPhoneSDKVersion();
            mProperties.phone_imei = AppUtil.getPhoneIMEI();
            mProperties.platform = "2";  // 平台 1 IOS, 2 安卓

            log.i("initProperties(): " + mProperties);
        } catch (Exception e) {
            log.e("initProperties():  " + e.getLocalizedMessage());
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
        System.runFinalization();
    }
}
