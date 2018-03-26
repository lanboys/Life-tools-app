//package com.bing.lan.comm.view;
//
//import android.content.Context;
//import android.content.res.TypedArray;
//import android.graphics.drawable.Drawable;
//import android.support.annotation.DrawableRes;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.text.Editable;
//import android.text.InputFilter;
//import android.text.TextUtils;
//import android.text.TextWatcher;
//import android.text.method.DigitsKeyListener;
//import android.text.method.HideReturnsTransformationMethod;
//import android.text.method.PasswordTransformationMethod;
//import android.util.AttributeSet;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.bing.lan.comm.R;
//import com.bing.lan.comm.utils.CashierInputFilterUtil;
//
//public class EditTextInputView extends LinearLayout implements TextWatcher {
//
//    RegExpValidator mValidator;
//    EditTextWatcher mEditTextWatcher;
//    private EditText mEdContent;
//    private ImageView mIvImage;
//    private TextView mTextView;
//    /**
//     * 开启校验总开关
//     */
//    private boolean isOpenValidate = true;
//
//    public EditTextInputView(Context context) {
//        super(context);
//        initView(context, null);
//    }
//
//    public EditTextInputView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        initView(context, attrs);
//    }
//
//    public EditTextInputView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        initView(context, attrs);
//    }
//
//    private void initView(Context context, AttributeSet attrs) {
//        inflate(context, R.layout.view_edittext_input, this);
//
//        mEdContent = (EditText) findViewById(R.id.et_content);
//        mIvImage = (ImageView) findViewById(R.id.iv_image);
//        mTextView = (TextView) findViewById(R.id.tv_content);
//        mEdContent.addTextChangedListener(this);
//
//        if (attrs != null) {
//            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.EditTextInputView);
//            String hint = a.getString(R.styleable.EditTextInputView_edit_hint1);
//            String text = a.getString(R.styleable.EditTextInputView_text_string1);
//            String editDigits = a.getString(R.styleable.EditTextInputView_edit_digits1);
//            int editMaxLength = a.getInt(R.styleable.EditTextInputView_edit_maxLength1, -1);
//            Drawable drawable = a.getDrawable(R.styleable.EditTextInputView_image1);
//            boolean enable = a.getBoolean(R.styleable.EditTextInputView_edit_enable1, true);
//            boolean cashier = a.getBoolean(R.styleable.EditTextInputView_edit_cashier1, false);
//            int inputType = a.getInt(R.styleable.EditTextInputView_edit_inputType1, -1);
//            int visible = a.getInt(R.styleable.EditTextInputView_text_visibility1, -1);
//            mEdContent.setEnabled(enable);
//            setEditMaxLength(editMaxLength);
//            setEditDigits(editDigits);
//            if (cashier) {
//                setEditOnlyCashier();
//            }
//
//            if (visible != -1) {
//                switch (visible) {
//                    case View.GONE:
//                        mTextView.setVisibility(GONE);
//                        break;
//                    case View.INVISIBLE:
//                        mTextView.setVisibility(INVISIBLE);
//                        break;
//                    case View.VISIBLE:
//                        mTextView.setVisibility(VISIBLE);
//                        break;
//                }
//            }
//
//            if (drawable != null) {
//                mIvImage.setImageDrawable(drawable);
//            }
//
//            if (inputType != -1) {
//                mEdContent.setInputType(inputType);
//
//                if (inputType == 128) {
//                    setEditShowPassword(false);
//                }
//            }
//
//            if (hint != null) {
//                mEdContent.setHint(hint);
//            }
//
//            if (text != null) {
//                mTextView.setHint(text);
//            }
//
//            a.recycle();
//        }
//    }
//
//    public void setEditShowPassword(boolean show) {
//        if (mEdContent != null) {
//            if (show) {
//                //如果选中，显示密码
//                mEdContent.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//            } else {
//                //否则隐藏密码
//                mEdContent.setTransformationMethod(PasswordTransformationMethod.getInstance());
//            }
//        }
//    }
//
//    public void setEditDigits(String editDigits) {
//        if (editDigits != null) {
//            mEdContent.setKeyListener(DigitsKeyListener.getInstance(editDigits));
//        }
//    }
//
//    public void setEditMaxLength(int editMaxLength) {
//
//        if (editMaxLength >= 0) {
//            setEditFilter(new InputFilter[]{new InputFilter.LengthFilter(editMaxLength)});
//        }
//    }
//
//    public void setEditOnlyCashier() {
//        setEditFilter(new InputFilter[]{new CashierInputFilterUtil()});
//    }
//
//    private void setEditFilter(InputFilter[] inputFilter) {
//        mEdContent.setFilters(inputFilter);
//    }
//
//    public void setLeftImageDrawable(Drawable drawable) {
//        if (drawable != null && mIvImage != null) {
//            mIvImage.setImageDrawable(drawable);
//        }
//    }
//
//    public void setLeftImageResource(@DrawableRes int resId) {
//
//        if (mIvImage != null) {
//            mIvImage.setImageResource(resId);
//        }
//    }
//
//    public void setLeftImageVisibility(int visibility) {
//
//        if (mIvImage != null) {
//            mIvImage.setVisibility(visibility);
//        }
//    }
//
//    public ImageView getLeftImageView() {
//        return mIvImage;
//    }
//
//    public EditText getEditTextView() {
//        return mEdContent;
//    }
//
//    public TextView getRightTextView() {
//        return mTextView;
//    }
//
//    public void setTextViewVisibility(int visibility) {
//
//        if (mTextView != null) {
//            mTextView.setVisibility(visibility);
//        }
//    }
//
//    public void setEditEnabled(boolean enabled) {
//        mEdContent.setEnabled(false);
//    }
//
//    public void setTextViewClickable(boolean clickable) {
//
//        if (mTextView != null) {
//            mTextView.setClickable(clickable);
//        }
//    }
//
//    public void setTextViewOnClickListener(@Nullable OnClickListener l) {
//        if (mTextView != null) {
//            mTextView.setOnClickListener(l);
//        }
//    }
//
//    public void setEditHint(@NonNull String s) {
//        if (mEdContent != null) {
//            mEdContent.setHint(s);
//        }
//    }
//
//    public String getEditContent() {
//
//        return mEdContent.getText().toString().trim();
//    }
//
//    public void setEditContent(@NonNull String s) {
//
//        if (mEdContent != null) {
//            mEdContent.setText(s);
//        }
//    }
//
//    public CharSequence getTextViewString() {
//        return mTextView.getText();
//    }
//
//    public void setTextViewString(@NonNull String s) {
//
//        if (mTextView != null) {
//            mTextView.setText(s);
//        }
//    }
//
//    public boolean isEditTextEmpty() {
//        return TextUtils.isEmpty(getEditContent());
//    }
//
//    public void setEditTextGravity(int gravity) {
//        if (mEdContent != null) {
//            mEdContent.setGravity(gravity);
//        }
//    }
//
//    public void setValidator(RegExpValidator validator) {
//        mValidator = validator;
//    }
//
//    public boolean validateEditRegExp() {
//
//        if (isOpenValidate) {
//
//            if (mValidator != null) {
//                return mValidator.validateEditRegExp(getId(), getEditContent());
//            } else {
//                throw new RuntimeException("请先设置校验器");
//            }
//        } else {
//            return true;
//        }
//    }
//
//    public void validateEditIsEmpty() {
//        if (isOpenValidate) {
//            if (mValidator != null) {
//                mValidator.validateEditIsEmpty(getId(), isEditTextEmpty());
//            }
//        }
//    }
//
//    public void setEditTextWatcher(EditTextWatcher editTextWatcher) {
//        mEditTextWatcher = editTextWatcher;
//    }
//
//    @Override
//    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//        if (mEditTextWatcher != null) {
//            mEditTextWatcher.beforeTextChanged(charSequence, i, i1, i2);
//        }
//    }
//
//    @Override
//    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//        if (mEditTextWatcher != null) {
//            mEditTextWatcher.onTextChanged(charSequence, i, i1, i2);
//        }
//    }
//
//    @Override
//    public void afterTextChanged(Editable editable) {
//        if (mEditTextWatcher != null) {
//            mEditTextWatcher.afterTextChanged(editable);
//        }
//
//        validateEditIsEmpty();
//    }
//
//    public interface EditTextWatcher {
//
//        void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2);
//
//        void onTextChanged(CharSequence charSequence, int i, int i1, int i2);
//
//        void afterTextChanged(Editable editable);
//    }
//
//    public interface RegExpValidator {
//
//        boolean validateEditRegExp(int id, String str);
//
//        void validateEditIsEmpty(int id, boolean isEmpty);
//    }
//}
