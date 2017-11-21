package com.rodrigobresan.domain.movie.usecase

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.rodrigobresan.domain.base.executor.PostExecutionThread
import com.rodrigobresan.domain.base.executor.ThreadExecutor
import com.rodrigobresan.domain.movie_detail.interactor.GetMovieDetails
import com.rodrigobresan.domain.movie_detail.model.MovieDetail
import com.rodrigobresan.domain.movie_detail.repository.MovieDetailRepository
import com.rodrigobresan.domain.test.factory.MovieDetailFactory
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Class for testing GetMovieDetails use case class
 */
@RunWith(JUnit4::class)
class GetMovieDetailsTest {

    private lateinit var getMovies: GetMovieDetails
    private lateinit var movieRepository: MovieDetailRepository
    private lateinit var threadExecutor: ThreadExecutor
    private lateinit var postExecutionThread: PostExecutionThread

    @Before
    fun setUp() {
        movieRepository = mock()
        threadExecutor = mock()
        postExecutionThread = mock()
        getMovies = GetMovieDetails(movieRepository, threadExecutor, postExecutionThread)
    }

    @Test
    fun buildCaseObservableCallsMoviesRepository() {
        getMovies.buildUseCaseObservable(0)
        verify(movieRepository).getMovieDetails(0)
    }

    @Test
    fun buildUseCaseObservableCompletes() {
        stubMovieRepositoryGetMovieDetails(Single.just(MovieDetailFactory.makeMovie()))

        val testObservable = getMovies.buildUseCaseObservable(0).test()
        testObservable.assertComplete()
    }

    @Test
    fun buildUseCaseObservableReturnsData() {
        val movie = MovieDetailFactory.makeMovie()
        stubMovieRepositoryGetMovieDetails(Single.just(movie))

        val testObserver = getMovies.buildUseCaseObservable(0).test()
        testObserver.assertResult(movie)
    }

    private fun stubMovieRepositoryGetMovieDetails(singleMovieList: Single<MovieDetail>?) {
        whenever(movieRepository.getMovieDetails(any()))
                .thenReturn(singleMovieList)
    }

    @Test(expected = IllegalArgumentException::class)
    fun throwExceptionWhenMovieDetailIsNull() {
        getMovies.buildUseCaseObservable(null)
    }
}