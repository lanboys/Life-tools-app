package com.mjn.libs.comm.ui.register;

import com.mjn.libs.base.vcode.IGetVcodeContract;
import com.mjn.libs.comm.bean.UserBean;

/**
 * @author 蓝兵
 */
public interface IRegisterContract {

    interface IRegisterView
            extends IGetVcodeContract.IGetVcodeView<IRegisterPresenter> {

        void registerSuccess(UserBean userBean);
    }

    interface IRegisterPresenter
            extends IGetVcodeContract.IGetVcodePresenter<IRegisterView, IRegisterModule> {

        void register(String mobile, String password, String authCode, String inviteCode);
    }

    interface IRegisterModule
            extends IGetVcodeContract.IGetVcodeModule {

    }
}
