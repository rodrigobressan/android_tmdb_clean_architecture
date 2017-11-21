package com.rodrigobresan.sampleboilerplateandroid.util

import android.content.Context
import android.net.ConnectivityManager
import com.rodrigobresan.data.connection.ConnectionStatus
import javax.inject.Inject


class ConnectionUtils @Inject constructor(private val context: Context) : ConnectionStatus {

    override fun isOffline(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = cm.activeNetworkInfo
        val isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting
        return !isConnected
    }

}