package com.rodrigobresan.sampleboilerplateandroid.injection.module

import com.rodrigobresan.domain.interactor.GetMovies
import com.rodrigobresan.presentation.movies.contract.MoviesContract
import com.rodrigobresan.presentation.movies.mapper.MovieMapper
import com.rodrigobresan.presentation.movies.presenter.MoviesPresenter
import com.rodrigobresan.sampleboilerplateandroid.injection.scope.PerActivity
import com.rodrigobresan.sampleboilerplateandroid.movies.ui.activity.MoviesActivity
import dagger.Module
import dagger.Provides

@Module
open class MoviesModule {

    @PerActivity
    @Provides
    internal fun provideMovieView(moviesActivity: MoviesActivity) : MoviesContract.View {
        return moviesActivity
    }

    @PerActivity
    @Provides
    internal fun provideMoviePresenter(moviesView: MoviesContract.View,
                                       getMovies: GetMovies,
                                       movieMapper: MovieMapper) : MoviesContract.Presenter {
        return MoviesPresenter(moviesView, getMovies, movieMapper)
    }
}