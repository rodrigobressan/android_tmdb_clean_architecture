package com.rodrigobresan.data.movie_detail.sources.data_store.local

import com.rodrigobresan.data.movie_detail.model.MovieDetailEntity
import io.reactivex.Completable
import io.reactivex.Single

interface MovieDetailCache {

    fun clearMovieDetails(): Completable

    fun saveMovieDetails(movie: MovieDetailEntity): Completable

    fun getMovieDetails(movieId: Long): Single<MovieDetailEntity>

    fun isMovieCached(movieId: Long): Boolean

    fun setLastCacheTime(lastCacheTime: Long)

    fun isExpired(): Boolean
}