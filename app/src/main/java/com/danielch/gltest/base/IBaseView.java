package com.danielch.gltest.base;

import android.content.Context;
import android.support.annotation.StringRes;

public interface IBaseView {

    Context context();

    void showMessage(@StringRes int stringId);

    void showLoading();

    void hideLoading();
}
