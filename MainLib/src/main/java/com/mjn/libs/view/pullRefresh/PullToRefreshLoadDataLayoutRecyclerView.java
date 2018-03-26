package com.mjn.libs.view.pullRefresh;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.ganxin.library.LoadDataLayout;
import com.handmark.pulltorefresh.library.LoadingLayoutBase;

/**
 * Created by 蓝兵 on 2018/3/24.
 */

public class PullToRefreshLoadDataLayoutRecyclerView extends PullToRefreshLoadDataLayout<RecyclerView> {

    public PullToRefreshLoadDataLayoutRecyclerView(Context context) {
        super(context);
    }

    public PullToRefreshLoadDataLayoutRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToRefreshLoadDataLayoutRecyclerView(Context context, Mode mode) {
        super(context, mode);
    }

    public PullToRefreshLoadDataLayoutRecyclerView(Context context, Mode mode, AnimationStyle animStyle) {
        super(context, mode, animStyle);
    }

    @Override
    public RecyclerView createRealRefreshableView(Context context, AttributeSet attrs) {
        return new RecyclerView(context);
    }

    @Override
    protected LoadingLayoutBase createLoadingLayout(Context context, Mode mode, TypedArray attrs) {
        // 为了得到 TypedArray
        LoadingLayoutBase layout = new BaoGaoLoadingLayout(context, mode, getPullToRefreshScrollDirection(), attrs);
        layout.setVisibility(View.INVISIBLE);
        return layout;
    }

    @Override
    protected boolean isReadyForPullStart() {
        //boolean firstItemVisible = isFirstItemVisible();
        //boolean b1 = getRefreshableView().getStatus() != LoadDataLayout.SUCCESS;
        //boolean b = firstItemVisible || b1;
        //return b;
        //
        return isFirstItemVisible() || getRefreshableView().getStatus() != LoadDataLayout.SUCCESS;
    }

    @Override
    protected boolean isReadyForPullEnd() {
        return isLastItemVisible();
    }

    /**
     * @return boolean:
     * @Description: 判断第一个条目是否完全可见
     * @version 1.0
     * @date 2015-9-23
     * @Author zhou.wenkai
     */
    private boolean isFirstItemVisible() {
        final RecyclerView.Adapter<?> adapter = mRealRefreshableView.getAdapter();

        // 如果未设置Adapter或者Adapter没有数据可以下拉刷新
        if (null == adapter || adapter.getItemCount() == 0) {
            if (DEBUG) {
                Log.d(LOG_TAG, "isFirstItemVisible. Empty View.");
            }
            return true;
        } else {
            // 第一个条目完全展示,可以刷新
            if (getFirstVisiblePosition() == 0) {
                return mRealRefreshableView.getChildAt(0).getTop() >= mRealRefreshableView
                        .getTop();
            }
        }

        return false;
    }

    /**
     * @return int: 位置
     * @Description: 获取第一个可见子View的位置下标
     * @version 1.0
     * @date 2015-9-23
     * @Author zhou.wenkai
     */
    private int getFirstVisiblePosition() {
        View firstVisibleChild = mRealRefreshableView.getChildAt(0);
        return firstVisibleChild != null ? mRealRefreshableView
                .getChildAdapterPosition(firstVisibleChild) : -1;
    }

    /**
     * @return boolean:
     * @Description: 判断最后一个条目是否完全可见
     * @version 1.0
     * @date 2015-9-23
     * @Author zhou.wenkai
     */
    private boolean isLastItemVisible() {
        final RecyclerView.Adapter<?> adapter = mRealRefreshableView.getAdapter();

        // 如果未设置Adapter或者Adapter没有数据可以上拉刷新
        if (null == adapter || adapter.getItemCount() == 0) {
            if (DEBUG) {
                Log.d(LOG_TAG, "isLastItemVisible. Empty View.");
            }
            return true;
        } else {
            // 最后一个条目View完全展示,可以刷新
            int lastVisiblePosition = getLastVisiblePosition();
            if (lastVisiblePosition >= mRealRefreshableView.getAdapter().getItemCount() - 1) {
                return mRealRefreshableView.getChildAt(
                        mRealRefreshableView.getChildCount() - 1).getBottom() <= mRealRefreshableView
                        .getBottom();
            }
        }

        return false;
    }

    /**
     * @return int: 位置
     * @Description: 获取最后一个可见子View的位置下标
     * @version 1.0
     * @date 2015-9-23
     * @Author zhou.wenkai
     */
    private int getLastVisiblePosition() {
        View lastVisibleChild = mRealRefreshableView.getChildAt(mRealRefreshableView
                .getChildCount() - 1);
        return lastVisibleChild != null ? mRealRefreshableView
                .getChildAdapterPosition(lastVisibleChild) : -1;
    }
}
