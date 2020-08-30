package com.assignment.venuesearchapp.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log


class ConnectivityHelper {

    companion object {

        fun isConnectedToNetwork(context: Context): Boolean {

            val connectivityManager =
                context.let {
                    it.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                }

            if (connectivityManager != null) {
                val capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                if (capabilities != null) {
                    when {
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                            Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                            return true
                        }
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                            Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                            return true
                        }
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                            Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                            return true
                        }
                    }
                }
            }
            return false
        }
    }
}