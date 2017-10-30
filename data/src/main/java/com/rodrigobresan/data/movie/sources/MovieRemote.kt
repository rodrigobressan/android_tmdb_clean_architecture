package com.rodrigobresan.data.movie.sources

import com.rodrigobresan.data.movie.model.MovieEntity
import io.reactivex.Single

interface MovieRemote {
    fun getPopularMovies(): Single<List<MovieEntity>>
    fun getNowPlayingMovies(): Single<List<MovieEntity>>
    fun getTopRatedMovies(): Single<List<MovieEntity>>
    fun getUpcomingMovies(): Single<List<MovieEntity>>
}