package com.phonereminder.ryutb.phonereminder.control;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.KeyEvent;

import com.phonereminder.ryutb.phonereminder.PhoneReminderApplication;
import com.phonereminder.ryutb.phonereminder.R;


public class AppTextView extends AppCompatTextView {
    public Runnable runnableBackPress;

    public AppTextView(Context context, String asset) {
        super(context);
        setCustomFont(context, asset);
    }

    public AppTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
    }

    public AppTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setCustomFont(context, attrs);
    }

    private void setCustomFont(Context ctx, AttributeSet attrs) {
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.TextViewPlus);
        String customFont = a.getString(R.styleable.TextViewPlus_customFont);
        setCustomFont(ctx, customFont);
        a.recycle();
    }

    public void setCustomFont(Context ctx, String asset) {
        setTypeface(((PhoneReminderApplication)ctx.getApplicationContext()).getCustomFont(asset));
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (runnableBackPress != null) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                runnableBackPress.run();
                return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }
}