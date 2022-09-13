package com.guagua.broadcastsample

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var networkChangeReceiver: NetworkChangeReceiver

    private lateinit var localBroadcastManager: LocalBroadcastManager
    private lateinit var mLocalReceiver: MyLocalReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 注册"网络变化"广播接收器
        val filter = IntentFilter()
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, filter)

        // 注册本地广播接收器
        localBroadcastManager = LocalBroadcastManager.getInstance(this)
        mLocalReceiver = MyLocalReceiver()
        val iFilter = IntentFilter()
        iFilter.addAction(MyLocalReceiver.ACTION_LOCAL)
        localBroadcastManager.registerReceiver(mLocalReceiver, iFilter)

        // 点击按钮，发送本地广播
        val localBtn: Button = findViewById(R.id.sendLocalBtn)
        localBtn.setOnClickListener {
            Log.i(TAG, "send local broadcast ...")
            val intent = Intent(MyLocalReceiver.ACTION_LOCAL)
            localBroadcastManager.sendBroadcast(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // 注销广播接收器
        unregisterReceiver(networkChangeReceiver)

        // 注销本地广播接收器
        localBroadcastManager.unregisterReceiver(mLocalReceiver)
    }
}