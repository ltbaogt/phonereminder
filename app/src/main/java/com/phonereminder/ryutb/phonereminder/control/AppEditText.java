package com.phonereminder.ryutb.phonereminder.control;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;

import com.phonereminder.ryutb.phonereminder.PhoneReminderApplication;
import com.phonereminder.ryutb.phonereminder.R;


public class AppEditText extends AppCompatEditText implements TextWatcher {
    private static final String TAG = "TextView";
    public Runnable runnableBackPress;

    public AppEditText(Context context) {
        super(context);
    }

    public AppEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
    }

    public AppEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setCustomFont(context, attrs);
    }

    private void setCustomFont(Context ctx, AttributeSet attrs) {
//        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.TextViewPlus);
//        String customFont = a.getString(R.styleable.TextViewPlus_customFont);
        setCustomFont(ctx, getContext().getString(R.string.customFont_OpenSans_Italic));
        addTextChangedListener(this);
//        a.recycle();
    }

    public void setCustomFont(Context ctx, String asset) {
        setTypeface(((PhoneReminderApplication)ctx.getApplicationContext()).getCustomFont(asset));
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (runnableBackPress != null) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                // Do your thing.
                runnableBackPress.run();
                return true;  // So it is not propagated.
            }
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (TextUtils.isEmpty(editable.toString().trim())) {
            setCustomFont(getContext(), getContext().getString(R.string.customFont_OpenSans_Italic));
        } else {
            setCustomFont(getContext(), getContext().getString(R.string.customFont_OpenSans_Regular));
        }
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {

    }
}
