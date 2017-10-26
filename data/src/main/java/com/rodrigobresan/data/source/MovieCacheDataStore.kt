package com.rodrigobresan.data.source

import com.rodrigobresan.domain.model.MovieCategory
import com.rodrigobresan.data.model.MovieEntity
import com.rodrigobresan.data.repository.movie.movie.MovieCache
import com.rodrigobresan.data.repository.movie.movie.MovieDataStore
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

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