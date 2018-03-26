// package com.bing.lan.comm.mvp.test;
//
// import android.content.Intent;
// import android.os.Bundle;
//
// /**
//  * @author 蓝兵
//  */
// public class FragTestFragment extends BaseFragment<IFragTestContract.IFragTestPresenter>
//         implements IFragTestContract.IFragTestView {
//
//     public FragTestFragment() {
//
//     }
//
//     public static FragTestFragment newInstance(String title) {
//         FragTestFragment fragment = new FragTestFragment();
//         Bundle args = new Bundle();
//         args.putString("fragment标题", title);
//         fragment.setArguments(args);
//         return fragment;
//     }
//
//     @Override
//     protected int getLayoutResId() {
//         return 0;
//     }
//
//     @Override
//     protected IFragTestContract.IFragTestPresenter initPresenter() {
//         FragTestPresenter presenter = new FragTestPresenter();
//         presenter.setModule(new FragTestModule());
//         presenter.onAttachView(this);
//         return presenter;
//     }
//
//     // @Override
//     // protected void startInject(FragmentComponent fragmentComponent) {
//     //     //        fragmentComponent.inject(this);
//     // }
//
//     @Override
//     protected void initViewAndData(Intent intent, Bundle arguments) {
//
//     }
//
//     @Override
//     protected void readyStartPresenter() {
//
//     }
// }
