package com.rodrigobresan.data.repository.movie.movie

import com.rodrigobresan.domain.model.MovieCategory
import com.rodrigobresan.data.model.MovieEntity
import io.reactivex.Completable
import io.reactivex.Single

interface MovieDataStore {

    fun clearMovies() : Completable

    fun saveMovies(movieCategory: MovieCategory, movies: List<MovieEntity>) : Completable

    fun getMovies(category: MovieCategory) : Single<List<MovieEntity>>

}