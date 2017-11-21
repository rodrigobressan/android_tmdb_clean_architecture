package com.rodrigobresan.tv.injection.module

import com.rodrigobresan.tv.injection.scope.PerActivity
import com.rodrigobresan.tv.movie_details.MovieDetailsFragment
import com.rodrigobresan.tv.movies.MoviesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBindingModule {
//
//    @PerActivity
//    @ContributesAndroidInjector(modules = arrayOf(MoviesModule::class))
//    abstract fun bindMoviesActivity(): MoviesActivity

    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(MoviesModule::class))
    abstract fun bindMoviesFragment(): MoviesFragment


    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(MovieDetailsModule::class))
    abstract fun bindMovieDetailsFragment(): MovieDetailsFragment

}