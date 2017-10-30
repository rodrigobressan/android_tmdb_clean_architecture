package com.rodrigobresan.cache.mapper

import com.rodrigobresan.cache.base.mapper.entity.EntityMapper
import com.rodrigobresan.cache.movie.model.MovieCached
import com.rodrigobresan.data.model.MovieEntity
import javax.inject.Inject

open class MovieEntityMapper @Inject constructor() : EntityMapper<MovieCached, MovieEntity> {

    override fun mapFromCached(cached: MovieCached): MovieEntity {
        return MovieEntity(cached.id, cached.title, cached.rating, cached.picture)
    }

    override fun mapToCached(entity: MovieEntity): MovieCached {
        return MovieCached(entity.id, entity.title, entity.rating, entity.posterPath)
    }

}