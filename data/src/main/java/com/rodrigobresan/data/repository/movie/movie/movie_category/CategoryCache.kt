package com.rodrigobresan.data.repository.movie.movie.movie_category

import com.rodrigobresan.data.model.CategoryEntity
import io.reactivex.Completable
import io.reactivex.Single

interface CategoryCache {
    fun clearCategories(): Completable

    fun saveCategories(categoryList: List<CategoryEntity>): Completable

    fun getCategories(): Single<List<CategoryEntity>>?

    fun isCached(): Boolean

    fun setLastCacheTime(lastCacheTime: Long)

    fun isExpired(): Boolean
}