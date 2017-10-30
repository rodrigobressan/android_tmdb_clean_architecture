package com.rodrigobresan.data.movie.sources.data_store

import com.rodrigobresan.data.movie.model.MovieEntity
import com.rodrigobresan.data.movie.sources.MovieRemote
import com.rodrigobresan.domain.movie_category.model.MovieCategory
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

        throw UnsupportedOperationException()
    }

}