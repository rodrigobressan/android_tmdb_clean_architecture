package com.rodrigobresan.data.movie_detail.sources.data_store

import com.rodrigobresan.data.connection.ConnectionStatus
import com.rodrigobresan.data.movie_detail.sources.data_store.local.MovieDetailCache
import com.rodrigobresan.data.movie_detail.sources.data_store.local.MovieDetailCacheDataStore
import com.rodrigobresan.data.movie_detail.sources.data_store.remote.MovieDetailRemoteDataStore
import javax.inject.Inject

open class MovieDetailDataStoreFactory @Inject constructor(
        private val connectionStatus: ConnectionStatus,
        private val movieDetailCache: MovieDetailCache,
        private val movieDetailCacheDataStore: MovieDetailCacheDataStore,
        private val movieDetailRemoteDataStore: MovieDetailRemoteDataStore) {

    open fun retrieveDataStore(movieId: Long): MovieDetailDataStore {
        if (connectionStatus.isOffline()) {
            return retrieveCachedDataStore()
        }

        if (movieDetailCache.isMovieCached(movieId) && !movieDetailCache.isExpired()) {
            return retrieveCachedDataStore()
        }

        return retrieveRemoteDataStore()
    }

    open fun retrieveCachedDataStore(): MovieDetailDataStore {
        return movieDetailCacheDataStore
    }

    open fun retrieveRemoteDataStore(): MovieDetailDataStore {
        return movieDetailRemoteDataStore
    }
}