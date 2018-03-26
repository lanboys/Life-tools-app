package com.mjn.home.ui;

import com.mjn.libs.base.MainLibActivityPresenter;

/**
 * @author 蓝兵
 */
public class HomeComponentPresenter
        extends MainLibActivityPresenter<IHomeComponentContract.IHomeComponentView, IHomeComponentContract.IHomeComponentModule>
        implements IHomeComponentContract.IHomeComponentPresenter {

    @Override
    public void onStart(Object... params) {
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onSuccess(int action, Object data) {
        super.onSuccess(action, data);
        switch (action) {
        }
    }

    @Override
    public void onError(int action, Throwable e) {
        super.onError(action, e);
    }

    @Override
    public void onCompleted(int action) {
        super.onCompleted(action);
    }
}
