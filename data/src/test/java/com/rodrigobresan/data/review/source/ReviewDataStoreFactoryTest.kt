package com.rodrigobresan.data.review.source

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.rodrigobresan.data.connection.ConnectionStatus
import com.rodrigobresan.data.movie.sources.data_store.ReviewDataStoreFactory
import com.rodrigobresan.data.review.sources.data_store.local.ReviewCache
import com.rodrigobresan.data.review.sources.data_store.local.ReviewCacheDataStore
import com.rodrigobresan.data.review.sources.data_store.local.ReviewRemoteDataStore
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ReviewDataStoreFactoryTest {

    private lateinit var reviewDataStoreFactory: ReviewDataStoreFactory

    private lateinit var reviewCache: ReviewCache
    private lateinit var reviewCacheDataStore: ReviewCacheDataStore
    private lateinit var reviewRemoteDataStore: ReviewRemoteDataStore
    private lateinit var connectionStatus: ConnectionStatus

    @Before
    fun setUp() {
        reviewCache = mock()
        reviewCacheDataStore = mock()
        reviewRemoteDataStore = mock()
        connectionStatus = mock()

        reviewDataStoreFactory = ReviewDataStoreFactory(connectionStatus, reviewRemoteDataStore,
                reviewCacheDataStore)
    }

    @Test
    fun retrieveDataStoreWhenNoConnectionReturnsCachedDataStore() {
        stubIsOffline(true)

        val dataStore = reviewDataStoreFactory.retrieveDataStore()
        assert(dataStore is ReviewCacheDataStore)
    }

    @Test
    fun retrieveRemoteDataStoreReturnsRemoteDataStore() {
        val ReviewDataStore = reviewDataStoreFactory.retrieveRemoteDataStore()
        assert(ReviewDataStore is ReviewRemoteDataStore)
    }

    @Test
    fun retrieveCachedDataStoreReturnsCachedDataStore() {
        val ReviewDataStore = reviewDataStoreFactory.retrieveCachedDataStore()
        assert(ReviewDataStore is ReviewCacheDataStore)
    }

    private fun stubIsOffline(isOffline: Boolean) {
        whenever(connectionStatus.isOffline())
                .thenReturn(isOffline)
    }

}