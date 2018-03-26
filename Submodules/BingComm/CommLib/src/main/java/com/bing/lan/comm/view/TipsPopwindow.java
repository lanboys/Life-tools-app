package com.bing.lan.comm.view;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.bing.lan.comm.R;

/**
 * 封装了显示Popupwindow的方法.
 *
 * @author ansen
 * @create time 2015-10-27
 */
public class TipsPopwindow implements TipRelativeLayout.AnimationEndCallback {
    private PopupWindow reportVideoPopwindow;

    public void showTips(Activity activity,String msg) {

//		Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_launcher);
        int height = 100;
        View parent = ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);

        View popView = LayoutInflater.from(activity).inflate(R.layout.activity_popupwindow_tips, null);
        reportVideoPopwindow = new PopupWindow(popView, LayoutParams.MATCH_PARENT, height * 2);
        reportVideoPopwindow.showAtLocation(parent, Gravity.TOP, 0, 0);
        TipRelativeLayout tvTips = (TipRelativeLayout) popView.findViewById(R.id.rl_tips);
        TextView tips_msg = (TextView) popView.findViewById(R.id.tips_msg);
        tips_msg.setText(msg);
        tvTips.setTitleHeight(height / 2);//只需要移动高度的一半,初始化的时候就显示了一半,不知道具体原因
        tvTips.setAnimationEnd(this);//设置动画结束监听函数
        tvTips.showTips();//显示提示RelativeLayout,移动动画.
    }

    @Override
    public void onAnimationEnd() {
        reportVideoPopwindow.dismiss();//动画结束，隐藏popupwindow
    }

    private View viewGroup;

    public void setParentView(View viewGroup) {
        this.viewGroup = viewGroup;
    }
}
