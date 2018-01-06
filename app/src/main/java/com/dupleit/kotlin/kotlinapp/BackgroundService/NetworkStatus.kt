package com.dupleit.kotlin.kotlinapp.BackgroundService

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by android on 6/1/18.
 */
object NetWorkStatus {
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null
    }
}