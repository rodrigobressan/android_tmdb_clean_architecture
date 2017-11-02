package com.rodrigobresan.presentation.base

/**
 * Mapper between presentation and domain layer
 */
interface PresentationMapper<out V, in D> {
    fun mapToView(type: D): V
}