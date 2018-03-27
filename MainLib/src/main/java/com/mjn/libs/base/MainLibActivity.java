package com.mjn.libs.base;

import android.content.Intent;
import android.os.Bundle;

import com.bing.lan.comm.mvp.activity.BaseActivity;
import com.ganxin.library.LoadDataLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.mjn.libs.comm.ui.h5.activity.WebViewActivity;
import com.mjn.libs.cons.WebViewCons;

import java.util.ArrayList;

/**
 * @author 蓝兵
 */
public abstract class MainLibActivity
        <T extends IMainLibActivityContract.IMainLibActivityPresenter>
        extends BaseActivity<T>
        implements IMainLibActivityContract.IMainLibActivityView<T> {

    protected PullToRefreshBase mPullToRefreshBase;
    protected LoadDataLayout mLoadDataLayout;

    protected ArrayList mLoadDataLayoutList;

    @Override
    public void closeRefreshing() {
        if (mPullToRefreshBase != null) {
            mPullToRefreshBase.onRefreshComplete();
        }
    }

    @Override
    public void setLoadDataLayoutStatus(@LoadDataLayout.Flavour int state) {
        if (mLoadDataLayoutList != null && mLoadDataLayoutList.size() > 0) {
            // 有数据显示成功的状态
            if (mLoadDataLayout != null) {
                mLoadDataLayout.setStatus(LoadDataLayout.SUCCESS);
            }
        } else {
            // 页面暂时还没数据 做无网络 无数据状态处理 等处理
            if (mLoadDataLayout != null) {
                mLoadDataLayout.setStatus(state);
            }
        }
    }

    public void toHtml5Pager(Bundle bundle) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra(WebViewCons.WEBVIEW_PARAMS_NAME, bundle);
        startActivity(intent, false, true);
    }
}
