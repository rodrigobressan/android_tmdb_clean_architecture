package com.rodrigobresan.presentation.movies.mapper

import com.rodrigobresan.domain.movies.model.Movie
import com.rodrigobresan.presentation.base.PresentationMapper
import com.rodrigobresan.presentation.movies.model.MovieView
import javax.inject.Inject

open class MovieMapper @Inject constructor() : PresentationMapper<MovieView, Movie> {
    override fun mapToView(type: Movie): MovieView {
        return MovieView(type.id, type.title, type.rating, type.posterPath)
    }

}