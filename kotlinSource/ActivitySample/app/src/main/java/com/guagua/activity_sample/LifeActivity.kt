package com.guagua.activity_sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class LifeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_life)
        Log.i("LifeActivity", "onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.i("LifeActivity", "onStart")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("LifeActivity", "onRestart")
    }

    override fun onResume() {
        super.onResume()
        Log.i("LifeActivity", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i("LifeActivity", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i("LifeActivity", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("LifeActivity", "onDestroy")
    }
}