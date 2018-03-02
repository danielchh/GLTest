package com.danielch.gltest.base;

import android.content.Context;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.View;


public abstract class BaseFragment<P extends IBasePresenter> extends Fragment implements IBaseView {

    protected P presenter;

    protected abstract void initUi(View view);

    @Override
    public Context context() {
        return getContext();
    }

    @Override
    public void showMessage(@StringRes int stringId) {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).showMessage(stringId);
        }
    }

    @Override
    public void showLoading() {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).showLoading();
        }
    }

    @Override
    public void hideLoading() {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).hideLoading();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
