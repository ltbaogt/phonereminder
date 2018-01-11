package com.phonereminder.ryutb.phonereminder;

import android.app.Application;
import android.graphics.Typeface;
import android.support.annotation.StringRes;

import java.util.HashMap;

/**
 * Created by ryutb on 11/01/2018.
 */

public class PhoneReminderApplication extends Application {

    private final HashMap<String, Typeface> cacheFont = new HashMap<>();

    public Typeface getCustomFont(@StringRes int fontName) {
        return getCustomFont(getString(fontName));
    }

    public Typeface getCustomFont(String fontName) {
        synchronized (cacheFont) {
            if (!cacheFont.containsKey(fontName)) {
                try {
                    Typeface t = Typeface.createFromAsset(getAssets(), fontName);
                    cacheFont.put(fontName, t);
                } catch (Exception e) {
                    return null;
                }
            }
            return cacheFont.get(fontName);
        }
    }
}
