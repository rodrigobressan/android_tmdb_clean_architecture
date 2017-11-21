package com.rodrigobresan.remote.movie_detail.impl

import com.rodrigobresan.data.movie_detail.model.MovieDetailEntity
import com.rodrigobresan.data.movie_detail.sources.data_store.remote.MovieDetailRemote
import com.rodrigobresan.remote.movie_detail.mapper.MovieDetailRemoteMapper
import com.rodrigobresan.remote.service.MovieService
import io.reactivex.Single
import javax.inject.Inject

class MovieDetailRemoteImpl @Inject constructor(private val movieService: MovieService,
                                                private val movieDetailRemoteMapper: MovieDetailRemoteMapper) : MovieDetailRemote {
    override fun getMovieDetails(movieId: Long): Single<MovieDetailEntity> {
        return movieService.getMovieDetails(movieId).map { movieDetailRemoteMapper.mapRemoteToEntity(it) }
    }

}