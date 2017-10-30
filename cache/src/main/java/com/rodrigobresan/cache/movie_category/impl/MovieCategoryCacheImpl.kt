package com.rodrigobresan.cache.movie_category.impl

import android.database.sqlite.SQLiteDatabase
import com.rodrigobresan.cache.PreferencesHelper
import com.rodrigobresan.cache.db.DbOpenHelper
import com.rodrigobresan.cache.movie.mapper.db.MovieCategoryDbMapper
import com.rodrigobresan.cache.movie_category.MovieCategoryQueries
import com.rodrigobresan.cache.movie_category.mapper.entity.MovieCategoryEntityMapper
import com.rodrigobresan.cache.movie_category.model.MovieCategoryCached
import com.rodrigobresan.data.model.MovieCategoryEntity
import com.rodrigobresan.data.model.MovieEntity
import com.rodrigobresan.data.repository.movie.movie.movie_category.MovieCategoryCache
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class MovieCategoryCacheImpl @Inject constructor(dbOpenHelper: DbOpenHelper,
                                                 private val dbMapper: MovieCategoryDbMapper,
                                                 private val entityMapper: MovieCategoryEntityMapper,
                                                 private val preferencesHelper: PreferencesHelper) : MovieCategoryCache {

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

    override fun clearCategories(): Completable {
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

    private val CACHE_EXPIRATION_TIME = (0.5 * 10 * 1000)

    private var database: SQLiteDatabase = dbOpenHelper.writableDatabase

    fun getDatabase(): SQLiteDatabase {
        return database
    }

}