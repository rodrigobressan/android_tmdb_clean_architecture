package com.rodrigobresan.domain.movies.repository

import com.rodrigobresan.domain.movie_category.model.Category
import com.rodrigobresan.domain.movies.model.Movie
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Contract for any class that will be a MovieRepository. The implementation will be done
 * by the data layer
 */
interface MovieRepository {
    fun clearMovies(): Completable
    fun saveMovies(category: Category, movies: List<Movie>): Completable
    fun deleteMovie(category: Category, movie: Movie): Completable
    fun getMovies(category: Category): Single<List<Movie>>
    fun getMovie(movieId: Long): Single<Movie>
}