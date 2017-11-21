package com.rodrigobresan.sampleboilerplateandroid.test

import com.rodrigobresan.base.DataFactory
import com.rodrigobresan.presentation.movies.model.MovieView

class MovieFactory {

    companion object Factory {
        fun makeMovieView(): MovieView {
            return MovieView(DataFactory.randomLong(), DataFactory.randomUuid(),
                    DataFactory.randomDouble(), DataFactory.randomUuid(), DataFactory.randomBoolean())
        }
    }
}