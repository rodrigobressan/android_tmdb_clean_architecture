package com.rodrigobresan.remote.movie_detail.mapper

import com.rodrigobresan.data.movie_detail.model.MovieDetailEntity
import com.rodrigobresan.remote.EntityMapper
import com.rodrigobresan.remote.movie_detail.model.MovieDetailResponse
import javax.inject.Inject

/**
 * Class used to map movie detail entity between then remote and data layers
 */
open class MovieDetailRemoteMapper @Inject constructor() : EntityMapper<MovieDetailResponse, MovieDetailEntity> {

    val moviePrefixImage = "https://image.tmdb.org/t/p/original"
    override fun mapRemoteToEntity(type: MovieDetailResponse): MovieDetailEntity {
        return MovieDetailEntity(type.id, type.title, type.voteAverage, moviePrefixImage + type.posterPath,
                moviePrefixImage + type.backdropPath, type.overview, type.tagline)
    }

}