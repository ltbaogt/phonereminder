package com.phonereminder.ryutb.phonereminder.base;

/**
 * Created by ryutb on 12/01/2018.
 */

public interface Mvp {
    interface Model {
    }

    interface View {
        void setupMvp();
    }

    interface Presenter<V extends View, M extends Model> {
        V createDummyView();
        void destroy();
    }
}
