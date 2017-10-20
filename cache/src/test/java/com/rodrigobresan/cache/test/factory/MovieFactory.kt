package com.rodrigobresan.cache.test.factory

import com.rodrigobresan.cache.model.MovieCached
import com.rodrigobresan.data.model.MovieEntity

class MovieFactory {
    companion object Factory {

        fun makeCachedMovie(): MovieCached {
            return MovieCached(DataFactory.randomLong(), DataFactory.randomUuid(),
                    DataFactory.randomDouble(), DataFactory.randomUuid())
        }

        fun makeMovieEntity(): MovieEntity {
            return MovieEntity(DataFactory.randomLong(), DataFactory.randomUuid(),
                    DataFactory.randomDouble(), DataFactory.randomUuid())
        }

        fun makeMovieEntityList(count: Int): List<MovieEntity> {
            val movieEntities = mutableListOf<MovieEntity>()
            repeat(count) {
                movieEntities.add(makeMovieEntity())
            }

            return movieEntities
        }

        fun makeCachedMoviesList(count: Int): List<MovieCached> {
            val cachedMovies = mutableListOf<MovieCached>()
            repeat(count) {
                cachedMovies.add(makeCachedMovie())
            }

            return cachedMovies
        }

    }
}