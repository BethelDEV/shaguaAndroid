package com.guagua.activitysample;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

public class MyActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my1);

        Button button = findViewById(R.id.btn1);
        button.setOnClickListener(view -> {
            Toast.makeText(this, "close activity", Toast.LENGTH_SHORT).show();
            finish();
        });

        Button start = findViewById(R.id.startBtn);
        start.setOnClickListener(view -> {
            Intent intent = new Intent(this, MyActivity2.class);
            startActivity(intent);
        });

        Button send = findViewById(R.id.sendBtn);
        send.setOnClickListener(view -> {
            Intent intent = new Intent(this, MyActivity2.class);
            intent.putExtra("nick", "guagua");
            intent.putExtra("age", 19);
            startActivity(intent);
        });

        Button req = findViewById(R.id.requestBtn);
        req.setOnClickListener(view -> {
            Intent intent = new Intent(this, MyActivity2.class);
            startActivityForResult(intent, 1010);
        });

        Button life = findViewById(R.id.lifeBtn);
        life.setOnClickListener(view -> {
            Intent intent = new Intent(this, LifeActivity.class);
            startActivity(intent);
        });

        Button standardBtn = findViewById(R.id.standardBtn);
        standardBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, StandardActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK != resultCode) return;
        if (1010 == requestCode && null != data) {
            String value = data.getStringExtra("data");
            Log.i("MyActivity1", "onActivityResult(" + requestCode + "), result value: " + value);
        }
    }
}