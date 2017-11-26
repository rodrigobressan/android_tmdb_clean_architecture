package com.rodrigobresan.cache.movie_category.mapper.entity

import com.rodrigobresan.cache.base.mapper.entity.EntityMapper
import com.rodrigobresan.cache.movie_category.model.MovieCategoryCached
import com.rodrigobresan.data.movie_category.model.MovieCategoryEntity
import javax.inject.Inject

/**
 * Mapper for MovieCategoryEntity. Maps MovieCategoryCached <-> MovieCategoryEntity
 */
open class MovieCategoryCacheMapper @Inject constructor() : EntityMapper<MovieCategoryCached, MovieCategoryEntity> {

    override fun mapFromCached(cached: MovieCategoryCached): MovieCategoryEntity {
        return MovieCategoryEntity(cached.movieId, cached.categoryId)
    }

    override fun mapToCached(entity: MovieCategoryEntity): MovieCategoryCached {
        val movieCategoryCached = MovieCategoryCached()
        movieCategoryCached.movieId = entity.movieId
        movieCategoryCached.categoryId = entity.categoryId
        return movieCategoryCached
    }

}