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

        fun showMovies(movies: List<MovieView>)
        fun hideMovies()
    }

    interface Presenter : BasePresenter {
        fun loadMovies()
    }
}