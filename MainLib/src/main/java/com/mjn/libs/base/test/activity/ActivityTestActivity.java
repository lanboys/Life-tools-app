package com.mjn.libs.base.test.activity;

import android.content.Intent;

import com.mjn.libs.base.MainLibActivity;

/**
 * @author 蓝兵
 */
public class ActivityTestActivity extends MainLibActivity<IActivityTestContract.IActivityTestPresenter>
        implements IActivityTestContract.IActivityTestView {

    // @BindView(R.id.toolbar)
    // MyToolbar mToolbar;

    @Override
    protected int getLayoutResId() {
        return 0;
    }

    //@Override
    //protected void startInject(ActivityComponent activityComponent) {
    //    // activityComponent.inject(this);
    //}

    @Override
    protected IActivityTestContract.IActivityTestPresenter initPresenter() {
        ActivityTestPresenter presenter = new ActivityTestPresenter();
        presenter.setModule(new ActivityTestModule());
        presenter.onAttachView(this);
        return presenter;
    }

    @Override
    protected void initViewAndData(Intent intent) {
        // setToolBar(mToolbar, "提现申请", true, 0);
    }

    @Override
    protected void readyStartPresenter() {

    }
}
