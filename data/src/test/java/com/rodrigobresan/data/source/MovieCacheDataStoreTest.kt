package com.rodrigobresan.data.source

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.rodrigobresan.data.repository.movie.MovieCache
import com.rodrigobresan.data.test.factory.MovieFactory
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
        whenever(movieCache.saveMovies(any()))
                .thenReturn(Completable.complete())

        movieCacheDataStore.saveMovies(MovieFactory.makeMovieEntityList(2)).test().assertComplete()
    }

    @Test
    fun getMoviesCompletes() {
        val movies = MovieFactory.makeMovieEntityList(2)
        whenever(movieCache.getMovies())
                .thenReturn(Single.just(movies))

        movieCache.getMovies().test().assertComplete()
    }
}