package com.rodrigobresan.presentation.movie_details.model

data class MovieDetailView(val id: Long, val title: String, val voteAverage: Double,
                       val posterPath: String, val backdropPath: String)