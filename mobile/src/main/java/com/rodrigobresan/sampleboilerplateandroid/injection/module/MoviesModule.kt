package com.rodrigobresan.sampleboilerplateandroid.injection.module

import com.rodrigobresan.domain.movies.interactor.GetMovies
import com.rodrigobresan.presentation.movies.contract.MoviesContract
import com.rodrigobresan.presentation.movies.mapper.MovieMapper
import com.rodrigobresan.presentation.movies.presenter.MoviesPresenter
import com.rodrigobresan.sampleboilerplateandroid.injection.scope.PerActivity
import com.rodrigobresan.sampleboilerplateandroid.movies.ui.activity.MoviesActivity
import com.rodrigobresan.sampleboilerplateandroid.movies.ui.adapter.MoviesAdapter
import dagger.Module
import dagger.Provides

@Module
open class MoviesModule {

    @Provides
    internal fun provideMovieAdapter(moviesView: MoviesActivity): MoviesAdapter {
        return MoviesAdapter(moviesView)
    }

    @PerActivity
    @Provides
    internal fun provideMovieView(moviesView: MoviesActivity): MoviesContract.View {
        return moviesView
    }

    @PerActivity
    @Provides
    internal fun provideMoviePresenter(moviesView: MoviesContract.View,
                                       getMovies: GetMovies,
                                       movieMapper: MovieMapper): MoviesContract.Presenter {
        return MoviesPresenter(moviesView, getMovies, movieMapper)
    }
}