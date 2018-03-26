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

    }

    @Override
    public void updateHome(String userId) {
        showProgressDialog("");
        requestData(ACTION_UPDATE_HOME, userId);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onSuccess(int action, Object data) {
        super.onSuccess(action, data);
        switch (action) {
            case ACTION_UPDATE_HOME:
                ResponseListDataResult<Home> listDataResult = (ResponseListDataResult<Home>) data;
                Home home = listDataResult.getList().get(0);
                mView.onUpdateSuccess(home);
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
