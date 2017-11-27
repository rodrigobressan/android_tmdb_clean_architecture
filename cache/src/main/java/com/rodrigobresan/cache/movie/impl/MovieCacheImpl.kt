package com.rodrigobresan.cache.movie.impl

import com.rodrigobresan.cache.PreferencesHelper
import com.rodrigobresan.cache.mapper.MovieCacheMapper
import com.rodrigobresan.cache.movie.MovieQueries.MovieTable
import com.rodrigobresan.cache.movie.dao.MovieDao
import com.rodrigobresan.data.movie.model.MovieEntity
import com.rodrigobresan.data.movie.sources.data_store.local.MovieCache
import com.rodrigobresan.domain.movie_category.model.Category
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Movie Cache contract implementation
 */
class MovieCacheImpl @Inject constructor(
        private val movieDao: MovieDao,
        private val movieCacheMapper: MovieCacheMapper,
        private val preferences: PreferencesHelper) : MovieCache {

    private val CACHE_EXPIRATION_TIME = (60 * 10 * 1000)

    override fun clearMovies(): Completable {
        return Completable.defer {
            Completable.complete()
        }
    }

    override fun saveMovies(category: Category, movies: List<MovieEntity>): Completable {
        return Completable.defer {
            movies.forEach {
                insertMovie(it)
            }
            Completable.complete()
        }
    }

    private fun insertMovie(it: MovieEntity) {
        val cachedMovie = movieCacheMapper.mapToCached(it)
        movieDao.insert(cachedMovie)
    }

    override fun getMovie(movieId: Long): Single<MovieEntity> {
        return Single.defer<MovieEntity> {
            val movie = movieDao.getMovieById(movieId)
            Single.just(movieCacheMapper.mapFromCached(movie))
        }
    }

    override fun getMovies(category: Category): Single<List<MovieEntity>> {
        return Single.defer<List<MovieEntity>> {
            val movies = movieDao.getAllMovies(category.name)
            Single.just<List<MovieEntity>>(movies.map { movieCacheMapper.mapFromCached(it) })
        }
    }

    override fun isCached(): Boolean {
        return movieDao.getAllMovies().size > 0
    }

    override fun updateLastCacheTime() {
        preferences.updateLastCacheTime(MovieTable.TABLE_NAME)
    }

    override fun isExpired(): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastUpdate = this.preferences.getLastCacheTime(MovieTable.TABLE_NAME)

        return currentTime - lastUpdate > CACHE_EXPIRATION_TIME
    }

}