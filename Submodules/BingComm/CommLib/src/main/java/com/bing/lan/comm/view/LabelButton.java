package com.bing.lan.comm.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bing.lan.comm.R;

/**
 * Created by 520 on 2017/7/7.
 */

public class LabelButton extends FrameLayout implements View.OnClickListener {

    ImageView shade_view;
    TextView button;
    OnLabelClickListener mListener;

    public LabelButton(@NonNull Context context) {
        super(context);
        initView(context, null);
    }

    public LabelButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        inflate(context, R.layout.view_label_button, this);

        shade_view = (ImageView) findViewById(R.id.shade_view);
        button = (TextView) findViewById(R.id.button);

        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.LabelButton);

            String text = a.getString(R.styleable.LabelButton_button_text);
            Drawable background = a.getDrawable(R.styleable.LabelButton_button_background);
            ColorStateList colors = a.getColorStateList(R.styleable.LabelButton_button_text_color);

            float alpha = a.getFloat(R.styleable.LabelButton_shade_alpha, 0);
            Drawable drawable = a.getDrawable(R.styleable.LabelButton_shade_background);
            if (text != null) {
                button.setText(text);
            }
            if (background != null) {
                button.setBackground(background);
            }
            if (colors != null) {
                button.setTextColor(colors);
            }
            shade_view.setImageAlpha((int) alpha);
            if (drawable != null) {
                shade_view.setBackground(drawable);
            }
            a.recycle();
        }

        button.setOnClickListener(this);
    }

    public void setButtonText(CharSequence text) {
        button.setText(text);
    }

    public void setButtonSelect(boolean selected) {
        button.setSelected(selected);
    }

    public void setButtonEnabled(boolean enabled) {
        if (button == null) {
            return;
        }
        button.setEnabled(enabled);
        shade_view.setVisibility(enabled ? View.GONE : View.VISIBLE);
    }

    public void setOnLabelClickListener(@Nullable final OnLabelClickListener l) {
        mListener = l;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public void onClick(View v) {
        button.setSelected(!button.isSelected());

        if (mListener != null) {
            mListener.onLabelClick(this, button.isSelected());
        }
    }

    public interface OnLabelClickListener {

        void onLabelClick(View v, boolean isSelected);
    }


    public void setButtonTextColor(int id){
        button.setTextColor(id);
    }

    public void setButtonBackground(int id){
        button.setBackgroundResource(id);
    }
}
