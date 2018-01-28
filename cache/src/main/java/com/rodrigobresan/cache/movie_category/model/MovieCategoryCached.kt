package com.rodrigobresan.cache.movie_category.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import com.rodrigobresan.cache.category.model.CategoryCached
import com.rodrigobresan.cache.movie.model.MovieCached

/**
 * Data class for stored Category
 */
@Entity(primaryKeys = arrayOf("movieId", "categoryId"),
        tableName = "MovieCategory",
        foreignKeys = arrayOf(
                ForeignKey(
                        entity = MovieCached::class,
                        parentColumns = arrayOf("id"),
                        childColumns = arrayOf("movieId")
                ),
                ForeignKey(entity = CategoryCached::class,
                        parentColumns = arrayOf("id"),
                        childColumns = arrayOf("categoryId"))
        ))
data class MovieCategoryCached(@ColumnInfo(name = "movieId") var movieId: Long = 0,
                               @ColumnInfo(name = "categoryId") var categoryId: String = "") {
}