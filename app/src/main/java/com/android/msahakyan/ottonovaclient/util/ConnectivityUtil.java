package com.android.msahakyan.ottonovaclient.util;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.android.msahakyan.ottonovaclient.R;

/**
 * @author msahakyan
 */

public class ConnectivityUtil {

    public static boolean isConnectedToNetwork(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void notifyNoConnection(Context context) {
        Toast.makeText(context, R.string.no_connection, Toast.LENGTH_SHORT).show();
    }

    public static boolean isConnectedToWiFi(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiConnection = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return wifiConnection != null && wifiConnection.isConnected();
    }
}
