package com.guagua.contentprovidersample;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @ProjectName: ContentProvider_Sample
 * @Package: com.guagua.contentprovidersample
 * @ClassName: TouristSpotContentProvider
 * @Description: java类作用描述
 */
public class TouristSpotContentProvider extends ContentProvider {
    private final static int TOURISTS = 1;
    private final static int SPOT = 2;
    private static final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

    static { // 向UriMatcher对象注册Uri
        matcher.addURI(TouristSpot.AUTHORITY, "tourists", TOURISTS);
        matcher.addURI(TouristSpot.AUTHORITY, "spot/#", SPOT);
    }

    private TSDatabaseHelper dbHelper;

    @Override
    public boolean onCreate() {// ContentProvider创建时调用此函数
        dbHelper = new TSDatabaseHelper(this.getContext(), "tourist_spots.db3", 1);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        switch (matcher.match(uri)) {
            case TOURISTS:
                // 查询
                return db.query(TouristSpot.TABLE, projection, selection, selectionArgs, null, null, sortOrder);

            case SPOT:
                // 解析所要查询记录的 ID
                long id = ContentUris.parseId(uri);
                // 拼接 where 语句
                StringBuilder where = new StringBuilder(TouristSpot.Spot._ID);
                where.append("=").append(id);
                if (!TextUtils.isEmpty(selection)) {
                    where.append(" and ").append(selection);
                }
                return db.query(TouristSpot.TABLE, projection, where.toString(), selectionArgs, null, null, sortOrder);

            default:
                return null;
            // throw new IllegalArgumentException("unknown Uri: " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (matcher.match(uri)) {
            case TOURISTS: // 数据是多项记录
                return "vnd.android.cursor.dir/com.guagua.tourist_spot";
            case SPOT: // 数据是单项记录
                return "vnd.android.cursor.item/com.guagua.tourist_spot";
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        // 获取数据库实例
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // 插入数据
        long rowId = db.insert(TouristSpot.TABLE, TouristSpot.Spot._ID, contentValues);
        if (0 < rowId) {
            //在Uri后追加 ID数据
            Uri spotUri = ContentUris.withAppendedId(uri, rowId);
            // 通知数据发生了改变
            getContext().getContentResolver().notifyChange(spotUri, null);
            return spotUri;
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count = 0;// 删除记录的数量
        switch (matcher.match(uri)) { // 匹配 uri
            case TOURISTS:
                count = db.delete(TouristSpot.TABLE, s, strings);
                break;
            case SPOT:
                // 解析所要删除记录的 ID
                long id = ContentUris.parseId(uri);
                // 拼接 where 语句
                StringBuilder where = new StringBuilder(TouristSpot.Spot._ID);
                where.append("=").append(id);
                if (!TextUtils.isEmpty(s)) {
                    where.append(" and ").append(s);
                }
                count = db.delete(TouristSpot.TABLE, where.toString(), strings);
                break;
            // default:
            //   throw new IllegalArgumentException("unknown Uri: " + uri);
        }

        // 通知数据发生了改变
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count = 0;// 修改记录的数量
        switch (matcher.match(uri)) {
            case TOURISTS:
                count = db.update(TouristSpot.TABLE, contentValues, s, strings);
                break;
            case SPOT:
                // 解析所要修改记录的 ID
                long id = ContentUris.parseId(uri);
                // 拼接 where 语句
                StringBuilder where = new StringBuilder(TouristSpot.Spot._ID);
                where.append("=").append(id);
                if (!TextUtils.isEmpty(s)) {
                    where.append(" and ").append(s);
                }
                count = db.update(TouristSpot.TABLE, contentValues, where.toString(), strings);
                break;
            // default:
            //   throw new IllegalArgumentException("unknown Uri: " + uri);
        }

        // 通知数据发生了改变
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
