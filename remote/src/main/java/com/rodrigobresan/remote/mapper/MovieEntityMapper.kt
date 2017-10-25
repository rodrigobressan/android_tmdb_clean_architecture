package com.rodrigobresan.remote.mapper

import com.rodrigobresan.data.model.MovieEntity
import com.rodrigobresan.remote.model.response.movies_list.MovieModel
import javax.inject.Inject

open class MovieEntityMapper @Inject constructor() : EntityMapper<MovieModel, MovieEntity> {
    override fun mapRemoteToEntity(type: MovieModel): MovieEntity {
        val prefixImage = "https://image.tmdb.org/t/p/w300_and_h450_bestv2"
        return MovieEntity(type.id, type.title, type.voteAverage, prefixImage + type.posterPath)
    }

}