package com.guagua.activitysample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
    }
}