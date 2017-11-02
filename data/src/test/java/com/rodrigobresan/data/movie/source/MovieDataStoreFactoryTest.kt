package com.rodrigobresan.data.movie.source

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.rodrigobresan.data.movie.sources.MovieCache
import com.rodrigobresan.data.movie.sources.data_store.MovieCacheDataStore
import com.rodrigobresan.data.movie.sources.data_store.MovieDataStoreFactory
import com.rodrigobresan.data.movie.sources.data_store.MovieRemoteDataStore
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
/**
 * Class for testing MovieDataStoreFactory class
 */
@RunWith(JUnit4::class)
class MovieDataStoreFactoryTest {

    private lateinit var movieDataStoreFactory: MovieDataStoreFactory

    private lateinit var movieCache: MovieCache
    private lateinit var movieCacheDataStore: MovieCacheDataStore
    private lateinit var movieRemoteDataStore: MovieRemoteDataStore

    @Before
    fun setUp() {
        movieCache = mock()
        movieCacheDataStore = mock()
        movieRemoteDataStore = mock()

        movieDataStoreFactory = MovieDataStoreFactory(movieCache, movieCacheDataStore, movieRemoteDataStore)
    }

    @Test
    fun retrieveDataStoreWhenNotCachedReturnsRemoteDataStore() {
        stubMovieIsCached(false)

        val dataStore = movieDataStoreFactory.retrieveDataStore()
        assert(dataStore is MovieRemoteDataStore)
    }

    @Test
    fun retrieveDataStoreWhenCachedExpiredReturnsRemoteDataStore() {
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

    private fun stubMovieIsCached(isCached: Boolean) {
        whenever(movieCache.isCached())
                .thenReturn(isCached)
    }

    private fun stubMovieIsExpired(isExpired: Boolean) {
        whenever(movieCache.isExpired())
                .thenReturn(isExpired)
    }

}