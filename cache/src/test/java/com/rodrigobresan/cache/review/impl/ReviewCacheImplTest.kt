package com.rodrigobresan.cache.review.impl

import android.arch.persistence.room.Room
import com.rodrigobresan.cache.AppDatabase
import com.rodrigobresan.cache.PreferencesHelper
import com.rodrigobresan.cache.review.mapper.ReviewCacheMapper
import com.rodrigobresan.cache.test.factory.ReviewFactory
import com.rodrigobresan.data.review.mapper.ReviewMapper
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
@Config(sdk = intArrayOf(21))
class ReviewCacheImplTest {

    private var context = RuntimeEnvironment.application
    private var database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).allowMainThreadQueries().build()

    private var reviewCacheMapper = ReviewCacheMapper()
    private lateinit var reviewCacheImpl: ReviewCacheImpl

    @Before
    fun setUp() {
        reviewCacheImpl = ReviewCacheImpl(database.reviewDao(), reviewCacheMapper)
    }

    @Test
    fun clearTableCompletes() {
        reviewCacheImpl.clearReviews().test().assertComplete()
    }

    @Test
    fun saveReviewSavesData() {
        var movieId = 0L
        var review = ReviewFactory.makeReviewEntity()

        reviewCacheImpl.saveReviews(movieId, arrayListOf(review)).test()

        val size = database.reviewDao().getAllReviews(movieId).size

        assertEquals(size, 1)
    }

    @Test
    fun saveReviewCompletes() {
        val review = ReviewFactory.makeReviewEntity()
        reviewCacheImpl.saveReviews(0, arrayListOf(review)).test().assertComplete()
    }

}