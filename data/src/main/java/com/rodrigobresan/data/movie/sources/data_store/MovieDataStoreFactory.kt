package com.rodrigobresan.data.movie.sources.data_store

import com.rodrigobresan.data.movie.sources.MovieCache
import javax.inject.Inject

open class MovieDataStoreFactory @Inject constructor(private val movieCache: MovieCache,
                                                     private val movieCacheDataStore: MovieCacheDataStore,
                                                     private val movieRemoteDataStore: MovieRemoteDataStore) {

    open fun retrieveDataStore(): MovieDataStore {
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