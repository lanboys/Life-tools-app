package com.mjn.home.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ganxin.library.LoadDataLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.mjn.home.R;
import com.mjn.home.ui.home.adapter.HomeRecyclerAdapter;
import com.mjn.home.ui.home.bean.BannerBean;
import com.mjn.home.ui.home.bean.HomeBtnBean;
import com.mjn.libs.api.ResponseListDataResult;
import com.mjn.libs.base.MainLibFragment;
import com.mjn.libs.comm.bean.Adv;
import com.mjn.libs.comm.bean.Home;
import com.mjn.libs.comm.bean.IHomeItemBean;
import com.mjn.libs.comm.bean.IProduct;
import com.mjn.libs.comm.ui.login.LoginActivity;
import com.mjn.libs.utils.SPUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 蓝兵
 */
public class HomeFragment extends MainLibFragment<IHomeContract.IHomePresenter>
        implements IHomeContract.IHomeView, HomeRecyclerAdapter.OnHomeClickCallBack {

    private com.bing.lan.comm.view.MyToolbar mToolbar;
    private com.mjn.libs.view.pullRefresh.PullToRefreshLoadDataLayoutRecyclerView mPullRefreshRecycler;
    private RecyclerView mRecyclerView;
    private HomeRecyclerAdapter mAdapter;
    private ArrayList<IHomeItemBean> mList = new ArrayList<>();
    private List<Adv> mBannerList = new ArrayList<>();
    private List<Adv> mBottomList = new ArrayList<>();
    private ArrayList<IHomeItemBean> mHomeItemBeans = new ArrayList<>();

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

    private void initView() {
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

            }
        });

        mPullRefreshRecycler.setMode(PullToRefreshBase.Mode.PULL_FROM_START);

        mLoadDataLayout = mPullRefreshRecycler.getRefreshableView();
        mLoadDataLayout.setStatus(LoadDataLayout.SUCCESS);

        mRecyclerView = mPullRefreshRecycler.getRealRefreshableView();
        //mRecyclerView.addItemDecoration(
        //        new HorizontalDividerItemDecoration.Builder(getActivity())
        //                .color(Color.TRANSPARENT)
        //                .sizeResId(R.dimen.dimen_recycleView_divider_10dp)
        //                .marginResId(R.dimen.dimen_recycleView_divider_left_margin_10dp,
        //                        R.dimen.dimen_recycleView_divider_right_margin_10dp)
        //                .build());

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new HomeRecyclerAdapter();
        mAdapter.setOnHomeClickCallBack(this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setDataAndRefresh(mList);
    }

    @Override
    protected void readyStartPresenter() {
        mPresenter.updateHome("");
    }

    @Override
    public void onUpdateSuccess(ResponseListDataResult<Home> listDataResult) {

        Long servicetime = listDataResult.getServicetime();
        Home home = listDataResult.getList().get(0);

        mHomeItemBeans.clear();

        if (home.getBannerList() != null && home.getBannerList().size() > 0) {
            //有数据才清空
            mBannerList.clear();
            mBannerList.addAll(home.getBannerList());
        }
        if (mBannerList.size() == 0) {
            mBannerList.add(new Adv());
        }
        BannerBean bannerBean = new BannerBean(mBannerList, IHomeItemBean.HomeBeanType.HOME_ITEM_TYPE_BANNER);
        mHomeItemBeans.add(bannerBean);

        //主页中上三个按钮 平台简介，最新活动，邀请好友
        String platformDescUrl = home.getPlatformDescUrl();
        SPUtil.getInstance().putString("spPlatformProfile", platformDescUrl);
        HomeBtnBean homeBtnBean = new HomeBtnBean(platformDescUrl, IHomeItemBean.HomeBeanType.HOME_ITEM_TYPE_BTN);
        mHomeItemBeans.add(homeBtnBean);
        //主页中上三个按钮

        // TODO: 2018/3/27 通知数据格式？？？？

        //新手标
        List<IProduct> firstUserList = home.getFirstUserList();
        if (firstUserList != null && firstUserList.size() > 0) {
            for (IProduct iProduct : firstUserList) {
                iProduct.setHomeBeanType(IHomeItemBean.HomeBeanType.HOME_ITEM_TYPE_PRODUCT);
                iProduct.setServicetime(servicetime);
                mHomeItemBeans.add(iProduct);
            }
        }
        //新手标

        //多个标
        List<IProduct> preferenceList = home.getPreferenceList();
        if (preferenceList != null && preferenceList.size() > 0) {
            for (IProduct iProduct : preferenceList) {
                iProduct.setHomeBeanType(IHomeItemBean.HomeBeanType.HOME_ITEM_TYPE_PRE_PRODUCT);
                iProduct.setServicetime(servicetime);
                mHomeItemBeans.add(iProduct);
            }
        }
        //多个标

        //底部广告
        //List<Adv> bottomList = home.getBottomList();
        //if (bottomList != null && bottomList.size() > 0) {
        //    //有数据才清空
        //    mBottomList.clear();
        //    for (Adv adv : bottomList) {
        //        adv.setInvestBeanType(IHomeItemBean.HomeBeanType.HOME_ITEM_TYPE_BOTTOM_GUIDE);
        //        mBottomList.add(adv);
        //    }
        //}
        //if (mBottomList.size() == 0) {
        //    Adv adv = new Adv();
        //    adv.setInvestBeanType(IHomeItemBean.HomeBeanType.HOME_ITEM_TYPE_BOTTOM_GUIDE);
        //    mBottomList.add(adv);
        //}
        //
        //mHomeItemBeans.addAll(mBottomList);
        mHomeItemBeans.add(new IHomeItemBean() {
            @Override
            public int getHomeBeanType() {
                return IHomeItemBean.HomeBeanType.HOME_ITEM_TYPE_BOTTOM_GUIDE;
            }

            @Override
            public void setHomeBeanType(int homeBeanType) {

            }
        });
        //底部广告

        //最底部文字  包公有财，保你有财
        mHomeItemBeans.add(new IHomeItemBean() {
            @Override
            public int getHomeBeanType() {
                return IHomeItemBean.HomeBeanType.HOME_ITEM_TYPE_BOTTOM;
            }

            @Override
            public void setHomeBeanType(int homeBeanType) {

            }
        });
        //最底部文字  包公有财，保你有财

        mAdapter.setDataAndRefresh(mHomeItemBeans);
        //mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClickToHtml5Pager(Bundle bundle) {
        toHtml5Pager(bundle);
    }

    @Override
    public void onClickToLoginPager() {
        startActivity(LoginActivity.class, false, true);
    }

    @Override
    public void onClickToActivityCenterPager() {
        showError("去活动中心页面");
    }

    @Override
    public void onClickToInvestDetailPager(Bundle bundle) {
        showError("去投资详情页面");
    }
}
