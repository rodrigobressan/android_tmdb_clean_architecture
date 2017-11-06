package com.rodrigobresan.domain.movie_detail.repository

import com.rodrigobresan.domain.movie_detail.model.MovieDetail
import io.reactivex.Completable
import io.reactivex.Single

interface MovieDetailRepository {
    fun clearMovieDetails() : Completable

    fun saveMovieDetail(movie: MovieDetail) : Completable

    fun getMovieDetails(movieId: Long) : Single<MovieDetail>
}