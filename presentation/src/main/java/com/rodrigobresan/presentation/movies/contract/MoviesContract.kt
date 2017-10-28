package com.rodrigobresan.presentation.movies.contract

import com.rodrigobresan.domain.model.MovieCategory
import com.rodrigobresan.presentation.base.BasePresenter
import com.rodrigobresan.presentation.base.BaseView
import com.rodrigobresan.presentation.movies.model.MovieView

interface MoviesContract {
    interface View : BaseView<Presenter> {
        fun showProgress(category: MovieCategory)
        fun hideProgress(category: MovieCategory)

        fun showErrorState(category: MovieCategory)
        fun hideErrorState(category: MovieCategory)

        fun showEmptyState(category: MovieCategory)
        fun hideEmptyState(category: MovieCategory)

        fun showMovies(category: MovieCategory, movies: List<MovieView>)
        fun hideMovies(category: MovieCategory)
    }

    interface Presenter : BasePresenter {
        fun loadMovies()
    }
}