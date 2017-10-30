package com.rodrigobresan.data.test.factory

import com.rodrigobresan.data.movie.model.MovieEntity
import com.rodrigobresan.domain.movies.model.Movie

/**
 * Created by rodrigobresan on 05/10/17.

In case of any questions, feel free to ask me

E-mail: rcbresan@gmail.com
Slack: bresan

 */

class MovieFactory {
    companion object Factory {
        fun makeMovieEntity(): MovieEntity {
            return MovieEntity(DataFactory.randomLong(), DataFactory.randomUuid(),
                    DataFactory.randomDouble(), DataFactory.randomUuid())
        }

        fun makeMovie(): Movie {
            return Movie(DataFactory.randomLong(), DataFactory.randomUuid(),
                    DataFactory.randomDouble(), DataFactory.randomUuid())
        }

        fun makeMovieEntityList(count: Int): List<MovieEntity> {
            val movieEntities = mutableListOf<MovieEntity>()

            repeat(count) {
                movieEntities.add(makeMovieEntity())
            }

            return movieEntities
        }

        fun makeMovieList(count: Int): List<Movie> {
            val movies = mutableListOf<Movie>()

            repeat(count) {
                movies.add(makeMovie())
            }

            return movies
        }
    }
}
