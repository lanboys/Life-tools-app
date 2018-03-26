package com.bing.lan.comm.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bing.lan.comm.R;
import com.bing.lan.comm.utils.CashierInputFilterUtil;
import com.bing.lan.comm.utils.RegExpUtil;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.bing.lan.comm.view.EditTextInputLayout.RegExpType.TYPE_NO_TYPE;

public class EditTextInputLayout extends LinearLayout
        implements TextWatcher {

    // protected final LogUtil log = LogUtil.getLogUtil(getClass(), LogUtil.LOG_VERBOSE);
    RegExpValidator mRegExpValidator;
    EmptyValidator mEmptyValidator;

    EditTextWatcher mEditTextWatcher;
    @RegExpType.Type
    int mRegExpType = TYPE_NO_TYPE;
    private EditText mEdContent;
    private TextView mTvTitle;
    private ImageView mIvImage;
    private View mLineContainer;
    private LinearLayout mLlContainer;
    private boolean mIsEditEnable;
    /**
     * 开启校验总开关
     */
    private boolean isOpenValidate = true;

    public EditTextInputLayout(Context context) {
        super(context);
        initView(context, null);
    }

    public EditTextInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public EditText getEditText() {
        return mEdContent;
    }

    private void initView(Context context, AttributeSet attrs) {
        inflate(context, R.layout.view_input_layout, this);
        mEdContent = (EditText) findViewById(R.id.et_content);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mIvImage = (ImageView) findViewById(R.id.iv_image);
        mLineContainer = findViewById(R.id.fl_line_container);
        mLlContainer = (LinearLayout) findViewById(R.id.ll_container);
        mEdContent.addTextChangedListener(this);

        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.EditTextInputLayout);

            Drawable drawable = a.getDrawable(R.styleable.EditTextInputLayout_edit_layout_background);
            if (drawable != null) {
                setEditLayoutBackgroundDrawable(drawable);
            }

            String edit_hint = a.getString(R.styleable.EditTextInputLayout_edit_hint);
            String edit_text = a.getString(R.styleable.EditTextInputLayout_edit_text);

            String title_text = a.getString(R.styleable.EditTextInputLayout_title_text);
            if (title_text == null) {
                title_text = a.getString(R.styleable.EditTextInputLayout_title);
            }
            Drawable image_src = a.getDrawable(R.styleable.EditTextInputLayout_image_src);
            if (image_src == null) {
                image_src = a.getDrawable(R.styleable.EditTextInputLayout_image);
            }

            boolean line_show = a.getBoolean(R.styleable.EditTextInputLayout_line_show, true);
            String edit_digits = a.getString(R.styleable.EditTextInputLayout_edit_digits);

            Drawable title_drawableLeft = a.getDrawable(R.styleable.EditTextInputLayout_title_drawableLeft);
            mIsEditEnable = a.getBoolean(R.styleable.EditTextInputLayout_edit_enable, true);

            int edit_inputType = a.getInt(R.styleable.EditTextInputLayout_edit_inputType, -1);
            int image_visibility = a.getInt(R.styleable.EditTextInputLayout_image_visibility, -1);
            int title_visibility = a.getInt(R.styleable.EditTextInputLayout_title_visibility, -1);
            int edit_maxLength = a.getInt(R.styleable.EditTextInputLayout_edit_maxLength, -1);
            float edit_layout_height = a.getDimension(R.styleable.EditTextInputLayout_edit_layout_height, -1.0f);

            float image_height = a.getDimension(R.styleable.EditTextInputLayout_image_height, -1.0f);
            float image_width = a.getDimension(R.styleable.EditTextInputLayout_image_width, -1.0f);

            float title_marginLeft = a.getDimension(R.styleable.EditTextInputLayout_title_marginLeft, -1.0f);
            float title_marginRight = a.getDimension(R.styleable.EditTextInputLayout_title_marginRight, -1.0f);
            float title_drawableLeft_padding = a.getDimension(R.styleable.EditTextInputLayout_title_drawableLeft_padding, -1.0f);

            float title_text_size = a.getDimension(R.styleable.EditTextInputLayout_title_text_size, -1.0f);
            float edit_text_size = a.getDimension(R.styleable.EditTextInputLayout_edit_text_size, -1.0f);

            float title_paddingLeft = a.getDimension(R.styleable.EditTextInputLayout_edit_paddingLeft, -1.0f);
            float title_paddingRight = a.getDimension(R.styleable.EditTextInputLayout_edit_paddingRight, -1.0f);
            int edit_gravity = a.getInt(R.styleable.EditTextInputLayout_edit_gravity, -1);

            int title_color = a.getColor(R.styleable.EditTextInputLayout_title_color, -1);
            int edit_color = a.getColor(R.styleable.EditTextInputLayout_edit_color, -1);
            int edit_hint_color = a.getColor(R.styleable.EditTextInputLayout_edit_hint_color, -1);

            if (title_drawableLeft_padding != -1.0f) {
                setTitleDrawablePadding(px2dp((int) title_drawableLeft_padding));
            }

            if (title_text_size != -1.0f) {
                mTvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, title_text_size);
            }
            if (edit_text_size != -1.0f) {
                mEdContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, edit_text_size);
            }

            if (title_paddingLeft != -1.0f) {
                setEditPadding((int) title_paddingLeft, 0, 0, 0);
            }
            if (title_paddingRight != -1.0f) {
                setEditPadding(0, 0, (int) title_paddingRight, 0);
            }

            if (title_marginLeft != -1.0f) {
                setTitleMargins((int) title_marginLeft, 0, 0, 0);
            }
            if (title_marginRight != -1.0f) {
                setTitleMargins(0, 0, (int) title_marginRight, 0);
            }
            if (image_height != -1.0f) {
                setRightImageHeight((int) image_height);
            }
            if (image_width != -1.0f) {
                setRightImageWidth((int) image_width);
            }

            if (edit_hint_color != -1) {
                setEditHintColor(edit_hint_color);
            }
            if (edit_color != -1) {
                setEditColor(edit_color);
            }
            if (title_color != -1) {
                setTitleColor(title_color);
            }

            if (edit_gravity != -1) {
                setEditTextGravity(edit_gravity);
            }

            if (edit_layout_height != -1.0f) {
                setEditLayoutHeight((int) edit_layout_height);
            }

            boolean edit_cashier = a.getBoolean(R.styleable.EditTextInputLayout_edit_cashier, false);
            if (edit_cashier) {
                setEditOnlyCashier();
            }

            setEditMaxLength(edit_maxLength);
            setEditDigits(edit_digits);
            setEditEnabled(mIsEditEnable);
            setLineVisibility(line_show ? VISIBLE : GONE);

            if (image_visibility != -1) {
                setRightImageVisibility(image_visibility);
            }
            if (title_visibility != -1) {
                setTitleVisibility(title_visibility);
            }

            if (image_src != null) {
                mIvImage.setImageDrawable(image_src);
            }

            if (title_drawableLeft != null) {
                setTitleDrawableLeft(title_drawableLeft);
            }

            if (edit_inputType != -1) {
                // mEdContent.setInputType(edit_inputType);
                //
                // if (edit_inputType == 128) {
                //     setEditShowPassword(false);
                // }

                setEditInputType(edit_inputType);
            }

            if (edit_hint != null) {
                setEditHint(edit_hint);
            }

            if (title_text != null) {
                setTitle(title_text);
            }
            if (edit_text != null) {
                setEditContent(edit_text);
            }

            a.recycle();
        }
    }

    public int dp2px(int dip) {
        // denstity*dip=px;
        float density = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dip * density + .5f);
    }

    public int px2dp(int px) {
        // denstity*dip=px;
        float density = Resources.getSystem().getDisplayMetrics().density;
        return (int) (px / density + .5f);
    }

    public void setTitleTextSize(float size) {
        if (mTvTitle != null) {
            mTvTitle.setTextSize(size);
        }
    }

    //不填写单位，默认是dp
    public void setEditTextSize(float size) {
        if (mEdContent != null) {
            mEdContent.setTextSize(size);
        }
    }

    public void setTitleColor(int color) {
        if (mTvTitle != null) {
            mTvTitle.setTextColor(color);
        }
    }

    public void setEditColor(int color) {
        if (mEdContent != null) {
            mEdContent.setTextColor(color);
        }
    }

    public void setEditHintColor(int color) {
        if (mEdContent != null) {
            mEdContent.setHintTextColor(color);
        }
    }

    // 只有输入类型是 字符串 时才能起作用
    public void setEditDigits(String editDigits) {
        if (editDigits != null) {
            mEdContent.setKeyListener(DigitsKeyListener.getInstance(editDigits));
        }
    }

    /**
     * 整个布局的高度
     *
     * @param height
     */
    public void setEditLayoutHeight(int height) {
        LinearLayout.LayoutParams layoutParams = (LayoutParams) mLlContainer.getLayoutParams();
        layoutParams.height = height;
    }

    public void setEditLayoutBackgroundDrawable(Drawable background) {
        mLlContainer.setBackground(background);
    }

    public void setEditLayoutBackgroundRes(@DrawableRes int resId) {
        mLlContainer.setBackgroundResource(resId);
    }

    public void setRightImagePadding(int left, int top, int right, int bottom) {
        mIvImage.setPadding(left, top, right, bottom);
    }

    public void setRightImageMargins(int left, int top, int right, int bottom) {
        LinearLayout.LayoutParams layoutParams = (LayoutParams) mIvImage.getLayoutParams();
        layoutParams.setMargins(left, top, right, bottom);
    }

    public void setTitleMargins(int left, int top, int right, int bottom) {
        LinearLayout.LayoutParams layoutParams = (LayoutParams) mTvTitle.getLayoutParams();
        layoutParams.setMargins(left, top, right, bottom);
    }

    public void setTitleWidth(int width) {
        LinearLayout.LayoutParams layoutParams = (LayoutParams) mTvTitle.getLayoutParams();
        layoutParams.width = width;
    }

    public void setRightImageHeight(int height) {
        LinearLayout.LayoutParams layoutParams = (LayoutParams) mIvImage.getLayoutParams();
        layoutParams.height = height;
    }

    public void setRightImageWidth(int width) {
        LinearLayout.LayoutParams layoutParams = (LayoutParams) mIvImage.getLayoutParams();
        layoutParams.width = width;
    }

    public void setEditPadding(int left, int top, int right, int bottom) {
        mEdContent.setPadding(left, top, right, bottom);
    }

    public void setEditMaxLength(int editMaxLength) {
        if (editMaxLength >= 0) {
            setEditFilter(new InputFilter[]{new InputFilter.LengthFilter(editMaxLength)});
        }
    }

    public void setEditOnlyCashier() {
        setEditFilter(new InputFilter[]{new CashierInputFilterUtil()});
    }

    private void setEditFilter(InputFilter[] inputFilter) {
        mEdContent.setFilters(inputFilter);
    }

    public void setLineVisibility(int visibility) {
        if (mLineContainer != null) {
            mLineContainer.setVisibility(visibility);
        }
    }

    public void setRightImageOnClickListener(@Nullable OnClickListener l) {
        if (mIvImage != null) {
            mIvImage.setOnClickListener(l);
        }
    }

    public ImageView getRightImageView() {
        return mIvImage;
    }

    public void setRightImageResource(@DrawableRes int resId) {

        if (mIvImage != null) {
            mIvImage.setImageResource(resId);
        }
    }

    public void setRightImageVisibility(int visibility) {

        if (mIvImage != null) {
            mIvImage.setVisibility(visibility);
        }
    }

    public void setTitleVisibility(int visibility) {

        if (mTvTitle != null) {
            mTvTitle.setVisibility(visibility);
        }
    }

    public void setEditTextGravity(int gravity) {
        if (mEdContent != null) {
            mEdContent.setGravity(gravity);
        }
    }

    public void setTitleDrawableLeft(@NonNull Drawable drawable) {
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        mTvTitle.setCompoundDrawables(drawable, null, null, null);
    }

    public void setTitleDrawableLeft(@DrawableRes int resId) {
        if (resId != 0) {
            Drawable drawable = getResources().getDrawable(resId);
            setTitleDrawableLeft(drawable);
        }
    }

    public void setTitleDrawablePadding(int dp) {
        if (mTvTitle != null) {
            float density = getResources().getDisplayMetrics().density;
            int px = (int) (dp * density + .5f);
            mTvTitle.setCompoundDrawablePadding(px);
        }
    }

    public void setEditHint(@NonNull String s) {
        if (mEdContent != null) {
            mEdContent.setHint(s);
        }
    }

    public String getEditContent() {

        return mEdContent.getText().toString().trim();
    }

    public void setEditContent(@NonNull String s) {

        if (mEdContent != null) {
            mEdContent.setText(s);
        }
    }

    public void setEditShowPassword(boolean show) {
        if (mEdContent != null) {
            if (show) {
                //如果选中，显示密码
                mEdContent.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                //否则隐藏密码
                mEdContent.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
    }

    public void setEditInputType(int type) {

        if (mEdContent != null) {
            mEdContent.setInputType(type);
            if (type == 128) {
                setEditShowPassword(false);
            }
        }

        // //选择状态 显示明文--设置为可见的密码
        // mEdContent.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        // //默认状态显示密码--设置文本 要一起写才能起作用 InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
        // mEdContent.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }

    public CharSequence getTitle() {
        return mTvTitle.getText();
    }

    public void setTitle(@NonNull String s) {
        if (mTvTitle != null) {
            mTvTitle.setText(s);
        }
    }

    public TextView getTitleTextView() {
        return mTvTitle;
    }

    public boolean isEditTextEmpty() {
        return TextUtils.isEmpty(getEditContent());
    }

    public boolean isEditEnable() {
        return mIsEditEnable;
    }

    public void setEditEnabled(boolean editEnable) {
        mIsEditEnable = editEnable;
        mEdContent.setEnabled(mIsEditEnable);
    }

    /**
     * 如果 编辑框 设置为 不可点击 就拦截事件，交给父类处理
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //  return mIsEditEnable || super.onInterceptTouchEvent(ev);

        if (!mIsEditEnable) {
            return true;
        } else {
            return super.onInterceptTouchEvent(ev);
        }
    }

    public void setEditMultiLine() {
        //设置EditText的显示方式为多行文本输入
        mEdContent.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        //改变默认的单行模式
        mEdContent.setSingleLine(false);
        //水平滚动设置为False
        mEdContent.setHorizontallyScrolling(false);
    }

    public void setEditGravity(int gravity) {
        //文本显示的位置在EditText的最上方
        mEdContent.setGravity(gravity);
    }

    public void setEditTextWatcher(EditTextWatcher editTextWatcher) {
        mEditTextWatcher = editTextWatcher;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        if (mEditTextWatcher != null) {
            mEditTextWatcher.beforeTextChanged(charSequence, i, i1, i2);
        }
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (mEditTextWatcher != null) {
            mEditTextWatcher.onTextChanged(charSequence, i, i1, i2);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (mEditTextWatcher != null) {
            mEditTextWatcher.afterTextChanged(editable);
        }
        validateEditIsEmpty();
        validateEditEmpty();
    }

    public void setValidateType(@RegExpType.Type int regExpType) {
        mRegExpType = regExpType;
    }

    public void setRegExpValidator(RegExpValidator regExpValidator) {
        mRegExpValidator = regExpValidator;
    }

    public void setEmptyValidator(EmptyValidator emptyValidator) {
        mEmptyValidator = emptyValidator;
    }

    @Deprecated
    public void validateEditIsEmpty() {
        if (isOpenValidate) {
            if (mRegExpValidator != null) {
                mRegExpValidator.validateEditIsEmpty(getId(), isEditTextEmpty());
            }
        }
    }

    public void validateEditEmpty() {
        if (isOpenValidate) {
            if (mEmptyValidator != null) {
                mEmptyValidator.validateEditEmpty(getId(), isEditTextEmpty());
            }
        }
    }

    public boolean validateEditRegExp() {
        if (isOpenValidate) {
            if (mRegExpValidator != null) {
                return mRegExpValidator.validateEditRegExp(getId(), getEditContent());
            } else if (mRegExpType != TYPE_NO_TYPE) {
                return validateEditContentRegExp(mRegExpType);
            } else {
                throw new RuntimeException("请设置校验器或者校验类型再进行校验");
            }
        } else {//不打开验证按钮 直接全部通过
            return true;
        }
    }

    /**
     * 默认的正则校验器
     */
    private boolean validateEditContentRegExp(@RegExpType.Type int regExpType) {
        switch (regExpType) {
            case RegExpType.TYPE_MOBILE_PHONE:
                return RegExpUtil.checkPhoneNum(getEditContent());
            case RegExpType.TYPE_TELEPHONE:
                return RegExpUtil.checkTelephone(getEditContent());
            case RegExpType.TYPE_PASSWORD:
                return RegExpUtil.checkPassword(getEditContent());
            case RegExpType.TYPE_ID_CARD:
                return RegExpUtil.checkIdCard1(getEditContent());
            case RegExpType.TYPE_VCODE:
                return RegExpUtil.checkVcode(getEditContent());
            case RegExpType.TYPE_NICKNAME:
                return RegExpUtil.checkNickName(getEditContent());
            case RegExpType.TYPE_NOT_EMPTY:
                return RegExpUtil.checkNotEmpty(getEditContent());
            case RegExpType.TYPE_NO_TYPE:
                break;
            default:
                return false;
        }
        return false;
    }

    public interface EditTextWatcher {

        void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2);

        void onTextChanged(CharSequence charSequence, int i, int i1, int i2);

        void afterTextChanged(Editable editable);
    }

    public interface RegExpValidator {

        boolean validateEditRegExp(int id, String str);

        @Deprecated
        void validateEditIsEmpty(int id, boolean isEmpty);
    }

    public interface EmptyValidator {

        void validateEditEmpty(int id, boolean isEmpty);
    }

    public static class RegExpType {

        public static final int TYPE_NO_TYPE = -1;
        public static final int TYPE_MOBILE_PHONE = 0; //手机号
        public static final int TYPE_TELEPHONE = 1; //座机号
        public static final int TYPE_PASSWORD = 2;  //密码
        public static final int TYPE_ID_CARD = 3;  //身份证
        public static final int TYPE_VCODE = 4;  //验证码
        public static final int TYPE_NICKNAME = 5;  //昵称
        public static final int TYPE_NOT_EMPTY = 6;  //非空

        @IntDef({
                TYPE_NO_TYPE,
                TYPE_MOBILE_PHONE,
                TYPE_TELEPHONE,
                TYPE_PASSWORD,
                TYPE_ID_CARD,
                TYPE_VCODE,
                TYPE_NICKNAME,
                TYPE_NOT_EMPTY
        })
        @Retention(RetentionPolicy.SOURCE)
        public @interface Type {

        }
    }
}
