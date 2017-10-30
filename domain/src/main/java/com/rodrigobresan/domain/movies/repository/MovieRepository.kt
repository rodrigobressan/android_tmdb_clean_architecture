package com.rodrigobresan.domain.movies.repository

import com.rodrigobresan.domain.movies.model.Movie
import com.rodrigobresan.domain.movie_category.model.MovieCategory
import io.reactivex.Completable
import io.reactivex.Single

interface MovieRepository {
    fun clearMovies(): Completable
    fun saveMovies(movieCategory: MovieCategory, movies: List<Movie>): Completable
    fun getMovies(movieCategory: MovieCategory): Single<List<Movie>>
}