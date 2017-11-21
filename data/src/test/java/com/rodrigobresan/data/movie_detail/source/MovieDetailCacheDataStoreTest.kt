package com.rodrigobresan.data.movie.source

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.rodrigobresan.data.movie_category.sources.MovieCategoryCache
import com.rodrigobresan.data.movie_detail.sources.data_store.local.MovieDetailCache
import com.rodrigobresan.data.movie_detail.sources.data_store.local.MovieDetailCacheDataStore
import com.rodrigobresan.data.test.factory.MovieDetailFactory
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Class for testing MovieCacheDataStore class
 */
@RunWith(JUnit4::class)
class MovieDetailCacheDataStoreTest {

    private lateinit var movieCacheDataStore: MovieDetailCacheDataStore
    private lateinit var movieCache: MovieDetailCache
    private lateinit var movieCategoryCache: MovieCategoryCache

    @Before
    fun setUp() {
        movieCategoryCache = mock()
        movieCache = mock()
        movieCacheDataStore = MovieDetailCacheDataStore(movieCache, movieCategoryCache)
    }

    @Test
    fun clearMoviesCompletes() {
        whenever(movieCache.clearMovieDetails())
                .thenReturn(Completable.complete())

        val testObserver = movieCache.clearMovieDetails().test()
        testObserver.assertComplete()
    }

    @Test
    fun saveMovieDetailsCompletes() {
        val movieEntity = MovieDetailFactory.makeMovieDetailEntity()
        whenever(movieCache.saveMovieDetails(movieEntity))
                .thenReturn(Completable.complete())

        movieCacheDataStore.saveMovieDetails(movieEntity).test().assertComplete()
    }

    @Test
    fun getMovieDetailsCompletes() {
        val movieDetail = MovieDetailFactory.makeMovieDetailEntity()
        whenever(movieCache.getMovieDetails(movieDetail.id))
                .thenReturn(Single.just(movieDetail))

        movieCacheDataStore.getMovieDetails(movieDetail.id).test().assertComplete()
    }

    @Test
    fun clearMovieDetailsCompletes() {
        whenever(movieCache.clearMovieDetails())
                .thenReturn(Completable.complete())

        movieCacheDataStore.clearMovieDetails().test().assertComplete()
    }
}