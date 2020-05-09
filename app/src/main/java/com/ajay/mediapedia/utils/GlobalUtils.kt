package com.ajay.mediapedia.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager

class GlobalUtils {

    companion object {

        /**
         * Method to check internet connectivity
         */
        fun isNetWorkConnected(activity: Activity): Boolean {
            val connectivityManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
    }
}