package com.guagua.activitysample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MyActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my2);

        TextView tv = findViewById(R.id.message_tv);
        Intent intent = getIntent();
        if (intent.hasExtra("nick")) {
            String nick = intent.getStringExtra("nick");
            tv.setText("nick: "+nick);
        }
        if (intent.hasExtra("age")) {
            int age = intent.getIntExtra("age", 0);
            tv.append("\nage: "+age);
        }

        Button button = findViewById(R.id.btn2);
        button.setOnClickListener(view -> {
            Intent result = new Intent();
            result.putExtra("data", " 🐸 shaguaAndroid!");
            setResult(RESULT_OK, result);
            finish();
        });

    }
}