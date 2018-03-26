package com.bing.lan.comm.mvp.fragment;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.bing.lan.comm.utils.LogUtil;

import java.util.List;

/**
 * @author 蓝兵
 */
public class LifecycleFragment extends Fragment {

    protected final LogUtil log = LogUtil.getLogUtil(getClass(), LogUtil.LOG_VERBOSE);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        log.d("onCreate(): ");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        log.d("onViewCreated(): ");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        log.d("onActivityCreated(): ");
    }

    private boolean isFirstVisible = true;

    @Override
    public void onStart() {
        super.onStart();
        log.d("onStart(): ");

        if (isFirstVisible) {
            isFirstVisible = false;
        } else {
            onStartAgain();
        }
    }

    //第二次可见 回调
    protected void onStartAgain() {
        log.i("onStartAgain(): ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        log.d("onDestroy(): ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        log.d("onDetach(): ");
    }

    private static boolean isAppActiveForeground; //全局变量

    @Override
    public void onResume() {
        log.d("onResume(): ");

        if (!isAppActiveForeground) {
            //app 从后台唤醒，进入前台
            isAppActiveForeground = true;
            log.d("onResume 程序从后台唤醒");
            onAppForeground(true);
        }
        super.onResume();
    }

    public void onAppForeground(boolean isBackground2Foreground) {

    }

    @Override
    public void onStop() {
        log.d("onStop(): ");
        super.onStop();
        if (!isAppOnForeground()) {
            //app 进入后台
            isAppActiveForeground = false;//记录当前已经进入后台
            log.d("onStop 程序进入后台");
            onAppForeground(false);
        }
    }

    /**
     * APP是否处于前台唤醒状态
     */
    public boolean isAppOnForeground() {
        ActivityManager activityManager = (ActivityManager) getActivity().
                getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);

        String packageName = getActivity().getApplicationContext().getPackageName();
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }
}
