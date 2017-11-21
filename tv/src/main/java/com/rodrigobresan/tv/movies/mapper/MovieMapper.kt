package com.rodrigobresan.tv.movies.mapper

import com.rodrigobresan.presentation.movies.model.MovieView
import com.rodrigobresan.tv.base.BaseMapper
import com.rodrigobresan.tv.movies.model.MovieViewModel
import javax.inject.Inject

open class MovieMapper @Inject constructor() : BaseMapper<MovieViewModel, MovieView> {
    override fun mapToViewModel(type: MovieView): MovieViewModel {
        return MovieViewModel(type.id, type.title, type.rating, type.posterPath)
    }

}