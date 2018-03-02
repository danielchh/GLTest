package com.danielch.gltest.base;

import android.content.Context;
import android.support.annotation.StringRes;

public interface IBaseInteractor {

    Context context();

    void showMessage(@StringRes int stringId);

    void hideLoading();

    void onDestroy();
}
