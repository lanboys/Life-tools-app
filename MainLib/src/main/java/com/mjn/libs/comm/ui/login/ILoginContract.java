package com.mjn.libs.comm.ui.login;

import com.mjn.libs.base.vcode.IGetVcodeContract;

/**
 * @author 蓝兵
 */
public interface ILoginContract {

    interface ILoginView
            extends IGetVcodeContract.IGetVcodeView<ILoginPresenter> {

    }

    interface ILoginPresenter
            extends IGetVcodeContract.IGetVcodePresenter<ILoginView, ILoginModule> {

        void login(String phone, String password);
    }

    interface ILoginModule
            extends IGetVcodeContract.IGetVcodeModule {

    }
}
