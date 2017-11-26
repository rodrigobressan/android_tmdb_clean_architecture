package com.rodrigobresan.cache.category.mapper.entity

import com.rodrigobresan.cache.base.mapper.entity.EntityMapper
import com.rodrigobresan.cache.category.model.CategoryCached
import com.rodrigobresan.data.category.model.CategoryEntity
import javax.inject.Inject

/**
 * Mapper for CategoryCached and CategoryEntity
 */
open class CategoryCacheMapper @Inject constructor() : EntityMapper<CategoryCached, CategoryEntity> {

    override fun mapFromCached(cached: CategoryCached): CategoryEntity {
        val category = CategoryEntity(cached.id, cached.name)
        return category
    }

    override fun mapToCached(entity: CategoryEntity): CategoryCached {
        val category = CategoryCached(entity.name)
        category.id = entity.id

        return category
    }

}