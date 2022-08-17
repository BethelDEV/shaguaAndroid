package com.guagua.contentprovidersample

import android.content.Intent
import android.database.ContentObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import com.guagua.contentprovidersample.MainActivity.TouristSpotObserver

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val contactsBtn: Button = findViewById(R.id.contactsBtn)
        contactsBtn.setOnClickListener {
            val intent = Intent(this, ContactsActivity::class.java)
            startActivity(intent)
        }

        val spotBtn = findViewById<Button>(R.id.spotBtn)
        spotBtn.setOnClickListener {
            val intent = Intent(this, TouristSpotActivity::class.java)
            startActivity(intent)
        }

        registerContentObserver()
    }

    /**
     * 注册数据监听器
     */
    private fun registerContentObserver() {
        val observer = TouristSpotObserver(Handler())
        contentResolver.registerContentObserver(
            TouristSpot.Spot.TOURISTS_CONTENT_URI,
            true,
            observer
        )
        contentResolver.registerContentObserver(TouristSpot.Spot.SPOT_CONTENT_URI, true, observer)
    }

    private class TouristSpotObserver(handler: Handler?) :
        ContentObserver(handler) {
        override fun onChange(selfChange: Boolean) { // 当数据变化，回调该函数
            super.onChange(selfChange)
            Log.i(TAG, "data changed")
        }

        companion object {
            private const val TAG = "TouristSpotObserver"
        }
    }
}