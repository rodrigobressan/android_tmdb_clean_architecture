package com.rodrigobresan.remote.test.factory

import com.rodrigobresan.remote.model.response.movies_list.MovieModel
import com.rodrigobresan.remote.model.response.movies_list.MovieResponse

class MovieFactory {
    companion object Factory {

        fun makeMovieModel(): MovieModel {
            return MovieModel(DataFactory.randomLong(), DataFactory.randomUuid(),
                    DataFactory.randomDouble(), DataFactory.randomUuid())
        }

        fun makeMovieResponse(): MovieResponse {
            val response = MovieResponse()
            response.results = makeMovieModelList(3)
            return response
        }

        fun makeMovieModelList(count: Int): List<MovieModel> {
            val movieList = mutableListOf<MovieModel>()
            repeat(count) {
                movieList.add(makeMovieModel())
            }

            return movieList
        }
    }
}