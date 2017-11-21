package com.rodrigobresan.presentation.movies.factory

import com.rodrigobresan.base.DataFactory
import com.rodrigobresan.domain.movies.model.Movie

class MovieFactory {

    companion object Factory {
        fun makeMovie(): Movie {
            return Movie(DataFactory.randomLong(), DataFactory.randomUuid(),
                    DataFactory.randomDouble(), DataFactory.randomUuid(), DataFactory.randomBoolean())
        }

        fun makeMovieList(count: Int): List<Movie> {
            val movieList = mutableListOf<Movie>()
            repeat(count) {
                movieList.add(makeMovie())
            }

            return movieList
        }
    }
}