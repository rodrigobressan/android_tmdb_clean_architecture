package com.rodrigobresan.presentation.movies.presenter

import com.nhaarman.mockito_kotlin.*
import com.rodrigobresan.domain.movie_category.model.Category
import com.rodrigobresan.domain.movies.interactor.GetMovies
import com.rodrigobresan.domain.movies.model.Movie
import com.rodrigobresan.presentation.movies.contract.MoviesContract
import com.rodrigobresan.presentation.movies.factory.MovieFactory
import com.rodrigobresan.presentation.movies.mapper.MovieMapper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Class for testing MoviePresenter class
 */
@RunWith(JUnit4::class)
class MoviePresenterTest {

    private lateinit var moviesPresenter: MoviesPresenter
    private lateinit var moviesView: MoviesContract.View
    private lateinit var getMoviesUseCase: GetMovies
    private lateinit var movieMapper: MovieMapper

    private lateinit var captor: KArgumentCaptor<DisposableSingleObserver<List<Movie>>>

    private val category: Category = Category.POPULAR

    @Before
    fun setUp() {
        captor = argumentCaptor<DisposableSingleObserver<List<Movie>>>()

        moviesView = mock()
        getMoviesUseCase = mock()
        movieMapper = mock()

        moviesPresenter = MoviesPresenter(moviesView, getMoviesUseCase, movieMapper)
    }

    @Test
    fun onStartCallLoadMovies() {
        moviesPresenter.start()

        verify(getMoviesUseCase).execute(captor.capture(), eq(category))
    }

    @Test
    fun loadMoviesHideErrorState() {
        moviesPresenter.loadMovies()

        verify(getMoviesUseCase).execute(captor.capture(), eq(category))
        captor.firstValue.onSuccess(MovieFactory.makeMovieList(2))
        verify(moviesView).hideErrorState(category)
    }

    @Test
    fun loadMoviesHideEmptyState() {
        moviesPresenter.loadMovies()

        verify(getMoviesUseCase).execute(captor.capture(), eq(category))
        captor.firstValue.onSuccess(MovieFactory.makeMovieList(2))
        verify(moviesView).hideEmptyState(category)
    }

    @Test
    fun loadMoviesShowMovies() {
        val movies = MovieFactory.makeMovieList(2)
        moviesPresenter.loadMovies()

        verify(getMoviesUseCase).execute(captor.capture(), eq(category))
        captor.firstValue.onSuccess(movies)

        verify(moviesView).showMovies(category,
                movies.map {
                    movieMapper.mapToView(it)
                }
        )
    }

    @Test
    fun loadMoviesShowEmptyState() {
        moviesPresenter.loadMovies()

        verify(getMoviesUseCase).execute(captor.capture(), eq(category))
        captor.firstValue.onSuccess(MovieFactory.makeMovieList(0))
        verify(moviesView).showEmptyState(category)
    }

    @Test
    fun loadMoviesHideMovies() {
        moviesPresenter.loadMovies()

        verify(getMoviesUseCase).execute(captor.capture(), eq(category))
        captor.firstValue.onSuccess(MovieFactory.makeMovieList(0))
        verify(moviesView, times(2)).hideMovies(category)
    }

    @Test
    fun loadMoviesShowErrorState() {
        moviesPresenter.loadMovies()

        verify(getMoviesUseCase).execute(captor.capture(), eq(category))
        captor.firstValue.onError(RuntimeException())
        verify(moviesView).showErrorState(category)
    }

    @Test
    fun loadMoviesHideMoviesWhenErrorThrown() {
        moviesPresenter.loadMovies()

        verify(getMoviesUseCase).execute(captor.capture(), eq(category))
        captor.firstValue.onError(RuntimeException())
        verify(moviesView, times(2)).hideMovies(category)
    }

    @Test
    fun loadMoviesHideEmptyStateWhenErrorIsThrown() {
        moviesPresenter.loadMovies()

        verify(getMoviesUseCase).execute(captor.capture(), eq(category))
        captor.firstValue.onError(RuntimeException())
        verify(moviesView).hideEmptyState(category)
    }

}