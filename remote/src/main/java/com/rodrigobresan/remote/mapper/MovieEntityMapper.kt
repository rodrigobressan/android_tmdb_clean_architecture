package com.rodrigobresan.remote.mapper

import com.rodrigobresan.data.model.MovieEntity
import com.rodrigobresan.remote.model.response.movies_list.MovieModel
import javax.inject.Inject

open class MovieEntityMapper @Inject constructor() : EntityMapper<MovieModel, MovieEntity> {
    override fun mapRemoteToEntity(movie: MovieModel): MovieEntity {
        return MovieEntity(movie.id, movie.title, movie.voteAverage, movie.posterPath)
    }

}