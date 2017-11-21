package com.rodrigobresan.tv.injection.component

import com.rodrigobresan.tv.movies.MoviesFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent
interface MoviesSubComponent : AndroidInjector<MoviesFragment> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<MoviesFragment>()
}