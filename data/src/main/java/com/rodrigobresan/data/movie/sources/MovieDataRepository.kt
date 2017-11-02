package com.rodrigobresan.data.movie.sources

import com.rodrigobresan.data.movie.mapper.MovieMapper
import com.rodrigobresan.data.movie.sources.data_store.MovieDataStoreFactory
import com.rodrigobresan.data.movie.sources.data_store.MovieRemoteDataStore
import com.rodrigobresan.domain.movies.model.Movie
import com.rodrigobresan.domain.movie_category.model.MovieCategory
import com.rodrigobresan.domain.movies.repository.MovieRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

/**
 * MovieRepository implementation
 */
class MovieDataRepository @Inject constructor(private val factory: MovieDataStoreFactory,
                                              private val movieMapper: MovieMapper) : MovieRepository {
    override fun clearMovies(): Completable {
        return factory.retrieveCachedDataStore().clearMovies()
    }

    override fun saveMovies(movieCategory: MovieCategory, movies: List<Movie>): Completable {
        val movieEntities = movies.map { movieMapper.mapToEntity(it) }
        return factory.retrieveCachedDataStore().saveMovies(movieCategory, movieEntities)
    }

    override fun getMovies(movieCategory: MovieCategory): Single<List<Movie>> {
        val dataStore = factory.retrieveDataStore()

        return dataStore.getMovies(movieCategory)
                .flatMap {
                    if (dataStore is MovieRemoteDataStore) {
                        factory.retrieveCachedDataStore().saveMovies(movieCategory, it).toSingle { it }
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