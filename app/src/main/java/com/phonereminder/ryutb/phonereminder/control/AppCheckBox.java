package com.phonereminder.ryutb.phonereminder.control;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;

import com.phonereminder.ryutb.phonereminder.PhoneReminderApplication;
import com.phonereminder.ryutb.phonereminder.R;


/**
 * Created by MyPC on 13/06/2017.
 */

public class AppCheckBox extends AppCompatCheckBox {
    public AppCheckBox(Context ctx, String fontName) {
        super(ctx);
        setCustomFont(ctx, fontName);
    }
    public AppCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
    }

    private void setCustomFont(Context ctx, AttributeSet attrs) {
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.TextViewPlus);
        String customFont = a.getString(R.styleable.TextViewPlus_customFont);
        setCustomFont(ctx, customFont);
//        int state[][] = {{android.R.attr.state_checked}, {}};
//        int color[] = {R.color.green_dark, R.color.compound_button_state_normal};
//        CompoundButtonCompat.setButtonTintList(this, new ColorStateList(state, color));
        a.recycle();
    }

    public void setCustomFont(Context ctx, String asset) {
        setTypeface(((PhoneReminderApplication)ctx.getApplicationContext()).getCustomFont(asset));
    }
}
