package com.rodrigobresan.cache.movie_category.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.PrimaryKey
import com.rodrigobresan.cache.category.model.CategoryCached
import com.rodrigobresan.cache.movie.model.MovieCached

/**
 * Data class for stored Category
 */
@Entity(tableName = "MovieCategory",
        foreignKeys = arrayOf(
                ForeignKey(onDelete = CASCADE,
                        entity = MovieCached::class,
                        parentColumns = arrayOf("id"),
                        childColumns = arrayOf("movieId")
                ),
                ForeignKey(onDelete = CASCADE,
                        entity = CategoryCached::class,
                        parentColumns = arrayOf("id"),
                        childColumns = arrayOf("categoryId"))
        ),
        primaryKeys = arrayOf("movieId", "categoryId"))
data class MovieCategoryCached(var description: String) {

    constructor() : this("description")

//    @field:PrimaryKey
    var movieId: Long = 0

//    @field:PrimaryKey
    var categoryId: String = ""
}