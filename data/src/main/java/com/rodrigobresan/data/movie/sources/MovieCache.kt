package com.rodrigobresan.data.movie.sources

import com.rodrigobresan.data.movie.model.MovieEntity
import com.rodrigobresan.domain.movie_category.model.MovieCategory
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