package com.rodrigobresan.presentation.movies.presenter

import com.rodrigobresan.domain.interactor.GetMovies
import com.rodrigobresan.domain.model.Movie
import com.rodrigobresan.presentation.movies.contract.MoviesContract
import com.rodrigobresan.presentation.movies.mapper.MovieMapper
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
        getMoviesUseCase.execute(MoviesSubscriber())
    }


    inner class MoviesSubscriber : DisposableSingleObserver<List<Movie>>() {
        override fun onError(e: Throwable) {
            moviesView.hideMovies()
            moviesView.hideProgress()
            moviesView.hideEmptyState()
            moviesView.showErrorState()
        }

        override fun onSuccess(movies: List<Movie>) {
            handleGetMoviesSuccess(movies)
        }

    }

    private fun handleGetMoviesSuccess(movies: List<Movie>) {
        moviesView.hideErrorState()

        if (movies.isNotEmpty()) {
            moviesView.hideEmptyState()
            moviesView.hideProgress()
            moviesView.showMovies(
                    movies.map {
                        movieMapper.mapToView(it)
                    }
            )
        } else {
            moviesView.showEmptyState()
            moviesView.hideMovies()
        }
    }

}
