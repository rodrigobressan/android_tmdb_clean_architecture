package com.rodrigobresan.domain.movies.model

/**
 * Data class for a Movie
 */
data class Movie(val id: Long, val title: String, val rating: Double, val posterPath: String,
                 val isFavorite: Boolean) {
}