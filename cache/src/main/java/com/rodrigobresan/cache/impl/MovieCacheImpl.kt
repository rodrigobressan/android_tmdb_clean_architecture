package com.rodrigobresan.cache.impl

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.rodrigobresan.cache.PreferencesHelper
import com.rodrigobresan.cache.db.DbOpenHelper
import com.rodrigobresan.cache.db.constants.DbConstants
import com.rodrigobresan.cache.db.mapper.movie.MovieDbMapper
import com.rodrigobresan.cache.mapper.MovieEntityMapper
import com.rodrigobresan.domain.model.MovieCategory
import com.rodrigobresan.data.model.MovieEntity
import com.rodrigobresan.data.repository.movie.movie.MovieCache
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

    override fun saveMovies(movieCategory: MovieCategory, movies: List<MovieEntity>): Completable {
        return Completable.defer {
            database.beginTransaction()

            try {
                movies.forEach {
                    insertMovie(it)
                    insertCategory(movieCategory)
                    insertMovieCategory(movieCategory, it)
                }
                database.setTransactionSuccessful()
            } finally {
                database.endTransaction()
            }

            Completable.complete()
        }
    }

    private fun insertMovie(it: MovieEntity) {
        database.insert(DbConstants.MovieTable.TABLE_NAME, null,
                movieDbMapper.toContentValues(movieEntityMapper.mapToCached(it)))
    }

    private fun insertCategory(movieCategory: MovieCategory) {
        val values = ContentValues()
        values.put(DbConstants.CategoryTable.CATEGORY_ID, movieCategory.getId())

        database.insert(DbConstants.CategoryTable.TABLE_NAME, null, values)
    }

    // TODO use mapper
    private fun insertMovieCategory(movieCategory: MovieCategory, it: MovieEntity) {
        val values = ContentValues()
        values.put(DbConstants.MovieCategoryTable.MOVIE_ID, it.id)
        values.put(DbConstants.MovieCategoryTable.CATEGORY_ID, movieCategory.getId())

        database.insert(DbConstants.MovieCategoryTable.TABLE_NAME, null,
                values)
    }

    override fun getMovies(movieCategory: MovieCategory): Single<List<MovieEntity>> {
        return Single.defer<List<MovieEntity>> {

            val query = "SELECT * FROM " + DbConstants.MovieTable.TABLE_NAME +
                    " INNER JOIN " + DbConstants.MovieCategoryTable.TABLE_NAME +
                    " ON " +
                    DbConstants.MovieCategoryTable.TABLE_NAME + "." + DbConstants.MovieCategoryTable.MOVIE_ID +
                    " = " + DbConstants.MovieTable.TABLE_NAME + "." + DbConstants.MovieTable.MOVIE_ID +
                    " WHERE " +
                    DbConstants.MovieCategoryTable.TABLE_NAME + "." + DbConstants.MovieCategoryTable.CATEGORY_ID +
                    " = " + movieCategory.getId()

            val updatesCursor = database.rawQuery(query, null)
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