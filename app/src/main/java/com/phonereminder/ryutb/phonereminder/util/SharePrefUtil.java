package com.phonereminder.ryutb.phonereminder.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.phonereminder.ryutb.phonereminder.model.ReminderItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryutb on 12/01/2018.
 */

public class SharePrefUtil {

    private static SharedPreferences getSharedPref(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void putReminderItem(Context ctx, ReminderItem item) {
        List<ReminderItem> list = getReminderListItem(ctx);
        list.add(item);
        getSharedPref(ctx).edit().putString(Constants.SHAREDPREF_REMINDER_LIST_ITEM, (new Gson()).toJson(list)).apply();
    }

    public static void removeReminderItem(Context ctx, ReminderItem item) {
        List<ReminderItem> list = getReminderListItem(ctx);
        String phone = item.getPhone();
        for (ReminderItem i : list) {
            if (i.getPhone().equalsIgnoreCase(phone)) {
                list.remove(i);
                break;
            }
        }
        getSharedPref(ctx).edit().putString(Constants.SHAREDPREF_REMINDER_LIST_ITEM, (new Gson()).toJson(list)).apply();
    }

    public static List<ReminderItem> getReminderListItem(Context ctx) {
        String itemString = getSharedPref(ctx).getString(Constants.SHAREDPREF_REMINDER_LIST_ITEM, null);
        if (TextUtils.isEmpty(itemString)) return new ArrayList<>();
        List<ReminderItem> list = (new Gson()).fromJson(itemString, new TypeToken<List<ReminderItem>>() {}.getType());
        return list;
    }

}
