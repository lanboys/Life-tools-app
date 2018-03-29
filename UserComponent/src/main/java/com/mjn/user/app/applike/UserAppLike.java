package com.mjn.user.app.applike;

import com.luojilab.component.componentlib.applicationlike.IApplicationLike;
import com.luojilab.component.componentlib.router.Router;
import com.luojilab.component.componentlib.router.ui.UIRouter;
import com.mjn.libs.comm.service.UserService;
import com.mjn.user.UserServiceImpl;

import static com.mjn.libs.cons.UIRouterCons.USER_HOST;

public class UserAppLike implements IApplicationLike {

    Router router = Router.getInstance();
    UIRouter uiRouter = UIRouter.getInstance();

    @Override
    public void onCreate() {
        uiRouter.registerUI(USER_HOST);
        router.addService(UserService.class.getSimpleName(), new UserServiceImpl());
    }

    @Override
    public void onStop() {
        uiRouter.unregisterUI(USER_HOST);
        router.removeService(UserService.class.getSimpleName());
    }
}
