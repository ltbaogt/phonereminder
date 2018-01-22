package com.phonereminder.ryutb.phonereminder.module.permission;

import com.phonereminder.ryutb.phonereminder.base.BasePresenter;

/**
 * Created by MyPC on 17/05/2017.
 */

public class PermissionPresenter extends BasePresenter<PermissionMvp.View, PermissionMvp.Model> implements PermissionMvp.Presenter {

    private static final String TAG = "PermissionPresenter";

    public PermissionPresenter(PermissionMvp.View view, PermissionMvp.Model model) {
        super(view, model);
    }

    @Override
    public PermissionMvp.View createDummyView() {
        return new PermissionMvp.View() {
            @Override
            public void setupMvp() {

            }
        };
    }
}