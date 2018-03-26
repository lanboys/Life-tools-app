package com.bing.lan.comm.listener;

import android.widget.AbsListView;

/**
 * @author 蓝兵
 * @email lan_bing2013@163.com
 * @time 2017/4/22  23:16
 */
public abstract class ListViewScrollListener implements AbsListView.OnScrollListener {

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            case SCROLL_STATE_IDLE:
                //滑动停止时调用
                if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
                    // 如果滚动到底部
                    onListViewLoadMore();
                } else if (view.getFirstVisiblePosition() == 0) {
                    // 滚动到顶部
                }
                break;
            case SCROLL_STATE_TOUCH_SCROLL:
                //正在滚动时调用
                break;
            case SCROLL_STATE_FLING:
                //手指快速滑动时,在离开ListView由于惯性滑动
                break;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    public abstract void onListViewLoadMore();

}
