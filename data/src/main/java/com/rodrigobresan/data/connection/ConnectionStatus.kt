package com.rodrigobresan.data.connection

/**
 * Interface to check whether connection is available or not
 */
interface ConnectionStatus {

    /**
     * Function to check if the device is offline
     */
    fun isOffline(): Boolean
}