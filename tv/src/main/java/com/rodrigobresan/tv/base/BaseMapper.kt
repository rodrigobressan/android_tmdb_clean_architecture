package com.rodrigobresan.tv.base

interface BaseMapper<out V, in D> {
    fun mapToViewModel(type: D): V
}