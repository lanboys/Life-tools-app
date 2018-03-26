package com.mjn.home.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.ganxin.library.LoadDataLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.mjn.home.R;
import com.mjn.home.ui.home.adapter.HomeRecyclerAdapter;
import com.mjn.home.ui.home.bean.BannerBean;
import com.mjn.libs.base.MainLibFragment;
import com.mjn.libs.comm.bean.Adv;
import com.mjn.libs.comm.bean.Home;
import com.mjn.libs.comm.bean.IHomeItemBean;
import com.mjn.libs.comm.ui.h5.activity.WebViewActivity;
import com.mjn.libs.comm.ui.login.LoginActivity;
import com.mjn.libs.cons.WebViewCons;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 蓝兵
 */
public class HomeFragment extends MainLibFragment<IHomeContract.IHomePresenter>
        implements IHomeContract.IHomeView, HomeRecyclerAdapter.OnHomeClickCallBack {

    private android.widget.Button mBtnLogin;
    private com.bing.lan.comm.view.MyToolbar mToolbar;
    private com.mjn.libs.view.pullRefresh.PullToRefreshLoadDataLayoutRecyclerView mPullRefreshRecycler;
    private RecyclerView mRecyclerView;
    private HomeRecyclerAdapter mAdapter;
    private ArrayList<IHomeItemBean> mList = new ArrayList<>();

    public HomeFragment() {

    }

    public static HomeFragment newInstance(String title) {
        HomeFragment fragment = new HomeFragment();
        fragment.setTitle(title);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected IHomeContract.IHomePresenter initPresenter() {
        HomePresenter presenter = new HomePresenter();
        presenter.setModule(new HomeModule());
        presenter.onAttachView(this);
        return presenter;
    }

    @Override
    protected void initViewAndData(Intent intent, Bundle arguments) {
        initView();
        setToolBar(mToolbar, "包公有财", false, 0);
    }

    @Override
    protected void readyStartPresenter() {
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "点我登录", Toast.LENGTH_SHORT).show();
                startActivity(LoginActivity.class, false, true);
            }
        });
    }

    private void initView() {
        mBtnLogin = mContentView.findViewById(R.id.btn_login);
        mToolbar = mContentView.findViewById(R.id.toolbar);
        mPullRefreshRecycler = mContentView.findViewById(R.id.pull_refresh_recycler);

        mPullToRefreshBase = mPullRefreshRecycler;
        mLoadDataLayoutList = mList;

        mPullRefreshRecycler.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<LoadDataLayout>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<LoadDataLayout> refreshView) {
                mPresenter.updateHome("");
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<LoadDataLayout> refreshView) {
                mPresenter.updateHome("");
            }
        });

        mLoadDataLayout = mPullRefreshRecycler.getRefreshableView();
        mLoadDataLayout.setStatus(LoadDataLayout.SUCCESS);
        mRecyclerView = mPullRefreshRecycler.getRealRefreshableView();
        mRecyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getActivity())
                        .color(Color.TRANSPARENT)
                        .sizeResId(R.dimen.dimen_recycleView_divider_10dp)
                        .marginResId(R.dimen.dimen_recycleView_divider_left_margin_10dp,
                                R.dimen.dimen_recycleView_divider_right_margin_10dp)
                        .build());

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new HomeRecyclerAdapter();
        mAdapter.setOnHomeClickCallBack(this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setDataAndRefresh(mList);
    }

    @Override
    public void onUpdateSuccess(Home home) {
        List<Adv> bannerList = home.getBannerList();
        BannerBean bannerBean = new BannerBean(bannerList, IHomeItemBean.HomeBeanType.HOME_ITEM_TYPE_BANNER);
        ArrayList<IHomeItemBean> list = new ArrayList<>();
        list.add(bannerBean);
        mAdapter.setDataAndRefresh(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBannerClick(boolean isToHtml5, Bundle bundle) {
        if (isToHtml5) {
            Intent intent = new Intent(getActivity(), WebViewActivity.class);
            intent.putExtra(WebViewCons.WEBVIEW_PARAMS_NAME, bundle);
            startActivity(intent, false, true);
        } else {
            startActivity(LoginActivity.class, false, true);
        }
    }
}
