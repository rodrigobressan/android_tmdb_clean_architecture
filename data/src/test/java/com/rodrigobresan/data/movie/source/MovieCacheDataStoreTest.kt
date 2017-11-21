package com.rodrigobresan.data.movie.source

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.rodrigobresan.data.category.sources.CategoryCache
import com.rodrigobresan.data.movie.sources.data_store.local.MovieCache
import com.rodrigobresan.data.movie.sources.data_store.local.MovieCacheDataStore
import com.rodrigobresan.data.movie_category.sources.MovieCategoryCache
import com.rodrigobresan.data.test.factory.MovieFactory
import com.rodrigobresan.domain.movie_category.model.Category
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
class MovieCacheDataStoreTest {

    private lateinit var movieCacheDataStore: MovieCacheDataStore
    private lateinit var movieCache: MovieCache
    private lateinit var movieCategoryCache: MovieCategoryCache
    private lateinit var categoryCache: CategoryCache

    @Before
    fun setUp() {
        movieCache = mock()
        categoryCache = mock()
        movieCategoryCache = mock()

        movieCacheDataStore = MovieCacheDataStore(categoryCache, movieCategoryCache, movieCache)
    }

    @Test
    fun clearMoviesCompletes() {
        whenever(movieCache.clearMovies())
                .thenReturn(Completable.complete())

        val testObserver = movieCacheDataStore.clearMovies().test()
        testObserver.assertComplete()
    }

    @Test
    fun saveMoviesCompletes() {
        val movieCategory = Category.POPULAR
        val movieEntityList = MovieFactory.makeMovieEntityList(2)
        whenever(movieCache.saveMovies(movieCategory, movieEntityList))
                .thenReturn(Completable.complete())

        whenever(movieCategoryCache.saveMovieCategory(any()))
                .thenReturn(Completable.complete())

        whenever(categoryCache.saveCategory(any()))
                .thenReturn(Completable.complete())

        movieCacheDataStore.saveMovies(movieCategory, movieEntityList).test().assertComplete()
    }

    @Test
    fun getMoviesCompletes() {
        val movieCategory = Category.POPULAR
        val movies = MovieFactory.makeMovieEntityList(2)
        whenever(movieCache.getMovies(movieCategory))
                .thenReturn(Single.just(movies))

        movieCacheDataStore.getMovies(movieCategory).test().assertComplete()
    }

}