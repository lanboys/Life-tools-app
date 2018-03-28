package com.mjn.home.ui.home;

import com.mjn.libs.api.ResponseListDataResult;
import com.mjn.libs.base.MainLibFragmentPresenter;
import com.mjn.libs.comm.bean.Home;

import static com.mjn.libs.cons.RequestActionCons.ACTION_UPDATE_HOME;

/**
 * @author 蓝兵
 */
public class HomePresenter extends
        MainLibFragmentPresenter<IHomeContract.IHomeView, IHomeContract.IHomeModule>
        implements IHomeContract.IHomePresenter {

    @Override
    public void onStart(Object... params) {
        showProgressDialog("正在加载..");
        updateHome((String) params[0]);

    }

    @Override
    public void updateHome(String userId) {
        requestData(ACTION_UPDATE_HOME, userId);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onSuccess(int action, Object data) {
        super.onSuccess(action, data);
        switch (action) {
            case ACTION_UPDATE_HOME:

                mView.onUpdateSuccess((ResponseListDataResult<Home>) data);
                break;
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
