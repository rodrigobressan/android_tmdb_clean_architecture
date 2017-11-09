package com.rodrigobresan.cache.movie_detail.mapper.entity

import com.rodrigobresan.cache.base.mapper.entity.EntityMapper
import com.rodrigobresan.cache.movie_detail.model.MovieDetailCached
import com.rodrigobresan.data.movie_detail.model.MovieDetailEntity
import javax.inject.Inject

open class MovieDetailEntityMapper @Inject constructor() : EntityMapper<MovieDetailCached, MovieDetailEntity> {
    override fun mapFromCached(cached: MovieDetailCached): MovieDetailEntity {
        return MovieDetailEntity(cached.id, cached.title, cached.voteAverage, cached.posterPath,
                cached.backdropPath, cached.overview, cached.tagline)
    }

    override fun mapToCached(entity: MovieDetailEntity): MovieDetailCached {
        return MovieDetailCached(entity.id, entity.title, entity.voteAverage, entity.posterPath,
                entity.backdropPath, entity.overview, entity.tagline)
    }
}