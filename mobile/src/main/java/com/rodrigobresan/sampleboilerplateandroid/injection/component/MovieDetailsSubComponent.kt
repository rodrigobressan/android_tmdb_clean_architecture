package com.rodrigobresan.sampleboilerplateandroid.injection.component

import com.rodrigobresan.sampleboilerplateandroid.movie_detail.ui.activity.MovieDetailActivity
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent
interface MovieDetailsSubComponent : AndroidInjector<MovieDetailActivity> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<MovieDetailActivity>()
}