package com.mjn.libs.comm.ui.login;

import com.mjn.libs.api.ResponseListDataResult;
import com.mjn.libs.base.vcode.GetVcodePresenter;
import com.mjn.libs.comm.bean.IUser;

import static com.mjn.libs.cons.RequestActionCons.ACTION_LOGIN;

/**
 * @author 蓝兵
 */
public class LoginPresenter
        extends GetVcodePresenter<ILoginContract.ILoginView, ILoginContract.ILoginModule>
        implements ILoginContract.ILoginPresenter {

    @Override
    public void onStart(Object... params) {
    }

    @Override
    public void login(String phone, String password) {
        showProgressDialog("正在登录..");
        requestData(ACTION_LOGIN, phone, password);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onSuccess(int action, Object data) {
        super.onSuccess(action, data);
        switch (action) {
            case ACTION_LOGIN:
                ResponseListDataResult<IUser> listDataResult = (ResponseListDataResult<IUser>) data;
                IUser iUser = listDataResult.getList().get(0);
                showToast(listDataResult.getMessage());
                mView.onLoginSuccess(iUser);

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
