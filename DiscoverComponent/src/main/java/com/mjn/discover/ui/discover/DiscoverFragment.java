package com.mjn.discover.ui.discover;

import android.content.Intent;
import android.os.Bundle;

import com.mjn.discover.R;
import com.mjn.libs.base.MainLibFragment;

/**
 * @author 蓝兵
 */
public class DiscoverFragment extends MainLibFragment<IDiscoverContract.IDiscoverPresenter>
        implements IDiscoverContract.IDiscoverView {

    public DiscoverFragment() {

    }

    public static DiscoverFragment newInstance(String title) {
        DiscoverFragment fragment = new DiscoverFragment();
        fragment.setTitle(title);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_discover;
    }

    @Override
    protected IDiscoverContract.IDiscoverPresenter initPresenter() {
        DiscoverPresenter presenter = new DiscoverPresenter();
        presenter.setModule(new DiscoverModule());
        presenter.onAttachView(this);
        return presenter;
    }

    // @Override
    // protected void startInject(FragmentComponent fragmentComponent) {
    //     //        fragmentComponent.inject(this);
    // }

    @Override
    protected void initViewAndData(Intent intent, Bundle arguments) {

    }

    @Override
    protected void readyStartPresenter() {

    }
}
