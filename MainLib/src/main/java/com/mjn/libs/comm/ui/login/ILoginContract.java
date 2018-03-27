package com.mjn.libs.comm.ui.login;

import com.mjn.libs.base.vcode.IGetVcodeContract;
import com.mjn.libs.comm.bean.IUser;

/**
 * @author 蓝兵
 */
public interface ILoginContract {

    interface ILoginView
            extends IGetVcodeContract.IGetVcodeView<ILoginPresenter> {

        void onLoginSuccess(IUser iUser);
    }

    interface ILoginPresenter
            extends IGetVcodeContract.IGetVcodePresenter<ILoginView, ILoginModule> {

        void login(String phone, String password);
    }

    interface ILoginModule
            extends IGetVcodeContract.IGetVcodeModule {

    }
}
