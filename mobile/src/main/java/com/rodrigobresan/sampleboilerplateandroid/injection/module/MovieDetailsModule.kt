package com.rodrigobresan.sampleboilerplateandroid.injection.module

import android.app.Application
import android.content.Context
import com.rodrigobresan.data.connection.ConnectionStatus
import com.rodrigobresan.domain.movie_detail.interactor.GetMovieDetails
import com.rodrigobresan.presentation.movie_details.contract.MovieDetailsContract
import com.rodrigobresan.presentation.movie_details.mapper.MovieDetailsMapper
import com.rodrigobresan.presentation.movie_details.presenter.MovieDetailsPresenter
import com.rodrigobresan.presentation.movies.mapper.MovieMapper
import com.rodrigobresan.presentation.movies.presenter.MoviesPresenter
import com.rodrigobresan.sampleboilerplateandroid.injection.scope.PerActivity
import com.rodrigobresan.sampleboilerplateandroid.injection.scope.PerApplication
import com.rodrigobresan.sampleboilerplateandroid.movie_detail.ui.activity.MovieDetailActivity
import com.rodrigobresan.sampleboilerplateandroid.movies.ui.activity.MoviesActivity
import com.rodrigobresan.sampleboilerplateandroid.util.ConnectionUtils
import dagger.Module
import dagger.Provides


@Module
open class MovieDetailsModule {

//    @Provides
//    @PerApplication
//    fun provideContext(application: Application): Context {
//        return application
//    }
//
//    @Provides
//    @PerApplication
//    fun provideConnectionStatus(application: Application): ConnectionStatus {
//        return ConnectionUtils(application)
//    }

    @PerActivity
    @Provides
    internal fun provideMovieDetailsView(moviesView: MovieDetailActivity): MovieDetailsContract.View {
        return moviesView
    }

    @PerActivity
    @Provides
    internal fun provideMovieDetailsPresenter(
            connectionStatus: ConnectionStatus,
            movieDetailsView: MovieDetailsContract.View,
            getMovieDetails: GetMovieDetails,
            movieMapper: MovieDetailsMapper): MovieDetailsContract.Presenter {

        return MovieDetailsPresenter(connectionStatus, movieDetailsView, getMovieDetails, movieMapper)
    }
}