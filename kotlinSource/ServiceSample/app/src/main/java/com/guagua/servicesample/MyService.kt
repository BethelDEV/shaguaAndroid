package com.guagua.servicesample

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

class MyService : Service() {
    companion object{
        private const val TAG = "MyService"
    }

    override fun onCreate() {
        Log.i(TAG, "onCreate()")
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i(TAG, "onStartCommand(flags:$flags, startId:$startId)")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        Log.i(TAG, "onDestroy()")
        super.onDestroy()
    }

    private val mBinder = MyBinder()
    override fun onBind(intent: Intent): IBinder {
        Log.i(TAG, "onBind()")
        return mBinder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.i(TAG, "onUnbind()")
        return super.onUnbind(intent)
    }

    class MyBinder: Binder() {
        fun doSomething() {
            Log.i(TAG, "MyBinder : doSomething()")
        }
    }
}