package com.rodrigobresan.data.movie_detail.sources

import com.rodrigobresan.data.movie_detail.mapper.MovieDetailMapper
import com.rodrigobresan.data.movie_detail.sources.data_store.MovieDetailDataStoreFactory
import com.rodrigobresan.data.movie_detail.sources.data_store.remote.MovieDetailRemoteDataStore
import com.rodrigobresan.domain.movie_detail.model.MovieDetail
import com.rodrigobresan.domain.movie_detail.repository.MovieDetailRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class MovieDetailDataRepository @Inject constructor(private val factory: MovieDetailDataStoreFactory,
                                                    private val movieDetailMapper: MovieDetailMapper) : MovieDetailRepository {
    override fun clearMovieDetails(): Completable {
        return factory.retrieveCachedDataStore().clearMovieDetails()
    }

    override fun saveMovieDetail(movie: MovieDetail): Completable {
        return factory.retrieveCachedDataStore().saveMovieDetails(movieDetailMapper.mapToEntity(movie))
    }

    override fun getMovieDetails(movieId: Long): Single<MovieDetail> {
        val dataStore = factory.retrieveDataStore(movieId)

        return dataStore.getMovieDetails(movieId)
                .flatMap {
                    if (dataStore is MovieDetailRemoteDataStore) {
                        factory.retrieveCachedDataStore().saveMovieDetails(it).toSingle { it }
                    } else {
                        Single.just(it)
                    }
                }
                .map {
                    movieDetailMapper.mapFromEntity(it)
                }
    }

}