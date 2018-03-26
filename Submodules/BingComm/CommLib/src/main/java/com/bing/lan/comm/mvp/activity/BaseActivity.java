package com.bing.lan.comm.mvp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.widget.ImageView;

import com.bing.lan.comm.photoSelect.OnPhotoSelectListener;
import com.bing.lan.comm.photoSelect.PhotoSelectCropUtil;
import com.bing.lan.comm.photoSelect.PhotoSelectSource;
import com.bing.lan.comm.utils.SoftInputUtil;
import com.ganxin.library.LoadDataLayout;

import java.io.File;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.bing.lan.comm.cons.BaseCons.REQUEST_CODE_PERMISSION_BASE;

/**
 * @author 蓝兵
 */
public abstract class BaseActivity<T extends IBaseActivityContract.IBaseActivityPresenter>
        extends CommActivity
        implements IBaseActivityContract.IBaseActivityView<T>, OnPhotoSelectListener,
        SwipeRefreshLayout.OnRefreshListener {

    // @Inject
    // protected LogUtil log;
    // protected final LogUtil log = LogUtil.getLogUtil(getClass(), LogUtil.LOG_VERBOSE);
    protected Unbinder mViewBind;
    // @Inject
    protected T mPresenter;
    protected PhotoSelectCropUtil mSelectPhotoUtil;

    // protected ActivityComponent getActivityComponent() {
    //     return DaggerActivityComponent.builder()
    //             .activityModule(new ActivityModule(this, getIntent()))
    //             .build();
    // }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //初始化布局
        initWindowUI();
        //启动di
        mPresenter = initPresenter();
        // startInject(getActivityComponent());

        //初始化View 和 数据
        initViewAndData(getIntent());

        //友盟(注意: 16的模拟器一用就卡死 )
        // MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        //友盟

        // //选择照片
        // if (isHaveSelectPhoto()) {
        //     //mSelectPhotoUtil = new PhotoSelectUtil(this);
        //     mSelectPhotoUtil = new PhotoSelectCropUtil(this);
        //     mSelectPhotoUtil.setPhotoSelectListener(this);
        // }

        //获取权限
        if (isCheckPermissions() && getPermissionArrId() != 0) {
            requestPermissions(REQUEST_CODE_PERMISSION_BASE, getPermissionArrId());
        } else {
            readyStartPresenter();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mPresenter != null) {
            mPresenter.onDetachView();
        }

        //解绑 必须后面执行，否则有些延时很久的成功请求 返回来 报空指针
        if (mViewBind != null) {
            mViewBind.unbind();
            mViewBind = null;
        }
        //AppUtil.MemoryLeakCheck(this);
    }

    // protected abstract void startInject(ActivityComponent activityComponent);
    // protected void startInject(ActivityComponent activityComponent) {
    //
    // }

    protected abstract T initPresenter();

    protected void requestBasePermissionSucceed(List<String> successPermissions) {
        super.requestBasePermissionSucceed(successPermissions);
        readyStartPresenter();
    }

    protected abstract int getLayoutResId();

    protected abstract void initViewAndData(Intent intent);

    /**
     * 权限请求成功时调用
     */
    protected abstract void readyStartPresenter();

    protected void initWindowUI() {
        //初始化布局
        setContentView(getLayoutResId());
        //绑定控件
        mViewBind = ButterKnife.bind(this);
    }

    @Override
    public T getPresenter() {
        return mPresenter;
    }

    /**
     * 要设置了toolbar(调用setToolBar()) 才能显示出来菜单项
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (getMenuId() <= 0) {
            return super.onCreateOptionsMenu(menu);
        }
        getMenuInflater().inflate(getMenuId(), menu);
        return true;
    }

    /**
     * 加载图片
     */
    // protected void loadImage(Object path, ImageView imageView) {
    //     mPresenter.loadImage(path, imageView);
    // }
    protected int getMenuId() {
        return 0;
    }

    // 拍照返回
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mSelectPhotoUtil != null) {
            mSelectPhotoUtil.onSelectActivityResult(requestCode, resultCode, data);
        }
    }

    //选择照片
    protected void selectPhoto(ImageView imageView) {
        selectPhoto(imageView, PhotoSelectSource.SELECT_ALL);
    }

    //选择照片
    protected void selectPhoto(ImageView imageView, @PhotoSelectSource int type) {
        SoftInputUtil.closeSoftInput(this);
        if (mSelectPhotoUtil == null) {
            // if (isHaveSelectPhoto()) {
            //mSelectPhotoUtil = new PhotoSelectUtil(this);
            mSelectPhotoUtil = new PhotoSelectCropUtil(this);
            mSelectPhotoUtil.setPhotoSelectListener(this);
            // }
        }

        if (type == PhotoSelectSource.SELECT_CAMERA) {
            //拍照
            mSelectPhotoUtil.selectPhotoFromCamera(imageView);
        } else if (type == PhotoSelectSource.SELECT_ALBUM) {
            //相册
            mSelectPhotoUtil.selectPhotoFromAlbum(imageView);
        } else if (type == PhotoSelectSource.SELECT_ALL) {
            //用户选择
            mSelectPhotoUtil.showSelectPhotoDialog(imageView);
        }
    }

    @Override
    public void onPhotoSelect(ImageView imageView, File source) {
        //Toast.makeText(this, "上传图片", Toast.LENGTH_SHORT).show();

        // File file = new File(source.getPath());
        // mPresenter.onPhotoSelect(file);

    }

    // protected boolean isHaveSelectPhoto() {
    //     return true;
    // }

    // @Override
    // public void onResume() {
    //     super.onResume();
    //友盟
    // MobclickAgent.onResume(this);
    //友盟
    // }

    // @Override
    // public void onPause() {
    //     super.onPause();
    //友盟
    // MobclickAgent.onPause(this);
    //友盟
    // }

    @Override
    public void onRefresh() {

    }

    @Override
    public void setLoadDataLayoutStatus(@LoadDataLayout.Flavour int state) {

    }

    @Override
    public void closeRefreshing() {
        //if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
        //    mSwipeRefreshLayout.setRefreshing(false);
        //}
    }
}
