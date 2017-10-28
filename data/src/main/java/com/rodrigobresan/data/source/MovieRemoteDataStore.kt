package com.rodrigobresan.data.source

import com.rodrigobresan.data.model.MovieEntity
import com.rodrigobresan.data.repository.movie.movie.MovieDataStore
import com.rodrigobresan.data.repository.movie.movie.MovieRemote
import com.rodrigobresan.domain.model.MovieCategory
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

open class MovieRemoteDataStore @Inject constructor(private val movieRemote: MovieRemote) : MovieDataStore {
    override fun clearMovies(): Completable {
        throw UnsupportedOperationException()
    }

    override fun saveMovies(movieCategory: MovieCategory, movies: List<MovieEntity>): Completable {
        throw UnsupportedOperationException()
    }

    override fun getMovies(movieCategory: MovieCategory): Single<List<MovieEntity>> {
        when (movieCategory) {
            MovieCategory.POPULAR -> return movieRemote.getPopularMovies()
            MovieCategory.TOP_RATED -> return movieRemote.getTopRatedMovies()
            MovieCategory.NOW_PLAYING -> return movieRemote.getNowPlayingMovies()
            MovieCategory.UPCOMING -> return movieRemote.getUpcomingMovies()
        }

        return movieRemote.getTopRatedMovies()
    }

}