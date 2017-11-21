package com.rodrigobresan.tv.movie_details.mapper

import com.rodrigobresan.presentation.movie_details.model.MovieDetailView
import com.rodrigobresan.tv.base.BaseMapper
import com.rodrigobresan.tv.movie_details.model.MovieDetailViewModel
import javax.inject.Inject

open class MovieDetailsMapper @Inject constructor() : BaseMapper<MovieDetailViewModel, MovieDetailView> {
    override fun mapToViewModel(type: MovieDetailView): MovieDetailViewModel {
        return MovieDetailViewModel(type.id, type.title, type.voteAverage, type.posterPath,
                type.backdropPath, type.overview, type.tagline)
    }
}