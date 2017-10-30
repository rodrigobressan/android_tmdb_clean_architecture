package com.rodrigobresan.cache.base.mapper.entity

interface EntityMapper<T, V> {
    fun mapFromCached(cached: T): V
    fun mapToCached(entity: V): T
}