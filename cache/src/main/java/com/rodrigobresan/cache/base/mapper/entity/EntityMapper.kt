package com.rodrigobresan.cache.base.mapper.entity

/**
 * Contract to map from an Entity (data layer) to a Cached (cache layer) and vice-versa
 */
interface EntityMapper<T, V> {
    fun mapFromCached(cached: T): V
    fun mapToCached(entity: V): T
}