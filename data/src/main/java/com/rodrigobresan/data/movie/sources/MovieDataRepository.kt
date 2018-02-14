package com.rodrigobresan.data.movie.sources

import com.rodrigobresan.data.movie.mapper.MovieMapper
import com.rodrigobresan.data.movie.sources.data_store.MovieDataStoreFactory
import com.rodrigobresan.data.movie.sources.data_store.remote.MovieRemoteDataStore
import com.rodrigobresan.data.movie_category.model.MovieCategoryEntity
import com.rodrigobresan.domain.movie_category.model.Category
import com.rodrigobresan.domain.movies.model.Movie
import com.rodrigobresan.domain.movies.repository.MovieRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

/**
 * MovieRepository implementation
 */
class MovieDataRepository @Inject constructor(private val factory: MovieDataStoreFactory,
                                              private val movieMapper: MovieMapper) : MovieRepository {

    override fun deleteMovie(category: Category, movie: Movie): Completable {
        return factory.retrieveCachedDataStore().deleteMovieFromCategory(MovieCategoryEntity(movie.id, category.name))
    }

    override fun getMovie(movieId: Long): Single<Movie> {
        val dataStore = factory.retrieveCachedDataStore()
        return dataStore.getMovie(movieId)
                .map {
                    movieMapper.mapFromEntity(it)
                }
    }

    override fun clearMovies(): Completable {
        return factory.retrieveCachedDataStore().clearMovies()
    }

    override fun saveMovies(category: Category, movies: List<Movie>): Completable {
        val movieEntities = movies.map { movieMapper.mapToEntity(it) }
        return factory.retrieveCachedDataStore().saveMovies(category, movieEntities)
    }

    override fun getMovies(category: Category): Single<List<Movie>> {
        val dataStore = factory.retrieveDataStore()

        return dataStore.getMovies(category)
                .flatMap {
                    if (dataStore is MovieRemoteDataStore) {
                        factory.retrieveCachedDataStore().saveMovies(category, it).toSingle { it }
                    } else {
                        Single.just(it)
                    }
                }
                .map {
                    list ->
                    list.map {
                        listItem ->
                        movieMapper.mapFromEntity(listItem)
                    }
                }
    }

}