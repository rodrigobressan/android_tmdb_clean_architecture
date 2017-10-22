package com.rodrigobresan.data.source

import com.rodrigobresan.data.model.MovieEntity
import com.rodrigobresan.data.repository.movie.MovieCache
import com.rodrigobresan.data.repository.movie.MovieDataStore
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class MovieCacheDataStore @Inject constructor(private val movieCache: MovieCache) : MovieDataStore {
    override fun clearMovies(): Completable {
        return movieCache.clearMovies()
    }

    override fun saveMovies(movies: List<MovieEntity>): Completable {
        return movieCache.saveMovies(movies)
                .doOnComplete {
                    movieCache.setLastCacheTime(System.currentTimeMillis())
                }
    }

    override fun getMovies(): Single<List<MovieEntity>> {
        return movieCache.getMovies()
    }


}