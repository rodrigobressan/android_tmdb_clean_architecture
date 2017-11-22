package com.rodrigobresan.domain.movie.usecase

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.rodrigobresan.domain.base.executor.PostExecutionThread
import com.rodrigobresan.domain.base.executor.ThreadExecutor
import com.rodrigobresan.domain.movie_category.model.Category
import com.rodrigobresan.domain.movies.interactor.UnfavoriteMovie
import com.rodrigobresan.domain.movies.model.Movie
import com.rodrigobresan.domain.movies.repository.MovieRepository
import com.rodrigobresan.domain.test.factory.MovieFactory
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class UnfavoriteMovieTest {

    private lateinit var movieRepository: MovieRepository
    private lateinit var unfavoriteMovie: UnfavoriteMovie
    private lateinit var threadExecutor: ThreadExecutor
    private lateinit var postExecutionThread: PostExecutionThread

    @Before
    fun setUp() {
        threadExecutor = mock()
        postExecutionThread = mock()
        movieRepository = mock()

        unfavoriteMovie = UnfavoriteMovie(movieRepository, threadExecutor, postExecutionThread)
    }

    @Test
    fun buildUseCaseObservableCallsMovieRepositoryGetMovie() {
        val movie = MovieFactory.makeMovie()
        stubMovieRepositoryGetMovie(Single.just(movie))
        unfavoriteMovie.buildUseCaseObservable(0)

        verify(movieRepository).getMovie(0)
    }

    @Test
    fun buildUseCaseObservableCallsMovieRepositorySaveMovie() {
        val movie = MovieFactory.makeMovie()
        stubMovieRepositoryGetMovie(Single.just(movie))
        unfavoriteMovie.buildUseCaseObservable(movie.id)
                .subscribe({

                })

        verify(movieRepository).deleteMovie(Category.FAVORITE, movie)
    }

    private fun stubMovieRepositoryGetMovie(movie: Single<Movie>) {
        whenever(movieRepository.getMovie(any()))
                .thenReturn(movie)
    }

}