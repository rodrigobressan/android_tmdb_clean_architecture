package com.rodrigobresan.presentation.movie_details.model

import java.io.Serializable

data class MovieDetailView(val id: Long, val title: String, val voteAverage: Double,
                           val posterPath: String, val backdropPath: String, val overview: String,
                           val tagline: String, var isFavorite: Boolean) : Serializable