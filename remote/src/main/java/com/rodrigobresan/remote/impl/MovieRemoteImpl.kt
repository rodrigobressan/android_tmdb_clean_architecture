package com.rodrigobresan.remote.impl

import com.rodrigobresan.data.model.MovieEntity
import com.rodrigobresan.data.repository.movie.MovieRemote
import com.rodrigobresan.remote.mapper.MovieEntityMapper
import com.rodrigobresan.remote.service.MovieService
import io.reactivex.Single
import javax.inject.Inject

class MovieRemoteImpl @Inject constructor(private val service: MovieService,
                                          private val movieEntityMapper: MovieEntityMapper)
    : MovieRemote {
    override fun getMovies(): Single<List<MovieEntity>> {
        return service.getPopularMovies()
                .map {
                    it.results.map {
                        item -> movieEntityMapper.mapRemoteToEntity(item)
                    }
                }
    }

}