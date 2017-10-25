package com.rodrigobresan.sampleboilerplateandroid.injection.module

import com.rodrigobresan.sampleboilerplateandroid.injection.scope.PerActivity
import com.rodrigobresan.sampleboilerplateandroid.movies.ui.activity.MoviesActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(MoviesModule::class))
    abstract fun bindMoviesActivity(): MoviesActivity

}