package com.bing.lan.comm.mvp.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bing.lan.comm.R;
import com.bing.lan.comm.app.AppUtil;
import com.bing.lan.comm.mvp.activity.BaseActivity;
import com.bing.lan.comm.rxTools.RxToast;
import com.bing.lan.comm.utils.ProgressDialogUtil;

/**
 * Created by 520 on 2017/7/13.
 */

public class CommFragment extends LifecycleFragment {

    protected String mTitle;
    private ProgressDialogUtil mProgressDialog;

    /**
     * 获取标题
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * 设置标题
     */
    public void setTitle(String title) {
        mTitle = title;
    }

    public void startActivity(Class<? extends BaseActivity> clazz, boolean isFinish, boolean isAnim) {
        AppUtil.startActivity(getActivity(), clazz, isFinish, false);
    }

    public void startActivity(Intent intent, boolean isFinish, boolean isAnim) {
        AppUtil.startActivity(getActivity(), intent, isFinish, false);
        if (isAnim) {
            // overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
        }
    }

    /**
     * 默认false
     */
    public void startActivity(Class<? extends BaseActivity> clazz) {
        startActivity(clazz, false, true);
    }

    /**
     * 设置页面的toolbar
     *
     * @param toolBar        toolBar
     * @param title          标题
     * @param finishActivity 是否设置结束activity事件
     * @param resId          返回箭头图标 0 默认图标  >0 设置resId
     */
    protected void setToolBar(Toolbar toolBar, String title, boolean finishActivity, int resId) {

        //自定标题
        TextView toolBarTitle = (TextView) toolBar.findViewById(R.id.toolbar_title);
        if (title != null && toolBarTitle != null) {
            toolBarTitle.setText(title);
            toolBarTitle.setTextColor(Color.WHITE);

            //ViewGroup.LayoutParams layoutParams = toolBarTitle.getLayoutParams();
            //
            //RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) layoutParams;
            //params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
            //params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            //params.addRule(RelativeLayout.RIGHT_OF, 0);
        }
        //不想有标题，title 传 ""
        toolBar.setTitle("");
        //setSupportActionBar(toolBar);//不调用 setTitle() 会用默认标题，类名？
        // toolBar.setIcon(R.mipmap.ic_launcher);// 设置应用图标
        toolBar.setTitleTextColor(Color.WHITE);

        if (finishActivity) {

            // 全部更改为自定义的toolbar
            ImageView iv_toolbar_back = (ImageView) toolBar.findViewById(R.id.iv_toolbar_back);

            iv_toolbar_back.setVisibility(View.VISIBLE);
            if (resId > 0) {
                iv_toolbar_back.setImageResource(resId);
            }

            iv_toolbar_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().onBackPressed();
                }
            });

            // ActionBar actionBar = getSupportActionBar();
            // if (actionBar != null) {
            //     //将默认的 返回箭头 显示出来
            //     actionBar.setDisplayHomeAsUpEnabled(true);
            //     // 返回箭头的图标
            //     if (resId > 0) {
            //         actionBar.setHomeAsUpIndicator(resId);
            //     } else {
            //         actionBar.setHomeAsUpIndicator(R.drawable.iv_back);
            //     }
            // }
            // //给箭头添加监听器
            // toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            //     @Override
            //     public void onRecyclerViewItemClick(View v) {
            //         onBackPressed();
            //     }
            // });
        }
    }

    public void showError(CharSequence msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        //RxToast.error(AppUtil.getAppContext(), (String) msg, Toast.LENGTH_SHORT, true).show();
        showToast(msg);
    }

    public void showTip(CharSequence msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.pop_tip_layout, null);
        TextView tip = (TextView) view.findViewById(R.id.tv_tip);
        tip.setText(msg);

        //背景颜色
        // view.setBackgroundColor(Color.WHITE);
        PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setAnimationStyle(R.style.PopupWindowTipAnimation);
        //显示（自定义位置）随便new一个view 填下去 或者如下写法
        try {
            popupWindow.showAtLocation(getActivity().getWindow().getDecorView().findViewById(android.R.id.content),
                    Gravity.NO_GRAVITY  /* | Gravity.CENTER_VERTICAL*/, 0, 250);
        } catch (Exception e) {
            log.e("showTip():  " + e.getLocalizedMessage());
        }
    }

    public void showInfo(CharSequence msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }

        //RxToast.info(AppUtil.getAppContext(), (String) msg, Toast.LENGTH_SHORT, true).show();
        showToast(msg);
    }

    public void showProgressDialog(CharSequence msg) {
        try {
            if (mProgressDialog != null) {
                mProgressDialog.setProgressMessage(msg == null ? "" : msg);
            } else {
                mProgressDialog = new ProgressDialogUtil(getActivity());
                mProgressDialog.setProgressMessage(msg == null ? "" : msg);
                mProgressDialog.setCancelable(false);
            }
            mProgressDialog.show();
        } catch (Exception e) {
            log.e("showProgressDialog():  ", e);
        }
    }

    public void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            //mProgressDialog = null;
        }
    }

    /**
     * 显示 吐司
     *
     * @param msg 显示的消息
     */
    public void showToast(CharSequence msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        // Toast.makeText(AppUtil.getAppContext(), msg, Toast.LENGTH_SHORT).show();

        RxToast.normal(AppUtil.getAppContext(), (String) msg).show();
    }
}
