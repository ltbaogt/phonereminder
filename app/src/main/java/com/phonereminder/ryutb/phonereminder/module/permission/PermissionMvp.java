package com.phonereminder.ryutb.phonereminder.module.permission;


import com.phonereminder.ryutb.phonereminder.base.Mvp;

/**
 * Created by MyPC on 02/07/2017.
 */

public interface PermissionMvp extends Mvp.View {

    interface Model extends Mvp.Model {
    }

    interface View extends Mvp.View {

    }

    interface Presenter extends Mvp.Presenter<View, Model> {

    }
}