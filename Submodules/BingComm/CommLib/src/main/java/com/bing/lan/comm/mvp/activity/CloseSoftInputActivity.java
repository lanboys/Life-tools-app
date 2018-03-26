package com.bing.lan.comm.mvp.activity;

import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.bing.lan.comm.utils.SoftInputUtil;

/**
 * Created by 520 on 2017/7/12.
 */

/**
 * 过滤
 */

public class CloseSoftInputActivity extends AppCompatActivity {

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (isNeedCloseSoftInput() && ev.getAction() == MotionEvent.ACTION_DOWN) {
            //触摸在指定view上,返回
            if (isTouchFilterHideSoftViews(ev)) {
                return super.dispatchTouchEvent(ev);
            }
            //View v = getCurrentFocus();
            //隐藏键盘
            SoftInputUtil.closeSoftInput(this);
        }
        return super.dispatchTouchEvent(ev);
    }

    //@Override
    //public boolean onTouchEvent(MotionEvent event) {
    //    return super.onTouchEvent(event);
    //}
    //
    //public void updateButtonBackground() {
    //}

    /**
     * 是否触摸在指定过滤view上面
     */
    protected boolean isTouchFilterHideSoftViews(MotionEvent ev) {

        View[] views = filterHideSoftViews();
        if (views == null || views.length == 0) {
            int[] ints = filterHideSoftViewsByIds();
            if (ints != null) {
                views = new View[ints.length];
                for (int i = 0; i < ints.length; i++) {
                    views[i] = findViewById(ints[i]);
                }
            }
        }

        if (views == null || views.length == 0) {
            return false;
        }

        for (View view : views) {
            boolean touchingView = isTouchingView(view, ev);
            if (touchingView) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否触摸在指定view上面
     */
    protected boolean isTouchingView(View view, MotionEvent ev) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        return ev.getX() > x && ev.getX() < (x + view.getWidth())
                && ev.getY() > y && ev.getY() < (y + view.getHeight());
    }

    protected boolean isNeedCloseSoftInput() {
        return false;
    }

    protected View[] filterHideSoftViews() {
        //return new View[]{mEtInputPhoneNumber, mEtInputPassword, mEtInputVerification};
        return null;
    }

    protected int[] filterHideSoftViewsByIds() {
        //return new int[]{R.id.btn_code};
        return null;
    }

    protected <T> T checkNotNull(T value) {
        return checkNotNull(value, value + "为空异常");
    }

    protected <T> T checkNotNull(T value, String message) {
        if (value == null) {
            throw new NullPointerException(message);
        }
        return value;
    }
}
