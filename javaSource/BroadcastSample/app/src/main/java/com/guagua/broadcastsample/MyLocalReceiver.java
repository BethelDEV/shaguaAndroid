package com.guagua.broadcastsample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * @ProjectName: Broadcast_Sample
 * @Package: com.guagua.broadcastsample
 * @ClassName: MyLocalReceiver
 * @Description: java类作用描述
 */
public class MyLocalReceiver extends BroadcastReceiver {
    private static final String TAG = "MyLocalReceiver";
    public static final String ACTION_LOCAL = "com.guagua.broadcastsample.action.local";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.i(TAG, "receive a local broadcast, action: " + action);
    }
}
