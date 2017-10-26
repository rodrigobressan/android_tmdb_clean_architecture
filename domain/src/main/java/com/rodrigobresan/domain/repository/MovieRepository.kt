package com.rodrigobresan.domain.repository

import com.rodrigobresan.domain.model.Movie
import com.rodrigobresan.domain.model.MovieCategory
import io.reactivex.Completable
import io.reactivex.Single

interface MovieRepository {
    fun clearMovies(): Completable
    fun saveMovies(movieCategory: MovieCategory, movies: List<Movie>): Completable

    fun getMovies(movieCategory: MovieCategory): Single<List<Movie>>
}