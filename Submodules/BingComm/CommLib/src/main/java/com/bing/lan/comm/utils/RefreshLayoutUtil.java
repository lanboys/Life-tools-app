package com.bing.lan.comm.utils;

import com.scwang.smartrefresh.layout.api.RefreshLayout;

/**
 * Created by Administrator on 2017/8/23.
 */

public class RefreshLayoutUtil {
    public static void hideLayout(RefreshLayout layout){
        if (layout!=null){
            if (layout.isRefreshing()){
                layout.finishRefresh();
            }
            if (layout.isLoading()){
                layout.finishLoadmore();
            }
        }
    }
}
