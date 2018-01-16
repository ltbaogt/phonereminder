package com.phonereminder.ryutb.phonereminder;

import android.app.Application;
import android.graphics.Typeface;
import android.support.annotation.StringRes;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.SimpleProgressiveJpegConfig;

import java.util.HashMap;

/**
 * Created by ryutb on 11/01/2018.
 */

public class PhoneReminderApplication extends Application {

    private final HashMap<String, Typeface> cacheFont = new HashMap<>();

    @Override
    public void onCreate() {
        super.onCreate();
        ImagePipelineConfig frescoConfig = ImagePipelineConfig.newBuilder(this)
                .setProgressiveJpegConfig(new SimpleProgressiveJpegConfig())
                .setResizeAndRotateEnabledForNetwork(true)
                .setDownsampleEnabled(true)
                .build();
        Fresco.initialize(this, frescoConfig);
    }

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
