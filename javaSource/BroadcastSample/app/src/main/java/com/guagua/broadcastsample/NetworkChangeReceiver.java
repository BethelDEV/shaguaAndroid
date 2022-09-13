package com.guagua.broadcastsample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class NetworkChangeReceiver extends BroadcastReceiver {
    private static final String TAG = "NetworkChangeReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (null != info && info.isAvailable()) {
            Log.i(TAG, " 网络可用 O(∩_∩)O ");
        } else {
            Log.i(TAG, " 网络被外星人劫持了 ╮(╯_╰)╭ ");
        }
    }
}
