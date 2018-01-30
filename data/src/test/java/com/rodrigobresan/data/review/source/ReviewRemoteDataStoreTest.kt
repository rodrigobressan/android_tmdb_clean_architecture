package com.rodrigobresan.data.movie.source

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.rodrigobresan.data.movie.sources.data_store.remote.MovieRemoteDataStore
import com.rodrigobresan.data.movie_category.model.MovieCategoryEntity
import com.rodrigobresan.data.review.sources.data_store.local.ReviewRemoteDataStore
import com.rodrigobresan.data.review.sources.data_store.remote.ReviewRemote
import com.rodrigobresan.domain.movie_category.model.Category
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