package com.rodrigobresan.data.movie_category.sources

import com.rodrigobresan.data.movie_category.model.MovieCategoryEntity
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Contract to all the classes that want to cache the MovieCategory entity
 */
interface MovieCategoryCache {
    /**
     * Save the movie category into the cache
     */
    fun saveMovieCategory(movieCategoryEntity: MovieCategoryEntity): Completable

    /**
     * Clear all the cached movie categories
     */
    fun clearMovieCategories() : Completable

    /**
     * Get all the cached categories
     */
    fun getCategories() : Single<List<MovieCategoryEntity>>

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