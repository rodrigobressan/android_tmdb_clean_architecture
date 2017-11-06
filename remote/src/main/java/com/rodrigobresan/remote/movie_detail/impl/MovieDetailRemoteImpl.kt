package com.rodrigobresan.remote.movie_detail.impl

import com.rodrigobresan.data.movie.sources.MovieRemote
import com.rodrigobresan.data.movie_detail.model.MovieDetailEntity
import com.rodrigobresan.data.movie_detail.sources.MovieDetailRemote
import com.rodrigobresan.remote.movie_detail.mapper.MovieDetailEntityMapper
import com.rodrigobresan.remote.service.MovieService
import io.reactivex.Single
import javax.inject.Inject

class MovieDetailRemoteImpl @Inject constructor(private val movieService: MovieService,
                                                private val movieDetailEntityMapper: MovieDetailEntityMapper) : MovieDetailRemote {
    override fun getMovieDetails(movieId: Long): Single<MovieDetailEntity> {
        return movieService.getMovieDetails(movieId).map { movieDetailEntityMapper.mapRemoteToEntity(it) }
    }

}