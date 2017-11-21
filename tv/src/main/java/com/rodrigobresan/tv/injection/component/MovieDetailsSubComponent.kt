package com.rodrigobresan.tv.injection.component

import com.rodrigobresan.tv.movie_details.MovieDetailsFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent
interface MovieDetailsSubComponent : AndroidInjector<MovieDetailsFragment> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<MovieDetailsFragment>()
}