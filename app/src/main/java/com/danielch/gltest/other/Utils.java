package com.danielch.gltest.other;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utils {

    public static void isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            NetworkStateReceiver.IS_CONNECTED = activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
    }

    public static boolean isNetworkAvailable() {
        return NetworkStateReceiver.IS_CONNECTED;
    }
}
