package com.mjn.discover.ui.discover;

import com.mjn.libs.base.MainLibFragmentPresenter;

/**
 * @author 蓝兵
 */
public class DiscoverPresenter extends
        MainLibFragmentPresenter<IDiscoverContract.IDiscoverView, IDiscoverContract.IDiscoverModule>
        implements IDiscoverContract.IDiscoverPresenter {

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
