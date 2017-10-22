package com.rodrigobresan.cache.impl

import android.database.sqlite.SQLiteDatabase
import com.rodrigobresan.cache.PreferencesHelper
import com.rodrigobresan.cache.db.DbOpenHelper
import com.rodrigobresan.cache.db.constants.DbConstants
import com.rodrigobresan.cache.db.mapper.MovieDbMapper
import com.rodrigobresan.cache.mapper.MovieEntityMapper
import com.rodrigobresan.data.model.MovieEntity
import com.rodrigobresan.data.repository.movie.MovieCache
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class MovieCacheImpl @Inject constructor(dbOpenHelper: DbOpenHelper,
                                         private val movieEntityMapper: MovieEntityMapper,
                                         private val movieDbMapper: MovieDbMapper,
                                         private val preferences: PreferencesHelper) : MovieCache {

    private val CACHE_EXPIRATION_TIME = (60 * 10 * 1000)

    private var database: SQLiteDatabase = dbOpenHelper.writableDatabase

    fun getDatabase(): SQLiteDatabase {
        return database
    }

    override fun clearMovies(): Completable {
        return Completable.defer {
            database.beginTransaction()
            try {
                database.delete(DbConstants.MovieTable.TABLE_NAME, null, null)
                database.setTransactionSuccessful()
            } finally {
                database.endTransaction()
            }

            Completable.complete()
        }
    }

    override fun saveMovies(movies: List<MovieEntity>): Completable {
        return Completable.defer {
            database.beginTransaction()

            try {
                movies.forEach {
                    database.insert(DbConstants.MovieTable.TABLE_NAME, null,
                            movieDbMapper.toContentValues(movieEntityMapper.mapToCached(it)))
                }
                database.setTransactionSuccessful()
            } finally {
                database.endTransaction()
            }

            Completable.complete()
        }
    }

    override fun getMovies(): Single<List<MovieEntity>> {
        return Single.defer<List<MovieEntity>> {
            val updatesCursor = database.rawQuery(DbConstants.MovieTable.SELECT_ALL, null)
            val movies = mutableListOf<MovieEntity>()

            while (updatesCursor.moveToNext()) {
                val cachedMovie = movieDbMapper.fromCursor(updatesCursor);
                movies.add(movieEntityMapper.mapFromCached(cachedMovie))
            }

            updatesCursor.close()
            Single.just<List<MovieEntity>>(movies)
        }
    }

    override fun isCached(): Boolean {
        return database.rawQuery(DbConstants.MovieTable.SELECT_ALL, null).count > 0
    }

    override fun setLastCacheTime(lastCacheTime: Long) {
        preferences.lastCacheTime = lastCacheTime
    }

    override fun isExpired(): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastUpdate = this.preferences.lastCacheTime

        return currentTime - lastUpdate > CACHE_EXPIRATION_TIME
    }

}