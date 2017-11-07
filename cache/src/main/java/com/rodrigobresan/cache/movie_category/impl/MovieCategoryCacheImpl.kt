package com.rodrigobresan.cache.movie_category.impl

import android.database.sqlite.SQLiteDatabase
import com.rodrigobresan.cache.PreferencesHelper
import com.rodrigobresan.cache.db.DbOpenHelper
import com.rodrigobresan.cache.movie.MovieQueries
import com.rodrigobresan.cache.movie.mapper.db.MovieCategoryDbMapper
import com.rodrigobresan.cache.movie_category.MovieCategoryQueries
import com.rodrigobresan.cache.movie_category.mapper.entity.MovieCategoryEntityMapper
import com.rodrigobresan.data.movie_category.model.MovieCategoryEntity
import com.rodrigobresan.data.movie_category.sources.MovieCategoryCache
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Movie Category Cache contract implementation
 */
class MovieCategoryCacheImpl @Inject constructor(dbOpenHelper: DbOpenHelper,
                                                 private val dbMapper: MovieCategoryDbMapper,
                                                 private val entityMapper: MovieCategoryEntityMapper,
                                                 private val preferences: PreferencesHelper) : MovieCategoryCache {

    private val CACHE_EXPIRATION_TIME = (60 * 10 * 1000)

    private var database: SQLiteDatabase = dbOpenHelper.writableDatabase

    /**
     * Returns the database instance. Mostly used for testing
     */
    fun getDatabase(): SQLiteDatabase {
        return database
    }

    override fun getCategories(): Single<List<MovieCategoryEntity>> {
        return Single.defer<List<MovieCategoryEntity>> {

            val query = MovieCategoryQueries.MovieCategoryTable.SELECT_ALL

            val updatesCursor = database.rawQuery(query, null)
            val movieCategories = mutableListOf<MovieCategoryEntity>()

            while (updatesCursor.moveToNext()) {
                val cachedMovieCategory = dbMapper.fromCursor(updatesCursor)
                movieCategories.add(entityMapper.mapFromCached(cachedMovieCategory))
            }

            updatesCursor.close()
            Single.just(movieCategories)
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
                val cachedMovieCategory = entityMapper.mapToCached(movieCategoryEntity)
                val valuesMovieCategory = dbMapper.toContentValues(cachedMovieCategory)

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