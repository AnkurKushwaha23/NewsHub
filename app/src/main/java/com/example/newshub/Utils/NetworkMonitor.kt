package com.example.newshub.Utils


import android.content.Context
import android.content.Intent
import com.example.newshub.Utils.NetworkConnectivityService

object NetworkMonitor {
    fun startNetworkMonitoring(context: Context) {
        val intent = Intent(context, NetworkConnectivityService::class.java)
        context.startService(intent)
    }

    fun stopNetworkMonitoring(context: Context) {
        val intent = Intent(context, NetworkConnectivityService::class.java)
        context.stopService(intent)
    }
}