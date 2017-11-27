package com.rodrigobresan.cache.test.factory

import com.rodrigobresan.base.DataFactory
import com.rodrigobresan.cache.movie_detail.model.MovieDetailsCached
import com.rodrigobresan.data.movie_detail.model.MovieDetailEntity

class MovieDetailFactory {

    companion object Factory {

        fun makeMovieDetailCached(): MovieDetailsCached {
            return MovieDetailsCached(DataFactory.randomLong(), DataFactory.randomUuid(),
                    DataFactory.randomDouble(), DataFactory.randomUuid(), DataFactory.randomUuid(),
                    DataFactory.randomUuid(), DataFactory.randomUuid())
        }

        fun makeMovieDetailEntity(): MovieDetailEntity {
            return MovieDetailEntity(DataFactory.randomLong(), DataFactory.randomUuid(),
                    DataFactory.randomDouble(), DataFactory.randomUuid(), DataFactory.randomUuid(),
                    DataFactory.randomUuid(), DataFactory.randomUuid())
        }
    }
}