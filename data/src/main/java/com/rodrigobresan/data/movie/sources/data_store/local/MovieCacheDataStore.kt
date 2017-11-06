package com.rodrigobresan.data.movie.sources.data_store.local

import com.rodrigobresan.data.movie.model.MovieEntity
import com.rodrigobresan.data.movie.sources.data_store.MovieDataStore
import com.rodrigobresan.domain.movie_category.model.MovieCategory
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Implementation of DataStore for Movies
 */
open class MovieCacheDataStore @Inject constructor(private val movieCache: MovieCache) : MovieDataStore {
    override fun clearMovies(): Completable {
        return movieCache.clearMovies()
    }

    override fun saveMovies(movieCategory: MovieCategory, movies: List<MovieEntity>): Completable {
        return movieCache.saveMovies(movieCategory, movies)
                .doOnComplete {
                    movieCache.setLastCacheTime(System.currentTimeMillis())
                }
    }

    override fun getMovies(category: MovieCategory): Single<List<MovieEntity>> {
        return movieCache.getMovies(category)
    }
}