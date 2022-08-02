package com.guagua.activity_sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MyActivity1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my1)

        val button: Button = findViewById(R.id.btn1)
        button.setOnClickListener{
            Toast.makeText(this, "close activity", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}