package com.mjn.libs.base.test.activity;

import com.mjn.libs.base.MainLibActivityPresenter;

/**
 * @author 蓝兵
 */
public class ActivityTestPresenter
        extends MainLibActivityPresenter<IActivityTestContract.IActivityTestView, IActivityTestContract.IActivityTestModule>
        implements IActivityTestContract.IActivityTestPresenter {

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
