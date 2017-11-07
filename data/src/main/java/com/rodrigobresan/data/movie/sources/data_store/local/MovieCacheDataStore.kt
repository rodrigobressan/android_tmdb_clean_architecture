package com.rodrigobresan.data.movie.sources.data_store.local

import com.rodrigobresan.data.category.model.CategoryEntity
import com.rodrigobresan.data.category.sources.CategoryCache
import com.rodrigobresan.data.movie.model.MovieEntity
import com.rodrigobresan.data.movie.sources.data_store.MovieDataStore
import com.rodrigobresan.data.movie_category.model.MovieCategoryEntity
import com.rodrigobresan.data.movie_category.sources.MovieCategoryCache
import com.rodrigobresan.domain.movie_category.model.MovieCategory
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Implementation of DataStore for Movies
 */
open class MovieCacheDataStore @Inject constructor(
        private val categoryCache: CategoryCache,
        private val movieCategoryCache: MovieCategoryCache,
        private val movieCache: MovieCache) : MovieDataStore {

    override fun clearMovies(): Completable {
        return movieCache.clearMovies()
    }

    override fun saveMovies(movieCategory: MovieCategory, movies: List<MovieEntity>): Completable {
        return movieCache.saveMovies(movieCategory, movies)
                .doOnComplete {
                    movieCache.updateLastCacheTime()
                    saveCategory(movieCategory, movies)
                }
    }

    private fun saveCategory(movieCategory: MovieCategory, movies: List<MovieEntity>) {
        categoryCache.saveCategory(CategoryEntity(movieCategory.name, movieCategory.name))
                .doOnComplete({
                    categoryCache.updateLastCacheTime()
                    movies.forEach {
                        saveMovieCategory(it, movieCategory)
                    }

                }).subscribe()
    }

    private fun saveMovieCategory(it: MovieEntity, movieCategory: MovieCategory) {
        movieCategoryCache.saveMovieCategory(MovieCategoryEntity(it.id, movieCategory.name))
                .doOnComplete({
                    movieCategoryCache.updateLastCacheTime()
                }).subscribe()
    }

    override fun getMovies(category: MovieCategory): Single<List<MovieEntity>> {
        return movieCache.getMovies(category)
    }
}