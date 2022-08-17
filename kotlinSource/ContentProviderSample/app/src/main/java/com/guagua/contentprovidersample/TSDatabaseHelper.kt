package com.guagua.contentprovidersample

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 *
 * @ProjectName:    ContentProviderSample
 * @Package:        com.guagua.contentprovidersample
 * @ClassName:      TSDatabaseHelper
 * @Description:     java类作用描述
 */
class TSDatabaseHelper(context: Context, name: String, version: Int): SQLiteOpenHelper(context, name, null, version) {
    // sql语句，创建一个名为 TouristSpot 数据库表，该表包含三个字段：_id、spot、detail
    private val createTouristSpot = "create table TouristSpot (" +
            " _id integer primary key autoincrement," +
            "spot text," +
            "detail text)"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createTouristSpot)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }
}