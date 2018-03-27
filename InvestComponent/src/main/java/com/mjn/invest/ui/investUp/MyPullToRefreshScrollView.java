package com.mjn.invest.ui.investUp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

/**
 * Created by 蓝兵 on 2018/3/27.
 */

public class MyPullToRefreshScrollView extends PullToRefreshScrollView {

    public MyPullToRefreshScrollView(Context context) {
        super(context);
    }

    public MyPullToRefreshScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyPullToRefreshScrollView(Context context, Mode mode) {
        super(context, mode);
    }

    public MyPullToRefreshScrollView(Context context, Mode mode, AnimationStyle style) {
        super(context, mode, style);
    }

    /*********************************************************************
     * 为了拖拽屏幕控件单独加的逻辑
     *********************************************************************/
    private float downY = 0;
    /**
     * 是否需要父布局控制滑动
     */
    private boolean needConsumeTouch = true;
    private int maxScroll = -1; // 最大滑动距离

    /**
     * 设置是否容许父布局控制滑动
     *
     * @param needConsumeTouch
     */
    public void setNeedConsumeTouch(boolean needConsumeTouch) {
        this.needConsumeTouch = needConsumeTouch;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!(mRefreshableView instanceof ScrollView)) {
            return super.dispatchTouchEvent(ev);
        }
        if (getMode() != Mode.PULL_FROM_START) {
            getParent().requestDisallowInterceptTouchEvent(true);
            return super.dispatchTouchEvent(ev);
        }
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            downY = ev.getRawY();
        } else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            if (downY - ev.getRawY() > 0) {//上滑
                if (mRefreshableView.getScrollY() + mRefreshableView.getHeight() >= ((ScrollView) mRefreshableView).getChildAt(0).getMeasuredHeight()) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                    return false;
                } else {
                    getParent().requestDisallowInterceptTouchEvent(true);
                    return super.dispatchTouchEvent(ev);
                }
            }
        }
        // 通知父view是否要处理touch事件
        getParent().requestDisallowInterceptTouchEvent(needConsumeTouch);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        if (maxScroll < 0) {
            maxScroll = computeVerticalScrollRange();
        }

        super.onScrollChanged(l, t, oldl, oldt);
    }
}
