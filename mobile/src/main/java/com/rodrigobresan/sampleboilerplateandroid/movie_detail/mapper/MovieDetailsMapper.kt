package com.rodrigobresan.sampleboilerplateandroid.movie_detail.mapper

import com.rodrigobresan.presentation.movie_details.model.MovieDetailView
import com.rodrigobresan.sampleboilerplateandroid.base.BaseMapper
import com.rodrigobresan.sampleboilerplateandroid.movie_detail.model.MovieDetailViewModel
import javax.inject.Inject

open class MovieDetailsMapper @Inject constructor() : BaseMapper<MovieDetailViewModel, MovieDetailView> {
    override fun mapToViewModel(type: MovieDetailView): MovieDetailViewModel {
        return MovieDetailViewModel(type.id, type.title, type.voteAverage, type.posterPath,
                type.backdropPath, type.overview, type.tagline)
    }
}