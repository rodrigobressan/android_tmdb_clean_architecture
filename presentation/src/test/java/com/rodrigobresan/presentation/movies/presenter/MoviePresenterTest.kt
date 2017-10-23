package com.rodrigobresan.presentation.movies.presenter

import com.nhaarman.mockito_kotlin.*
import com.rodrigobresan.domain.interactor.GetMovies
import com.rodrigobresan.domain.model.Movie
import com.rodrigobresan.presentation.movies.contract.MoviesContract
import com.rodrigobresan.presentation.movies.factory.MovieFactory
import com.rodrigobresan.presentation.movies.mapper.MovieMapper
import io.reactivex.observers.DisposableSingleObserver
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MoviePresenterTest {

    private lateinit var moviesPresenter: MoviesPresenter
    private lateinit var moviesView: MoviesContract.View
    private lateinit var getMoviesUseCase: GetMovies
    private lateinit var movieMapper: MovieMapper

    private lateinit var captor: KArgumentCaptor<DisposableSingleObserver<List<Movie>>>

    @Before
    fun setUp() {
        captor = argumentCaptor<DisposableSingleObserver<List<Movie>>>()

        moviesView = mock()
        getMoviesUseCase = mock()
        movieMapper = mock()

        moviesPresenter = MoviesPresenter(moviesView, getMoviesUseCase, movieMapper)
    }

    @Test
    fun loadMoviesHideErrorState() {
        moviesPresenter.loadMovies()

        verify(getMoviesUseCase).execute(captor.capture(), eq(null))
        captor.firstValue.onSuccess(MovieFactory.makeMovieList(2))
        verify(moviesView).hideErrorState()
    }

    @Test
    fun loadMoviesHideEmptyState() {
        moviesPresenter.loadMovies()

        verify(getMoviesUseCase).execute(captor.capture(), eq(null))
        captor.firstValue.onSuccess(MovieFactory.makeMovieList(2))
        verify(moviesView).hideEmptyState()
    }

    @Test
    fun loadMoviesShowMovies() {
        val movies = MovieFactory.makeMovieList(2)
        moviesPresenter.loadMovies()

        verify(getMoviesUseCase).execute(captor.capture(), eq(null))
        captor.firstValue.onSuccess(movies)

        verify(moviesView).showMovies(
                movies.map {
                    movieMapper.mapToView(it)
                }
        )
    }

    @Test
    fun loadMoviesShowEmptyState() {
        moviesPresenter.loadMovies()

        verify(getMoviesUseCase).execute(captor.capture(), eq(null))
        captor.firstValue.onSuccess(MovieFactory.makeMovieList(0))
        verify(moviesView).showEmptyState()
    }

    @Test
    fun loadMoviesHideMovies() {
        moviesPresenter.loadMovies()

        verify(getMoviesUseCase).execute(captor.capture(), eq(null))
        captor.firstValue.onSuccess(MovieFactory.makeMovieList(0))
        verify(moviesView, times(2)).hideMovies()
    }

    @Test
    fun loadMoviesShowErrorState() {
        moviesPresenter.loadMovies()

        verify(getMoviesUseCase).execute(captor.capture(), eq(null))
        captor.firstValue.onError(RuntimeException())
        verify(moviesView).showErrorState()
    }

    @Test
    fun loadMoviesHideMoviesWhenErrorThrown() {
        moviesPresenter.loadMovies()

        verify(getMoviesUseCase).execute(captor.capture(), eq(null))
        captor.firstValue.onError(RuntimeException())
        verify(moviesView, times(2)).hideMovies()
    }

    @Test
    fun loadMoviesHideEmptyStateWhenErrorIsThrown() {
        moviesPresenter.loadMovies()

        verify(getMoviesUseCase).execute(captor.capture(), eq(null))
        captor.firstValue.onError(RuntimeException())
        verify(moviesView).hideEmptyState()
    }

}