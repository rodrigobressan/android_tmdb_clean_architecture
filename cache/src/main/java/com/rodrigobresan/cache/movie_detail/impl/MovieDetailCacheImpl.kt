package com.rodrigobresan.cache.movie_detail.impl

import com.rodrigobresan.cache.PreferencesHelper
import com.rodrigobresan.cache.movie_detail.MovieDetailQueries
import com.rodrigobresan.cache.movie_detail.dao.MovieDetailsDao
import com.rodrigobresan.cache.movie_detail.mapper.entity.MovieDetailCacheMapper
import com.rodrigobresan.data.movie_detail.model.MovieDetailEntity
import com.rodrigobresan.data.movie_detail.sources.data_store.local.MovieDetailCache
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class MovieDetailCacheImpl @Inject constructor(
        private val movieDetailsDao: MovieDetailsDao,
        private val movieDetailCacheMapper: MovieDetailCacheMapper,
        private val preferencesHelper: PreferencesHelper) : MovieDetailCache {

    private val CACHE_EXPIRATION_TIME = (60 * 10 * 1000)

    override fun clearMovieDetails(): Completable {
        return Completable.defer {
            Completable.complete()
        }
    }

    override fun saveMovieDetails(movie: MovieDetailEntity): Completable {
        return Completable.defer {
            movieDetailsDao.insertMovie(movieDetailCacheMapper.mapToCached(movie))
            Completable.complete()
        }
    }

    override fun getMovieDetails(movieId: Long): Single<MovieDetailEntity> {
        return Single.defer {
            val movieCached = movieDetailsDao.getMovieDetails(movieId)
            Single.just(movieDetailCacheMapper.mapFromCached(movieCached))
        }
    }

    override fun isMovieCached(movieId: Long): Boolean {
        return movieDetailsDao.getMovieDetails(movieId) != null
    }

    override fun setLastCacheTime(lastCacheTime: Long) {
        preferencesHelper.updateLastCacheTime(MovieDetailQueries.MovieDetailTable.TABLE_NAME)
    }

    override fun isExpired(): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastUpdate = this.preferencesHelper.getLastCacheTime(MovieDetailQueries.MovieDetailTable.TABLE_NAME)

        return currentTime - lastUpdate > CACHE_EXPIRATION_TIME
    }

}