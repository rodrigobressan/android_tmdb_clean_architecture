package com.rodrigobresan.presentation.movies.presenter

import com.rodrigobresan.domain.interactor.GetMovies
import com.rodrigobresan.domain.model.Movie
import com.rodrigobresan.domain.model.MovieCategory
import com.rodrigobresan.presentation.movies.contract.MoviesContract
import com.rodrigobresan.presentation.movies.mapper.MovieMapper
import com.rodrigobresan.presentation.movies.model.MovieView
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class MoviesPresenter @Inject constructor(val moviesView: MoviesContract.View,
                                          val getMoviesUseCase: GetMovies,
                                          val movieMapper: MovieMapper) : MoviesContract.Presenter {

    init {
        moviesView.setPresenter(this)
    }

    override fun start() {
        loadMovies()
    }

    override fun stop() {
        getMoviesUseCase.dispose()
    }

    override fun loadMovies() {
        moviesView.showProgress()
        moviesView.hideMovies()
        getMoviesUseCase.execute(MoviesSubscriber(MovieCategory.UPCOMING), MovieCategory.UPCOMING)
        getMoviesUseCase.execute(MoviesSubscriber(MovieCategory.POPULAR), MovieCategory.POPULAR)
        getMoviesUseCase.execute(MoviesSubscriber(MovieCategory.TOP_RATED), MovieCategory.TOP_RATED)
        getMoviesUseCase.execute(MoviesSubscriber(MovieCategory.NOW_PLAYING), MovieCategory.NOW_PLAYING)
    }


    inner class MoviesSubscriber(private val movieCategory: MovieCategory) : DisposableSingleObserver<List<Movie>>() {
        override fun onError(e: Throwable) {
            moviesView.hideMovies()
            moviesView.hideProgress()
            moviesView.hideEmptyState()
            moviesView.showErrorState()
        }

        override fun onSuccess(movies: List<Movie>) {
            handleGetMoviesSuccess(movieCategory, movies)
        }

    }

    private fun handleGetMoviesSuccess(movieCategory: MovieCategory, movies: List<Movie>) {
        moviesView.hideErrorState()

        if (movies.isNotEmpty()) {
            moviesView.hideEmptyState()
            moviesView.hideProgress()
            showMovieList(movieCategory, movies.map { movieMapper.mapToView(it) })
        } else {
            hideMovieList(movieCategory)
            moviesView.showEmptyState()
            moviesView.hideMovies()
        }
    }

    private fun hideMovieList(movieCategory: MovieCategory) {

    }

    private fun showMovieList(movieCategory: MovieCategory, movies: List<MovieView>) {
        when (movieCategory) {
            MovieCategory.TOP_RATED -> moviesView.showTopRatedMovies(movies)
            MovieCategory.NOW_PLAYING -> moviesView.showNowPlayingMovies(movies)
            MovieCategory.UPCOMING -> moviesView.showUpcomingMovies(movies)
            MovieCategory.POPULAR -> moviesView.showPopularMovies(movies)
            MovieCategory.FAVORITE -> TODO()
            MovieCategory.SEEN -> TODO()
        }
    }

}
