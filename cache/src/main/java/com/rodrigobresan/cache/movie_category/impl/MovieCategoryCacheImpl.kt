package com.rodrigobresan.cache.movie_category.impl

import com.rodrigobresan.cache.movie_category.dao.MovieCategoryDao
import com.rodrigobresan.cache.movie_category.mapper.entity.MovieCategoryCacheMapper
import com.rodrigobresan.data.movie_category.model.MovieCategoryEntity
import com.rodrigobresan.data.movie_category.sources.MovieCategoryCache
import com.rodrigobresan.domain.movie_category.model.Category
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Movie Category Cache contract implementation
 */
class MovieCategoryCacheImpl @Inject constructor(
        private val movieCategoryDao: MovieCategoryDao,
        private val cacheMapper: MovieCategoryCacheMapper) : MovieCategoryCache {


    override fun hasMovieInCategory(movieId: Long, category: Category): Boolean {
        val movieCategoryList = movieCategoryDao.getMovieInCategory(movieId, category.name)
        return movieCategoryList.size > 0
    }

    override fun getMovieCategories(): Single<List<MovieCategoryEntity>> {
        return Single.defer<List<MovieCategoryEntity>> {

            val movieCategories = movieCategoryDao.getMovieCategories()
            Single.just(movieCategories.map { cacheMapper.mapFromCached(it) })
        }
    }

    override fun deleteMovieFromCategory(movieCategoryEntity: MovieCategoryEntity): Completable {
        return Completable.defer {
            movieCategoryDao.delete(cacheMapper.mapToCached(movieCategoryEntity))
            Completable.complete()
        }
    }

    override fun clearMovieCategories(): Completable {
        return Completable.defer {
            Completable.complete()
        }
    }

    override fun saveMovieCategory(movieCategoryEntity: MovieCategoryEntity): Completable {
        return Completable.defer {
            movieCategoryDao.insert(cacheMapper.mapToCached(movieCategoryEntity))
            Completable.complete()
        }
    }
}