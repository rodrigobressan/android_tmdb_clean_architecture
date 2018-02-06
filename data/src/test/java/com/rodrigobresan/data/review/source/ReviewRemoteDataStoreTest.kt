package com.rodrigobresan.data.review.source

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.rodrigobresan.data.review.sources.data_store.local.ReviewRemoteDataStore
import com.rodrigobresan.data.review.sources.data_store.remote.ReviewRemote
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ReviewRemoteDataStoreTest {

    lateinit var reviewRemoteDataStore: ReviewRemoteDataStore
    lateinit var reviewRemote: ReviewRemote

    @Before
    fun setUp() {
        reviewRemote = mock()
        reviewRemoteDataStore = ReviewRemoteDataStore(reviewRemote)
    }

    @Test(expected = UnsupportedOperationException::class)
    fun clearReviewsShouldThrowUnsupportedOperationException() {
        reviewRemoteDataStore.clearReviews()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun saveReviewsShouldThrowUnsupportedOperationException() {
        reviewRemoteDataStore.saveReviews(0, arrayListOf())
    }

    @Test
    fun getReviewsShouldCallRemote() {
        reviewRemoteDataStore.getReviews(0)
        verify(reviewRemote).getReviews(0)
    }
}