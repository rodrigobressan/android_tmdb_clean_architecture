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
        private val categoryCacheMapper: CategoryCacheMapper) : CategoryCache {

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
}