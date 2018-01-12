package com.phonereminder.ryutb.phonereminder.module.contactdetail;

import com.phonereminder.ryutb.phonereminder.base.Mvp;

/**
 * Created by MyPC on 02/07/2017.
 */

public interface ContactDetailMvp extends Mvp.View {

    interface Model extends Mvp.Model {
    }

    interface View extends Mvp.View {
        void displayName();
        void displayPhone();
    }

    interface Presenter extends Mvp.Presenter<View, Model> {
        void renderName();
        void renderPhone();
    }
}