package com.rodrigobresan.remote.service

import com.rodrigobresan.remote.model.response.movies_list.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET

interface MovieService {

    @GET("movie/popular")
    fun getPopularMovies(): Single<MovieResponse>

    @GET("movie/latest")
    fun getLatestMovies(): Single<MovieResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovies(): Single<MovieResponse>

    @GET("movie/upcoming")
    fun getUpcomingMovies(): Single<MovieResponse>

    @GET("movie/now_playing")
    fun getNowPlayingMovies(): Single<MovieResponse>
}