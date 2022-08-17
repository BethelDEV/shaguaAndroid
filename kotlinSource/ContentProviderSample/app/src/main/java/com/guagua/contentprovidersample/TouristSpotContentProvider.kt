package com.guagua.contentprovidersample

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.text.TextUtils
import java.lang.StringBuilder

/**
 *
 * @ProjectName:    ContentProviderSample
 * @Package:        com.guagua.contentprovidersample
 * @ClassName:      TouristSpotContentProvider
 * @Description:     java类作用描述
 */
class TouristSpotContentProvider : ContentProvider() {
    private val TOURISTS = 1
    private val SPOT = 2
    private val matcher = UriMatcher(UriMatcher.NO_MATCH)

    init { // 向UriMatcher对象注册Uri
        matcher.addURI(TouristSpot.AUTHORITY, "tourists", TOURISTS)
        matcher.addURI(TouristSpot.AUTHORITY, "spot/#", SPOT)
    }

    private var dbHelper: TSDatabaseHelper? = null

    override fun onCreate(): Boolean {
        context?.let {
            dbHelper = TSDatabaseHelper(it, "tourist_spots.db3", 1)
            return true
        } ?: return false
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val db = dbHelper?.readableDatabase
        if (dbHelper == null) {
            return null
        }
        when (matcher.match(uri)) {
            TOURISTS -> {
                // 查询
                return db!!.query(
                    TouristSpot.TABLE,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    sortOrder
                )
            }
            SPOT -> {
                // 解析所要查询记录的 ID
                val id = ContentUris.parseId(uri)
                // 拼接 where 语句
                val where = StringBuilder(TouristSpot.Spot._ID)
                where.append("=").append(id)
                if (!TextUtils.isEmpty(selection)) {
                    where.append(" and ").append(selection)
                }
                return db!!.query(
                    TouristSpot.TABLE,
                    projection,
                    where.toString(),
                    selectionArgs,
                    null,
                    null,
                    sortOrder
                )
            }
        }
        return null
    }

    override fun getType(uri: Uri): String? {
        when (matcher.match(uri)) {
            TOURISTS -> return "vnd.android.cursor.dir/com.guagua.tourist_spot"
            SPOT -> return "vnd.android.cursor.item/com.guagua.tourist_spot"
        }
        return null
    }

    override fun insert(uri: Uri, contentValues: ContentValues?): Uri? {
        if (dbHelper == null) {
            return null
        }
        // 获取数据库实例
        val db = dbHelper!!.writableDatabase
        // 插入数据
        val rowId = db.insert(TouristSpot.TABLE, TouristSpot.Spot._ID, contentValues)
        if (0 < rowId) {
            //在Uri后追加 ID数据
            val spotUri = ContentUris.withAppendedId(uri, rowId)
            // 通知数据发生了改变
            context!!.contentResolver.notifyChange(spotUri, null)
            return spotUri
        }
        return null
    }

    override fun delete(uri: Uri, s: String?, strings: Array<out String>?): Int {
        if (dbHelper == null) return 0
        val db = dbHelper!!.writableDatabase
        var count = 0 // 删除记录的数量

        when (matcher.match(uri)) {
            TOURISTS -> count = db.delete(TouristSpot.TABLE, s, strings)
            SPOT -> {
                // 解析所要删除记录的 ID
                val id = ContentUris.parseId(uri)
                // 拼接 where 语句
                val where = StringBuilder(TouristSpot.Spot._ID)
                where.append("=").append(id)
                if (!TextUtils.isEmpty(s)) {
                    where.append(" and ").append(s)
                }
                count = db.delete(TouristSpot.TABLE, where.toString(), strings)
            }
        }

        // 通知数据发生了改变
        context!!.contentResolver.notifyChange(uri, null)
        return count
    }

    override fun update(
        uri: Uri,
        contentValues: ContentValues?,
        s: String?,
        strings: Array<out String>?
    ): Int {
        if (dbHelper == null) return 0
        val db = dbHelper!!.writableDatabase
        var count = 0 // 修改记录的数量

        when (matcher.match(uri)) {
            TOURISTS -> count =
                db.update(TouristSpot.TABLE, contentValues, s, strings)
            SPOT -> {
                // 解析所要修改记录的 ID
                val id = ContentUris.parseId(uri)
                // 拼接 where 语句
                val where = StringBuilder(TouristSpot.Spot._ID)
                where.append("=").append(id)
                if (!TextUtils.isEmpty(s)) {
                    where.append(" and ").append(s)
                }
                count = db.update(TouristSpot.TABLE, contentValues, where.toString(), strings)
            }
        }

        // 通知数据发生了改变
        context!!.contentResolver.notifyChange(uri, null)
        return count
    }
}