package com.guagua.contentprovidersample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

public class TouristSpotActivity extends AppCompatActivity {
    private TextView spotsTv, spotEt, detailEt;
    private final List<String> spotsList = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourist_spot);
        spotEt = findViewById(R.id.spotEt);
        detailEt = findViewById(R.id.detailEt);
        spotsTv = findViewById(R.id.spotsTv);

        findViewById(R.id.insertBtn).setOnClickListener(view -> insertTouristSpot());

        readTouristSpot();
    }

    /**
     * 读取共享的旅游景点数据
     */
    private void readTouristSpot() {
        spotsList.clear();

        // 查询数据
        Cursor cursor = getContentResolver().query(TouristSpot.Spot.TOURISTS_CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            String spot = cursor.getString(cursor.getColumnIndex(TouristSpot.Spot.SPOT));
            String detail = cursor.getString(cursor.getColumnIndex(TouristSpot.Spot.DETAIL));
            spotsList.add(spot + "\n" + detail);// 将读取到的数据放入集合中
        }
        cursor.close();

        // 将数据在界面上呈现出来
        updateUI();
    }

    private void updateUI() {
        spotsTv.setText("");
        for (String s : spotsList) {// 简化的操作，用TextView显示数据
            spotsTv.append("\n\n");
            spotsTv.append(s);
        }
    }

    /**
     * 写入共享的旅游景点数据
     */
    private void insertTouristSpot() {
        String spot = spotEt.getText().toString();
        String detail = detailEt.getText().toString();
        if (TextUtils.isEmpty(spot) || TextUtils.isEmpty(detail)) return;

        // 插入数据
        ContentValues values = new ContentValues();
        values.put(TouristSpot.Spot.SPOT, spot);
        values.put(TouristSpot.Spot.DETAIL, detail);
        getContentResolver().insert(TouristSpot.Spot.TOURISTS_CONTENT_URI, values);

        // 提示插入数据完成
        Toast.makeText(this, " 添加景点记录完成 ", Toast.LENGTH_SHORT).show();
        spotEt.setText("");
        detailEt.setText("");

        // 重新读取数据，检验上述操作是否成功
        readTouristSpot();
    }
}