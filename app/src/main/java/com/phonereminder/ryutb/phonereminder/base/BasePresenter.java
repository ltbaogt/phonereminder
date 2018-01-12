package com.phonereminder.ryutb.phonereminder.base;

/**
 * Created by ryutb on 12/01/2018.
 */

public abstract class BasePresenter<V extends Mvp.View, M extends Mvp.Model> implements Mvp.Presenter<V, M> {

    private V mView;
    private M mModel;

    public V getView() {
        return mView;
    }

    public M getModel() {
        return mModel;
    }

    public BasePresenter(V v, M m) {
        mView = v;
        mModel = m;
    }

    @Override
    public void destroy() {
        mView = null;
        mModel = null;
    }
}
