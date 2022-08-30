package com.guagua.servicesample

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, iBinder: IBinder?) {
            Log.i(TAG, "onServiceConnected()")
            if (iBinder == null) return
            val mBinder = iBinder as MyService.MyBinder
            mBinder.doSomething()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.i(TAG, "onServiceDisconnected()")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startBtn = findViewById<Button>(R.id.startBtn)
        startBtn.setOnClickListener {
            Log.i(TAG, "startService clicked.")
            val intent = Intent(this, MyService::class.java)
            startService(intent)
        }

        val stopBtn = findViewById<Button>(R.id.stopBtn)
        stopBtn.setOnClickListener {
            Log.i(TAG, "stopService clicked.")
            val intent = Intent(this, MyService::class.java)
            stopService(intent)
        }

        val bindBtn = findViewById<Button>(R.id.bindBtn)
        bindBtn.setOnClickListener {
            Log.i(TAG, "bindService clicked.")
            val intent = Intent(this, MyService::class.java)
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }

        val unbindBtn = findViewById<Button>(R.id.unbindBtn)
        unbindBtn.setOnClickListener {
            Log.i(TAG, "unbindService clicked.")
            unbindService(connection)
        }
    }
}