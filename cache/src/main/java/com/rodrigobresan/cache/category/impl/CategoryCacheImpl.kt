package com.rodrigobresan.cache.category.impl

import android.database.sqlite.SQLiteDatabase
import com.rodrigobresan.cache.PreferencesHelper
import com.rodrigobresan.cache.category.CategoryQueries
import com.rodrigobresan.cache.category.mapper.db.CategoryDbMapper
import com.rodrigobresan.cache.category.mapper.entity.CategoryCacheMapper
import com.rodrigobresan.cache.db.DbOpenHelper
import com.rodrigobresan.data.category.model.CategoryEntity
import com.rodrigobresan.data.category.sources.CategoryCache
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Implementation of the CategoryCache
 */
class CategoryCacheImpl @Inject constructor(dbOpenHelper: DbOpenHelper,
                                            private val categoryCacheMapper: CategoryCacheMapper,
                                            private val categoryDbMapper: CategoryDbMapper,
                                            private val preferences: PreferencesHelper) : CategoryCache {

    private val CACHE_EXPIRATION_TIME = (0.5 * 10 * 1000)

    private var database: SQLiteDatabase = dbOpenHelper.writableDatabase

    /**
     * Returns the database instance. Mostly used for testing
     */
    fun getDatabase(): SQLiteDatabase {
        return database
    }

    override fun clearCategories(): Completable {
        return Completable.defer {
            database.beginTransaction()

            try {
                database.delete(CategoryQueries.CategoryTable.TABLE_NAME, null, null)
                database.setTransactionSuccessful()
            } finally {
                database.endTransaction()
            }

            Completable.complete()
        }
    }

    override fun saveCategory(category: CategoryEntity): Completable {
        return Completable.defer {
            database.beginTransaction()

            try {
                insertCategory(category)
                database.setTransactionSuccessful()
            } finally {
                database.endTransaction()
            }

            Completable.complete()
        }
    }

    private fun insertCategory(categoryEntity: CategoryEntity) {
        database.insert(CategoryQueries.CategoryTable.TABLE_NAME, null,
                categoryDbMapper.toContentValues(categoryCacheMapper.mapToCached(categoryEntity)))
    }

    override fun getCategories(): Single<List<CategoryEntity>> {
        return Single.defer<List<CategoryEntity>> {
            val updatesCursor = database.rawQuery(CategoryQueries.CategoryTable.SELECT_ALL, null)
            val categories = mutableListOf<CategoryEntity>()

            while (updatesCursor.moveToNext()) {
                val cachedQuery = categoryDbMapper.fromCursor(updatesCursor)
                categories.add(categoryCacheMapper.mapFromCached(cachedQuery))
            }

            updatesCursor.close()

            Single.just(categories)
        }
    }

    override fun isCached(): Boolean {
        return database.rawQuery(CategoryQueries.CategoryTable.SELECT_ALL, null).count > 0
    }

    override fun updateLastCacheTime() {
        preferences.updateLastCacheTime(CategoryQueries.CategoryTable.TABLE_NAME)
    }

    override fun isExpired(): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastUpdate = this.preferences.getLastCacheTime(CategoryQueries.CategoryTable.TABLE_NAME)

        return currentTime - lastUpdate > CACHE_EXPIRATION_TIME
    }

}