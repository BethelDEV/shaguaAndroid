package com.guagua.servicesample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private final ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.i(TAG, "onServiceConnected()");
            if (iBinder == null) return;
            MyService.MyBinder mBinder = (MyService.MyBinder) iBinder;
            mBinder.doSomething();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i(TAG, "onServiceDisconnected()");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startBtn = findViewById(R.id.startBtn);
        startBtn.setOnClickListener(view -> {
            Log.i(TAG, "startService clicked.");
            Intent intent = new Intent(this, MyService.class);
            startService(intent);
        });

        Button stopBtn = findViewById(R.id.stopBtn);
        stopBtn.setOnClickListener(view -> {
            Log.i(TAG, "stopService clicked.");
            Intent intent = new Intent(this, MyService.class);
            stopService(intent);
        });

        Button bindBtn = findViewById(R.id.bindBtn);
        bindBtn.setOnClickListener(view -> {
            Log.i(TAG, "bindService clicked.");
            Intent intent = new Intent(this, MyService.class);
            bindService(intent, connection, Context.BIND_AUTO_CREATE);
        });

        Button unbindBtn = findViewById(R.id.unbindBtn);
        unbindBtn.setOnClickListener(view -> {
            Log.i(TAG, "unbindService clicked.");
            unbindService(connection);
        });
    }
}