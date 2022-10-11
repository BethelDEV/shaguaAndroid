package com.guagua.networksample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private TextView resultTv;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTv = findViewById(R.id.result_tv);
        webView = findViewById(R.id.webview);
        Button btn = findViewById(R.id.http_get_btn);
        btn.setOnClickListener(view -> sendHttpRequest());
    }

    private void sendHttpRequest() {
        Log.i(TAG, "sendHttpRequest()");
        new Thread() {// 开启线程访问网络
            @Override
            public void run() {
                super.run();
                HttpURLConnection connection = null;

                try {
                    URL url = new URL("https://cn.bing.com");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(1200);
                    connection.setReadTimeout(1200);

                    // 读取获得到的输入流
                    InputStream input = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String li;
                    // readLine()方法是阻塞式的, 如果到达流末尾, 就返回null
                    while (null != (li = reader.readLine())) {
                        result.append(li);
                    }

                    // 展示返回数据
                    displayResponse(result.toString());
                } catch (Exception e) {
                    Log.e(TAG, "Http Request error: " + e);
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }.start();
    }

    private void displayResponse(String result) {
        Log.i(TAG, "displayResponse: " + result);
        runOnUiThread(() -> {// 在界面上展示数据
            resultTv.setText(result);

            // WebView 加载 HTML代码
            webView.loadDataWithBaseURL(null, result, "text/html", "utf-8", null);
        });
    }
}