package com.rodrigobresan.sampleboilerplateandroid.injection.component

import com.rodrigobresan.sampleboilerplateandroid.movies.ui.activity.MoviesActivity
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent
interface MoviesSubComponent : AndroidInjector<MoviesActivity> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<MoviesActivity>()
}