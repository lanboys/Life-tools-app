package com.mjn.invest.service;

import com.mjn.invest.ui.investList.InvestListFragment;
import com.mjn.libs.base.MainLibFragment;
import com.mjn.libs.comm.service.InvestService;
import com.mjn.libs.cons.FragmentTitleCons;

/**
 * Created by 蓝兵 on 2018/3/21.
 */

public class InvestServiceImpl implements InvestService {

    @Override
    public MainLibFragment getInvestFragment() {
        return InvestListFragment.newInstance(FragmentTitleCons.FRAGMENT_INVEST_TITLE);
    }
}
