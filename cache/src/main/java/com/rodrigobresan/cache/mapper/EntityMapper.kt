package com.rodrigobresan.cache.mapper

interface EntityMapper<T, V> {

    fun mapFromCached(cached: T): V
    fun mapToCached(entity: V): T
}