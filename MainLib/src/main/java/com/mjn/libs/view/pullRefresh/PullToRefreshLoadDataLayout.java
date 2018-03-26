package com.mjn.libs.view.pullRefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.ganxin.library.LoadDataLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by 蓝兵 on 2018/3/24.
 */

public abstract class PullToRefreshLoadDataLayout<T extends View> extends PullToRefreshBase<LoadDataLayout> {

    protected T mRealRefreshableView;

    public PullToRefreshLoadDataLayout(Context context) {
        super(context);
    }

    public PullToRefreshLoadDataLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToRefreshLoadDataLayout(Context context, Mode mode) {
        super(context, mode);
    }

    public PullToRefreshLoadDataLayout(Context context, Mode mode, AnimationStyle animStyle) {
        super(context, mode, animStyle);
    }

    @Override
    public Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    @Override
    protected LoadDataLayout createRefreshableView(Context context, AttributeSet attrs) {
        LoadDataLayout loadDataLayout = new LoadDataLayout(context, attrs);

        // 解决 ViewGroup 自身或者父控件 消费down事件后，move事件不在传递到 自身的 onInterceptTouchEvent方法
        // 两种情况：1. 拦截了子控件的down事件,则事件直接由自身消费或者自身不消费父控件消费,后续事件
        //              都不经过onInterceptTouchEvent方法(但是有可能到onTouchEvent,比如自身消费事件
        //              就会直接跳过onInterceptTouchEvent到onTouchEvent)
        //          2. 未拦截子控件的down事件,但是子控件都没有消费事件,又传回自身或者父控件,情况同上

        // ViewGroup 未拦截子控件的down事件，并且事件被子控件消费，则后续事件仍然会经过自身的 onInterceptTouchEvent方法

        // 给 loadDataLayout 控件设置点击事件, 保证move事件会持续经过 父控件
        // PullToRefreshFrameLayout的 onInterceptTouchEvent方法，从而保证能够下拉
        loadDataLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mRealRefreshableView = createRealRefreshableView(context, attrs);
        loadDataLayout.addView(mRealRefreshableView, new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));

        try {
            Method build = loadDataLayout.getClass().getDeclaredMethod("build");
            build.setAccessible(true);
            build.invoke(loadDataLayout);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return loadDataLayout;
    }

    public T getRealRefreshableView() {
        return mRealRefreshableView;
    }

    public abstract T createRealRefreshableView(Context context, AttributeSet attrs);
}
