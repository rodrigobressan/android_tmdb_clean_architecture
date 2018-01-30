package com.rodrigobresan.data.review.source

import com.nhaarman.mockito_kotlin.*
import com.rodrigobresan.data.category.sources.CategoryCache
import com.rodrigobresan.data.movie.sources.data_store.local.MovieCache
import com.rodrigobresan.data.movie.sources.data_store.local.MovieCacheDataStore
import com.rodrigobresan.data.movie_category.sources.MovieCategoryCache
import com.rodrigobresan.data.review.sources.data_store.local.ReviewCache
import com.rodrigobresan.data.review.sources.data_store.local.ReviewCacheDataStore
import com.rodrigobresan.data.test.factory.MovieCategoryFactory
import com.rodrigobresan.data.test.factory.MovieFactory
import com.rodrigobresan.data.test.factory.ReviewFactory
import com.rodrigobresan.domain.movie_category.model.Category
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ReviewCacheDataStoreTest {

    private lateinit var reviewCacheDataStore: ReviewCacheDataStore
    private lateinit var reviewCache: ReviewCache

    @Before
    fun setUp() {
        reviewCache = mock()

        reviewCacheDataStore = ReviewCacheDataStore(reviewCache)
    }

    @Test
    fun clearReviewsCompletes() {
        whenever(reviewCache.clearReviews())
                .thenReturn(Completable.complete())

        val testObserver = reviewCacheDataStore.clearReviews().test()
        testObserver.assertComplete()
    }

    @Test
    fun saveReviewsCompletes() {
        val reviewEntityList = ReviewFactory.makeReviewEntityList(5)
        whenever(reviewCache.saveReviews(0, reviewEntityList))
                .thenReturn(Completable.complete())

        reviewCacheDataStore.saveReviews(0, reviewEntityList).test().assertComplete()
    }

    @Test
    fun getReviewsCompletes() {
        val reviewEntityList = ReviewFactory.makeReviewEntityList(5)
        whenever(reviewCache.getReviews(0))
                .thenReturn(Single.just(reviewEntityList))

        reviewCacheDataStore.getReviews(0).test().assertComplete()
    }
}