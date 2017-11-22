package com.rodrigobresan.domain.movie.usecase

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.rodrigobresan.domain.base.executor.PostExecutionThread
import com.rodrigobresan.domain.base.executor.ThreadExecutor
import com.rodrigobresan.domain.movie_category.model.Category
import com.rodrigobresan.domain.movies.interactor.FavoriteMovie
import com.rodrigobresan.domain.movies.model.Movie
import com.rodrigobresan.domain.movies.repository.MovieRepository
import com.rodrigobresan.domain.test.factory.MovieFactory
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class FavoriteMovieTest {

    private lateinit var movieRepository: MovieRepository
    private lateinit var favoriteMovie: FavoriteMovie
    private lateinit var threadExecutor: ThreadExecutor
    private lateinit var postExecutionThread: PostExecutionThread

    @Before
    fun setUp() {
        threadExecutor = mock()
        postExecutionThread = mock()
        movieRepository = mock()

        favoriteMovie = FavoriteMovie(movieRepository, threadExecutor, postExecutionThread)
    }

    @Test
    fun buildUseCaseObservableCallsMovieRepositoryGetMovie() {
        val movie = MovieFactory.makeMovie()
        stubMovieRepositoryGetMovie(Single.just(movie))
        favoriteMovie.buildUseCaseObservable(0)

        verify(movieRepository).getMovie(0)
    }

    @Test
    fun buildUseCaseObservableCallsMovieRepositorySaveMovie() {
        val movie = MovieFactory.makeMovie()
        stubMovieRepositoryGetMovie(Single.just(movie))
        favoriteMovie.buildUseCaseObservable(movie.id)
                .subscribe({

                })

        verify(movieRepository).saveMovies(Category.FAVORITE, listOf(movie))
    }

    private fun stubMovieRepositoryGetMovie(movie: Single<Movie>) {
        whenever(movieRepository.getMovie(any()))
                .thenReturn(movie)
    }

}