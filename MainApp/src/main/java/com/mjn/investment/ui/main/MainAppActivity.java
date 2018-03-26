package com.mjn.investment.ui.main;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bing.lan.comm.app.AppUtil;
import com.bing.lan.comm.view.MyToolbar;
import com.luojilab.component.componentlib.router.Router;
import com.luojilab.component.componentlib.service.AutowiredService;
import com.luojilab.router.facade.annotation.Autowired;
import com.luojilab.router.facade.annotation.RouteNode;
import com.mjn.investment.R;
import com.mjn.libs.base.MainLibActivity;
import com.mjn.libs.base.MainLibFragment;
import com.mjn.libs.comm.service.DiscoverService;
import com.mjn.libs.comm.service.HomeService;
import com.mjn.libs.comm.service.UserService;
import com.mjn.libs.cons.UIRouterCons;
import com.mjn.libs.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author 蓝兵
 */

@RouteNode(path = UIRouterCons.MAIN_APP_ROUTE_NODE_PATH,
        desc = UIRouterCons.MAIN_APP_ROUTE_NODE_DESC)
public class MainAppActivity extends MainLibActivity<IMainAppContract.IMainAppPresenter>
        implements IMainAppContract.IMainAppView {

    @Autowired(name = UIRouterCons.MAIN_APP_AUTOWIRED_NAME,
            desc = UIRouterCons.MAIN_APP_AUTOWIRED_DESC)
    String mBookName;

    @BindView(R.id.toolbar)
    MyToolbar mToolbar;
    @BindView(R.id.view_pager_market)
    NoScrollViewPager mViewPager;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;

    private List<Fragment> fragments = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    private int[] icon = new int[]{
            R.drawable.tab_btn_home_selector,
            R.drawable.tab_btn_invest_selector,
            R.drawable.tab_btn_discover_selector,
            R.drawable.tab_btn_user_selector
    };

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main_app;
    }

    @Override
    protected IMainAppContract.IMainAppPresenter initPresenter() {
        MainAppPresenter presenter = new MainAppPresenter();
        presenter.setModule(new MainAppModule());
        presenter.onAttachView(this);
        return presenter;
    }

    @Override
    protected void initViewAndData(Intent intent) {
        setToolBar(mToolbar, "包公有财主项目", false, 0);
    }

    @Override
    protected void readyStartPresenter() {

        AutowiredService.Factory.getInstance().create().autowire(this);
        initFragment();
    }

    private void initFragment() {
        Router router = Router.getInstance();

        if (router.getService(HomeService.class.getSimpleName()) != null) {
            HomeService service = (HomeService) router.getService(HomeService.class.getSimpleName());
            MainLibFragment fragment = service.getHomeFragment();
            //FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            //ft.add(R.id.tab_content, fragment).commitAllowingStateLoss();
            //Log.i("onCreate():", " -----------------------");
            if (fragment != null) {
                fragments.add(fragment);
                titles.add(fragment.getTitle());
            }
        }

        if (router.getService(DiscoverService.class.getSimpleName()) != null) {
            DiscoverService service = (DiscoverService) router.getService(DiscoverService.class.getSimpleName());
            MainLibFragment fragment = service.getDiscoverFragment();
            if (fragment != null) {
                fragments.add(fragment);
                titles.add(fragment.getTitle());
            }
        }

        for (int i = 0; i < 2; i++) {
            if (router.getService(UserService.class.getSimpleName()) != null) {
                UserService service = (UserService) router.getService(UserService.class.getSimpleName());
                MainLibFragment fragment = service.getUserFragment();
                if (fragment != null) {
                    fragments.add(fragment);
                    titles.add(fragment.getTitle());
                }
            }
        }

        mViewPager.setScanScroll(true);
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles.get(position);
            }
        });
        mTabLayout.setupWithViewPager(mViewPager);

        // 设置图标 方式一
        //for (int i = 0; i < mTabLayout.getTabCount(); i++) {
        //    TabLayout.Tab tab = mTabLayout.getTabAt(i);
        //    tab.setIcon(icon[i]);//不能控制布局高度等
        //}

        // 设置图标  方式二
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            tab.setCustomView(getTabLayoutChildView(i));
        }

        // 设置图标  方式三 不能加  mTabLayout.setupWithViewPager(mViewPager); 并且需要自己定义事件
        //for (int i = 0; i < fragments.size(); i++) {
        //    TabLayout.Tab tab = mTabLayout.newTab();
        //    mTabLayout.addTab(tab);
        //    tab.setCustomView(getTabLayoutChildView(i));
        //}
    }

    public View getTabLayoutChildView(int index) {
        LinearLayout inflate = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.item_tab, null);
        ImageView imageView = inflate.findViewById(R.id.iv_icon);
        TextView item = inflate.findViewById(R.id.tv_item);

        imageView.setImageResource(icon[index]);
        item.setText(titles.get(index));

        return inflate;
    }

    @Override
    public void onBackPressed() {
        if (AppUtil.hitClick(2000, 2)) {
            super.onBackPressed();//正真退出
        } else {
            showToast("再按一次退出程序");
        }
    }

    /* 启动activity时 进行权限请求 的开关 */
    @Override
    protected boolean isCheckPermissions() {
        return true;
    }

    /* 返回权限数组资源id */
    @Override
    protected int getPermissionArrId() {
        return R.array.basic_runtime_permissions;
    }
}
