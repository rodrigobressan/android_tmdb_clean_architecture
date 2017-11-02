package com.rodrigobresan.remote.service

import com.rodrigobresan.remote.movies.model.movies.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET

/**
 * Contract defined to be used by our Retrofit client, in order to create the calls to the
 * IMDB API
 */
interface MovieService {

    /**
     * Get the list of the popular movies
     */
    @GET("movie/popular")
    fun getPopularMovies(): Single<MovieResponse>

    /**
     * Get the list of the top rated movies
     */
    @GET("movie/top_rated")
    fun getTopRatedMovies(): Single<MovieResponse>

    /**
     * Get the list of the upcoming movies
     */
    @GET("movie/upcoming")
    fun getUpcomingMovies(): Single<MovieResponse>

    /**
     * Get the list of the now playing movies
     */
    @GET("movie/now_playing")
    fun getNowPlayingMovies(): Single<MovieResponse>
}