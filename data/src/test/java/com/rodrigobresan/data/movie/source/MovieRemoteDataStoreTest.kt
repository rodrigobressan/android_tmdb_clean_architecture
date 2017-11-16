package com.rodrigobresan.data.movie.source

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.rodrigobresan.data.movie.sources.data_store.remote.MovieRemote
import com.rodrigobresan.data.movie.sources.data_store.remote.MovieRemoteDataStore
import com.rodrigobresan.domain.movie_category.model.MovieCategory
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MovieRemoteDataStoreTest {

    lateinit var movieRemoteDataStore: MovieRemoteDataStore
    lateinit var movieRemote: MovieRemote

    @Before
    fun setUp() {
        movieRemote = mock()
        movieRemoteDataStore = MovieRemoteDataStore(movieRemote)
    }

    @Test(expected = UnsupportedOperationException::class)
    fun clearMoviesShouldThrowUnsupportedOperationException() {
        movieRemoteDataStore.clearMovies()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun saveMoviesShouldThrowUnsupportedOperationException() {
        movieRemoteDataStore.saveMovies(MovieCategory.POPULAR, arrayListOf())
    }

    @Test
    fun getMoviesShouldCallRemoteWithPopularMovies() {
        movieRemoteDataStore.getMovies(MovieCategory.POPULAR)
        verify(movieRemote).getPopularMovies()
    }

    @Test
    fun getMoviesShouldCallRemoteWithNowPlayingMovies() {
        movieRemoteDataStore.getMovies(MovieCategory.NOW_PLAYING)
        verify(movieRemote).getNowPlayingMovies()
    }

    @Test
    fun getMoviesShouldCallRemoteWithUpcomingMovies() {
        movieRemoteDataStore.getMovies(MovieCategory.UPCOMING)
        verify(movieRemote).getUpcomingMovies()
    }

    @Test
    fun getMoviesShouldCallRemoteWithTopRatedMovies() {
        movieRemoteDataStore.getMovies(MovieCategory.TOP_RATED)
        verify(movieRemote).getTopRatedMovies()
    }
}