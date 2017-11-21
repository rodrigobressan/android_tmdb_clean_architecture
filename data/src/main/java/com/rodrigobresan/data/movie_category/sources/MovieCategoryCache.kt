package com.rodrigobresan.data.movie_category.sources

import com.rodrigobresan.data.movie_category.model.MovieCategoryEntity
import com.rodrigobresan.domain.movie_category.model.Category
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Contract to all the classes that want to cache the Category entity
 */
interface MovieCategoryCache {

    /**
     * Check if movie exists in category
     */
    fun hasMovieInCategory(movieId: Long, category: Category): Boolean

    /**
     * Delete a movie from a specific category
     */
    fun deleteMovieFromCategory(movieCategoryEntity: MovieCategoryEntity): Completable

    /**
     * Save the movie category into the cache
     */
    fun saveMovieCategory(movieCategoryEntity: MovieCategoryEntity): Completable

    /**
     * Clear all the cached movie categories
     */
    fun clearMovieCategories(): Completable

    /**
     * Get all the cached categories
     */
    fun getMovieCategories(): Single<List<MovieCategoryEntity>>

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