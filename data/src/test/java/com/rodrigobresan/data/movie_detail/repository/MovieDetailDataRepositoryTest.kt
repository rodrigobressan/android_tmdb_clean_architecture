package com.rodrigobresan.data.movie.repository

import com.nhaarman.mockito_kotlin.*
import com.rodrigobresan.data.movie_detail.mapper.MovieDetailMapper
import com.rodrigobresan.data.movie_detail.model.MovieDetailEntity
import com.rodrigobresan.data.movie_detail.sources.MovieDetailDataRepository
import com.rodrigobresan.data.movie_detail.sources.data_store.MovieDetailDataStoreFactory
import com.rodrigobresan.data.movie_detail.sources.data_store.local.MovieDetailCacheDataStore
import com.rodrigobresan.data.movie_detail.sources.data_store.remote.MovieDetailRemoteDataStore
import com.rodrigobresan.data.test.factory.MovieDetailFactory
import com.rodrigobresan.domain.movie_detail.model.MovieDetail
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Class for testing MovieDataRepository class
 */
@RunWith(JUnit4::class)
class MovieDetailDataRepositoryTest {

    private lateinit var movieDataRepository: MovieDetailDataRepository

    private lateinit var movieDataStoreFactory: MovieDetailDataStoreFactory
    private lateinit var movieMapper: MovieDetailMapper

    private lateinit var movieCacheDataStore: MovieDetailCacheDataStore
    private lateinit var movieRemoteDataStore: MovieDetailRemoteDataStore

    @Before
    fun setUp() {
        movieDataStoreFactory = mock()

        movieMapper = mock()

        movieCacheDataStore = mock()
        movieRemoteDataStore = mock()

        movieDataRepository = MovieDetailDataRepository(movieDataStoreFactory, movieMapper)

        stubMovieDataStoreFactoryRetrieveCacheDataStore()
        stubMovieDataStoreFactoryRetrieveRemoteDataStore()
    }

    // clear functions
    @Test
    fun clearMoviesCompletes() {
        stubMovieCacheClearMovies(Completable.complete())

        val testObserver = movieDataRepository.clearMovieDetails().test()
        testObserver.assertComplete()
    }

    // related to save
    @Test
    fun saveMovieDetailsCompletes() {
        val movie = MovieDetailFactory.makeMovieDetail()
        stubMovieCacheSaveMovieDetails(movie, Completable.complete())

        val testObserver = movieDataRepository.saveMovieDetail(movie).test()
        testObserver.assertComplete()
    }


    @Test
    fun saveMovieCallsCacheDataStore() {
        val movie = MovieDetailFactory.makeMovieDetail()
        stubMovieCacheSaveMovieDetails(movie, Completable.complete())

        movieDataRepository.saveMovieDetail(movie).test()
        verify(movieCacheDataStore).saveMovieDetails(movieMapper.mapToEntity(movie))
    }

    @Test
    fun saveMovieNeverCallRemoteDataStore() {
        val movie = MovieDetailFactory.makeMovieDetail()
        stubMovieCacheSaveMovieDetails(movie, Completable.complete())

        movieDataRepository.saveMovieDetail(movie).test()
        verify(movieRemoteDataStore, never()).saveMovieDetails(any())
    }

    // related to get
    @Test
    fun getMoviesCompletes() {
        val movie = MovieDetailFactory.makeMovieDetailEntity()
        stubMovieDataStoreFactoryRetrieveDataStore(movieCacheDataStore)
        stubMovieCacheDataStoreGetMovieDetails(Single.just(movie))

        val testObserver = movieDataRepository.getMovieDetails(movie.id).test()
        testObserver.assertComplete()
    }

    @Test
    fun getMoviesReturnsData() {
        stubMovieDataStoreFactoryRetrieveDataStore(movieCacheDataStore)
        val movie = MovieDetailFactory.makeMovieDetail()
        val moviesEntity = MovieDetailFactory.makeMovieDetailEntity()

        stubMovieMapperMapFromEntity(moviesEntity, movie)

        stubMovieCacheDataStoreGetMovieDetails(Single.just(moviesEntity))

        val testObserver = movieDataRepository.getMovieDetails(moviesEntity.id).test()
        testObserver.assertValue(movie)
    }

    @Test
    fun getMoviesSavesMoviesWhenFromCacheDataStore() {
        val movie = MovieDetailFactory.makeMovieDetail()
        stubMovieDataStoreFactoryRetrieveDataStore(movieCacheDataStore)
        stubMovieCacheSaveMovieDetails(movie, Completable.complete())

        movieDataRepository.saveMovieDetail(movie).test()
        verify(movieCacheDataStore).saveMovieDetails(movieMapper.mapToEntity(movie))
    }

    @Test
    fun getMoviesNeverSavedMoviesWhenRemoteDataStore() {
        val movie = MovieDetailFactory.makeMovieDetail()
        stubMovieDataStoreFactoryRetrieveDataStore(movieCacheDataStore)
        stubMovieCacheSaveMovieDetails(movie, Completable.complete())

        movieDataRepository.saveMovieDetail(movie).test()
        verify(movieRemoteDataStore, never()).saveMovieDetails(any())
    }

    private fun stubMovieMapperMapFromEntity(movieEntity: MovieDetailEntity, movie: MovieDetail) {
        whenever(movieMapper.mapFromEntity(movieEntity))
                .thenReturn(movie)
    }

    private fun stubMovieCacheDataStoreGetMovieDetails(singleMovieDetails: Single<MovieDetailEntity>) {
        whenever(movieCacheDataStore.getMovieDetails(any()))
                .thenReturn(singleMovieDetails)
    }

    private fun stubMovieDataStoreFactoryRetrieveDataStore(movieCacheDataStore: MovieDetailCacheDataStore) {
        whenever(movieDataStoreFactory.retrieveDataStore(any()))
                .thenReturn(movieCacheDataStore)
    }

    private fun stubMovieCacheSaveMovieDetails(movie: MovieDetail, complete: Completable?) {
        whenever(movieCacheDataStore.saveMovieDetails(movieMapper.mapToEntity(movie)))
                .thenReturn(complete)
    }
//
//    private fun stubMovieCacheSaveMovies(complete: Completable) {
//        whenever(movieCacheDataStore.saveMovieDetails(any()))
//                .thenReturn(complete)
//    }

    private fun stubMovieCacheClearMovies(complete: Completable?) {
        whenever(movieCacheDataStore.clearMovieDetails())
                .thenReturn(complete)
    }

    private fun stubMovieDataStoreFactoryRetrieveCacheDataStore() {
        whenever(movieDataStoreFactory.retrieveCachedDataStore())
                .thenReturn(movieCacheDataStore)
    }

    private fun stubMovieDataStoreFactoryRetrieveRemoteDataStore() {
        whenever(movieDataStoreFactory.retrieveRemoteDataStore())
                .thenReturn(movieRemoteDataStore)
    }

}