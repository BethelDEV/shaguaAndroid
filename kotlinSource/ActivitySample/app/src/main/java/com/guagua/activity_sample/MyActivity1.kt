package com.guagua.activity_sample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

        val start: Button = findViewById(R.id.startBtn)
        start.setOnClickListener{
            val intent = Intent(this, MyActivity2::class.java)
            startActivity(intent)
        }

        val send: Button = findViewById(R.id.sendBtn)
        send.setOnClickListener{
            val intent = Intent(this, MyActivity2::class.java)
            intent.putExtra("nick", "guagua")
            intent.putExtra("age", 19)
            startActivity(intent)
        }

        val req: Button = findViewById(R.id.requestBtn)
        req.setOnClickListener{
            val intent = Intent(this, MyActivity2::class.java)
            startActivityForResult(intent, 1010)
        }

        val life: Button = findViewById(R.id.lifeBtn)
        life.setOnClickListener{
            val intent = Intent(this, LifeActivity::class.java)
            startActivity(intent)
        }

        val standardBtn: Button = findViewById(R.id.standardBtn)
        standardBtn.setOnClickListener{
            val intent = Intent(this, StandardActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (RESULT_OK != resultCode) return
        if (1010 == requestCode) {
            val value = data?.getStringExtra("data")
            Log.i("MyActivity1", "onActivityResult($requestCode), result value: $value")
        }
    }
}