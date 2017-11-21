package com.rodrigobresan.data.movie_detail.mapper

import com.rodrigobresan.data.base.mapper.DataMapper
import com.rodrigobresan.data.movie_detail.model.MovieDetailEntity
import com.rodrigobresan.domain.movie_detail.model.MovieDetail
import javax.inject.Inject

open class MovieDetailMapper @Inject constructor() : DataMapper<MovieDetailEntity, MovieDetail> {
    override fun mapFromEntity(entity: MovieDetailEntity): MovieDetail {
        return MovieDetail(entity.id, entity.title, entity.voteAverage, entity.posterPath,
                entity.backdropPath, entity.overview, entity.tagline, entity.isFavorite)
    }

    override fun mapToEntity(model: MovieDetail): MovieDetailEntity {
        val movieDetailEntity = MovieDetailEntity(model.id, model.title, model.voteAverage, model.posterPath,
                model.backdropPath, model.overview, model.tagline)

        movieDetailEntity.isFavorite = model.isFavorite
        return movieDetailEntity
    }

}