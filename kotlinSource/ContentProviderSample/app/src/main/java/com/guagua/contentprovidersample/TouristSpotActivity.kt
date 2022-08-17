package com.guagua.contentprovidersample

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.util.*

class TouristSpotActivity : AppCompatActivity() {
    private lateinit var spotsTv: TextView
    private lateinit var spotEt: EditText
    private lateinit var detailEt: EditText
    private val spotsList: LinkedList<String> = LinkedList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tourist_spot)
        spotEt = findViewById(R.id.spotEt)
        detailEt = findViewById(R.id.detailEt)
        spotsTv = findViewById(R.id.spotsTv)

        findViewById<View>(R.id.insertBtn).setOnClickListener { insertTouristSpot() }

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

    /**
     * 写入共享的旅游景点数据
     */
    private fun insertTouristSpot() {
        val spot = spotEt.text.toString()
        val detail = detailEt.text.toString()
        if (TextUtils.isEmpty(spot) || TextUtils.isEmpty(detail)) return

        // 插入数据
        val values = ContentValues()
        values.put(TouristSpot.Spot.SPOT, spot)
        values.put(TouristSpot.Spot.DETAIL, detail)
        contentResolver.insert(TouristSpot.Spot.TOURISTS_CONTENT_URI, values)

        // 提示插入数据完成
        Toast.makeText(this, " 添加景点记录完成 ", Toast.LENGTH_SHORT).show()
        spotEt.setText("")
        detailEt.setText("")

        // 重新读取数据，检验上述操作是否成功
        readTouristSpot()

    }
}