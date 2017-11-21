package com.rodrigobresan.data.test.factory

import com.rodrigobresan.base.DataFactory
import com.rodrigobresan.data.movie_detail.model.MovieDetailEntity
import com.rodrigobresan.domain.movie_detail.model.MovieDetail

class MovieDetailFactory {
    companion object Factory {

        fun makeMovieDetailEntity(): MovieDetailEntity {
            return MovieDetailEntity(DataFactory.randomLong(), DataFactory.randomUuid(),
                    DataFactory.randomDouble(), DataFactory.randomUuid(), DataFactory.randomUuid(),
                    DataFactory.randomUuid(), DataFactory.randomUuid())
        }

        fun makeMovieDetail(): MovieDetail {
            return MovieDetail(DataFactory.randomLong(), DataFactory.randomUuid(),
                    DataFactory.randomDouble(), DataFactory.randomUuid(), DataFactory.randomUuid(),
                    DataFactory.randomUuid(), DataFactory.randomUuid(), DataFactory.randomBoolean())
        }
    }
}