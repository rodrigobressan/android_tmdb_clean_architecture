package com.rodrigobresan.cache.category.impl

import android.database.sqlite.SQLiteDatabase
import com.rodrigobresan.cache.PreferencesHelper
import com.rodrigobresan.cache.category.CategoryQueries
import com.rodrigobresan.cache.category.mapper.db.CategoryDbMapper
import com.rodrigobresan.cache.category.mapper.entity.CategoryEntityMapper
import com.rodrigobresan.cache.db.DbOpenHelper
import com.rodrigobresan.data.category.model.CategoryEntity
import com.rodrigobresan.data.category.sources.CategoryCache
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class CategoryCacheImpl @Inject constructor(dbOpenHelper: DbOpenHelper,
                                            private val categoryEntityMapper: CategoryEntityMapper,
                                            private val categoryDbMapper: CategoryDbMapper,
                                            private val preferences: PreferencesHelper) : CategoryCache {

    private val CACHE_EXPIRATION_TIME = (0.5 * 10 * 1000)

    private var database: SQLiteDatabase = dbOpenHelper.writableDatabase

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

    override fun saveCategories(categoryList: List<CategoryEntity>): Completable {
        return Completable.defer {
            database.beginTransaction()

            try {
                categoryList.forEach {
                    insertCategory(it)
                }

                database.setTransactionSuccessful()
            } finally {
                database.endTransaction()
            }

            Completable.complete()
        }
    }

    private fun insertCategory(categoryEntity: CategoryEntity) {
        database.insert(CategoryQueries.CategoryTable.TABLE_NAME, null,
                categoryDbMapper.toContentValues(categoryEntityMapper.mapToCached(categoryEntity)))
    }

    override fun getCategories(): Single<List<CategoryEntity>> {
        return Single.defer<List<CategoryEntity>> {
            val query = "SELECT * FROM " + CategoryQueries.CategoryTable.TABLE_NAME

            val updatesCursor = database.rawQuery(query, null)
            val categories = mutableListOf<CategoryEntity>()

            while (updatesCursor.moveToNext()) {
                val cachedQuery = categoryDbMapper.fromCursor(updatesCursor)
                categories.add(categoryEntityMapper.mapFromCached(cachedQuery))
            }

            updatesCursor.close()

            Single.just(categories)
        }
    }

    override fun isCached(): Boolean {
        return database.rawQuery(CategoryQueries.CategoryTable.SELECT_ALL, null).count > 0
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