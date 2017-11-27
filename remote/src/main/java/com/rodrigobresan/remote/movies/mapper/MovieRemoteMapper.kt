package com.rodrigobresan.remote.movies.mapper

import com.rodrigobresan.data.movie.model.MovieEntity
import com.rodrigobresan.remote.EntityMapper
import com.rodrigobresan.remote.movies.model.movies.MovieItem
import javax.inject.Inject

/**
 * Maps a Movie from remote (API) to our model
 */
open class MovieRemoteMapper @Inject constructor() : EntityMapper<MovieItem, MovieEntity> {

    val moviePrefixImage = "https://image.tmdb.org/t/p/w342"
    override fun mapRemoteToEntity(type: MovieItem): MovieEntity {
        return MovieEntity(type.id, type.title, type.voteAverage, moviePrefixImage + type.posterPath)
    }

}