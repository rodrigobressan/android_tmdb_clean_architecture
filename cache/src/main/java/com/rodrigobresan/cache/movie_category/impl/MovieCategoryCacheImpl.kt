package com.rodrigobresan.cache.movie_category.impl

import android.database.sqlite.SQLiteDatabase
import com.rodrigobresan.cache.PreferencesHelper
import com.rodrigobresan.cache.db.DbOpenHelper
import com.rodrigobresan.cache.movie.mapper.db.MovieCategoryCacheDbMapper
import com.rodrigobresan.cache.movie_category.MovieCategoryQueries
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
class MovieCategoryCacheImpl @Inject constructor(dbOpenHelper: DbOpenHelper,
                                                 private val cacheDbMapper: MovieCategoryCacheDbMapper,
                                                 private val cacheMapper: MovieCategoryCacheMapper,
                                                 private val preferences: PreferencesHelper) : MovieCategoryCache {

    private val CACHE_EXPIRATION_TIME = (60 * 10 * 1000)

    private var database: SQLiteDatabase = dbOpenHelper.writableDatabase

    /**
     * Returns the database instance. Mostly used for testing
     */
    fun getDatabase(): SQLiteDatabase {
        return database
    }

    override fun hasMovieInCategory(movieId: Long, category: Category): Boolean {
        val query = MovieCategoryQueries.getQueryForMovie(movieId, category.name)

        val cursor = database.rawQuery(query, null)
        cursor.moveToFirst()
        cursor.close()

        return cursor.count > 0
    }

    override fun getMovieCategories(): Single<List<MovieCategoryEntity>> {
        return Single.defer<List<MovieCategoryEntity>> {

            val query = MovieCategoryQueries.MovieCategoryTable.SELECT_ALL

            val updatesCursor = database.rawQuery(query, null)
            val movieCategories = mutableListOf<MovieCategoryEntity>()

            while (updatesCursor.moveToNext()) {
                val cachedMovieCategory = cacheDbMapper.fromCursor(updatesCursor)
                movieCategories.add(cacheMapper.mapFromCached(cachedMovieCategory))
            }

            updatesCursor.close()
            Single.just(movieCategories)
        }
    }

    override fun deleteMovieFromCategory(movieCategoryEntity: MovieCategoryEntity): Completable {
        return Completable.defer {
            database.beginTransaction()

            try {
                database.delete(MovieCategoryQueries.MovieCategoryTable.TABLE_NAME,
                        MovieCategoryQueries.MovieCategoryTable.CATEGORY_ID + "=? AND " +
                                MovieCategoryQueries.MovieCategoryTable.MOVIE_ID + "=? ",
                        arrayOf(movieCategoryEntity.categoryId, movieCategoryEntity.movieId.toString()))
                database.setTransactionSuccessful()
            } finally {
                database.endTransaction()
            }

            Completable.complete()
        }
    }

    override fun clearMovieCategories(): Completable {
        return Completable.defer {
            database.beginTransaction()

            try {
                database.delete(MovieCategoryQueries.MovieCategoryTable.TABLE_NAME, null, null)
                database.setTransactionSuccessful()
            } finally {
                database.endTransaction()
            }

            Completable.complete()
        }
    }

    override fun saveMovieCategory(movieCategoryEntity: MovieCategoryEntity): Completable {

        return Completable.defer {
            database.beginTransaction()

            try {
                val cachedMovieCategory = cacheMapper.mapToCached(movieCategoryEntity)
                val valuesMovieCategory = cacheDbMapper.toContentValues(cachedMovieCategory)

                database.insert(MovieCategoryQueries.MovieCategoryTable.TABLE_NAME, null, valuesMovieCategory)
                database.setTransactionSuccessful()
            } finally {
                database.endTransaction()
            }

            Completable.complete()
        }
    }

    override fun isCached(): Boolean {
        return database.rawQuery(MovieCategoryQueries.MovieCategoryTable.SELECT_ALL, null).count > 0
    }

    override fun updateLastCacheTime() {
        preferences.updateLastCacheTime(MovieCategoryQueries.MovieCategoryTable.TABLE_NAME)
    }

    override fun isExpired(): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastUpdate = this.preferences.getLastCacheTime(MovieCategoryQueries.MovieCategoryTable.TABLE_NAME)

        return currentTime - lastUpdate > CACHE_EXPIRATION_TIME
    }

}