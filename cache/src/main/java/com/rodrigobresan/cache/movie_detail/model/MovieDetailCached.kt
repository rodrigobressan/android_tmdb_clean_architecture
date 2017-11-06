package com.rodrigobresan.cache.movie_detail.model

data class MovieDetailCached(val id: Long, val title: String, val voteAverage: Double,
                             val posterPath: String, val backdropPath: String)