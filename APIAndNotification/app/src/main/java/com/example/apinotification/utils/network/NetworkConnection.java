package com.example.apinotification.utils.network;

import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkConnection {

    public static boolean isInternetAvailable(Context mContext) {

        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Service.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return networkInfo != null && networkInfo.isConnected();
    }

}
