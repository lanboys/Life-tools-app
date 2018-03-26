package com.mjn.libs.view.pullRefresh;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.handmark.pulltorefresh.library.LoadingLayoutBase;
import com.handmark.pulltorefresh.library.extras.recyclerview.PullToRefreshRecyclerView;

public class PullToRefreshRecyclerViewBaoGong extends PullToRefreshRecyclerView {

    public PullToRefreshRecyclerViewBaoGong(Context context) {
        super(context);
    }

    public PullToRefreshRecyclerViewBaoGong(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToRefreshRecyclerViewBaoGong(Context context, Mode mode) {
        super(context, mode);
    }

    public PullToRefreshRecyclerViewBaoGong(Context context, Mode mode, AnimationStyle style) {
        super(context, mode, style);
    }

    protected LoadingLayoutBase createLoadingLayout(Context context, Mode mode, TypedArray attrs) {

        // 为了得到 TypedArray
        LoadingLayoutBase layout = new BaoGaoLoadingLayout(context, mode, getPullToRefreshScrollDirection(), attrs);
        layout.setVisibility(View.INVISIBLE);
        return layout;
    }
}