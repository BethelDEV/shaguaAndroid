package com.guagua.networksample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.widget.Button
import android.widget.TextView
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var resultTv: TextView
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultTv = findViewById(R.id.result_tv)
        webView = findViewById(R.id.webview)
        val btn = findViewById<Button>(R.id.http_get_btn)
        btn.setOnClickListener { sendHttpRequest() }
    }

    private fun sendHttpRequest() {
        Log.i(TAG, "sendHttpRequest()")
        thread {// 开启线程访问网络
            var connection: HttpURLConnection? =null

            try {
                val url = URL("https://cn.bing.com")
                connection = url.openConnection() as HttpURLConnection
                connection.connectTimeout = 1200
                connection.readTimeout = 1200

                // 读取获得到的输入流
                val input = connection.inputStream
                val reader = BufferedReader(InputStreamReader(input))
                val result = StringBuilder()
                reader.use {
                    reader.forEachLine {
                        result.append(it)
                    }
                }

                // 展示返回数据
                displayResponse(result.toString())
            } catch (e: Exception) {
                Log.e(TAG, "Http Request error: $e")
            } finally {
                connection?.disconnect()
            }
        }
    }

    private fun displayResponse(result: String) {
        Log.i(TAG, "displayResponse: $result")
        runOnUiThread {// 在界面上展示数据
            resultTv.text = result

            // WebView 加载 HTML代码
            webView.loadDataWithBaseURL(null, result, "text/html", "utf-8", null)
        }
    }
}