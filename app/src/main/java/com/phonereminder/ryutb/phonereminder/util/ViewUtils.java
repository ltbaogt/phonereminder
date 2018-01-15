package com.phonereminder.ryutb.phonereminder.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.UnderlineSpan;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by MyPC on 23/07/2017.
 */

public class ViewUtils {
    private ViewUtils() {

    }

    public static int convertDp2Px(Context ctx, int dp) {
        Resources r = ctx.getResources();
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }

    public static int getYLocationOnScreen(View v) {
        int[] locations = new int[2];
        v.getLocationOnScreen(locations);
        return locations[1];
    }

    public static int getScreenHeight(Context context) {
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    public static String getString(String str) {
        if (TextUtils.isEmpty(str)) return "---";
        return str;
    }

    public static void clickableText(TextView tv, String templeText, ClickableSpan clickedCallback) {
        int startIndex = templeText.indexOf("[");
        int endIndex = templeText.indexOf("]") - 1;
        templeText = templeText.replaceAll("[\\[\\]]", "");

        tv.setText(templeText, TextView.BufferType.SPANNABLE);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        tv.setHighlightColor(Color.TRANSPARENT);

        Spannable ss = (Spannable) tv.getText();

        ss.setSpan(clickedCallback, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new UnderlineSpan(), startIndex, endIndex, 0);
    }
}
