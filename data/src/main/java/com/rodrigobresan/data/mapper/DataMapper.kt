package com.rodrigobresan.data.mapper

interface DataMapper<E, D> {
    fun mapFromEntity(entity: E): D
    fun mapToEntity(model: D): E
}