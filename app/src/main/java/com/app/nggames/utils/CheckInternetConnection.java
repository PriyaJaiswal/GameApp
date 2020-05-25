package com.app.nggames.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckInternetConnection {
    public static boolean checkConnection(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService("connectivity");
        NetworkInfo activeNetworkInfo = null;
        if (connMgr != null) {
            activeNetworkInfo = connMgr.getActiveNetworkInfo();
        }
        boolean z = false;
        if (activeNetworkInfo == null) {
            return false;
        }
        if (activeNetworkInfo.getType() == 1) {
            return true;
        }
        if (activeNetworkInfo.getType() == 0) {
            z = true;
        }
        return z;
    }
}
