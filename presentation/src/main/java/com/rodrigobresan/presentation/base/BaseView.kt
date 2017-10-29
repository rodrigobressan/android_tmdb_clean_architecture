package com.rodrigobresan.presentation.base

interface BaseView<in T: BasePresenter> {
    fun setPresenter(presenter: T)
}