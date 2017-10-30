package com.rodrigobresan.sampleboilerplateandroid.base

interface BaseMapper<out V, in D> {
    fun mapToViewModel(type: D): V
}