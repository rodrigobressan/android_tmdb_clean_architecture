package com.rodrigobresan.data.movie.repository

import com.nhaarman.mockito_kotlin.*
import com.rodrigobresan.data.movie.mapper.MovieMapper
import com.rodrigobresan.data.movie.model.MovieEntity
import com.rodrigobresan.data.movie.sources.MovieDataRepository
import com.rodrigobresan.data.movie.sources.data_store.MovieDataStoreFactory
import com.rodrigobresan.data.movie.sources.data_store.local.MovieCacheDataStore
import com.rodrigobresan.data.movie.sources.data_store.remote.MovieRemoteDataStore
import com.rodrigobresan.data.test.factory.MovieFactory
import com.rodrigobresan.domain.movie_category.model.Category
import com.rodrigobresan.domain.movies.model.Movie
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
        val movieCategory = Category.POPULAR
        val movieList = MovieFactory.makeMovieList(2)
        stubMovieCacheSaveMovies(Completable.complete())

        val testObserver = movieDataRepository.saveMovies(movieCategory, movieList).test()
        testObserver.assertComplete()
    }

    @Test
    fun saveMovieCallsCacheDataStore() {

        val movieCategory = Category.POPULAR
        stubMovieCacheSaveMovies(Completable.complete())

        val movies = MovieFactory.makeMovieList(2)
        movieDataRepository.saveMovies(movieCategory, movies).test()
        verify(movieCacheDataStore).saveMovies(any(), any())
    }

    @Test
    fun saveMovieNeverCallRemoteDataStore() {
        val movieCategory = Category.POPULAR
        stubMovieCacheSaveMovies(Completable.complete())

        movieDataRepository.saveMovies(movieCategory, MovieFactory.makeMovieList(2)).test()
        verify(movieRemoteDataStore, never()).saveMovies(any(), any())
    }

    // related to get
    @Test
    fun getMoviesCompletes() {
        val movieCategory = Category.POPULAR
        stubMovieDataStoreFactoryRetrieveDataStore(movieCacheDataStore)
        stubMovieCacheDataStoreGetMovies(movieCategory, Single.just(MovieFactory.makeMovieEntityList(2)))

        val testObserver = movieDataRepository.getMovies(movieCategory).test()
        testObserver.assertComplete()
    }

    @Test
    fun getMoviesReturnsData() {
        val movieCategory = Category.POPULAR
        stubMovieDataStoreFactoryRetrieveDataStore(movieCacheDataStore)
        val movies = MovieFactory.makeMovieList(2)
        val moviesEntities = MovieFactory.makeMovieEntityList(2)

        movies.forEachIndexed { index, movie ->
            stubMovieMapperMapFromEntity(moviesEntities[index], movie)
        }

        stubMovieCacheDataStoreGetMovies(movieCategory, Single.just(moviesEntities))

        val testObserver = movieDataRepository.getMovies(movieCategory).test()
        testObserver.assertValue(movies)
    }

    @Test
    fun getMoviesSavesMoviesWhenFromCacheDataStore() {
        val movieCategory = Category.POPULAR
        stubMovieDataStoreFactoryRetrieveDataStore(movieCacheDataStore)
        stubMovieCacheSaveMovies(Completable.complete())

        movieDataRepository.saveMovies(movieCategory, MovieFactory.makeMovieList(2)).test()
        verify(movieCacheDataStore).saveMovies(any(), any())
    }

    @Test
    fun getMoviesNeverSavedMoviesWhenRemoteDataStore() {
        val movieCategory = Category.POPULAR
        stubMovieDataStoreFactoryRetrieveDataStore(movieCacheDataStore)
        stubMovieCacheSaveMovies(Completable.complete())

        movieDataRepository.saveMovies(movieCategory, MovieFactory.makeMovieList(2)).test()
        verify(movieRemoteDataStore, never()).saveMovies(any(), any())
    }


    @Test
    fun getMoviesUsingRemoteDataStore() {
        whenever(movieDataStoreFactory.retrieveDataStore())
                .thenReturn(movieRemoteDataStore)

        whenever(movieRemoteDataStore.getMovies(any()))
                .thenReturn(Single.just(MovieFactory.makeMovieEntityList(2)))

        movieDataRepository.getMovies(Category.TOP_RATED).test()
    }

    private fun stubMovieMapperMapFromEntity(movieEntity: MovieEntity, movie: Movie) {
        whenever(movieMapper.mapFromEntity(movieEntity))
                .thenReturn(movie)
    }

    private fun stubMovieCacheDataStoreGetMovies(category: Category, singleMovies: Single<List<MovieEntity>>?) {
        whenever(movieCacheDataStore.getMovies(category))
                .thenReturn(singleMovies)
    }

    private fun stubMovieDataStoreFactoryRetrieveDataStore(movieCacheDataStore: MovieCacheDataStore) {
        whenever(movieDataStoreFactory.retrieveDataStore())
                .thenReturn(movieCacheDataStore)
    }

    private fun stubMovieCacheSaveMovies(complete: Completable?) {
        whenever(movieCacheDataStore.saveMovies(any(), any()))
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