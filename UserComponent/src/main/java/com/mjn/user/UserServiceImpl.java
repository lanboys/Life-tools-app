package com.mjn.user;

import com.mjn.libs.base.MainLibFragment;
import com.mjn.libs.comm.service.UserService;
import com.mjn.libs.cons.FragmentTitleCons;
import com.mjn.user.ui.user.UserFragment;

/**
 * Created by 蓝兵 on 2018/3/21.
 */

public class UserServiceImpl implements UserService {

    @Override
    public MainLibFragment getUserFragment() {
        return UserFragment.newInstance(FragmentTitleCons.FRAGMENT_USER_TITLE);
    }
}
