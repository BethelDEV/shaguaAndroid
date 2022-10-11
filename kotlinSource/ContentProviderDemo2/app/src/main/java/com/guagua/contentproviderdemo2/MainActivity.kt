package com.guagua.contentproviderdemo2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var spotsTv: TextView
    private val spotsList: LinkedList<String> = LinkedList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        spotsTv = findViewById(R.id.spotsTv)

        readTouristSpot()
    }

    /**
     * 读取共享的旅游景点数据
     */
    private fun readTouristSpot() {
        spotsList.clear()

        // 查询数据
        contentResolver.query(
            TouristSpot.Spot.TOURISTS_CONTENT_URI,
            null, null, null, null
        )?.apply {
            while (moveToNext()) {
                val spot = getString(getColumnIndex(TouristSpot.Spot.SPOT))
                val detail = getString(getColumnIndex(TouristSpot.Spot.DETAIL))
                spotsList.add("$spot \n$detail")
            }
            close()
        }

        // 将数据在界面上呈现出来
        updateUI()
    }

    private fun updateUI() {
        spotsTv.text = ""
        for (s in spotsList) { // 简化的操作，用TextView显示数据
            spotsTv.append("\n\n")
            spotsTv.append(s)
        }
    }

}