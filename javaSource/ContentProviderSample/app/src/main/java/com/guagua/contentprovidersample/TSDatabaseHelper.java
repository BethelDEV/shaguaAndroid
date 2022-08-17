package com.guagua.contentprovidersample;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * @ProjectName: ContentProvider_Sample
 * @Package: com.guagua.contentprovidersample
 * @ClassName: TSDatabaseHelper
 * @Description: java类作用描述
 */
public class TSDatabaseHelper extends SQLiteOpenHelper {
    // sql语句，创建一个名为 TouristSpot 数据库表，该表包含三个字段：_id、spot、detail
    private static final String createTouristSpot = "create table TouristSpot (" +
            " _id integer primary key autoincrement," +
            "spot text," +
            "detail text)";

    public TSDatabaseHelper(@Nullable Context context, @Nullable String name, int version) {
        this(context, name, null, version);
    }

    public TSDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(createTouristSpot);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    }
}
