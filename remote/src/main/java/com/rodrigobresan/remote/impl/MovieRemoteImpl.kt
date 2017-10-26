package com.rodrigobresan.remote.impl

import com.rodrigobresan.data.model.MovieEntity
import com.rodrigobresan.data.repository.movie.movie.MovieRemote
import com.rodrigobresan.remote.mapper.MovieEntityMapper
import com.rodrigobresan.remote.model.response.movies_list.MovieResponse
import com.rodrigobresan.remote.service.MovieService
import io.reactivex.Single
import javax.inject.Inject

class MovieRemoteImpl @Inject constructor(private val service: MovieService,
                                          private val movieEntityMapper: MovieEntityMapper)
    : MovieRemote {

    override fun getNowPlayingMovies(): Single<List<MovieEntity>> {
        return mapItems(service.getNowPlayingMovies())
    }

    override fun getLatestMovies(): Single<List<MovieEntity>> {
        return mapItems(service.getLatestMovies())
    }

    override fun getTopRatedMovies(): Single<List<MovieEntity>> {
        return mapItems(service.getTopRatedMovies())
    }

    override fun getUpcomingMovies(): Single<List<MovieEntity>> {
        return mapItems(service.getUpcomingMovies())
    }

    override fun getPopularMovies(): Single<List<MovieEntity>> {
        return mapItems(service.getPopularMovies())
    }

    private fun mapItems(movieResponse: Single<MovieResponse>): Single<List<MovieEntity>> {
        return movieResponse.map {
            it.results.map {
                item ->
                movieEntityMapper.mapRemoteToEntity(item)
            }
        }
    }

}