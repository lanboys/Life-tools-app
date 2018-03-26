package com.mjn.user.app.applike;

import com.luojilab.component.componentlib.applicationlike.IApplicationLike;
import com.luojilab.component.componentlib.router.Router;
import com.luojilab.component.componentlib.router.ui.UIRouter;
import com.mjn.libs.comm.service.UserService;
import com.mjn.user.UserServiceImpl;

public class UserAppLike implements IApplicationLike {

    Router router = Router.getInstance();
    UIRouter uiRouter = UIRouter.getInstance();

    @Override
    public void onCreate() {
        uiRouter.registerUI("user");
        router.addService(UserService.class.getSimpleName(), new UserServiceImpl());
    }

    @Override
    public void onStop() {
        uiRouter.unregisterUI("user");
        router.removeService(UserService.class.getSimpleName());
    }
}
