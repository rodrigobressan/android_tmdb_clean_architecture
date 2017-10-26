package com.rodrigobresan.presentation.movies.contract

import com.rodrigobresan.presentation.base.BasePresenter
import com.rodrigobresan.presentation.base.BaseView
import com.rodrigobresan.presentation.movies.model.MovieView

interface MoviesContract {
    interface View : BaseView<Presenter> {
        fun showProgress()
        fun hideProgress()

        fun showErrorState()
        fun hideErrorState()

        fun showEmptyState()
        fun hideEmptyState()

        fun showPopularMovies(movies: List<MovieView>)
        fun showTopRatedMovies(movies: List<MovieView>)
        fun showUpcomingMovies(movies: List<MovieView>)
        fun showNowPlayingMovies(movies: List<MovieView>)

        fun hideMovies()
    }

    interface Presenter : BasePresenter {
        fun loadMovies()
    }
}