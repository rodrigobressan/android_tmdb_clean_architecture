package com.rodrigobresan.cache.movie_detail.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "MovieDetails")
data class MovieDetailsCached(var title: String, var voteAverage: Double,
                              var posterPath: String, var backdropPath: String, var overview: String,
                              var tagline: String) {

    constructor() : this("title", 0.0, "poster_path", "backdrop", "overview", "tagline")

    @field:PrimaryKey
    var id: Long = 0
}