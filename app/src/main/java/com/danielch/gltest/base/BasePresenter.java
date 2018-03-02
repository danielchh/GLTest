package com.danielch.gltest.base;

import android.content.Context;
import android.support.annotation.StringRes;

public abstract class BasePresenter<V extends IBaseView, I extends IBaseInteractor> implements IBasePresenter {

    protected V view;

    protected I interactor;

    protected void setView(V view) {
        this.view = view;
    }

    @Override
    public Context context() {
        return view.context();
    }

    @Override
    public void showMessage(@StringRes int stringId) {
        view.showMessage(stringId);
    }

    @Override
    public void showLoading() {
        view.showLoading();
    }

    @Override
    public void hideLoading() {
        view.hideLoading();
    }

    @Override
    public void onDestroy() {
        interactor.onDestroy();
    }
}
