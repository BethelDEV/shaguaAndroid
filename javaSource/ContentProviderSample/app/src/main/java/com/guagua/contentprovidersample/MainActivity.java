package com.guagua.contentprovidersample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button contactsBtn = findViewById(R.id.contactsBtn);
        contactsBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, ContactsActivity.class);
            startActivity(intent);
        });

        Button spotBtn = findViewById(R.id.spotBtn);
        spotBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, TouristSpotActivity.class);
            startActivity(intent);
        });

        registerContentObserver();
    }

    /**
     * 注册数据监听器
     */
    private void registerContentObserver() {
        TouristSpotObserver observer = new TouristSpotObserver(new Handler());
        getContentResolver().registerContentObserver(TouristSpot.Spot.TOURISTS_CONTENT_URI, true, observer);
        getContentResolver().registerContentObserver(TouristSpot.Spot.SPOT_CONTENT_URI, true, observer);
    }

    private final class TouristSpotObserver extends ContentObserver {
        private static final String TAG = "TouristSpotObserver";

        public TouristSpotObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {// 当数据变化，回调该函数
            super.onChange(selfChange);
            Log.i(TAG, "data changed");
        }
    }
}