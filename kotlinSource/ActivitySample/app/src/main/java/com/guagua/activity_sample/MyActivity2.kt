package com.guagua.activity_sample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MyActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my2)

        val tv: TextView = findViewById(R.id.message_tv)
        if (intent.hasExtra("nick")) {
            val nick = intent.getStringExtra("nick")
            tv.text = "nick: $nick"
        }
        if (intent.hasExtra("age")) {
            val age = intent.getIntExtra("age", 0)
            tv.append("\nage: $age")
        }

        val button: Button = findViewById(R.id.btn2)
        button.setOnClickListener{
            val result = Intent()
            result.putExtra("data", " 🐸 shaguaAndroid!")
            setResult(RESULT_OK, result)
            finish()
        }
    }
}