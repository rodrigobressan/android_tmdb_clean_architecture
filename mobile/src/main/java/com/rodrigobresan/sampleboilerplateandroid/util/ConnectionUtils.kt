package com.rodrigobresan.sampleboilerplateandroid.util

import android.content.Context
import com.rodrigobresan.data.connection.ConnectionStatus
import javax.inject.Inject
import android.net.NetworkInfo
import android.net.ConnectivityManager


class ConnectionUtils @Inject constructor(private val context: Context) : ConnectionStatus {

    override fun isConnected(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = cm.activeNetworkInfo
        val isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting
        return isConnected
    }

}