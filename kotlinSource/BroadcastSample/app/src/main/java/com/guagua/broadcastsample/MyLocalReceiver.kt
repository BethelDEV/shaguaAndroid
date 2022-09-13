package com.guagua.broadcastsample

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

/**
 *
 * @ProjectName:    BroadcastSample
 * @Package:        com.guagua.broadcastsample
 * @ClassName:      MyLocalReceiver
 * @Description:     java类作用描述
 */
class MyLocalReceiver : BroadcastReceiver() {
    companion object {
        private const val TAG = "MyLocalReceiver"
        const val ACTION_LOCAL = "com.guagua.broadcastsample.action.local"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        Log.i(TAG, "receive a local broadcast, action: $action")
    }
}