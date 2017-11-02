package com.rodrigobresan.data.movie.sources

import com.rodrigobresan.data.movie.model.MovieEntity
import io.reactivex.Single

/**
 * Define the interface for any class that will fetch Movies from a remote storage
 */
interface MovieRemote {
    /**
     * Get the popular movies
     */
    fun getPopularMovies(): Single<List<MovieEntity>>

    /**
     * Get the now playing movies
     */
    fun getNowPlayingMovies(): Single<List<MovieEntity>>

    /**
     * Get the top rated movies
     */
    fun getTopRatedMovies(): Single<List<MovieEntity>>

    /**
     * Get the upcoming movies
     */
    fun getUpcomingMovies(): Single<List<MovieEntity>>
}