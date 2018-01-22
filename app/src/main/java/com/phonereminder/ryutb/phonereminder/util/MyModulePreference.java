package com.phonereminder.ryutb.phonereminder.util;


import android.content.Context;

import net.grandcentrix.tray.TrayPreferences;

/**
 * Created by ryutb on 22/01/2018.
 */

public class MyModulePreference extends TrayPreferences {

    public MyModulePreference(final Context context) {
        super(context, "mySharedPref", 1);
    }
}
