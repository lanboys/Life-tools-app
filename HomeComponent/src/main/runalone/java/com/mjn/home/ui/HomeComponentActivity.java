package com.mjn.home.ui;

import android.content.Intent;

import com.bing.lan.comm.view.MyToolbar;
import com.mjn.home.R;
import com.mjn.libs.base.MainLibActivity;

/**
 * @author 蓝兵
 */
public class HomeComponentActivity extends MainLibActivity<IHomeComponentContract.IHomeComponentPresenter>
        implements IHomeComponentContract.IHomeComponentView {

    private MyToolbar mToolbar;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected IHomeComponentContract.IHomeComponentPresenter initPresenter() {
        HomeComponentPresenter presenter = new HomeComponentPresenter();
        presenter.setModule(new HomeComponentModule());
        presenter.onAttachView(this);
        return presenter;
    }

    @Override
    protected void initViewAndData(Intent intent) {
        mToolbar = findViewById(R.id.toolbar);
        setToolBar(mToolbar, "Home组件", true, 0);
    }

    @Override
    protected void readyStartPresenter() {

    }    /* 启动activity时 进行权限请求 的开关 */

    @Override
    protected boolean isCheckPermissions() {
        return true;
    }

    /* 返回权限数组资源id */
    @Override
    protected int getPermissionArrId() {
        return R.array.basic_runtime_permissions;
    }
}
