package com.rodrigobresan.domain.test.factory

import com.rodrigobresan.domain.model.Movie

class MovieFactory {
    companion object Factory {
        fun makeMovie() : Movie {
            return Movie(DataFactory.randomLong(), DataFactory.randomUuid(),
                    DataFactory.randomDouble(), DataFactory.randomUuid())
        }

        fun makeMovieList(count : Int) : List<Movie> {
            val movieList = mutableListOf<Movie>()
            repeat(count) {
                movieList.add(makeMovie())
            }

            return movieList
        }
    }
}