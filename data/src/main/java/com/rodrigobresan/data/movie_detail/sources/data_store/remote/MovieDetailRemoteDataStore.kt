package com.rodrigobresan.data.movie_detail.sources.data_store.remote

import com.rodrigobresan.data.movie_detail.model.MovieDetailEntity
import com.rodrigobresan.data.movie_detail.sources.data_store.MovieDetailDataStore
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

open class MovieDetailRemoteDataStore @Inject constructor(private val movieDetailRemote: MovieDetailRemote) : MovieDetailDataStore {
    override fun clearMovieDetails(): Completable {
        throw UnsupportedOperationException()
    }

    override fun saveMovieDetails(movieDetail: MovieDetailEntity): Completable {
        throw UnsupportedOperationException()
    }

    override fun getMovieDetails(movieId: Long): Single<MovieDetailEntity> {
        return movieDetailRemote.getMovieDetails(movieId)
    }

}