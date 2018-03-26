package com.mjn.invest.ui.invest;

import android.content.Intent;
import android.os.Bundle;

import com.mjn.invest.R;
import com.mjn.libs.base.MainLibFragment;

/**
 * @author 蓝兵
 */
public class InvestFragment extends MainLibFragment<IInvestContract.IInvestPresenter>
        implements IInvestContract.IInvestView {

    public InvestFragment() {

    }

    public static InvestFragment newInstance(String title) {
        InvestFragment fragment = new InvestFragment();
        fragment.setTitle(title);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_html5;
    }

    @Override
    protected IInvestContract.IInvestPresenter initPresenter() {
        InvestPresenter presenter = new InvestPresenter();
        presenter.setModule(new InvestModule());
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
