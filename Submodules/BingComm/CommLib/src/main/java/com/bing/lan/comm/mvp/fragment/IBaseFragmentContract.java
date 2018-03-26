package com.bing.lan.comm.mvp.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.bing.lan.comm.mvp.IBaseContract.IBaseModule;
import com.bing.lan.comm.mvp.IBaseContract.IBasePresenter;
import com.bing.lan.comm.mvp.IBaseContract.IBaseView;

public interface IBaseFragmentContract {

    interface IBaseFragmentView<T extends IBaseFragmentPresenter> extends IBaseView<T> {

        void stopUpdate();

        void reStartUpdate();

        //void setState2PagerLayout(PagerLayout.LoadDataResult loadDataResult);

        //void resetErrorCount();

        FragmentActivity getActivity();

        //boolean isHaveData();

        //void setHaveData(boolean haveData);

        void updateTitle(String title);
    }

    interface IBaseFragmentPresenter<T extends IBaseFragmentView, M extends IBaseFragmentModule>
            extends IBasePresenter<T, M> {

        void stopUpdate();

        void setParams(Bundle bundle);

        void reStartUpdate();

        String getTitle();
    }

    interface IBaseFragmentModule extends IBaseModule {

    }
}
