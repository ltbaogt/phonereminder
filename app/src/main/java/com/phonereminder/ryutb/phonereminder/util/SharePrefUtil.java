package com.phonereminder.ryutb.phonereminder.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.phonereminder.ryutb.phonereminder.model.ReminderItem;

/**
 * Created by ryutb on 12/01/2018.
 */

public class SharePrefUtil {

    private static SharedPreferences getSharedPref(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void putReminderItem(Context ctx, ReminderItem item) {
        getSharedPref(ctx).edit().putString(item.getPhone(), (new Gson()).toJson(item)).apply();
    }

    public static ReminderItem getReminderItem(Context ctx, String phone) {
        String itemString = getSharedPref(ctx).getString(phone, "{}");
        return (new Gson()).fromJson(itemString, ReminderItem.class);
    }

}
