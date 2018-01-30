package com.rodrigobresan.cache.review;

import android.arch.persistence.room.Room
import com.rodrigobresan.cache.AppDatabase
import com.rodrigobresan.cache.review.dao.ReviewDao
import com.rodrigobresan.cache.test.factory.ReviewFactory
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = intArrayOf(21))
class ReviewEntityReadWriteTest {

    private lateinit var reviewDao: ReviewDao
    private lateinit var appDatabase: AppDatabase

    @Before
    fun createDb() {
        val targetContext = RuntimeEnvironment.application
        appDatabase = Room.inMemoryDatabaseBuilder(targetContext, AppDatabase::class.java)
                .allowMainThreadQueries()
                .build()

        reviewDao = appDatabase.reviewDao()
    }

    @After
    fun closeDb() {
        appDatabase.close()
    }

    @Test
    fun writeReviewsAndReadInList() {
        val reviewList = ReviewFactory.makeReviewCachedList(5)

        reviewList.forEach {
            reviewDao.insert(it)
        }

        for ((index, storedReview) in reviewDao.getAllReviews(0).withIndex()) {
            Assert.assertEquals(storedReview.content, reviewList.get(index).content)
        }
    }
}