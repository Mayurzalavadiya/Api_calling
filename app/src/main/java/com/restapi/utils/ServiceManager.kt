package com.restapi.utils

import android.content.Context
import android.net.ConnectivityManager

class ServiceManager(private var context: Context) {
    val isNetworkAvailable: Boolean
        get() {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = cm.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
}