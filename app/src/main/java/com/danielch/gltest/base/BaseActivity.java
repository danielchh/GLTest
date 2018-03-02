package com.danielch.gltest.base;

import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.danielch.gltest.R;
import com.danielch.gltest.other.NetworkStateReceiver;

public abstract class BaseActivity extends AppCompatActivity implements IBaseView, NetworkStateReceiver.NetworkStateReceiverListener {

    private FrameLayout progressBar;
    private NetworkStateReceiver networkStateReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        progressBar = findViewById(R.id.fl_progress_bar);

        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        this.registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    public Context context() {
        return this;
    }

    @Override
    public void showMessage(@StringRes int stringId) {
        Toast.makeText(getApplicationContext(), stringId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        if (progressBar.getVisibility() == View.GONE) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideLoading() {
        if (progressBar.getVisibility() == View.VISIBLE) {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void networkAvailable() {
        NetworkStateReceiver.IS_CONNECTED = true;
    }

    @Override
    public void networkUnavailable() {
        NetworkStateReceiver.IS_CONNECTED = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        networkStateReceiver.removeListener(this);
        this.unregisterReceiver(networkStateReceiver);
    }
}
