package com.bing.lan.comm.mvp.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ganxin.library.LoadDataLayout;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author 蓝兵
 *         http://www.cnblogs.com/dasusu/p/6745032.html
 */
public abstract class BaseFragment<T extends IBaseFragmentContract.IBaseFragmentPresenter>
        extends CommFragment
        implements IBaseFragmentContract.IBaseFragmentView<T>,
        SwipeRefreshLayout.OnRefreshListener {

    // @Inject
    // protected LogUtil log;
    // @Inject
    protected T mPresenter;
    protected View mContentView;
    private Unbinder mViewBind;
    /**
     * 启动注入
     */
    // protected abstract void startInject(FragmentComponent fragmentComponent);
    // protected void startInject(FragmentComponent activityComponent) {
    //
    // }
    private boolean isFragmentVisible = false;
    private boolean isFirstVisible = true;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        log.d("onAttach(): ");

        //启动di,可能第二次执行生命周期,故需要做个非空判断
        if (mPresenter == null) {
            //必须在子类注入,因为要注入的类型是泛型,只有在实现类才能确定
            // startInject(getFragmentComponent());
            mPresenter = initPresenter();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        log.d("onCreateView(): ");
        if (mContentView == null) {
            mContentView = initSuccessPager(inflater, container);
            mViewBind = ButterKnife.bind(this, mContentView);

            //不重用mContentView 和 FragmentStatePagerAdapter 的时候,重新点开fragment
            // 会显示不可见 getUserVisibleHint() == false 为什么??这样导致不能再次加载数据
            boolean userVisibleHint = getUserVisibleHint();
            if (userVisibleHint) {
                if (isFirstVisible) {
                    log.d("onCreateView(): onFragmentFirstVisible");
                    onFragmentFirstVisible();
                    isFirstVisible = false;
                }
                onFragmentVisibleChange(true);
                isFragmentVisible = true;
            }
        } else {
            ViewGroup parent = (ViewGroup) mContentView.getParent();
            if (parent != null) {
                parent.removeView(mContentView);//必须先移除,因为返回值会再次添加进去
            }
        }
        //绑定控件
        if (mViewBind == null) {
            mViewBind = ButterKnife.bind(this, mContentView);
        }

        return mContentView;
    }

    //setUserVisibleHint()在Fragment创建时会先被调用一次，传入isVisibleToUser = false
    //如果当前Fragment可见，那么setUserVisibleHint()会再次被调用一次，传入isVisibleToUser = true
    //如果Fragment从可见->不可见，那么setUserVisibleHint()也会被调用，传入isVisibleToUser = false
    //总结：setUserVisibleHint()除了Fragment的可见状态发生变化时会被回调外，在new Fragment()时也会被回调
    //如果我们需要在 Fragment 可见与不可见时干点事，用这个的话就会有多余的回调了，那么就需要重新封装一个
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        log.d("setUserVisibleHint(): isVisibleToUser " + isVisibleToUser);

        //setUserVisibleHint()有可能在fragment的生命周期外被调用
        //第一次 在 onAttach 前面调用,所以没有activity,但是又因为第一次可见时,加载数据是不能没有activity的
        if (mContentView == null || getActivity() == null) {
            return;
        }
        if (isFirstVisible && getUserVisibleHint()) {
            log.d("setUserVisibleHint(): 第一次可见");
            onFragmentFirstVisible();
            isFirstVisible = false;
        }
        if (getUserVisibleHint()) {
            //现在可见
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
        } else {
            //现在不可见
            //之前可见--->现在不可见,省略new Fragment()时的回调
            if (isFragmentVisible) {
                isFragmentVisible = false;
                onFragmentVisibleChange(false);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (!reuseContentView()) {
            unbind();
            mContentView = null;
            isFirstVisible = true;
            isFragmentVisible = false;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //解绑
        if (mPresenter != null) {
            mPresenter.onDetachView();
            mPresenter = null;
        }

        //解绑 必须后面执行，否则有些延时很久的成功请求 返回来 报空指针
        unbind();

        //AppUtil.MemoryLeakCheck(this);
    }

    private void unbind() {
        if (mViewBind != null) {
            mViewBind.unbind();
            mViewBind = null;
        }
    }

    /**
     * 设置是否使用 view 的复用，默认开启
     * view 的复用是指，ViewPager 在销毁和重建 Fragment 时会不断调用 onCreateView() -> onDestroyView()
     * 之间的生命函数，这样可能会出现重复创建 view 的情况，导致界面上显示多个相同的 Fragment
     * view 的复用其实就是指保存第一次创建的 view，后面再 onCreateView() 时直接返回第一次创建的 view
     */
    // 私有化 禁止更改
    private boolean reuseContentView() {
        //注意 不重用mContentView 和 FragmentStatePagerAdapter 的时候 会出问题
        return true;
    }

    protected boolean isFragmentVisible() {
        return isFragmentVisible;
    }

    /**
     * 在fragment首次可见时回调，可在这里进行加载数据，保证只在第一次打开Fragment时才会加载数据，
     * 这样就可以防止每次进入都重复加载数据
     * 该方法会在 onFragmentVisibleChange() 之前调用，所以第一次打开时，可以用一个全局变量表示数据下载状态，
     * 然后在该方法内将状态设置为下载状态，接着去执行下载的任务
     * 最后在 onFragmentVisibleChange() 里根据数据下载状态来控制下载进度ui控件的显示与隐藏
     */
    protected void onFragmentFirstVisible() {
        log.d("onFragmentFirstVisible(): ");
        //初始化数据
        initViewAndData(getActivity().getIntent(), getArguments());
        readyStartPresenter();
    }

    /**
     * 去除setUserVisibleHint()多余的回调场景，保证只有当fragment可见状态发生变化时才回调
     * 回调时机在view创建完后，所以支持ui操作，解决在setUserVisibleHint()里进行ui操作有可能报null异常的问题
     * <p>
     * 可在该回调方法里进行一些ui显示与隐藏，比如加载框的显示和隐藏
     *
     * @param isVisible true  不可见 -> 可见
     *                  false 可见  -> 不可见
     */
    protected void onFragmentVisibleChange(boolean isVisible) {//有bug 从activity回来 不回调
        log.d("onFragmentVisibleChange(): ");
    }

    // protected FragmentComponent getFragmentComponent() {
    //     return DaggerFragmentComponent.builder()
    //             .fragmentModule(new FragmentModule(this))
    //             .build();
    // }

    protected abstract T initPresenter();

    /**
     * 获取 全局的view
     *
     * @return contentView
     */
    public View getContentView() {
        return mContentView;
    }

    protected View initSuccessPager(LayoutInflater layoutInflater, ViewGroup container) {
        return layoutInflater.inflate(getLayoutResId(), container, false);
    }

    /**
     * 初始化数据 和 UI
     *
     * @param intent    activity 中的intent
     * @param arguments fragment中的bundle
     */
    protected abstract void initViewAndData(Intent intent, Bundle arguments);

    /**
     * @return 获取布局id
     */
    protected abstract int getLayoutResId();

    protected abstract void readyStartPresenter();

    public T getPresenter() {
        return mPresenter;
    }

    @Override
    public void updateTitle(String title) {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void closeRefreshing() {
        //if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
        //    mSwipeRefreshLayout.setRefreshing(false);
        //}
    }

    @Override
    public void setLoadDataLayoutStatus(@LoadDataLayout.Flavour int state) {

    }

    /**
     * 停止更新,释放一些正在进行的任务
     */
    public void stopUpdate() {
        if (mPresenter != null)
            mPresenter.stopUpdate();
    }

    @Override
    public void reStartUpdate() {
        if (mPresenter != null)
            mPresenter.reStartUpdate();
    }
}
