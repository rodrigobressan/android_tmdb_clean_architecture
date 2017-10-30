package com.rodrigobresan.data.movie.sources.data_store

import com.rodrigobresan.data.movie.model.MovieEntity
import com.rodrigobresan.domain.model.MovieCategory
import io.reactivex.Completable
import io.reactivex.Single

interface MovieDataStore {

    fun clearMovies() : Completable

    fun saveMovies(movieCategory: MovieCategory, movies: List<MovieEntity>) : Completable

    fun getMovies(category: MovieCategory) : Single<List<MovieEntity>>

}