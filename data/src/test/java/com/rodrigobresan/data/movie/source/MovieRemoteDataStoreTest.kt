package com.rodrigobresan.data.movie.source

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.rodrigobresan.data.movie.sources.data_store.remote.MovieRemote
import com.rodrigobresan.data.movie.sources.data_store.remote.MovieRemoteDataStore
import com.rodrigobresan.data.movie_category.model.MovieCategoryEntity
import com.rodrigobresan.domain.movie_category.model.Category
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
    fun deleteMovieFromCategoryShouldThrowUnsupportedOperationException() {
        movieRemoteDataStore.deleteMovieFromCategory(MovieCategoryEntity(0, Category.POPULAR.name))
    }

    @Test(expected = UnsupportedOperationException::class)
    fun getMovieShouldThrowUnsupportedOperationException() {
        movieRemoteDataStore.getMovie(0)
    }

    @Test(expected = UnsupportedOperationException::class)
    fun saveMoviesShouldThrowUnsupportedOperationException() {
        movieRemoteDataStore.saveMovies(Category.POPULAR, arrayListOf())
    }

    @Test
    fun getMoviesShouldCallRemoteWithPopularMovies() {
        movieRemoteDataStore.getMovies(Category.POPULAR)
        verify(movieRemote).getPopularMovies()
    }

    @Test
    fun getMoviesShouldCallRemoteWithNowPlayingMovies() {
        movieRemoteDataStore.getMovies(Category.NOW_PLAYING)
        verify(movieRemote).getNowPlayingMovies()
    }

    @Test
    fun getMoviesShouldCallRemoteWithUpcomingMovies() {
        movieRemoteDataStore.getMovies(Category.UPCOMING)
        verify(movieRemote).getUpcomingMovies()
    }

    @Test
    fun getMoviesShouldCallRemoteWithTopRatedMovies() {
        movieRemoteDataStore.getMovies(Category.TOP_RATED)
        verify(movieRemote).getTopRatedMovies()
    }

    @Test
    fun getMoviesShouldReturnEmptyWithFavorite() {
        movieRemoteDataStore.getMovies(Category.FAVORITE).test().assertValue(emptyList())
    }

    @Test
    fun getMoviesShouldReturnEmptyWithSeen() {
        movieRemoteDataStore.getMovies(Category.SEEN).test().assertValue(emptyList())
    }
}