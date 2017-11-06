package com.rodrigobresan.data.movie_detail.sources.data_store.local

import com.rodrigobresan.data.movie.model.MovieEntity
import com.rodrigobresan.data.movie_detail.model.MovieDetailEntity
import com.rodrigobresan.data.movie_detail.sources.data_store.MovieDetailDataStore
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

open class MovieDetailCacheDataStore @Inject constructor(private val movieDetailCache: MovieDetailCache) : MovieDetailDataStore {
    override fun clearMovieDetails(): Completable {
        return movieDetailCache.clearMovieDetails()
    }

    override fun saveMovieDetails(movieDetail: MovieDetailEntity): Completable {
        return movieDetailCache.saveMovieDetails(movieDetail)
                .doOnComplete {
                    movieDetailCache.setLastCacheTime(System.currentTimeMillis())
                }
    }

    override fun getMovieDetails(movieId: Long): Single<MovieDetailEntity> {
        return movieDetailCache.getMovieDetails(movieId)
    }

}