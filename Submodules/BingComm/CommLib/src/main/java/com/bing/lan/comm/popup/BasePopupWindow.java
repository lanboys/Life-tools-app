package com.bing.lan.comm.popup;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * Created by 520 on 2017/7/4.
 */
//http://www.jianshu.com/p/799dbb86f908
public class BasePopupWindow extends PopupWindow {

    protected Context mContext;

    public BasePopupWindow(Context context) {
        super(context);
        mContext = context;
    }

    /**
     * 设置背景灰色程度
     *
     * @param level 0.0f-1.0f
     */
    private void setBackGroundLevel(float level) {
        Window window = ((Activity) mContext).getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.alpha = level;
        window.setAttributes(params);
    }

    @Override
    public void showAsDropDown(View parent, int gravity, int x, int y) {
        setBackGroundLevel(0.5f);
        super.showAsDropDown(parent, gravity, x, y);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        setBackGroundLevel(0.5f);
        super.showAtLocation(parent, gravity, x, y);
    }

    @Override
    public void dismiss() {
        setBackGroundLevel(1.0f);
        super.dismiss();
    }
}
