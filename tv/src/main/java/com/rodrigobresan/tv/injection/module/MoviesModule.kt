package com.rodrigobresan.tv.injection.module

import com.rodrigobresan.domain.movies.interactor.GetMovies
import com.rodrigobresan.presentation.movies.contract.MoviesContract
import com.rodrigobresan.presentation.movies.mapper.MovieMapper
import com.rodrigobresan.presentation.movies.presenter.MoviesPresenter
import com.rodrigobresan.tv.injection.scope.PerActivity
import com.rodrigobresan.tv.movies.MoviesFragment
import dagger.Module
import dagger.Provides

@Module
open class MoviesModule {

    @PerActivity
    @Provides
    internal fun provideMovieView(moviesView: MoviesFragment): MoviesContract.View {
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