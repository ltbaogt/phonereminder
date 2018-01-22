package com.phonereminder.ryutb.phonereminder.util;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.phonereminder.ryutb.phonereminder.model.ReminderItem;

import net.grandcentrix.tray.TrayPreferences;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryutb on 12/01/2018.
 */

public class SharePrefUtil implements DataProvider {

    private static SharePrefUtil sInstance;
    private TrayPreferences mTrayPreferences;

    public SharePrefUtil(Context ctx) {
        mTrayPreferences = new MyModulePreference(ctx);
    }

    public static SharePrefUtil getInstace(Context ctx) {
        if (sInstance == null) sInstance = new SharePrefUtil(ctx);
        return sInstance;
    }

    private TrayPreferences getSharedPref(Context ctx) {
        if (mTrayPreferences == null) mTrayPreferences = new MyModulePreference(ctx);
        return mTrayPreferences;
    }

    @Override
    public void putReminderItem(Context ctx, ReminderItem item) {
        List<ReminderItem> list = getReminderListItem(ctx);
        list.add(item);
        getSharedPref(ctx).put(Constants.SHAREDPREF_REMINDER_LIST_ITEM, (new Gson()).toJson(list));
    }

    @Override
    public void removeReminderItem(Context ctx, ReminderItem item) {
        List<ReminderItem> list = getReminderListItem(ctx);
        String phone = item.getPhone();
        for (ReminderItem i : list) {
            if (i.getPhone().equalsIgnoreCase(phone)) {
                list.remove(i);
                break;
            }
        }
        getSharedPref(ctx).put(Constants.SHAREDPREF_REMINDER_LIST_ITEM, (new Gson()).toJson(list));
    }

    @Override
    public List<ReminderItem> getReminderListItem(Context ctx) {
        String itemString = getSharedPref(ctx).getString(Constants.SHAREDPREF_REMINDER_LIST_ITEM, null);
        if (TextUtils.isEmpty(itemString)) return new ArrayList<>();
        List<ReminderItem> list = (new Gson()).fromJson(itemString, new TypeToken<List<ReminderItem>>() {
        }.getType());
        return list;
    }

}
