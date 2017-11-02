package com.rodrigobresan.presentation.base

/**
 * Contract to any class that will take the role of the view
 */
interface BaseView<in T: BasePresenter> {
    fun setPresenter(presenter: T)
}