package com.rodrigobresan.data.movie.sources.data_store.remote

import com.rodrigobresan.data.movie.model.MovieEntity
import com.rodrigobresan.data.movie.sources.data_store.MovieDataStore
import com.rodrigobresan.data.movie_category.model.MovieCategoryEntity
import com.rodrigobresan.domain.movie_category.model.Category
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject


open class MovieRemoteDataStore @Inject constructor(private val movieRemote: MovieRemote) : MovieDataStore {
    override fun deleteMovieFromCategory(movieCategoryEntity: MovieCategoryEntity): Completable {
        throw UnsupportedOperationException()
    }

    override fun getMovie(movieId: Long): Single<MovieEntity> {
        throw UnsupportedOperationException()
    }

    override fun clearMovies(): Completable {
        throw UnsupportedOperationException()
    }

    override fun saveMovies(category: Category, movies: List<MovieEntity>): Completable {
        throw UnsupportedOperationException()
    }

    override fun getMovies(category: Category): Single<List<MovieEntity>> {
        when (category) {
            Category.POPULAR -> return movieRemote.getPopularMovies()
            Category.TOP_RATED -> return movieRemote.getTopRatedMovies()
            Category.NOW_PLAYING -> return movieRemote.getNowPlayingMovies()
            Category.UPCOMING -> return movieRemote.getUpcomingMovies()

            else -> {
                return Single.just(emptyList())
            }
        }
    }

}