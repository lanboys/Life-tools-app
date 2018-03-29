package com.mjn.home.app.applike;

import com.luojilab.component.componentlib.applicationlike.IApplicationLike;
import com.luojilab.component.componentlib.router.Router;
import com.luojilab.component.componentlib.router.ui.UIRouter;
import com.mjn.home.HomeServiceImpl;
import com.mjn.libs.comm.service.HomeService;

import static com.mjn.libs.cons.UIRouterCons.HOME_HOST;

public class HomeAppLike implements IApplicationLike {

    Router router = Router.getInstance();
    UIRouter uiRouter = UIRouter.getInstance();

    @Override
    public void onCreate() {
        //暂时 没有交互页面 不注册
        uiRouter.registerUI(HOME_HOST);
        router.addService(HomeService.class.getSimpleName(), new HomeServiceImpl());
    }

    @Override
    public void onStop() {
        uiRouter.unregisterUI(HOME_HOST);
        router.removeService(HomeService.class.getSimpleName());
    }
}
