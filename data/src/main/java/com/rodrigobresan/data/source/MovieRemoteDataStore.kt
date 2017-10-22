package com.rodrigobresan.data.source

import com.rodrigobresan.data.model.MovieEntity
import com.rodrigobresan.data.repository.movie.MovieDataStore
import com.rodrigobresan.data.repository.movie.MovieRemote
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

open class MovieRemoteDataStore @Inject constructor(private val movieRemote: MovieRemote) : MovieDataStore {
    override fun clearMovies(): Completable {
        throw UnsupportedOperationException()
    }

    override fun saveMovies(movies: List<MovieEntity>): Completable {
        throw UnsupportedOperationException()
    }

    override fun getMovies(): Single<List<MovieEntity>> {
        return movieRemote.getMovies()
    }

}