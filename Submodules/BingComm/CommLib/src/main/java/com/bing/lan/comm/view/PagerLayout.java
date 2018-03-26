//package com.bing.lan.comm.view;
//
//import android.content.Context;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.FrameLayout;
//
//import com.bing.lan.comm.R;
//import com.bing.lan.comm.utils.LogUtil;
//
///**
// * @author 蓝兵
// * @time 2017/1/10  18:42
// */
//public abstract class PagerLayout extends FrameLayout {
//
//    private static final int STATE_EMPTY = 0;
//    private static final int STATE_ERROR = 1;
//    private static final int STATE_LOADING = 2;
//    private static final int STATE_SUCCESS = 3;
//    /**
//     * 重试次数
//     */
//    private static final int RELOAD_TIMES = 2;
//    protected LogUtil log = LogUtil.getLogUtil(getClass(), 1);
//    // private boolean mIsOpenRefresh;
//    private View mEmptyPager;
//    private View mErrorPager;
//    private View mLoadingPager;
//    private View mSuccessPager;
//    private int mCurrentState = STATE_LOADING;
//
//    private OnErrorButtonListener mErrorButtonListener;
//    private int mErrorCount;
//
//    public PagerLayout(Context context) {
//        this(context, null);
//    }
//
//
//
//    public PagerLayout(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        initView(context);
//    }
//
//    /**
//     * 获取正在 加载页面 的布局
//     */
//    protected int getPagerLoading() {
//        return R.layout.item_md_loadbar;
//        // return R.layout.pager_loading;
//    }
//
//    /**
//     * 获取正在 空页面 的布局
//     */
//    protected int getPagerEmpty() {
//        return R.layout.pager_empty;
//    }
//
//    /**
//     * 获取正在 错误页面 的布局
//     */
//    protected int getPagerError() {
//        return R.layout.pager_error;
//    }
//
//    /**
//     * 初始化 空页面
//     */
//    private void initEmptyPager() {
//        mEmptyPager = View.inflate(getContext(), getPagerEmpty(), null);
//        this.addView(mEmptyPager, 0);
//    }
//
//    /**
//     * 初始化 错误页面
//     */
//    private void initErrorPager() {
//        mErrorPager = View.inflate(getContext(), getPagerError(), null);
//        this.addView(mErrorPager, 0);
//
//        //错误页面设置点击事件
//        mErrorPager.findViewById(R.id.error_btn_retry).setOnClickListener(new OnClickListener() {
//            @Override
//            public void onRecyclerViewItemClick(View v) {
//                // loadingDataAndRefreshState();
//
//                mCurrentState = STATE_LOADING;
//                refreshPagerByState();
//
//                if (mErrorButtonListener != null) {
//                    mErrorButtonListener.OnErrorButtonClick(v);
//                }
//            }
//        });
//    }
//
//    /**
//     * 设置错误按钮的监听器
//     *
//     * @param errorButtonListener 监听器
//     */
//    public void setErrorButtonListener(OnErrorButtonListener errorButtonListener) {
//        mErrorButtonListener = errorButtonListener;
//    }
//
//    private void initView(Context context) {
//
//        LayoutInflater inflater = LayoutInflater.from(context);
//
//        // mEmptyPager = View.inflate(context, getPagerEmpty(), null);
//        // mErrorPager = View.inflate(context, getPagerError(), null);
//        // this.addView(mEmptyPager, 0);
//        // this.addView(mErrorPager, 0);
//
//        //        mLoadingPager = inflater.inflate(R.layout.pager_loading, this, false);
//        //        mEmptyPager = inflater.inflate(R.layout.pager_loading, this, false);
//        //        mErrorPager = inflater.inflate(R.layout.pager_loading, this, false);
//
//        mLoadingPager = View.inflate(context, getPagerLoading(), null);
//        this.addView(mLoadingPager, 0);
//
//        mSuccessPager = initSuccessPager(inflater, this);
//        if (mSuccessPager != null) {
//            this.addView(mSuccessPager, 0);
//        }
//
//        refreshPagerByState();
//    }
//
//    /**
//     * 初始化数据加载成功的页面,子类必须重写该方法
//     */
//    protected abstract View initSuccessPager(LayoutInflater inflater, ViewGroup parent);
//
//    /**
//     * 通过状态 更新页面显示
//     */
//    private void refreshPagerByState() {
//
//        if (mEmptyPager != null) {
//            mEmptyPager.setVisibility(GONE);
//        }
//        if (mErrorPager != null) {
//            mErrorPager.setVisibility(GONE);
//        }
//
//        //懒加载
//        switch (mCurrentState) {
//            case STATE_EMPTY:
//                if (mEmptyPager == null) {
//                    initEmptyPager();
//                }
//                mEmptyPager.setVisibility(VISIBLE);
//                break;
//            case STATE_ERROR:
//                if (mErrorPager == null) {
//                    initErrorPager();
//                }
//                mErrorPager.setVisibility(VISIBLE);
//                break;
//        }
//
//        // mEmptyPager.setVisibility(mCurrentState == STATE_EMPTY ? VISIBLE : GONE);
//        // mErrorPager.setVisibility(mCurrentState == STATE_ERROR ? VISIBLE : GONE);
//
//        mLoadingPager.setVisibility(mCurrentState == STATE_LOADING ? VISIBLE : GONE);
//
//        if (mSuccessPager != null) {
//            mSuccessPager.setVisibility(mCurrentState == STATE_SUCCESS ? VISIBLE : GONE);
//        }
//    }
//
//    /**
//     * 外界设置页面状态的接口
//     *
//     * @param pagerState 加载数据的结果
//     */
//    public void setPagerState(LoadDataResult pagerState) {
//
//        mCurrentState = pagerState.getState();
//
//        //在此判断,防止在错误重试页面,用户不断点击重试按钮
//        //记录显示错误页面的次数,次数大于RELOAD_TIMES将显示空白界面
//        if (mCurrentState == STATE_ERROR) {
//            mErrorCount++;
//            if (mErrorCount > RELOAD_TIMES) {
//                mCurrentState = STATE_EMPTY;
//            }
//            log.d("setPagerState():出现错误页面的次数 " + mErrorCount);
//        }
//
//        //加载成功后清除计数
//        if (mCurrentState == STATE_SUCCESS) {
//            resetErrorCount();
//        }
//
//        //更新状态
//        refreshPagerByState();
//    }
//
//    /**
//     * 复位  出错重试按钮  计数
//     */
//    public void resetErrorCount() {
//        mErrorCount = 0;
//    }
//
//    /**
//     * 加载数据的结果
//     */
//    public enum LoadDataResult {
//
//        LOAD_SUCCESS(STATE_SUCCESS), //成功页面
//        LOAD_ERROR(STATE_ERROR),//错误页面
//        LOAD_EMPTY(STATE_EMPTY), //没有数据的页面
//        LOAD_LOADING(STATE_LOADING);//正在加载页面
//
//        private final int mState;
//
//        LoadDataResult(int state) {
//            this.mState = state;
//        }
//
//        public int getState() {
//            return mState;
//        }
//
//    }
//
//    public interface OnErrorButtonListener {
//
//        void OnErrorButtonClick(View v);
//    }
//}
