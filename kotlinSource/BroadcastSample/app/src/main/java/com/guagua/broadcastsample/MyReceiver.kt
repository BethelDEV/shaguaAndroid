package com.guagua.broadcastsample

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class MyReceiver : BroadcastReceiver() {
    companion object {
        private const val TAG = "MyReceiver"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        Log.i(TAG, "action: $action")

        when (action) {
            Intent.ACTION_BOOT_COMPLETED -> {
                Log.i(TAG, " 系统开机启动完成✅ ")
            }
            Intent.ACTION_SHUTDOWN -> {
                Log.i(TAG, " 系统正在关机📴 ")
            }
        }
    }
}