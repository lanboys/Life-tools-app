package com.mjn.user.ui.user;

import android.content.Intent;
import android.os.Bundle;

import com.mjn.libs.base.MainLibFragment;
import com.mjn.user.R;

/**
 * @author 蓝兵
 */
public class UserFragment extends MainLibFragment<IUserContract.IUserPresenter>
        implements IUserContract.IUserView {

    public UserFragment() {

    }

    public static UserFragment newInstance(String title) {
        UserFragment fragment = new UserFragment();
        fragment.setTitle(title);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_user;
    }

    @Override
    protected IUserContract.IUserPresenter initPresenter() {
        UserPresenter presenter = new UserPresenter();
        presenter.setModule(new UserModule());
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
