package com.mjn.invest.ui.investDown;

import android.content.Intent;
import android.os.Bundle;

import com.mjn.invest.R;
import com.mjn.libs.base.MainLibFragment;

/**
 * @author 蓝兵
 */
public class InvestDetailDownFragment extends MainLibFragment<IInvestDetailDownContract.IInvestDetailDownPresenter>
        implements IInvestDetailDownContract.IInvestDetailDownView {

    public InvestDetailDownFragment() {

    }

    //Bundle args = new Bundle();
    //    args.putString("fragment标题", title);
    //    fragment.setArguments(args);

    public static InvestDetailDownFragment newInstance(String title) {
        InvestDetailDownFragment fragment = new InvestDetailDownFragment();
        fragment.setTitle(title);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_invest_detail_up;
    }


    @Override
    protected IInvestDetailDownContract.IInvestDetailDownPresenter initPresenter() {
        InvestDetailDownPresenter presenter = new InvestDetailDownPresenter();
        presenter.setModule(new InvestDetailDownModule());
        presenter.onAttachView(this);
        return presenter;
    }

    // @Override
    // protected void startInject(FragmentComponent fragmentComponent) {
    //     //        fragmentComponent.inject(this);
    // }\

    @Override
    protected void initViewAndData(Intent intent, Bundle arguments) {

    }

    @Override
    protected void readyStartPresenter() {

    }
}
