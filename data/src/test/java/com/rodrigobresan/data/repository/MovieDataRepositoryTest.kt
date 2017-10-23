package com.rodrigobresan.data.repository

import com.nhaarman.mockito_kotlin.*
import com.rodrigobresan.data.mapper.MovieMapper
import com.rodrigobresan.data.model.MovieEntity
import com.rodrigobresan.data.repository.movie.MovieDataRepository
import com.rodrigobresan.data.source.MovieCacheDataStore
import com.rodrigobresan.data.source.MovieDataStoreFactory
import com.rodrigobresan.data.source.MovieRemoteDataStore
import com.rodrigobresan.data.test.factory.MovieFactory
import com.rodrigobresan.domain.model.Movie
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MovieDataRepositoryTest {

    private lateinit var movieDataRepository: MovieDataRepository

    private lateinit var movieDataStoreFactory: MovieDataStoreFactory
    private lateinit var movieMapper: MovieMapper

    private lateinit var movieCacheDataStore: MovieCacheDataStore
    private lateinit var movieRemoteDataStore: MovieRemoteDataStore

    @Before
    fun setUp() {
        movieDataStoreFactory = mock()

        movieMapper = mock()

        movieCacheDataStore = mock()
        movieRemoteDataStore = mock()

        movieDataRepository = MovieDataRepository(movieDataStoreFactory, movieMapper)

        stubMovieDataStoreFactoryRetrieveCacheDataStore()
        stubMovieDataStoreFactoryRetrieveRemoteDataStore()
    }

    // clear functions
    @Test
    fun clearMoviesCompletes() {
        stubMovieCacheClearMovies(Completable.complete())

        val testObserver = movieDataRepository.clearMovies().test()
        testObserver.assertComplete()
    }

    @Test
    fun clearMoviesCallsCacheDataStore() {
        stubMovieCacheClearMovies(Completable.complete())

        movieDataRepository.clearMovies().test()
        verify(movieCacheDataStore).clearMovies()
    }

    @Test
    fun clearMoviesNeverCallRemoteDataStore() {
        stubMovieCacheClearMovies(Completable.complete())

        movieDataRepository.clearMovies().test()
        verify(movieRemoteDataStore, never()).clearMovies()
    }

    // related to save
    @Test
    fun saveMoviesCompletes() {
        stubMovieCacheSaveMovies(Completable.complete())

        val testObserver = movieDataRepository.saveMovies(MovieFactory.makeMovieList(2)).test()
        testObserver.assertComplete()
    }

    @Test
    fun saveMovieCallsCacheDataStore() {
        stubMovieCacheSaveMovies(Completable.complete())

        val movies = MovieFactory.makeMovieList(2)
        movieDataRepository.saveMovies(movies).test()
        verify(movieCacheDataStore).saveMovies(any())
    }

    @Test
    fun saveMovieNeverCallRemoteDataStore() {
        stubMovieCacheSaveMovies(Completable.complete())

        movieDataRepository.saveMovies(MovieFactory.makeMovieList(2)).test()
        verify(movieRemoteDataStore, never()).saveMovies(any())
    }

    // related to get
    @Test
    fun getMoviesCompletes() {
        stubMovieDataStoreFactoryRetrieveDataStore(movieCacheDataStore)
        stubMovieCacheDataStoreGetMovies(Single.just(MovieFactory.makeMovieEntityList(2)))

        val testObserver = movieDataRepository.getMovies().test()
        testObserver.assertComplete()
    }

    @Test
    fun getMoviesReturnsData() {
        stubMovieDataStoreFactoryRetrieveDataStore(movieCacheDataStore)
        val movies = MovieFactory.makeMovieList(2)
        val moviesEntities = MovieFactory.makeMovieEntityList(2)

        movies.forEachIndexed { index, movie ->
            stubMovieMapperMapFromEntity(moviesEntities[index], movie)
        }

        stubMovieCacheDataStoreGetMovies(Single.just(moviesEntities))

        val testObserver = movieDataRepository.getMovies().test()
        testObserver.assertValue(movies)
    }

    @Test
    fun getMoviesSavesMoviesWhenFromCacheDataStore() {
        stubMovieDataStoreFactoryRetrieveDataStore(movieCacheDataStore)
        stubMovieCacheSaveMovies(Completable.complete())

        movieDataRepository.saveMovies(MovieFactory.makeMovieList(2)).test()
        verify(movieCacheDataStore).saveMovies(any())
    }

    @Test
    fun getMoviesNeverSavedMoviesWhenRemoteDataStore() {
        stubMovieDataStoreFactoryRetrieveDataStore(movieCacheDataStore)
        stubMovieCacheSaveMovies(Completable.complete())

        movieDataRepository.saveMovies(MovieFactory.makeMovieList(2)).test()
        verify(movieRemoteDataStore, never()).saveMovies(any())
    }


    private fun stubMovieMapperMapFromEntity(movieEntity: MovieEntity, movie: Movie) {
        whenever(movieMapper.mapFromEntity(movieEntity))
                .thenReturn(movie)
    }

    private fun stubMovieCacheDataStoreGetMovies(singleMovies: Single<List<MovieEntity>>?) {
        whenever(movieCacheDataStore.getMovies())
                .thenReturn(singleMovies)
    }

    private fun stubMovieDataStoreFactoryRetrieveDataStore(movieCacheDataStore: MovieCacheDataStore) {
        whenever(movieDataStoreFactory.retrieveDataStore())
                .thenReturn(movieCacheDataStore)
    }


    private fun stubMovieCacheSaveMovies(complete: Completable?) {
        whenever(movieCacheDataStore.saveMovies(any()))
                .thenReturn(complete)
    }

    private fun stubMovieCacheClearMovies(complete: Completable?) {
        whenever(movieCacheDataStore.clearMovies())
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