package com.rodrigobresan.data.mapper

import com.rodrigobresan.data.model.MovieEntity
import com.rodrigobresan.domain.model.Movie
import javax.inject.Inject

open class MovieMapper @Inject constructor(): DataMapper<MovieEntity, Movie> {
    override fun mapFromEntity(type: MovieEntity): Movie {
        return Movie(type.id, type.title, type.rating, type.posterPath)
    }

    override fun mapToEntity(type: Movie): MovieEntity {
        return MovieEntity(type.id, type.title, type.rating, type.posterPath)
    }
}