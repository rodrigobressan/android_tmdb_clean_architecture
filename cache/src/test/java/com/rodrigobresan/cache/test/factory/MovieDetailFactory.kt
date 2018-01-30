package com.rodrigobresan.cache.test.factory

import com.rodrigobresan.base.DataFactory
import com.rodrigobresan.cache.movie_detail.model.MovieDetailsCached
import com.rodrigobresan.data.movie_detail.model.MovieDetailEntity

class MovieDetailFactory {

    companion object Factory {

        fun makeMovieDetailCachedList(count: Int): List<MovieDetailsCached> {
            val listMovieDetails = arrayListOf<MovieDetailsCached>()

            (1..count).forEach {
                val movie = makeMovieDetailCached()
                movie.id = it.toLong()

                listMovieDetails.add(movie)
            }

            return listMovieDetails
        }

        fun makeMovieDetailCached(): MovieDetailsCached {
            return MovieDetailsCached(DataFactory.randomUuid(),
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