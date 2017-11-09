package com.rodrigobresan.sampleboilerplateandroid.movie_detail.model

data class MovieDetailViewModel(val id: Long, val title: String, val voteAverage: Double,
                                val posterPath: String, val backdropPath: String, val overview: String,
                                val tagline: String)