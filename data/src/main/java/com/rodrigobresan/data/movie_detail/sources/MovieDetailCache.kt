package com.rodrigobresan.data.movie_detail.sources

import com.rodrigobresan.data.movie_detail.model.MovieDetailEntity
import io.reactivex.Completable
import io.reactivex.Single

interface MovieDetailCache {

    fun clearMovieDetails(): Completable

    fun saveMovieDetails(movie: MovieDetailEntity): Completable

    fun getMovieDetails(movieId: Long): Single<MovieDetailEntity>

    fun isCached(): Boolean

    fun setLastCacheTime(lastCacheTime: Long)

    fun isExpired(): Boolean
}