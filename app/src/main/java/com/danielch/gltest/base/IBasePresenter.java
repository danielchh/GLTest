package com.danielch.gltest.base;

import android.content.Context;
import android.support.annotation.StringRes;

public interface IBasePresenter {

    Context context();

    void showMessage(@StringRes int stringId);

    void showLoading();

    void hideLoading();

    void onDestroy();

}
