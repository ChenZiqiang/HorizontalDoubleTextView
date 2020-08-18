package com.example.com;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;

/**
 * @author 陈自强
 * @version 1.0
 * @date 2020/7/22
 */
public class HorizontalDoubleTextView extends LinearLayout {
    private TextView leftText;
    private TextView rightText;
    private Context mContext;

    public HorizontalDoubleTextView(Context context) {
        super(context);
    }

    public HorizontalDoubleTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
        initTextView(attrs);
    }

    private void init() {
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
    }

    private void initTextView(AttributeSet attrs) {
        TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.HorizontalDoubleTextView);
        leftText = new TextView(mContext);
        if (ta.getBoolean(R.styleable.HorizontalDoubleTextView_right_edit, true)) {
            rightText = new EditText(mContext);
        } else {
            rightText = new TextView(mContext);
        }

        LayoutParams leftParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        leftText.setLayoutParams(leftParams);
        LayoutParams rightParams = new LayoutParams(0, LayoutParams.MATCH_PARENT);
        rightParams.weight =1;
        rightText.setLayoutParams(rightParams);

        // 通过这个方法，将你在attrs.xml中定义的declare-styleable的所有属性的值存储到TypedArray.
        // 从TypedArray中取出对应的值来为要设置的属性赋值
        leftText.setText(ta.getString(R.styleable.HorizontalDoubleTextView_left_text));
        leftText.setTextSize(ta.getDimension(R.styleable.HorizontalDoubleTextView_left_text_size, 14));
        leftText.setTextColor(ta.getColor(R.styleable.HorizontalDoubleTextView_left_text_color, mContext.getColor(R.color.mine_shaft)));
        leftText.setBackground(ta.getDrawable(R.styleable.HorizontalDoubleTextView_left_text_background));
        leftText.setGravity(ta.getInteger(R.styleable.HorizontalDoubleTextView_left_gravity, Gravity.START | Gravity.CENTER_VERTICAL));

        rightText.setText(ta.getString(R.styleable.HorizontalDoubleTextView_right_text));
        rightText.setTextSize(ta.getDimension(R.styleable.HorizontalDoubleTextView_right_text_size, 14));
        rightText.setTextColor(ta.getColor(R.styleable.HorizontalDoubleTextView_right_text_color, mContext.getColor(R.color.mine_shaft)));
        rightText.setBackground(ta.getDrawable(R.styleable.HorizontalDoubleTextView_right_text_background));
        rightText.setInputType(ta.getInteger(R.styleable.HorizontalDoubleTextView_right_input_type, InputType.TYPE_CLASS_TEXT));
        rightText.setHint(ta.getString(R.styleable.HorizontalDoubleTextView_right_hint));
        rightText.setHintTextColor(ta.getColor(R.styleable.HorizontalDoubleTextView_right_hint_color, mContext.getColor(R.color.text_hint_color)));
        rightText.setGravity(ta.getInteger(R.styleable.HorizontalDoubleTextView_right_gravity, Gravity.START | Gravity.CENTER_VERTICAL));
        rightText.setTypeface(null, ta.getInteger(R.styleable.HorizontalDoubleTextView_right_textStyle, 0));
        rightParams.setMarginStart((int) ta.getDimension(R.styleable.HorizontalDoubleTextView_right_to_left_padding, 0));
        ta.recycle();
        addView(leftText);
        addView(rightText);
    }

    public void setLeftText(String text) {
        leftText.setText(text);
    }

    public TextView getRightText() {
        return rightText;
    }

    public TextView getLeftText() {
        return leftText;
    }

    ///////////////////////////////////////////////////////////////////////////
    // 以下为MVVM开发模式使用的代码
    ///////////////////////////////////////////////////////////////////////////
    @BindingAdapter({"leftText"})
    public static void setLeftText(HorizontalDoubleTextView view, String text) {
        TextView rightText = view.getLeftText();
        if (TextUtils.isEmpty(text)) {
            if (!TextUtils.isEmpty(rightText.getText().toString())) {
                rightText.setText("");
            }
        } else {
            if (!text.equals(rightText.getText().toString())) {
                rightText.setText(text);
            }
        }
    }

    @BindingAdapter({"rightText"})
    public static void setRightText(HorizontalDoubleTextView view, String text) {
        TextView rightText = view.getRightText();
        if (TextUtils.isEmpty(text)) {
            if (!TextUtils.isEmpty(rightText.getText().toString())) {
                rightText.setText("");
            }
        } else {
            if (!text.equals(rightText.getText().toString())) {
                rightText.setText(text);
            }
        }
    }

    @InverseBindingAdapter(attribute = "rightText", event = "right_event")
    public static String getRightTextString(HorizontalDoubleTextView view) {
        TextView rightText = view.getRightText();
        return rightText.getText().toString();
    }

    @BindingAdapter("right_event")
    public static void rightTextNotify(HorizontalDoubleTextView view, final InverseBindingListener inverseBindingListener) {
        view.getRightText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inverseBindingListener.onChange();
            }
        });

    }
}
