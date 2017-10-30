package com.rodrigobresan.remote.movies.mapper

import com.rodrigobresan.data.movie.model.MovieEntity
import com.rodrigobresan.remote.movies.model.movies.MovieItem
import javax.inject.Inject

open class MovieEntityMapper @Inject constructor() : EntityMapper<MovieItem, MovieEntity> {

    val moviePrefixImage = "https://image.tmdb.org/t/p/w300_and_h450_bestv2"
    override fun mapRemoteToEntity(type: MovieItem): MovieEntity {
        return MovieEntity(type.id, type.title, type.voteAverage, moviePrefixImage + type.posterPath)
    }

}