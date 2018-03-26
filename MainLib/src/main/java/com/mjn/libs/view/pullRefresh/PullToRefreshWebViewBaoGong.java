package com.mjn.libs.view.pullRefresh;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.handmark.pulltorefresh.library.LoadingLayoutBase;
import com.handmark.pulltorefresh.library.PullToRefreshWebView;

/**
 * Created by 蓝兵 on 2018/3/24.
 */

public class PullToRefreshWebViewBaoGong extends PullToRefreshWebView {

    public PullToRefreshWebViewBaoGong(Context context) {
        super(context);
    }

    public PullToRefreshWebViewBaoGong(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToRefreshWebViewBaoGong(Context context, Mode mode) {
        super(context, mode);
    }

    public PullToRefreshWebViewBaoGong(Context context, Mode mode, AnimationStyle style) {
        super(context, mode, style);
    }

    protected LoadingLayoutBase createLoadingLayout(Context context, Mode mode, TypedArray attrs) {

        // 为了得到 TypedArray
        LoadingLayoutBase layout = new BaoGaoLoadingLayout(context, mode, getPullToRefreshScrollDirection(), attrs);
        layout.setVisibility(View.INVISIBLE);
        return layout;
    }
}
