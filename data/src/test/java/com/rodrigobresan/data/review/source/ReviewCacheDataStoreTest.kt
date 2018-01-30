package com.rodrigobresan.data.movie.source

import com.nhaarman.mockito_kotlin.*
import com.rodrigobresan.data.category.sources.CategoryCache
import com.rodrigobresan.data.movie.sources.data_store.local.MovieCache
import com.rodrigobresan.data.movie.sources.data_store.local.MovieCacheDataStore
import com.rodrigobresan.data.movie_category.sources.MovieCategoryCache
import com.rodrigobresan.data.test.factory.MovieCategoryFactory
import com.rodrigobresan.data.test.factory.MovieFactory
import com.rodrigobresan.domain.movie_category.model.Category
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Class for testing MovieCacheDataStore class
 */
@RunWith(JUnit4::class)
class ReviewCacheDataStoreTest {

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

    @Test
    fun deleteMoviesCompletes() {
        val movieCategoryEntity = MovieCategoryFactory.makeMovieCategoryEntity()

        whenever(movieCategoryCache.deleteMovieFromCategory(movieCategoryEntity))
                .thenReturn(Completable.complete())

        movieCacheDataStore.deleteMovieFromCategory(movieCategoryEntity).test().assertComplete()
    }

    @Test
    fun getMovieCompletes() {
        val movie = MovieFactory.makeMovieEntity()
        whenever(movieCache.getMovie(movie.id))
                .thenReturn(Single.just(movie))

        whenever(movieCategoryCache.hasMovieInCategory(movie.id, Category.FAVORITE))
                .thenReturn(true)

        movieCacheDataStore.getMovie(movie.id).test().assertComplete()
    }
}