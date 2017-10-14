package com.example.nimesha.memifyx;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by Nimesha on 10/14/2017.
 */

public class CheckNetwork {
    private static final String TAG = "Internet_Available";


    public static boolean isInternetAvailable(Context context) {
        NetworkInfo info = (NetworkInfo) ((ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();

        if (info == null) {
            Log.d(TAG, "no internet connection");
            return false;
        } else {
            if (info.isConnected()) {
                Log.d(TAG, " internet connection available...");
                return true;
            } else {
                Log.d(TAG, " internet connection");
                return true;
            }

        }
    }
}
