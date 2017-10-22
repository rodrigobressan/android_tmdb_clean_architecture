package com.rodrigobresan.domain.repository

import com.rodrigobresan.domain.model.Movie
import io.reactivex.Completable
import io.reactivex.Single

interface MovieRepository {
    fun clearMovies() : Completable
    fun saveMovies(movies: List<Movie>) : Completable
    fun getMovies() : Single<List<Movie>>
}