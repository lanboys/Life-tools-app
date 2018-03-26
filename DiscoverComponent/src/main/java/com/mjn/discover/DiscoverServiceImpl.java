package com.mjn.discover;

import com.mjn.discover.ui.discover.DiscoverFragment;
import com.mjn.libs.base.MainLibFragment;
import com.mjn.libs.comm.service.DiscoverService;
import com.mjn.libs.cons.FragmentTitleCons;

/**
 * Created by 蓝兵 on 2018/3/21.
 */

public class DiscoverServiceImpl implements DiscoverService {

    @Override
    public MainLibFragment getDiscoverFragment() {
        return DiscoverFragment.newInstance(FragmentTitleCons.FRAGMENT_DISCOVER_TITLE);
    }
}
