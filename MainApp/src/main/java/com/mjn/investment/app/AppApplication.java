package com.mjn.investment.app;

import com.bing.lan.comm.app.BaseApplication;
import com.luojilab.component.componentlib.router.ui.UIRouter;

import static com.mjn.libs.cons.UIRouterCons.APP_HOST;

public class AppApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        UIRouter.getInstance().registerUI(APP_HOST);

        //如果isRegisterCompoAuto为false，则需要通过反射加载组件
        //        Router.registerComponent("com.luojilab.reader.applike.ReaderAppLike");
        //        Router.registerComponent("com.luojilab.share.applike.ShareApplike");
    }

    @Override
    protected boolean getLogDebug() {
        return true;
    }
}
