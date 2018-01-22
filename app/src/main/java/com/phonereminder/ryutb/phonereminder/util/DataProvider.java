package com.phonereminder.ryutb.phonereminder.util;

import android.content.Context;

import com.phonereminder.ryutb.phonereminder.model.ReminderItem;

import java.util.List;

/**
 * Created by ryutb on 22/01/2018.
 */

public interface DataProvider {
    void putReminderItem(Context ctx, ReminderItem item);
    void removeReminderItem(Context ctx, ReminderItem item);
    List<ReminderItem> getReminderListItem(Context ctx);
}
