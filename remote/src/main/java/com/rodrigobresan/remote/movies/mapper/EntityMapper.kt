package com.rodrigobresan.remote.movies.mapper

/**
 * Contract between API response and our entities
 */
interface EntityMapper<in M, out E> {
    /**
     * Map from remote model to our entity
     */
    fun mapRemoteToEntity(type: M): E
}