package com.rodrigobresan.data.movie.sources.data_store

import com.rodrigobresan.data.connection.ConnectionStatus
import com.rodrigobresan.data.review.sources.data_store.ReviewDataStore
import com.rodrigobresan.data.review.sources.data_store.local.ReviewCacheDataStore
import com.rodrigobresan.data.review.sources.data_store.local.ReviewRemoteDataStore
import javax.inject.Inject

/**
 * Factory for MovieDataStore
 */
open class ReviewDataStoreFactory @Inject constructor(private val connectionStatus: ConnectionStatus,
                                                      private val reviewRemoteDataStore: ReviewRemoteDataStore,
                                                      private val reviewCacheDataStore: ReviewCacheDataStore) {

    open fun retrieveDataStore(): ReviewDataStore {

        // no connection
        if (connectionStatus.isOffline()) {
            return retrieveCachedDataStore()
        }

        return retrieveRemoteDataStore()
    }

    open fun retrieveRemoteDataStore(): ReviewDataStore {
        return reviewRemoteDataStore
    }

    open fun retrieveCachedDataStore(): ReviewDataStore {
        return reviewCacheDataStore
    }
}