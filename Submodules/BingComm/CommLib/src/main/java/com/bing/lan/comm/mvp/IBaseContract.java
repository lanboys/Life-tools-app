package com.bing.lan.comm.mvp;

import android.content.Intent;

import com.bing.lan.comm.mvp.activity.BaseActivity;
import com.bing.lan.comm.rx.OnDataChangerListener;
import com.ganxin.library.LoadDataLayout;

public interface IBaseContract {

    interface IBaseView<T extends IBasePresenter> {

        void showError(CharSequence msg);

        void showToast(CharSequence msg);

        void showInfo(CharSequence msg);

        void showTip(CharSequence msg);

        void showProgressDialog(CharSequence msg);

        void dismissProgressDialog();

        void closeRefreshing();

        void setLoadDataLayoutStatus(@LoadDataLayout.Flavour int state);

        T getPresenter();

        void startActivity(Class<? extends BaseActivity> clazz, boolean isFinish, boolean isAnim);

        void startActivity(Intent intent, boolean isFinish, boolean isAnim);

        // void startActivity(Class<? extends JzkActivity> clazz);
    }

    interface IBasePresenter<T extends IBaseView, M extends IBaseModule> extends OnDataChangerListener {

        void showError(CharSequence msg);

        void showToast(CharSequence msg);

        void showInfo(CharSequence msg);

        void showTip(CharSequence msg);

        void showProgressDialog(CharSequence msg);

        void dismissProgressDialog();

        void onAttachView(T view);

        void onDetachView();

        void onStart(Object... params);

        void setModule(M module);

        // void loadImage(Object path, ImageView imageView);

        void requestData(int action, Object... parameter);
    }

    interface IBaseModule {

        void releaseTask();

        // void loadImage(Object path, ImageView imageView);

        // void loadData(int action, OnDataChangerListener listener, Object... parameter);

        void refreshTask(int action);

        void requestData(int action, OnDataChangerListener listener, Object... parameter);

        OnDataChangerListener wrapperOnDataChangerListener(final OnDataChangerListener presenter);
    }
}
