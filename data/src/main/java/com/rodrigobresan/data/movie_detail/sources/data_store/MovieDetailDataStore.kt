package com.rodrigobresan.data.movie_detail.sources.data_store

import com.rodrigobresan.data.movie.model.MovieEntity
import com.rodrigobresan.data.movie_detail.model.MovieDetailEntity
import io.reactivex.Completable
import io.reactivex.Single

interface MovieDetailDataStore {

    fun clearMovieDetails() : Completable

    fun saveMovieDetails(movieDetail: MovieDetailEntity) : Completable

    fun getMovieDetails(movieId: Long) : Single<MovieDetailEntity>
}