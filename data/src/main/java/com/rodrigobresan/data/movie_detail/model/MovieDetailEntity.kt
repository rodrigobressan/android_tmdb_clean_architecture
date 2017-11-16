package com.rodrigobresan.data.movie_detail.model

data class MovieDetailEntity(val id: Long, val title: String, val voteAverage: Double,
                             val posterPath: String, val backdropPath: String, val overview: String,
                             val tagline: String) {
    override fun equals(other: Any?): Boolean {
        other as MovieDetailEntity

        if (id != other.id) return false
        if (title != other.title) return false
        if (voteAverage != other.voteAverage) return false
        if (posterPath != other.posterPath) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + voteAverage.hashCode()
        result = 31 * result + posterPath.hashCode()
        return result
    }
}