package com.rodrigobresan.data.source

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.rodrigobresan.data.repository.movie.movie.MovieCache
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
        whenever(movieCache.saveMovies(movieCategory, any()))
                .thenReturn(Completable.complete())

        movieCacheDataStore.saveMovies(movieCategory, MovieFactory.makeMovieEntityList(2)).test().assertComplete()
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