package com.rodrigobresan.presentation.movie_details.presenter

import com.rodrigobresan.domain.movie_detail.interactor.GetMovieDetails
import com.rodrigobresan.domain.movie_detail.model.MovieDetail
import com.rodrigobresan.presentation.movie_details.contract.MovieDetailsContract
import com.rodrigobresan.presentation.movie_details.mapper.MovieDetailsMapper
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

/**
 * Presenter class for MovieDetails section
 */
class MovieDetailsPresenter @Inject constructor(val connectionStatus: com.rodrigobresan.data.connection.ConnectionStatus,
                                                val movieDetailsView: MovieDetailsContract.View,
                                                val getMovieDetails: GetMovieDetails,
                                                val movieDetailsMapper: MovieDetailsMapper) : MovieDetailsContract.Presenter {

    init {
        movieDetailsView.setPresenter(this)
    }

    override fun start() {
    }

    override fun stop() {
        getMovieDetails.dispose()
    }

    override fun loadMovieDetails(movieId: Long) {
        movieDetailsView.showProgress()
        getMovieDetails.execute(MovieDetailsSubscriber(), movieId)
    }

    inner class MovieDetailsSubscriber : DisposableSingleObserver<MovieDetail>() {
        override fun onError(e: Throwable) {
            movieDetailsView.hideProgress()
            movieDetailsView.showErrorState()

            checkConnectionStatus(false)
        }

        override fun onSuccess(movieDetail: MovieDetail) {
            movieDetailsView.hideProgress()
            movieDetailsView.hideErrorState()

            if (movieDetail != null) {
                movieDetailsView.hideEmptyState()
                movieDetailsView.showMovieDetails(movieDetailsMapper.mapToView(movieDetail))
                checkConnectionStatus(true)
            } else {
                checkConnectionStatus(false)
                movieDetailsView.showEmptyState()
            }
        }
    }

    private fun checkConnectionStatus(hasData: Boolean) {
        if (connectionStatus.isOffline()) {
            movieDetailsView.showNoConnection()
        }
    }

}
