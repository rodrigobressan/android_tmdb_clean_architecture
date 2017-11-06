package com.rodrigobresan.data.movie_detail.sources

import com.rodrigobresan.data.movie_detail.model.MovieDetailEntity
import io.reactivex.Single

interface MovieDetailRemote {
    fun getMovieDetails(movieId: Long) : Single<MovieDetailEntity>
}