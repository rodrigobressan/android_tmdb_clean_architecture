package com.rodrigobresan.remote.test.factory

import com.rodrigobresan.base.DataFactory
import com.rodrigobresan.remote.movies.model.movies.MovieItem
import com.rodrigobresan.remote.movies.model.movies.MovieResponse

class MovieFactory {
    companion object Factory {

        fun makeMovieModel(): MovieItem {
            return MovieItem(DataFactory.randomLong(), DataFactory.randomUuid(),
                    DataFactory.randomDouble(), DataFactory.randomUuid())
        }

        fun makeMovieResponse(): MovieResponse {
            val response = MovieResponse()
            response.results = makeMovieModelList(3)
            return response
        }

        fun makeMovieModelList(count: Int): List<MovieItem> {
            val movieList = mutableListOf<MovieItem>()
            repeat(count) {
                movieList.add(makeMovieModel())
            }

            return movieList
        }
    }
}