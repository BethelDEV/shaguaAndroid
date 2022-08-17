package com.guagua.contentprovidersample

import android.net.Uri
import android.provider.BaseColumns

/**
 *
 * @ProjectName:    ContentProviderSample
 * @Package:        com.guagua.contentprovidersample
 * @ClassName:      TouristSpot
 * @Description:     常量，工具类
 *
 * 定义常量工具类是为了提高代码的可维护性。
 */
class TouristSpot {
    companion object {
        // 定义ContentProvider的 authority
       const val AUTHORITY = "com.guagua.providers.tourists"
       const val TABLE = "TouristSpot"
    }

    object Spot : BaseColumns {
        // 数据列
        const val _ID = "_id"
        const val SPOT = "spot"
        const val DETAIL = "detail"

        // 定义ContentProvider 提供的 Uri
        val TOURISTS_CONTENT_URI = Uri.parse("content://$AUTHORITY/tourists")
        val SPOT_CONTENT_URI = Uri.parse("content://$AUTHORITY/spot")
    }
}