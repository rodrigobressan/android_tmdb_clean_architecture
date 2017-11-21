package com.rodrigobresan.cache.movie.impl

import android.database.sqlite.SQLiteDatabase
import com.rodrigobresan.cache.PreferencesHelper
import com.rodrigobresan.cache.db.DbOpenHelper
import com.rodrigobresan.cache.mapper.MovieCacheMapper
import com.rodrigobresan.cache.movie.MovieQueries
import com.rodrigobresan.cache.movie.MovieQueries.MovieTable
import com.rodrigobresan.cache.movie.mapper.db.MovieCacheDbMapper
import com.rodrigobresan.data.movie.model.MovieEntity
import com.rodrigobresan.data.movie.sources.data_store.local.MovieCache
import com.rodrigobresan.domain.movie_category.model.Category
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Movie Cache contract implementation
 */
class MovieCacheImpl @Inject constructor(dbOpenHelper: DbOpenHelper,
                                         private val movieCacheMapper: MovieCacheMapper,
                                         private val movieCacheDbMapper: MovieCacheDbMapper,
                                         private val preferences: PreferencesHelper) : MovieCache {

    private val CACHE_EXPIRATION_TIME = (60 * 10 * 1000)

    private var database: SQLiteDatabase = dbOpenHelper.writableDatabase

    /**
     * Returns the database instance. Mostly used for testing
     */
    fun getDatabase(): SQLiteDatabase {
        return database
    }

    override fun clearMovies(): Completable {
        return Completable.defer {
            database.beginTransaction()
            try {
                database.delete(MovieTable.TABLE_NAME, null, null)
                database.setTransactionSuccessful()
            } finally {
                database.endTransaction()
            }

            Completable.complete()
        }
    }

    override fun saveMovies(category: Category, movies: List<MovieEntity>): Completable {
        return Completable.defer {
            database.beginTransaction()

            try {
                movies.forEach {
                    insertMovie(it)
                }
                database.setTransactionSuccessful()
            } finally {
                database.endTransaction()
            }

            Completable.complete()
        }
    }

    private fun insertMovie(it: MovieEntity) {
        database.insertWithOnConflict(MovieTable.TABLE_NAME, null,
                movieCacheDbMapper.toContentValues(movieCacheMapper.mapToCached(it)), SQLiteDatabase.CONFLICT_REPLACE)
    }

    override fun getMovie(movieId: Long): Single<MovieEntity> {
        return Single.defer<MovieEntity> {
            val query = MovieQueries.getQueryForMovie(movieId)

            val cursor = database.rawQuery(query, null)

            cursor.moveToFirst()
            val cachedMovie = movieCacheDbMapper.fromCursor(cursor)
            val entityMovie = movieCacheMapper.mapFromCached(cachedMovie)

            cursor.close()
            Single.just(entityMovie)
        }
    }

    override fun getMovies(category: Category): Single<List<MovieEntity>> {
        return Single.defer<List<MovieEntity>> {

            val query = MovieQueries.getQueryForMoviesOnCategory(category.name)

            val updatesCursor = database.rawQuery(query, null)
            val movies = mutableListOf<MovieEntity>()

            while (updatesCursor.moveToNext()) {
                val cachedMovie = movieCacheDbMapper.fromCursor(updatesCursor)
                movies.add(movieCacheMapper.mapFromCached(cachedMovie))
            }

            updatesCursor.close()
            Single.just<List<MovieEntity>>(movies)
        }
    }

    override fun isCached(): Boolean {
        return database.rawQuery(MovieTable.SELECT_ALL, null).count > 0
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