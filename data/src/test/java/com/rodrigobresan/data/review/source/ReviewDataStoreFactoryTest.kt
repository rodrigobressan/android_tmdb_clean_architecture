package com.rodrigobresan.data.review.source

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.rodrigobresan.data.connection.ConnectionStatus
import com.rodrigobresan.data.movie.sources.data_store.MovieDataStoreFactory
import com.rodrigobresan.data.movie.sources.data_store.local.MovieCache
import com.rodrigobresan.data.movie.sources.data_store.local.MovieCacheDataStore
import com.rodrigobresan.data.movie.sources.data_store.remote.MovieRemoteDataStore
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Class for testing MovieDataStoreFactory class
 */
@RunWith(JUnit4::class)
class ReviewDataStoreFactoryTest {

    private lateinit var movieDataStoreFactory: MovieDataStoreFactory

    private lateinit var movieCache: MovieCache
    private lateinit var movieCacheDataStore: MovieCacheDataStore
    private lateinit var movieRemoteDataStore: MovieRemoteDataStore
    private lateinit var connectionStatus: ConnectionStatus

    @Before
    fun setUp() {
        movieCache = mock()
        movieCacheDataStore = mock()
        movieRemoteDataStore = mock()
        connectionStatus = mock()

        movieDataStoreFactory = MovieDataStoreFactory(connectionStatus, movieCache,
                movieCacheDataStore, movieRemoteDataStore)
    }

    @Test
    fun retrieveDataStoreWhenNoConnectionReturnsCachedDataStore() {
        stubIsOffline(true)

        val dataStore = movieDataStoreFactory.retrieveDataStore()
        assert(dataStore is MovieCacheDataStore)
    }


    @Test
    fun retrieveDataStoreWhenNotCachedReturnsRemoteDataStore() {
        stubIsOffline(false)
        stubMovieIsCached(false)

        val dataStore = movieDataStoreFactory.retrieveDataStore()
        assert(dataStore is MovieRemoteDataStore)
    }

    @Test
    fun retrieveDataStoreWhenCachedExpiredReturnsRemoteDataStore() {
        stubIsOffline(false)
        stubMovieIsCached(false)
        stubMovieIsExpired(true)

        val dataStore = movieDataStoreFactory.retrieveDataStore()
        assert(dataStore is MovieRemoteDataStore)
    }

    @Test
    fun retrieveDataStoreWhenCacheExistsAndNotExpiredReturnsCachedDataStore() {
        stubMovieIsCached(true)
        stubMovieIsExpired(false)

        val dataStore = movieDataStoreFactory.retrieveDataStore()
        assert(dataStore is MovieCacheDataStore)
    }

    @Test
    fun retrieveRemoteDataStoreReturnsRemoteDataStore() {
        val movieDataStore = movieDataStoreFactory.retrieveRemoteDataStore()
        assert(movieDataStore is MovieRemoteDataStore)
    }

    @Test
    fun retrieveCachedDataStoreReturnsCachedDataStore() {
        val movieDataStore = movieDataStoreFactory.retrieveCachedDataStore()
        assert(movieDataStore is MovieCacheDataStore)
    }

    private fun stubIsOffline(isOffline: Boolean) {
        whenever(connectionStatus.isOffline())
                .thenReturn(isOffline)
    }

    private fun stubMovieIsCached(isCached: Boolean) {
        whenever(movieCache.isCached())
                .thenReturn(isCached)
    }

    private fun stubMovieIsExpired(isExpired: Boolean) {
        whenever(movieCache.isExpired())
                .thenReturn(isExpired)
    }

}