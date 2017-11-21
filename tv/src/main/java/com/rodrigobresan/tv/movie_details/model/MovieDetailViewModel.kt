package com.rodrigobresan.tv.movie_details.model

import java.io.Serializable

data class MovieDetailViewModel(val id: Long, val title: String, val voteAverage: Double,
                                val posterPath: String, val backdropPath: String, val overview: String,
                                val tagline: String) : Serializable