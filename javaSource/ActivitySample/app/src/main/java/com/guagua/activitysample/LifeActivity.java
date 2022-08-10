package com.guagua.activitysample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class LifeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life);
        Log.i("LifeActivity", "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("LifeActivity", "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("LifeActivity", "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("LifeActivity", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("LifeActivity", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("LifeActivity", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("LifeActivity", "onDestroy");
    }
}