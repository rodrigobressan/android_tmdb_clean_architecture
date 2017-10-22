package com.rodrigobresan.data.repository.movie

import com.rodrigobresan.data.model.MovieEntity
import io.reactivex.Completable
import io.reactivex.Single

interface MovieDataStore {

    fun clearMovies() : Completable

    fun saveMovies(movies: List<MovieEntity>) : Completable

    fun getMovies() : Single<List<MovieEntity>>

}