package com.guagua.activitysample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

/**
 * 基类
 */
public class BasicSingleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_single);
        Log.i("launchMode_Activity", String.format("onCreate() task: %d, instance: %s", getTaskId(), this.toString()));

        Button standardBtn = findViewById(R.id.standardBtn);
        standardBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, StandardActivity.class);
            startActivity(intent);
        });
        Button singleTaskBtn = findViewById(R.id.singleTaskBtn);
        singleTaskBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, SingleTaskActivity.class);
            startActivity(intent);
        });
        Button singleTopBtn = findViewById(R.id.singleTopBtn);
        singleTopBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, SingleTopActivity.class);
            startActivity(intent);
        });
        Button singleInstanceBtn = findViewById(R.id.singleInstanceBtn);
        singleInstanceBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, SingleInstanceActivity.class);
            startActivity(intent);
        });
    }
}