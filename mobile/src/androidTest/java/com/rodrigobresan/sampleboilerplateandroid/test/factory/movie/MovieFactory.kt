package com.rodrigobresan.sampleboilerplateandroid.test.factory.movie

import com.rodrigobresan.base.DataFactory
import com.rodrigobresan.domain.movies.model.Movie

class MovieFactory {

    companion object Factory {
        fun makeMovieList(count: Int): List<Movie> {
            val movies = mutableListOf<Movie>()

            repeat(count) {
                movies.add(makeMovie())
            }

            return movies
        }

        fun makeMovie(): Movie {
            return Movie(DataFactory.randomLong(), DataFactory.randomUuid(),
                    DataFactory.randomDouble(), DataFactory.randomUuid())
        }
    }
}