package com.rodrigobresan.cache.movie_category.dao

import android.arch.persistence.room.*
import com.rodrigobresan.cache.movie_category.model.MovieCategoryCached

@Dao
interface MovieCategoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(movieCategory: MovieCategoryCached)

    @Query("SELECT MovieCategory.movieId, MovieCategory.categoryId " +
            " FROM MovieCategory " +
            " WHERE MovieCategory.movieId = :movieId " +
            " AND MovieCategory.categoryId = :name")
    fun getMovieInCategory(movieId: Long, name: String): List<MovieCategoryCached>

    @Query("SELECT * FROM MovieCategory")
    fun getMovieCategories(): List<MovieCategoryCached>

    @Delete
    fun delete(mapToCached: MovieCategoryCached)
}