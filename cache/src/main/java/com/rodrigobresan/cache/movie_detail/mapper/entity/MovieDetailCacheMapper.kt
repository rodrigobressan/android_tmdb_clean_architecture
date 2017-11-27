package com.rodrigobresan.cache.movie_detail.mapper.entity

import com.rodrigobresan.cache.base.mapper.entity.EntityMapper
import com.rodrigobresan.cache.movie_detail.model.MovieDetailsCached
import com.rodrigobresan.data.movie_detail.model.MovieDetailEntity
import javax.inject.Inject

open class MovieDetailCacheMapper @Inject constructor() : EntityMapper<MovieDetailsCached, MovieDetailEntity> {
    override fun mapFromCached(cached: MovieDetailsCached): MovieDetailEntity {
        return MovieDetailEntity(cached.id, cached.title, cached.voteAverage, cached.posterPath,
                cached.backdropPath, cached.overview, cached.tagline)
    }

    override fun mapToCached(entity: MovieDetailEntity): MovieDetailsCached {
        val movieDetails = MovieDetailsCached(entity.title, entity.voteAverage, entity.posterPath,
                entity.backdropPath, entity.overview, entity.tagline)
        movieDetails.id = entity.id

        return movieDetails
    }
}