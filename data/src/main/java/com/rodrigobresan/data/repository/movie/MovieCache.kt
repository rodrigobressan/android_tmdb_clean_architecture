package com.rodrigobresan.data.repository.movie

import com.rodrigobresan.data.model.MovieEntity
import io.reactivex.Completable
import io.reactivex.Single

interface MovieCache {

    fun clearMovies(): Completable

    fun saveMovies(mvoies: List<MovieEntity>): Completable

    fun getMovies(): Single<List<MovieEntity>>

    fun isCached(): Boolean

    fun setLastCacheTime(lastCacheTime: Long)

    fun isExpired(): Boolean
}