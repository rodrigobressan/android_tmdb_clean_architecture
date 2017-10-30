package com.rodrigobresan.data.base.mapper

interface DataMapper<E, D> {
    fun mapFromEntity(entity: E): D
    fun mapToEntity(model: D): E
}