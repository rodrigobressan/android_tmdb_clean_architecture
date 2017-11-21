package com.rodrigobresan.data.movie.sources.data_store.local

import com.rodrigobresan.data.category.model.CategoryEntity
import com.rodrigobresan.data.category.sources.CategoryCache
import com.rodrigobresan.data.movie.model.MovieEntity
import com.rodrigobresan.data.movie.sources.data_store.MovieDataStore
import com.rodrigobresan.data.movie_category.model.MovieCategoryEntity
import com.rodrigobresan.data.movie_category.sources.MovieCategoryCache
import com.rodrigobresan.domain.movie_category.model.Category
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

    override fun deleteMovieFromCategory(movieCategoryEntity: MovieCategoryEntity): Completable {
        return movieCategoryCache.deleteMovieFromCategory(movieCategoryEntity)
    }

    override fun getMovie(movieId: Long): Single<MovieEntity> {
        return movieCache.getMovie(movieId)
    }

    override fun clearMovies(): Completable {
        return movieCache.clearMovies()
    }

    override fun saveMovies(category: Category, movies: List<MovieEntity>): Completable {
        return movieCache.saveMovies(category, movies)
                .doOnComplete {
                    movieCache.updateLastCacheTime()
                    saveCategory(category, movies)
                }
    }

    private fun saveCategory(category: Category, movies: List<MovieEntity>) {
        categoryCache.saveCategory(CategoryEntity(category.name, category.name))
                .doOnComplete({
                    categoryCache.updateLastCacheTime()
                    movies.forEach {
                        saveMovieCategory(it, category)
                    }

                }).subscribe()
    }

    private fun saveMovieCategory(it: MovieEntity, category: Category) {
        movieCategoryCache.saveMovieCategory(MovieCategoryEntity(it.id, category.name))
                .doOnComplete({
                    movieCategoryCache.updateLastCacheTime()
                }).subscribe()
    }

    override fun getMovies(category: Category): Single<List<MovieEntity>> {
        return movieCache.getMovies(category)
                .flatMap {
                    it.mapIndexed { _, movieEntity ->
                        {
                            val currentMovieId = movieEntity.id
                            movieEntity.isFavorite = movieCategoryCache.hasMovieInCategory(currentMovieId, Category.FAVORITE)
                        }
                    }

                    Single.just(it)
                }
    }
}