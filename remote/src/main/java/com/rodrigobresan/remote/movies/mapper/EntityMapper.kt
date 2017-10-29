package com.rodrigobresan.remote.movies.mapper

interface EntityMapper<in M, out E> {
    fun mapRemoteToEntity(type: M): E
}