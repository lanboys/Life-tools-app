package com.bing.lan.comm.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * 报异常 Inconsistency detected. Invalid view holder adapter
 * positionViewHolder{2064e5c6 position=2 id=-1, oldPos=2, pLpos:-1 scrap [attachedScrap]
 * tmpDetached no parent}
 * <p>
 * RecyclerVew 源码 Bug.
 * <p>
 * 解决方案
 * http://stackoverflow.com/questions/31759171/recyclerview-and-java-lang-indexoutofboundsexception-inconsistency-detected-in
 * <p>
 * 相关资料
 * 1. http://stackoverflow.com/questions/31759171/recyclerview-and-java-lang-indexoutofboundsexception-inconsistency-detected-in
 * 2. http://www.jianshu.com/p/2eca433869e9
 * 3. http://blog.csdn.net/singleton1900/article/details/48369239；
 */
public class WrapContentLinearLayoutManager extends LinearLayoutManager {

    public WrapContentLinearLayoutManager(Context context) {
        super(context);
    }

    public WrapContentLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public WrapContentLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            super.onLayoutChildren(recycler, state);
        } catch (IndexOutOfBoundsException e) {
            Log.e("probe", "meet a IOOBE in RecyclerView");
        }
    }
}
