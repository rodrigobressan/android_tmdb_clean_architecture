package com.rodrigobresan.cache.movie.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Class representation of the stored movie
 */
@Entity(tableName = "Movie")
data class MovieCached(var title: String, var rating: Double, var picture: String) {

    constructor() : this("title", 0.0, "picture")

    @field:PrimaryKey
    var id: Long = 0
}