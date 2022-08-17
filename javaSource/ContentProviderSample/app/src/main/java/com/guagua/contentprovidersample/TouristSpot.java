package com.guagua.contentprovidersample;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * @ProjectName: ContentProvider_Sample
 * @Package: com.guagua.contentprovidersample
 * @ClassName: TouristSpot
 * @Description: 常量，工具类
 *
 * 定义常量工具类是为了提高代码的可维护性。
 */
public final class TouristSpot {
    // 定义ContentProvider的 authority
    public final static String AUTHORITY = "com.guagua.providers.tourists";
    public final static String TABLE = "TouristSpot";

    public static final class Spot implements BaseColumns {
        // 数据列
        public static final String _ID = "_id";
        public static final String SPOT = "spot";
        public static final String DETAIL = "detail";

        // 定义ContentProvider 提供的 Uri
        public static final Uri TOURISTS_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/tourists");
        public static final Uri SPOT_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/spot");
    }
}
