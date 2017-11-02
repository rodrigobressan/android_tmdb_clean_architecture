package com.rodrigobresan.data.base.mapper

/**
 * Defined contract to map from entity (data layer) to model (domain layer)
 */
interface DataMapper<E, D> {
    /**
     * Map from a specified entity
     */
    fun mapFromEntity(entity: E): D

    /**
     * Map to a specified entity
     */
    fun mapToEntity(model: D): E
}