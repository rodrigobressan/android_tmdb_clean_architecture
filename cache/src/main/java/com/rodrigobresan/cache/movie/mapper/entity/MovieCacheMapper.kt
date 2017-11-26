package com.rodrigobresan.cache.mapper

import com.rodrigobresan.cache.base.mapper.entity.EntityMapper
import com.rodrigobresan.cache.movie.model.MovieCached
import com.rodrigobresan.data.movie.model.MovieEntity
import javax.inject.Inject

/**
 * Mapper for movie. MovieCached <-> MovieEntity
 */
open class MovieCacheMapper @Inject constructor() : EntityMapper<MovieCached, MovieEntity> {

    override fun mapFromCached(cached: MovieCached): MovieEntity {
        return MovieEntity(cached.id, cached.title, cached.rating, cached.picture)
    }

    override fun mapToCached(entity: MovieEntity): MovieCached {
        val movieCached = MovieCached(entity.title, entity.rating, entity.posterPath)
        movieCached.id = entity.id

        return movieCached
    }

}