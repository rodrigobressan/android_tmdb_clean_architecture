package com.rodrigobresan.remote.service

import com.rodrigobresan.remote.model.response.movies_list.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET

interface MovieService {

    @GET("movie/popular")
    fun getPopularMovies(): Single<MovieResponse>
}