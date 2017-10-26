package com.rodrigobresan.data.repository.movie.movie

import com.rodrigobresan.domain.model.MovieCategory
import com.rodrigobresan.data.model.MovieEntity
import io.reactivex.Completable
import io.reactivex.Single

interface MovieCache {

    fun clearMovies(): Completable

    fun saveMovies(movieCategory: MovieCategory, movies: List<MovieEntity>): Completable

    fun getMovies(movieCategory: MovieCategory): Single<List<MovieEntity>>

    fun isCached(): Boolean

    fun setLastCacheTime(lastCacheTime: Long)

    fun isExpired(): Boolean
}