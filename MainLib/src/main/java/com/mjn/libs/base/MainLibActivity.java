package com.mjn.libs.base;

import com.bing.lan.comm.mvp.activity.BaseActivity;
import com.ganxin.library.LoadDataLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

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
            if (mLoadDataLayout != null) {
                mLoadDataLayout.setStatus(state);
            }
        }
    }
}
