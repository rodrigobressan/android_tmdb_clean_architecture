package com.rodrigobresan.remote.mapper

interface EntityMapper<in M, out E> {
    fun mapRemoteToEntity(type: M): E
}