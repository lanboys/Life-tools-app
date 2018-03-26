package com.mjn.home;

import com.mjn.home.ui.home.HomeFragment;
import com.mjn.libs.base.MainLibFragment;
import com.mjn.libs.comm.service.HomeService;
import com.mjn.libs.cons.FragmentTitleCons;

/**
 * Created by 蓝兵 on 2018/3/21.
 */

public class HomeServiceImpl implements HomeService {

    @Override
    public MainLibFragment getHomeFragment() {
        return HomeFragment.newInstance(FragmentTitleCons.FRAGMENT_HOME_TITLE);
    }
}
