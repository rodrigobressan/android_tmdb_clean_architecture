package com.rodrigobresan.remote.review.mapper

import com.rodrigobresan.remote.movies.mapper.ReviewRemoteMapper
import com.rodrigobresan.remote.test.factory.ReviewFactory
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class ReviewRemoteMapperTest {
    private lateinit var reviewRemoteMapper: ReviewRemoteMapper

    @Before
    fun setUp() {
        reviewRemoteMapper = ReviewRemoteMapper()
    }

    @Test
    fun mapFromRemoteMapsDataProperly() {
        val reviewModel = ReviewFactory.makeReviewModel()
        val reviewEntity = reviewRemoteMapper.mapRemoteToEntity(reviewModel)

        assertEquals(reviewModel.id, reviewEntity.id)
        assertEquals(reviewModel.author, reviewEntity.author)
        assertEquals(reviewModel.content, reviewEntity.content)
        assertEquals(reviewModel.url, reviewEntity.url)
    }
}