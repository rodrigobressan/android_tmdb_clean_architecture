package com.rodrigobresan.data.movie.source

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.rodrigobresan.data.movie.sources.MovieCache
import com.rodrigobresan.data.movie.sources.data_store.MovieCacheDataStore
import com.rodrigobresan.data.test.factory.MovieFactory
import com.rodrigobresan.domain.model.MovieCategory
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MovieCacheDataStoreTest {


    private lateinit var movieCacheDataStore: MovieCacheDataStore
    private lateinit var movieCache: MovieCache

    @Before
    fun setUp() {
        movieCache = mock()
        movieCacheDataStore = MovieCacheDataStore(movieCache)
    }

    @Test
    fun clearMoviesCompletes() {
        whenever(movieCache.clearMovies())
                .thenReturn(Completable.complete())

        val testObserver = movieCache.clearMovies().test()
        testObserver.assertComplete()
    }

    @Test
    fun saveMoviesCompletes() {
        val movieCategory = MovieCategory.POPULAR
        val movieEntityList = MovieFactory.makeMovieEntityList(2)
        whenever(movieCache.saveMovies(movieCategory, movieEntityList))
                .thenReturn(Completable.complete())

        movieCacheDataStore.saveMovies(movieCategory, movieEntityList).test().assertComplete()
    }

    @Test
    fun getMoviesCompletes() {
        val movieCategory = MovieCategory.POPULAR
        val movies = MovieFactory.makeMovieEntityList(2)
        whenever(movieCache.getMovies(movieCategory))
                .thenReturn(Single.just(movies))

        movieCache.getMovies(movieCategory).test().assertComplete()
    }
}