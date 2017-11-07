package com.rodrigobresan.presentation.movies.factory

import com.rodrigobresan.base.DataFactory
import com.rodrigobresan.domain.movie_detail.model.MovieDetail
import com.rodrigobresan.domain.movies.model.Movie

class MovieDetailFactory {

    companion object Factory {
        fun makeMovieDetail(): MovieDetail {
            return MovieDetail(DataFactory.randomLong(), DataFactory.randomUuid(),
                    DataFactory.randomDouble(), DataFactory.randomUuid(), DataFactory.randomUuid())
        }
    }
}