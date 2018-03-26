package com.bing.lan.comm.mvp.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bing.lan.comm.theme.ImmersionUtil;

/**
 * Created by 蓝兵 on 2017/10/10.
 */

public class ImmersionActivity extends CloseSoftInputActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置全部竖屏
        setRequestedOrientation(getScreenOrientation());
        //部分手机的沉浸式, api < 19 的沉浸式设置方法
        initImmersion();
        //透明状态栏
        initTranslucentStatus();
    }

    protected int getScreenOrientation() {
        return ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }

    /**
     * 请求状态栏透明
     */
    protected boolean isTranslucentStatus() {
        return false;
    }

    /**
     * 请求沉浸式
     */
    protected boolean isImmersion() {
        // isImmersion = true;
        return false;
    }

    protected void initTranslucentStatus() {

        if (!isTranslucentStatus())
            return;
        ImmersionUtil.initTranslucentStatus(this);
    }

    // api < 19 的沉浸式设置方法
    protected void initImmersion() {
        if (!isImmersion())
            return;
        //
        ImmersionUtil.initImmersionSmallApi19(this);
    }

    protected void initImmersion(boolean hasFocus) {
        if (!isImmersion())
            return;
        if (hasFocus) {
            ImmersionUtil.initImmersion(this);
        }
    }

    /**
     * 获取焦点或失去焦点时调用
     *
     * @param hasFocus 获取焦点返回true,否则返回false
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        initImmersion(hasFocus);
    }
}
