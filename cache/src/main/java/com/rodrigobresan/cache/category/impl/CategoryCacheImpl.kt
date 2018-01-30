package com.rodrigobresan.cache.category.impl

import com.rodrigobresan.cache.PreferencesHelper
import com.rodrigobresan.cache.category.CategoryQueries
import com.rodrigobresan.cache.category.dao.CategoryDao
import com.rodrigobresan.cache.category.mapper.entity.CategoryCacheMapper
import com.rodrigobresan.data.category.model.CategoryEntity
import com.rodrigobresan.data.category.sources.CategoryCache
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Implementation of the CategoryCache
 */
class CategoryCacheImpl @Inject constructor(
        private val categoryDao: CategoryDao,
        private val categoryCacheMapper: CategoryCacheMapper,
        private val preferences: PreferencesHelper) : CategoryCache {

    private val CACHE_EXPIRATION_TIME = (0.5 * 10 * 1000)

    override fun clearCategories(): Completable {
        return Completable.defer {
            // TODO write clear categories
            Completable.complete()
        }
    }

    override fun saveCategory(category: CategoryEntity): Completable {
        return Completable.defer {
            categoryDao.insert(categoryCacheMapper.mapToCached(category))
            Completable.complete()
        }
    }

    override fun getCategories(): Single<List<CategoryEntity>> {
        return Single.defer<List<CategoryEntity>> {
            val categoryList = categoryDao.getAll()
            Single.just(categoryList.map { categoryCacheMapper.mapFromCached(it) })
        }
    }

    override fun isCached(): Boolean {
        return categoryDao.getAll().size > 0
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