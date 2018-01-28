package com.rodrigobresan.remote.review.impl

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.rodrigobresan.data.review.model.ReviewEntity
import com.rodrigobresan.remote.movies.mapper.ReviewRemoteMapper
import com.rodrigobresan.remote.service.MovieService
import com.rodrigobresan.remote.test.factory.ReviewFactory
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ReviewRemoteImplTest {

    private lateinit var movieService: MovieService
    private lateinit var reviewMapper: ReviewRemoteMapper

    private lateinit var reviewRemoteImpl: ReviewRemoteImpl

    @Before
    fun setUp() {
        movieService = mock()
        reviewMapper = mock()

        reviewRemoteImpl = ReviewRemoteImpl(movieService, reviewMapper)
    }

    @Test
    fun getReviewsCompletes() {
        stubMovieServiceGetReviews(0)

        var testObserver = movieService.getMovieReviews(0).test()
        testObserver.assertComplete()
    }

    @Test
    fun getReviewsReturnsData() {
        val reviewsResponse = ReviewFactory.makeReviewsResponse()
        val reviewsEntities = mutableListOf<ReviewEntity>()

        stubMovieServiceGetReviews(0)
        reviewsResponse.results.forEach {
            reviewsEntities.add(reviewMapper.mapRemoteToEntity(it))
        }

        val testObserver = reviewRemoteImpl.getReviews(0).test()
        testObserver.assertValue(reviewsEntities)
    }

    private fun stubMovieServiceGetReviews(movieId: Long) {
        whenever(movieService.getMovieReviews(movieId))
                .thenReturn(Single.just(ReviewFactory.makeReviewsResponse()))
    }
}