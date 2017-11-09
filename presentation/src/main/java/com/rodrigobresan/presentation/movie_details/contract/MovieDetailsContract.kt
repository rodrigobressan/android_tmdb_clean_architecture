package com.rodrigobresan.presentation.movie_details.contract

import com.rodrigobresan.presentation.base.BasePresenter
import com.rodrigobresan.presentation.base.BaseView
import com.rodrigobresan.presentation.movie_details.model.MovieDetailView

/**
 * Contract for MovieDetails section
 */
interface MovieDetailsContract {

    /**
     * Contract for MovieDetails View Layer
     */
    interface View : BaseView<Presenter> {

        /**
         * Show progress bar
         */
        fun showProgress()

        /**
         * Hide progress bar
         */
        fun hideProgress()

        /**
         * Show error state
         */
        fun showErrorState()

        /**
         * Hide error state
         */
        fun hideErrorState()

        /**
         * Load the movie into the screen
         */
        fun showMovieDetails(movieDetail : MovieDetailView)

        /**
         * Show a message to the user about being offline
         */
        fun showNoConnection()

        fun showEmptyState()

        fun hideEmptyState()
    }

    /**
     * Contract for MovieDetails Presenter
     */
    interface Presenter : BasePresenter {
        /**
         * Method to fetch movie details
         */
        fun loadMovieDetails(movieId: Long)
    }
}