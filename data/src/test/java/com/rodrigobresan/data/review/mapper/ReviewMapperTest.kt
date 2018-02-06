package com.rodrigobresan.data.review.mapper

import com.rodrigobresan.data.review.model.ReviewEntity
import com.rodrigobresan.data.test.factory.ReviewFactory
import com.rodrigobresan.domain.review.model.Review
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals


@RunWith(JUnit4::class)
class ReviewMapperTest {
    private lateinit var reviewMapper: ReviewMapper

    @Before
    fun setUp() {
        reviewMapper = ReviewMapper()
    }

    @Test
    fun mapFromEntityMaps() {
        val reviewEntity = ReviewFactory.makeReviewEntity()
        val reviewMapper = reviewMapper.mapFromEntity(reviewEntity)

        assertReviewDataEquality(reviewEntity, reviewMapper)
    }

    @Test
    fun mapToEntityMapsData() {
        val review = ReviewFactory.makeReview()
        val reviewEntity = reviewMapper.mapToEntity(review)

        assertReviewDataEquality(reviewEntity, review)
    }

    private fun assertReviewDataEquality(reviewEntity: ReviewEntity, review: Review) {
        assertEquals(reviewEntity.id, review.id)
        assertEquals(reviewEntity.author, review.author)
        assertEquals(reviewEntity.content, review.content)
        assertEquals(reviewEntity.url, review.url)
    }
}