package com.rodrigobresan.data.movie.source

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.rodrigobresan.data.connection.ConnectionStatus
import com.rodrigobresan.data.movie.sources.data_store.local.MovieCacheDataStore
import com.rodrigobresan.data.movie_detail.sources.data_store.MovieDetailDataStoreFactory
import com.rodrigobresan.data.movie_detail.sources.data_store.local.MovieDetailCache
import com.rodrigobresan.data.movie_detail.sources.data_store.local.MovieDetailCacheDataStore
import com.rodrigobresan.data.movie_detail.sources.data_store.remote.MovieDetailRemoteDataStore
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Class for testing MovieDataStoreFactory class
 */
@RunWith(JUnit4::class)
class MovieDetailDataStoreFactoryTest {

    private lateinit var movieDataStoreFactory: MovieDetailDataStoreFactory

    private lateinit var movieCache: MovieDetailCache
    private lateinit var movieCacheDataStore: MovieDetailCacheDataStore
    private lateinit var movieRemoteDataStore: MovieDetailRemoteDataStore
    private lateinit var connectionStatus: ConnectionStatus

    @Before
    fun setUp() {
        connectionStatus = mock()
        movieCache = mock()
        movieCacheDataStore = mock()
        movieRemoteDataStore = mock()

        movieDataStoreFactory = MovieDetailDataStoreFactory(connectionStatus,
                movieCache, movieCacheDataStore, movieRemoteDataStore)
    }

    @Test
    fun retrieveDataStoreWhenNotCachedReturnsRemoteDataStore() {
        stubIsOffline(false)
        stubMovieIsCached(false)

        val dataStore = movieDataStoreFactory.retrieveDataStore(0)
        assert(dataStore is MovieDetailRemoteDataStore)
    }

    @Test
    fun retrieveDataStoreWhenCachedExpiredReturnsRemoteDataStore() {
        stubIsOffline(false)
        stubMovieIsCached(false)
        stubMovieIsExpired(true)

        val dataStore = movieDataStoreFactory.retrieveDataStore(0)
        assert(dataStore is MovieDetailRemoteDataStore)
    }

    @Test
    fun retrieveCachedDataStoreWhenOffline() {
        stubIsOffline(true)

        val dataStore = movieDataStoreFactory.retrieveDataStore(0)
        assert(dataStore is MovieDetailCacheDataStore)
    }

    @Test
    fun retrieveDataStoreWhenCacheExistsAndNotExpiredReturnsCachedDataStore() {
        stubMovieIsCached(true)
        stubMovieIsExpired(false)

        val dataStore = movieDataStoreFactory.retrieveDataStore(0)
        assert(dataStore is MovieDetailCacheDataStore)
    }

    @Test
    fun retrieveRemoteDataStoreReturnsRemoteDataStore() {
        val movieDataStore = movieDataStoreFactory.retrieveRemoteDataStore()
        assert(movieDataStore is MovieDetailRemoteDataStore)
    }

    @Test
    fun retrieveCachedDataStoreReturnsCachedDataStore() {
        val movieDataStore = movieDataStoreFactory.retrieveCachedDataStore()
        assert(movieDataStore is MovieDetailCacheDataStore)
    }

    private fun stubMovieIsCached(isCached: Boolean) {
        whenever(movieCache.isMovieCached(any()))
                .thenReturn(isCached)
    }

    private fun stubMovieIsExpired(isExpired: Boolean) {
        whenever(movieCache.isExpired())
                .thenReturn(isExpired)
    }

    private fun stubIsOffline(isOffline: Boolean) {
        whenever(connectionStatus.isOffline())
                .thenReturn(isOffline)
    }

}