package com.rodrigobresan.data.repository.movie

import com.rodrigobresan.data.mapper.MovieMapper
import com.rodrigobresan.data.source.MovieDataStoreFactory
import com.rodrigobresan.data.source.MovieRemoteDataStore
import com.rodrigobresan.domain.model.Movie
import com.rodrigobresan.domain.repository.MovieRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class MovieDataRepository @Inject constructor(private val factory: MovieDataStoreFactory,
                                              private val movieMapper: MovieMapper) : MovieRepository {
    override fun clearMovies(): Completable {
        return factory.retrieveCachedDataStore().clearMovies()
    }

    override fun saveMovies(movies: List<Movie>): Completable {
        val movieEntities = movies.map { movieMapper.mapToEntity(it) }
        return factory.retrieveCachedDataStore().saveMovies(movieEntities)
    }

    override fun getMovies(): Single<List<Movie>> {
        val dataStore = factory.retrieveDataStore()

        return dataStore.getMovies()
                .flatMap {
                    if (dataStore is MovieRemoteDataStore) {
                        factory.retrieveDataStore().saveMovies(it).toSingle({ it })
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