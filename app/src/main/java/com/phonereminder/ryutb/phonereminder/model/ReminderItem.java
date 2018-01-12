package com.phonereminder.ryutb.phonereminder.model;

/**
 * Created by ryutb on 12/01/2018.
 */

public class ReminderItem {
    private String name;
    private String phone;
    private String note;

    public ReminderItem(String name, String phone, String note) {
        this.name = name;
        this.phone = phone;
        this.note = note;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getNote() {
        return note;
    }

}
