package com.rodrigobresan.remote

/**
 * Contract between API response and our entities
 */
interface EntityMapper<in M, out E> {
    /**
     * Map from remote model to our entity
     */
    fun mapRemoteToEntity(type: M): E
}