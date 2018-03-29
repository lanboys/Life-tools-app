package com.mjn.invest.app.applike;

import com.luojilab.component.componentlib.applicationlike.IApplicationLike;
import com.luojilab.component.componentlib.router.Router;
import com.luojilab.component.componentlib.router.ui.UIRouter;
import com.mjn.invest.service.InvestServiceImpl;
import com.mjn.libs.comm.service.InvestService;

import static com.mjn.libs.cons.UIRouterCons.INVEST_HOST;

public class InvestAppLike implements IApplicationLike {

    Router router = Router.getInstance();
    UIRouter uiRouter = UIRouter.getInstance();

    @Override
    public void onCreate() {
        uiRouter.registerUI(INVEST_HOST);
        router.addService(InvestService.class.getSimpleName(), new InvestServiceImpl());
    }

    @Override
    public void onStop() {
        uiRouter.unregisterUI(INVEST_HOST);
        router.removeService(InvestService.class.getSimpleName());
    }
}
