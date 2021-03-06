package com.mjn.invest.ui.investList;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ganxin.library.LoadDataLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.luojilab.component.componentlib.router.ui.UIRouter;
import com.mjn.invest.R;
import com.mjn.invest.ui.investList.adapter.InvestRecyclerAdapter;
import com.mjn.libs.api.ResponseListDataResult;
import com.mjn.libs.base.MainLibFragment;
import com.mjn.libs.comm.bean.IInvestItemBean;
import com.mjn.libs.comm.bean.IProduct;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import static com.mjn.libs.cons.UIRouterCons.INVEST_DETAIL_ROUTE_NODE_PATH;
import static com.mjn.libs.cons.UIRouterCons.INVEST_HOST;
import static com.mjn.libs.cons.UIRouterCons.UI_ROUTER_SCHEME;

/**
 * @author 蓝兵
 */
public class InvestListFragment extends MainLibFragment<IInvestListContract.IInvestListPresenter>
        implements IInvestListContract.IInvestListView, InvestRecyclerAdapter.OnInvestClickCallBack {

    private com.bing.lan.comm.view.MyToolbar mToolbar;
    private com.mjn.libs.view.pullRefresh.PullToRefreshLoadDataLayoutRecyclerView mPullRefreshRecycler;
    private RecyclerView mRecyclerView;
    private InvestRecyclerAdapter mAdapter;
    private ArrayList<IProduct> mList = new ArrayList<>();
    private long mTotalPages;
    private long mSize;
    private long mNumber;

    public InvestListFragment() {

    }

    public static InvestListFragment newInstance(String title) {
        InvestListFragment fragment = new InvestListFragment();
        fragment.setTitle(title);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_invest;
    }

    @Override
    protected IInvestListContract.IInvestListPresenter initPresenter() {
        InvestListPresenter presenter = new InvestListPresenter();
        presenter.setModule(new InvestListModule());
        presenter.onAttachView(this);
        return presenter;
    }

    @Override
    protected void initViewAndData(Intent intent, Bundle arguments) {
        initView();
        setToolBar(mToolbar, "投资", false, 0);
    }

    private void initView() {
        mToolbar = mContentView.findViewById(R.id.toolbar);
        mPullRefreshRecycler = mContentView.findViewById(R.id.pull_refresh_recycler);

        mPullToRefreshBase = mPullRefreshRecycler;
        mLoadDataLayoutList = mList;

        mPullRefreshRecycler.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<LoadDataLayout>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<LoadDataLayout> refreshView) {
                mPresenter.update();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<LoadDataLayout> refreshView) {
                if (mNumber < mTotalPages) {
                    mPresenter.loadMore(mNumber + 1);
                }
            }
        });
        mPullRefreshRecycler.setMode(PullToRefreshBase.Mode.PULL_FROM_START);

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

        mAdapter = new InvestRecyclerAdapter();
        mAdapter.setOnInvestClickCallBack(this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setDataAndRefresh(mList);
    }

    @Override
    protected void readyStartPresenter() {
        mPresenter.onStart();
    }

    @Override
    public void onClickToInvestPager(Bundle bundle) {
        UIRouter.getInstance().openUri(
                getActivity(),
                UI_ROUTER_SCHEME + INVEST_HOST + INVEST_DETAIL_ROUTE_NODE_PATH,
                bundle);
    }

    @Override
    public void updateSuccess(ResponseListDataResult<IProduct> listDataResult) {

        Long servicetime = listDataResult.getServicetime();

        mNumber = listDataResult.getNumber();
        mSize = listDataResult.getSize();
        mTotalPages = listDataResult.getTotalPages();

        List<IProduct> list = listDataResult.getList();
        if (list.size() > 0) {
            IProduct iProduct = list.get(0);
            //默认为0, 不做处理
            iProduct.setInvestBeanType(IInvestItemBean.InvestType.INVEST_TYPE_TOP_ITEM);
        }

        for (IProduct iProduct : list) {
            iProduct.setServicetime(servicetime);
        }
        mAdapter.setDataAndRefresh(list);
        setEnableLoadMore();
    }

    @Override
    public void loadMoreSuccess(ResponseListDataResult<IProduct> listDataResult) {
        Long servicetime = listDataResult.getServicetime();

        mNumber = listDataResult.getNumber();
        mSize = listDataResult.getSize();
        mTotalPages = listDataResult.getTotalPages();

        List<IProduct> list = listDataResult.getList();
        for (IProduct iProduct : list) {
            iProduct.setServicetime(servicetime);
        }
        mAdapter.addDataAndRefresh(list);
        setEnableLoadMore();
    }

    public void setEnableLoadMore() {
        if (mPullRefreshRecycler != null) {
            mPullRefreshRecycler.setMode(mNumber < mTotalPages ?
                    PullToRefreshBase.Mode.BOTH : PullToRefreshBase.Mode.PULL_FROM_START);
        }
    }
}
