package com.danielch.gltest.base;

import android.content.Context;
import android.support.annotation.StringRes;

public abstract class BaseInteractor<P extends IBasePresenter> implements IBaseInteractor {

    protected P presenter;

    protected void setPresenter(P presenter) {
        this.presenter = presenter;
    }

    public Context context() {
        return presenter.context();
    }

    @Override
    public void showMessage(@StringRes int stringId) {
        presenter.showMessage(stringId);
    }

    @Override
    public void hideLoading() {
        presenter.hideLoading();
    }
}
