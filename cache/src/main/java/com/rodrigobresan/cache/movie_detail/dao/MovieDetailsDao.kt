package com.rodrigobresan.cache.movie_detail.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.rodrigobresan.cache.movie_detail.model.MovieDetailsCached

@Dao
interface MovieDetailsDao {

    @Insert
    fun insertMovie(mapToCached: MovieDetailsCached)

    @Query("SELECT * FROM MovieDetails WHERE id = :movieId")
    fun getMovieDetails(movieId: Long) : MovieDetailsCached

}