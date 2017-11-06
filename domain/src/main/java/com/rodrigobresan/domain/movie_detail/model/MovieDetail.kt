package com.rodrigobresan.domain.movie_detail.model

data class MovieDetail(val id: Long, val title: String, val voteAverage: Double,
                             val posterPath: String, val backdropPath: String)