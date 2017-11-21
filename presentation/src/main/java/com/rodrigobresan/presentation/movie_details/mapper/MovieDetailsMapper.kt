package com.rodrigobresan.presentation.movie_details.mapper

import com.rodrigobresan.domain.movie_detail.model.MovieDetail
import com.rodrigobresan.presentation.base.PresentationMapper
import com.rodrigobresan.presentation.movie_details.model.MovieDetailView
import javax.inject.Inject

open class MovieDetailsMapper @Inject constructor() : PresentationMapper<MovieDetailView, MovieDetail> {
    override fun mapToView(type: MovieDetail): MovieDetailView {
        return MovieDetailView(type.id, type.title, type.voteAverage, type.posterPath,
                type.backdropPath, type.overview, type.tagline, type.isFavorite)
    }
}