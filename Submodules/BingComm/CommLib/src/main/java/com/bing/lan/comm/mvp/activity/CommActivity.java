package com.bing.lan.comm.mvp.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bing.lan.comm.R;
import com.bing.lan.comm.app.AppConfig;
import com.bing.lan.comm.app.AppUtil;
import com.bing.lan.comm.app.BaseApplication;
import com.bing.lan.comm.rxTools.RxToast;
import com.bing.lan.comm.utils.ProgressDialogUtil;
import com.bing.lan.comm.utils.SPUtil;

import static android.support.v7.app.AppCompatDelegate.MODE_NIGHT_NO;
import static android.support.v7.app.AppCompatDelegate.MODE_NIGHT_YES;
import static android.support.v7.app.AppCompatDelegate.setDefaultNightMode;
import static android.view.KeyEvent.KEYCODE_VOLUME_DOWN;
import static android.view.KeyEvent.KEYCODE_VOLUME_UP;

/**
 * @author 蓝兵
 */
public class CommActivity extends PermissionActivity {

    private ProgressDialogUtil mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isMonitorNetwork()) {
            boolean isNetworkAvailable = BaseApplication.netWorkStatus.isNetworkAvailable;
            String netWorkTip = BaseApplication.netWorkStatus.netWorkTip;

            if (!isNetworkAvailable) {
                showInfo(netWorkTip);
            }
        }
    }

    /**
     * 日间和夜景模式切换
     */
    protected void switchNightMode() {
        boolean isNight = !SPUtil.getBoolean(AppConfig.DAY_NIGHT_MODE);
        setDefaultNightMode(isNight ? MODE_NIGHT_YES : MODE_NIGHT_NO);
        SPUtil.putBoolean(AppConfig.DAY_NIGHT_MODE, isNight);
        recreate();
    }

    /**
     * 判断当前的模式
     *
     * @return true表示夜景模式
     */
    protected boolean isNightMode() {
        return SPUtil.getBoolean(AppConfig.DAY_NIGHT_MODE);
    }

    public void startActivity(Class<? extends BaseActivity> clazz, boolean isFinish, boolean isAnim) {
        AppUtil.startActivity(this, clazz, isFinish, false);
        if (isAnim) {
            // overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
        }
    }

    public void startActivity(Intent intent, boolean isFinish, boolean isAnim) {
        AppUtil.startActivity(this, intent, isFinish, false);
        if (isAnim) {
            // overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
        }
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
        setSupportActionBar(toolBar);//不调用 setTitle() 会用默认标题，类名？
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
                    onBackPressed();
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

    private boolean isFirstVisible = true;

    @Override
    protected void onStart() {
        super.onStart();

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

    public void showError(CharSequence msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }

        //RxToast.error(AppUtil.getAppContext(), (String) msg, Toast.LENGTH_SHORT, true).show();
        showToast(msg);
    }

    public void showInfo(CharSequence msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }

        //RxToast.info(AppUtil.getAppContext(), (String) msg, Toast.LENGTH_SHORT, true).show();
        showToast(msg);
    }

    public void showTip(CharSequence msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        View view = LayoutInflater.from(this).inflate(R.layout.pop_tip_layout, null);
        TextView tip = (TextView) view.findViewById(R.id.tv_tip);
        tip.setText(msg);

        //背景颜色
        // view.setBackgroundColor(Color.WHITE);
        PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setAnimationStyle(R.style.PopupWindowTipAnimation);
        //显示（自定义位置）随便new一个view 填下去 或者如下写法
        try {
            popupWindow.showAtLocation(getWindow().getDecorView().findViewById(android.R.id.content),
                    Gravity.NO_GRAVITY  /* | Gravity.CENTER_VERTICAL*/, 0, 250);
        } catch (Exception e) {
            log.e("showTip():  " + e.getLocalizedMessage());
        }
    }

    public void showToast(CharSequence msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        // Toast.makeText(AppUtil.getAppContext(), msg, Toast.LENGTH_SHORT).show();
        // RxToast.success(AppUtil.getAppContext(), msg, Toast.LENGTH_SHORT, false).show();
        RxToast.normal(AppUtil.getAppContext(), (String) msg).show();
        // RxToast.error(mContext, "这是一个提示错误的Toast！", Toast.LENGTH_SHORT, true).show();
        // RxToast.success(mContext, "这是一个提示成功的Toast!", Toast.LENGTH_SHORT, true).show();
        // RxToast.info(mContext, "这是一个提示信息的Toast.", Toast.LENGTH_SHORT, true).show();
        // RxToast.warning(mContext, "这是一个提示警告的Toast.", Toast.LENGTH_SHORT, true).show();
        // RxToast.normal(mContext, "这是一个普通的没有ICON的Toast").show();
        // Drawable icon = getResources().getDrawable(R.drawable.set);
        // RxToast.normal(mContext, "这是一个普通的包含ICON的Toast", icon).show();

    }

    public void showProgressDialog(CharSequence msg) {

        try {

            if (mProgressDialog != null) {
                mProgressDialog.setProgressMessage(msg == null ? "" : msg);
            } else {
                mProgressDialog = new ProgressDialogUtil(this);
                mProgressDialog.setProgressMessage(msg == null ? "" : msg);
                mProgressDialog.setCancelable(false);
            }
            mProgressDialog.show();
        } catch (Exception e) {
            //Activity com.juzhongke.jzkseller.ui.table.tableManagement.TableManagementActivity
            // has leaked window com.android.internal.policy.PhoneWindow$DecorView{526edd1 V.E.
            // ..... R......D 0,0-360,360} that was originally added here

            //vivo有时会报错

            log.e("showProgressDialog():  ", e);
        }
    }

    public void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            //mProgressDialog = null;
        }
    }

    protected boolean isMonitorNetwork() {
        return true;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KEYCODE_VOLUME_UP) {
            onMediaVolume(true);
            return true;
        } else if (keyCode == KEYCODE_VOLUME_DOWN) {
            onMediaVolume(false);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    protected void onMediaVolume(boolean isVolumeUp) {
        ((AudioManager) getSystemService(Context.AUDIO_SERVICE)).
                adjustStreamVolume(
                        AudioManager.STREAM_MUSIC,
                        isVolumeUp ? AudioManager.ADJUST_RAISE : AudioManager.ADJUST_LOWER,
                        AudioManager.FX_FOCUS_NAVIGATION_UP);
    }
}
