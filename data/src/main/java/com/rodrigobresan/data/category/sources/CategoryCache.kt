package com.rodrigobresan.data.category.sources

import com.rodrigobresan.data.category.model.CategoryEntity
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Interface to define the contract for classes that will implement cache of category
 */
interface CategoryCache {
    /**
     * Clear all the categories in the cache
     */
    fun clearCategories(): Completable

    /**
     * Save the list of categories specified into the cache
     */
    fun saveCategory(category: CategoryEntity): Completable

    /**
     * Get the list of the categories in the cache
     */
    fun getCategories(): Single<List<CategoryEntity>>?

    /**
     * Check if it's cached
     */
    fun isCached(): Boolean

    /**
     * Define the last time it was cached
     */
    fun updateLastCacheTime()

    /**
     * Check if the cache was expired
     */
    fun isExpired(): Boolean
}