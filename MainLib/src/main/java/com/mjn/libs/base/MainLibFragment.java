package com.mjn.libs.base;

import android.os.Bundle;

import com.bing.lan.comm.mvp.fragment.BaseFragment;
import com.ganxin.library.LoadDataLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.util.ArrayList;

/**
 * @author 蓝兵
 */
public abstract class MainLibFragment
        <T extends IMainLibFragmentContract.IMainLibFragmentPresenter>
        extends BaseFragment<T>
        implements IMainLibFragmentContract.IMainLibFragmentView<T> {

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

        if (mLoadDataLayout == null) {
            return;
        }

        // 页面暂时还没数据 做无网络 无数据状态处理 等处理
        if (mLoadDataLayoutList == null || mLoadDataLayoutList.size() == 0) {
            mLoadDataLayout.setStatus(state);
            return;
        }

        // 有数据显示成功的状态
        mLoadDataLayout.setStatus(LoadDataLayout.SUCCESS);
    }

    public void toHtml5Pager(Bundle bundle) {
        MainLibActivity mainLibActivity = (MainLibActivity) getActivity();
        if (mainLibActivity != null) {
            mainLibActivity.toHtml5Pager(bundle);
        }
    }
}
