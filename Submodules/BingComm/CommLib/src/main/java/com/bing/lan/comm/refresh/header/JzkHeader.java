package com.bing.lan.comm.refresh.header;

import android.content.Context;
import android.util.AttributeSet;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

/**
 * Created by 蓝兵 on 2017/9/5.
 */

public class JzkHeader extends ClassicsHeader {

    public JzkHeader(Context context) {
        super(context);
    }

    public JzkHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JzkHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {
        switch (newState) {
            case None:
                //                restoreRefreshLayoutBackground();
                mLastUpdateText.setVisibility(mEnableLastTime ? VISIBLE : GONE);
            case PullDownToRefresh:
                mTitleText.setText(REFRESH_HEADER_PULLDOWN);
                mArrowView.setVisibility(VISIBLE);
                mProgressView.setVisibility(GONE);
                //mArrowView.animate().rotation(0);
                break;
            case Refreshing:
                mTitleText.setText(REFRESH_HEADER_REFRESHING);
                mProgressView.setVisibility(VISIBLE);
                mArrowView.setVisibility(GONE);
                break;
            case ReleaseToRefresh:
                mTitleText.setText(REFRESH_HEADER_RELEASE);
                //mArrowView.animate().rotation(180);
                //                replaceRefreshLayoutBackground(refreshLayout);
                break;
            case Loading:
                mArrowView.setVisibility(GONE);
                mProgressView.setVisibility(GONE);
                mLastUpdateText.setVisibility(GONE);
                mTitleText.setText(REFRESH_HEADER_LOADING);
                break;
        }
    }
}
