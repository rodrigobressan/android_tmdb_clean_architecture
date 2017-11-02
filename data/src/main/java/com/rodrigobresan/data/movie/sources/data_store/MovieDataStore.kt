package com.rodrigobresan.data.movie.sources.data_store

import com.rodrigobresan.data.movie.model.MovieEntity
import com.rodrigobresan.domain.movie_category.model.MovieCategory
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Contract to define between any class that will be a Movie Data Store
 */
interface MovieDataStore {

    /**
     * Clear the movies
     */
    fun clearMovies() : Completable

    /**
     * Save the movies into a specified category, returning a Completable for the operation
     */
    fun saveMovies(movieCategory: MovieCategory, movies: List<MovieEntity>) : Completable

    /**
     * Get all the movies in a specified category
     */
    fun getMovies(category: MovieCategory) : Single<List<MovieEntity>>

}