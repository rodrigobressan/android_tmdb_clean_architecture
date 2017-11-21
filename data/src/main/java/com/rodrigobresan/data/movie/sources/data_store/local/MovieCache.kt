package com.rodrigobresan.data.movie.sources.data_store.local

import com.rodrigobresan.data.movie.model.MovieEntity
import com.rodrigobresan.domain.movie_category.model.Category
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Contract to any class that will be caching the movie entity
 */
interface MovieCache {

    /**
     * Clear the existent movies in the database
     */
    fun clearMovies(): Completable

    /**
     * Get specific movie
     */
    fun getMovie(movieId: Long): Single<MovieEntity>

    /**
     * Save the list of movies into the specified category
     */
    fun saveMovies(category: Category, movies: List<MovieEntity>): Completable

    /**
     * Get the movies from a specified category
     */
    fun getMovies(category: Category): Single<List<MovieEntity>>

    /**
     * Check if it's cached
     */
    fun isCached(): Boolean

    /**
     * Define the last time it was cached
     */
    fun updateLastCacheTime()

    /**
     * Check if the current cache is expired or not
     */
    fun isExpired(): Boolean
}