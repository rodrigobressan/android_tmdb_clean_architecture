package com.rodrigobresan.presentation.movies.contract

import com.rodrigobresan.domain.movie_category.model.Category
import com.rodrigobresan.presentation.base.BasePresenter
import com.rodrigobresan.presentation.base.BaseView
import com.rodrigobresan.presentation.movies.model.MovieView

/**
 * Interface to define the contract existent on the View and Presenter layers of the Movie section
 */
interface MoviesContract {

    /**
     * Contract for the Movie View
     */
    interface View : BaseView<Presenter> {
        /**
         * Show the progress bar for a specified category
         */
        fun showProgress(category: Category)

        /**
         * Hide the progress bar on the screen for a specified category
         */
        fun hideProgress(category: Category)

        /**
         * Show error state for a specified category
         */
        fun showErrorState(category: Category)

        /**
         * Hide error state for a specified category
         */
        fun hideErrorState(category: Category)

        /**
         * Show empty state for a specified category
         */
        fun showEmptyState(category: Category)

        /**
         * Hide empty state for a specified category
         */
        fun hideEmptyState(category: Category)

        /**
         * Show the list of movies for a specified category
         */
        fun showMovies(category: Category, movies: List<MovieView>)

        /**
         * Hide the list of movies for a specified category
         */
        fun hideMovies(category: Category)
    }

    /**
     * Contract for the Movie Presenter
     */
    interface Presenter : BasePresenter {

        /**
         * Load the movies
         */
        fun loadMovies()
    }
}