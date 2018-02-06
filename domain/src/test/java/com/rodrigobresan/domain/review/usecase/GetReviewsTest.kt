package com.rodrigobresan.domain.movie.usecase

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.rodrigobresan.domain.base.executor.PostExecutionThread
import com.rodrigobresan.domain.base.executor.ThreadExecutor
import com.rodrigobresan.domain.review.interactor.GetReviews
import com.rodrigobresan.domain.review.model.Review
import com.rodrigobresan.domain.review.repository.ReviewRepository
import com.rodrigobresan.domain.test.factory.ReviewFactory
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Class for testing GetMovies use case class
 */
@RunWith(JUnit4::class)
class GetReviewsTest {

    private lateinit var getReviews: GetReviews
    private lateinit var reviewsRepository: ReviewRepository
    private lateinit var threadExecutor: ThreadExecutor
    private lateinit var postExecutionThread: PostExecutionThread

    @Before
    fun setUp() {
        reviewsRepository = mock()
        threadExecutor = mock()
        postExecutionThread = mock()
        getReviews = GetReviews(reviewsRepository, threadExecutor, postExecutionThread)
    }

    @Test
    fun buildCaseObservableCallsMoviesRepository() {
        getReviews.buildUseCaseObservable(0)
        verify(reviewsRepository).getReviews(any())
    }

    @Test(expected = IllegalArgumentException::class)
    fun buildUseCaseObservableThrowsException() {
        getReviews.buildUseCaseObservable(null)
    }

    @Test
    fun buildUseCaseObservableCompletes() {
        stubReviewRepositoryGetReviews(Single.just(ReviewFactory.makeReviewList(5)))

        val testObservable = getReviews.buildUseCaseObservable(0).test()
        testObservable.assertComplete()
    }

    @Test
    fun buildUseCaseObservableReturnsData() {
        val reviews = ReviewFactory.makeReviewList(5)
        stubReviewRepositoryGetReviews(Single.just(reviews))

        val testObserver = getReviews.buildUseCaseObservable(0).test()
        testObserver.assertResult(reviews)
    }

    private fun stubReviewRepositoryGetReviews(reviewList: Single<List<Review>>?) {
        whenever(reviewsRepository.getReviews(any()))
                .thenReturn(reviewList)
    }
}