package com.guagua.broadcastsample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "MyReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        Log.i(TAG, "action: "+ action);

        if (Intent.ACTION_BOOT_COMPLETED.equals(action)) {
            Log.i(TAG, " 系统开机启动完成✅ ");
        } else if (Intent.ACTION_SHUTDOWN.equals(action)) {
            Log.i(TAG, " 系统正在关机📴 ");
        }
    }
}