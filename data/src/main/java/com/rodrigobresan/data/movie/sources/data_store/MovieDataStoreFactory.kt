package com.rodrigobresan.data.movie.sources.data_store

import com.rodrigobresan.data.connection.ConnectionStatus
import com.rodrigobresan.data.movie.sources.data_store.local.MovieCache
import com.rodrigobresan.data.movie.sources.data_store.local.MovieCacheDataStore
import com.rodrigobresan.data.movie.sources.data_store.remote.MovieRemoteDataStore
import javax.inject.Inject

/**
 * Factory for MovieDataStore
 */
open class MovieDataStoreFactory @Inject constructor(private val connectionStatus: ConnectionStatus,
                                                     private val movieCache: MovieCache,
                                                     private val movieCacheDataStore: MovieCacheDataStore,
                                                     private val movieRemoteDataStore: MovieRemoteDataStore) {

    open fun retrieveDataStore(): MovieDataStore {

        // no connection
        if (!connectionStatus.isConnected()) {
            return retrieveCachedDataStore()
        }

        // no cache
        if (movieCache.isCached() && !movieCache.isExpired()) {
            return retrieveCachedDataStore()
        }

        return retrieveRemoteDataStore()
    }

    open fun retrieveRemoteDataStore(): MovieDataStore {
        return movieRemoteDataStore
    }

    open fun retrieveCachedDataStore(): MovieDataStore {
        return movieCacheDataStore
    }
}