package com.rodrigobresan.data.movie.mapper

import com.rodrigobresan.data.base.mapper.DataMapper
import com.rodrigobresan.data.movie.model.MovieEntity
import com.rodrigobresan.domain.movies.model.Movie
import javax.inject.Inject

/**
 * Mapper for movie entity from data to domain layer
 */
open class MovieMapper @Inject constructor(): DataMapper<MovieEntity, Movie> {
    override fun mapFromEntity(entity: MovieEntity): Movie {
        return Movie(entity.id, entity.title, entity.rating, entity.posterPath)
    }

    override fun mapToEntity(model: Movie): MovieEntity {
        return MovieEntity(model.id, model.title, model.rating, model.posterPath)
    }
}