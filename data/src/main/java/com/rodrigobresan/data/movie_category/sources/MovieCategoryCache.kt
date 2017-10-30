package com.rodrigobresan.data.movie_category.sources

import com.rodrigobresan.data.movie_category.model.MovieCategoryEntity
import io.reactivex.Completable
import io.reactivex.Single

interface MovieCategoryCache {
    fun saveMovieCategory(movieCategoryEntity: MovieCategoryEntity): Completable
    fun clearCategories() : Completable
    fun getCategories() : Single<List<MovieCategoryEntity>>
}