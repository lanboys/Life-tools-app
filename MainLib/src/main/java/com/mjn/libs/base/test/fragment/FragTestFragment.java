package com.mjn.libs.base.test.fragment;

import android.content.Intent;
import android.os.Bundle;

import com.mjn.libs.base.MainLibFragment;

/**
 * @author 蓝兵
 */
public class FragTestFragment extends MainLibFragment<IFragTestContract.IFragTestPresenter>
        implements IFragTestContract.IFragTestView {

    public FragTestFragment() {

    }

    //Bundle args = new Bundle();
    //    args.putString("fragment标题", title);
    //    fragment.setArguments(args);

    public static FragTestFragment newInstance(String title) {
        FragTestFragment fragment = new FragTestFragment();
        fragment.setTitle(title);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return 0;
    }

    @Override
    protected IFragTestContract.IFragTestPresenter initPresenter() {
        FragTestPresenter presenter = new FragTestPresenter();
        presenter.setModule(new FragTestModule());
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
