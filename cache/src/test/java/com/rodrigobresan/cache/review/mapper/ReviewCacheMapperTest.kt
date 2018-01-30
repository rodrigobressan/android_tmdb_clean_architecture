package com.rodrigobresan.cache.review.mapper

import com.rodrigobresan.cache.review.model.ReviewCached
import com.rodrigobresan.cache.test.factory.ReviewFactory
import com.rodrigobresan.data.review.model.ReviewEntity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class ReviewCacheMapperTest {

    private lateinit var reviewCacheMapper: ReviewCacheMapper

    @Before
    fun setUp() {
        reviewCacheMapper = ReviewCacheMapper()
    }

    @Test
    fun mapFromCachedMapsData() {
        val reviewCached = ReviewFactory.makeReviewCached()
        val reviewEntity = reviewCacheMapper.mapFromCached(reviewCached)

        assertReviewDataEquality(reviewCached, reviewEntity)
    }

    @Test
    fun mapToCachedMapsData() {
        val reviewEntity = ReviewFactory.makeReviewEntity()
        val reviewCached = reviewCacheMapper.mapToCached(reviewEntity)

        assertReviewDataEquality(reviewCached, reviewEntity)
    }

    private fun assertReviewDataEquality(reviewCached: ReviewCached, reviewEntity: ReviewEntity) {
        assertEquals(reviewCached.id, reviewEntity.id)
        assertEquals(reviewCached.url, reviewEntity.url)
        assertEquals(reviewCached.content, reviewEntity.content)
        assertEquals(reviewCached.author, reviewEntity.author)
    }


}