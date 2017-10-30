package com.rodrigobresan.data.repository.movie.movie.movie_category

import com.rodrigobresan.data.model.MovieCategoryEntity
import com.rodrigobresan.data.model.MovieEntity
import io.reactivex.Completable
import io.reactivex.Single

interface MovieCategoryCache {
    fun saveMovieCategory(movieCategoryEntity: MovieCategoryEntity): Completable
    fun clearCategories() : Completable
    fun getCategories() : Single<List<MovieCategoryEntity>>
}