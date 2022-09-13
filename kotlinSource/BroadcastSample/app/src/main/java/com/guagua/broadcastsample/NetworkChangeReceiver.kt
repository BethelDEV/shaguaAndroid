package com.guagua.broadcastsample

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log

/**
 *
 * @ProjectName:    BroadcastSample
 * @Package:        com.guagua.broadcastsample
 * @ClassName:      NetworkChangeReceiver
 * @Description:     java类作用描述
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class NetworkChangeReceiver : BroadcastReceiver() {
    companion object {
        private const val TAG = "NetworkChangeReceiver"
    }

    override fun onReceive(context: Context?, p1: Intent?) {
        val manager: ConnectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info: NetworkInfo? = manager?.activeNetworkInfo
        if (info?.isAvailable == true) {
            Log.i(TAG, " 网络可用 O(∩_∩)O ")
        } else {
            Log.i(TAG, " 网络被外星人劫持了 ╮(╯_╰)╭ ")
        }
    }
}