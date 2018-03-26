//package com.mjn.home.ui.home.adapter;
//
//import android.content.Context;
//import android.support.v4.view.PagerAdapter;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//
//import com.juzhongke.jzkseller.cons.GrowingID;
//import com.juzhongke.jzkseller.ui.login.splash.bean.GuideBean;
//import com.juzhongke.jzkseller.utils.GrowIOUtil.GrowIOUtil;
//import com.juzhongke.seller.R;
//
//import java.util.List;
//
//public class ViewPagerAdapter1 extends PagerAdapter implements View.OnClickListener {
//
//    private List<GuideBean> mViewList;
//    private Context mContext;
//
//    public ViewPagerAdapter1(Context context, List<GuideBean> viewsList) {
//        mContext = context;
//        mViewList = viewsList;
//    }
//
//    @Override
//    public int getCount() {
//        return mViewList.size();
//    }
//
//    @Override
//    public boolean isViewFromObject(View view, Object object) {
//        return view == object;
//    }
//
//    // 实例化 View
//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
//
//        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_guide_page, null);
//        ImageView pic = (ImageView) inflate.findViewById(R.id.iv_guide_pic);
//        pic.setImageResource(mViewList.get(position).getResId());
//        //if (position == mViewList.size() - 1) {
//        //View btn = inflate.findViewById(R.id.btn_guide_enter);
//        //btn.setVisibility(View.VISIBLE);
//        //btn.setOnClickListener(this);
//        //}
//
//        View btn_guide_login = inflate.findViewById(R.id.btn_guide_login);
//        btn_guide_login.setOnClickListener(this);
//        GrowIOUtil.setViewId(btn_guide_login, GrowingID.A_Splash_2Login);
//
//        View btn_guide_register = inflate.findViewById(R.id.btn_guide_register);
//        btn_guide_register.setOnClickListener(this);
//        GrowIOUtil.setViewId(btn_guide_register, GrowingID.A_Splash_2Register);
//
//        View btn_guide_experience = inflate.findViewById(R.id.btn_guide_experience);
//        btn_guide_experience.setOnClickListener(this);
//        GrowIOUtil.setViewId(btn_guide_experience, GrowingID.A_Splash_2Experience);
//
//        container.addView(inflate);
//        return inflate;
//    }
//
//    // 销毁 View
//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//        container.removeView((View) object);
//    }
//
//    @Override
//    public void onClick(View v) {
//        if (mClickListener == null) {
//            return;
//        }
//        switch (v.getId()) {
//            case R.id.btn_guide_login:
//                mClickListener.onGuideLoginButtonClick(v);
//                break;
//            case R.id.btn_guide_register:
//                mClickListener.onGuideRegisterButtonClick(v);
//                break;
//            case R.id.btn_guide_experience:
//                mClickListener.onGuideExperienceButtonClick(v);
//                break;
//            default:
//                break;
//        }
//    }
//
//    public OnGuideButtonClickListener getClickListener() {
//        return mClickListener;
//    }
//
//    public void setClickListener(OnGuideButtonClickListener clickListener) {
//        mClickListener = clickListener;
//    }
//
//    OnGuideButtonClickListener mClickListener;
//
//    interface OnGuideButtonClickListener {
//
//        void onGuideLoginButtonClick(View view);
//
//        void onGuideRegisterButtonClick(View view);
//
//        void onGuideExperienceButtonClick(View view);
//    }
//}
