package com.rodrigobresan.domain.movies.repository

import com.rodrigobresan.domain.movies.model.Movie
import com.rodrigobresan.domain.movie_category.model.MovieCategory
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Contract for any class that will be a MovieRepository. The implementation will be done
 * by the data layer
 */
interface MovieRepository {
    fun clearMovies(): Completable
    fun saveMovies(movieCategory: MovieCategory, movies: List<Movie>): Completable
    fun getMovies(movieCategory: MovieCategory): Single<List<Movie>>
}