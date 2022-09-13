package com.guagua.broadcastsample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private NetworkChangeReceiver networkChangeReceiver;

    private LocalBroadcastManager localBroadcastManager;
    private MyLocalReceiver mLocalReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 注册"网络变化"广播接收器
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver, filter);

        // 注册本地广播接收器
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        mLocalReceiver = new MyLocalReceiver();
        IntentFilter iFilter = new IntentFilter();
        iFilter.addAction(MyLocalReceiver.ACTION_LOCAL);
        localBroadcastManager.registerReceiver(mLocalReceiver, iFilter);

        // 点击按钮，发送本地广播
        Button localBtn = findViewById(R.id.sendLocalBtn);
        localBtn.setOnClickListener(view -> {
            Log.i(TAG, "send local broadcast ...");
            Intent intent = new Intent(MyLocalReceiver.ACTION_LOCAL);
            localBroadcastManager.sendBroadcast(intent);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 注销广播接收器
        unregisterReceiver(networkChangeReceiver);

        // 注销本地广播接收器
        localBroadcastManager.unregisterReceiver(mLocalReceiver);
    }
}