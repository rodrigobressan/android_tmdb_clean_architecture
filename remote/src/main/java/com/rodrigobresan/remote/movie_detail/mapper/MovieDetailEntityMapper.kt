package com.rodrigobresan.remote.movie_detail.mapper

import com.rodrigobresan.data.movie_detail.model.MovieDetailEntity
import com.rodrigobresan.remote.EntityMapper
import com.rodrigobresan.remote.movie_detail.model.MovieDetailResponse
import javax.inject.Inject

open class MovieDetailEntityMapper @Inject constructor() : EntityMapper<MovieDetailResponse, MovieDetailEntity> {
    override fun mapRemoteToEntity(type: MovieDetailResponse): MovieDetailEntity {
        return MovieDetailEntity(type.id, type.title, type.voteAverage, type.posterPath, type.backdropPath)
    }

}