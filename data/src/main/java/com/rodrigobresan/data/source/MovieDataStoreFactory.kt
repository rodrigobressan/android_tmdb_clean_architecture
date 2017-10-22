package com.rodrigobresan.data.source

import com.rodrigobresan.data.repository.movie.MovieCache
import com.rodrigobresan.data.repository.movie.MovieDataStore
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

    private fun retrieveRemoteDataStore(): MovieDataStore {
        return movieRemoteDataStore
    }

    private fun retrieveCachedDataStore(): MovieDataStore {
        return movieCacheDataStore
    }
}