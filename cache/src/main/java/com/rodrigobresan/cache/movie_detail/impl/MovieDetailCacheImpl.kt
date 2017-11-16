package com.rodrigobresan.cache.movie_detail.impl

import android.database.sqlite.SQLiteDatabase
import com.rodrigobresan.cache.PreferencesHelper
import com.rodrigobresan.cache.db.DbOpenHelper
import com.rodrigobresan.cache.movie_detail.MovieDetailQueries
import com.rodrigobresan.cache.movie_detail.mapper.db.MovieDetailDbMapper
import com.rodrigobresan.cache.movie_detail.mapper.entity.MovieDetailEntityMapper
import com.rodrigobresan.data.movie_detail.model.MovieDetailEntity
import com.rodrigobresan.data.movie_detail.sources.data_store.local.MovieDetailCache
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class MovieDetailCacheImpl @Inject constructor(dbOpenHelper: DbOpenHelper,
                                               private val movieDetailEntityMapper: MovieDetailEntityMapper,
                                               private val movieDetailDbMapper: MovieDetailDbMapper,
                                               private val preferencesHelper: PreferencesHelper) : MovieDetailCache {

    private val CACHE_EXPIRATION_TIME = (60 * 10 * 1000)

    private var database: SQLiteDatabase = dbOpenHelper.writableDatabase

    /**
     * Return the current database. Used mostly for test purposes
     */
    fun getDatabase(): SQLiteDatabase {
        return database
    }

    override fun clearMovieDetails(): Completable {
        return Completable.defer {
            database.beginTransaction()

            try {
                database.delete(MovieDetailQueries.MovieDetailTable.TABLE_NAME, null, null)
                database.setTransactionSuccessful()
            } finally {
                database.endTransaction()
            }

            Completable.complete()
        }
    }

    override fun saveMovieDetails(movie: MovieDetailEntity): Completable {
        return Completable.defer {
            database.beginTransaction()

            try {
                var cachedMovie = movieDetailEntityMapper.mapToCached(movie)
                var contentValuesMovie = movieDetailDbMapper.toContentValues(cachedMovie)
                database.insertWithOnConflict(MovieDetailQueries.MovieDetailTable.TABLE_NAME, null,
                        contentValuesMovie, SQLiteDatabase.CONFLICT_REPLACE)
                database.setTransactionSuccessful()
            } finally {
                database.endTransaction()
            }

            Completable.complete()
        }
    }

    override fun getMovieDetails(movieId: Long): Single<MovieDetailEntity> {
        return Single.defer {
            val query = MovieDetailQueries.getQueryForMovieDetail(movieId)
            val cursor = database.rawQuery(query, null)

            cursor.moveToFirst()

            if (cursor.count > 0) {
                var cachedMovie = movieDetailDbMapper.fromCursor(cursor)
                var entityMovie = movieDetailEntityMapper.mapFromCached(cachedMovie)
                cursor.close()
                Single.just(entityMovie)
            } else {
                cursor.close()
                Single.just(null)
            }
        }
    }

    override fun isMovieCached(movieId: Long): Boolean {
        return database.rawQuery(MovieDetailQueries.getQueryForMovieDetail(movieId), null).count > 0
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