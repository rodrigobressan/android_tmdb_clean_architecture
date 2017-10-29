package com.rodrigobresan.remote.movies.impl

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.rodrigobresan.data.model.MovieEntity
import com.rodrigobresan.remote.movies.mapper.MovieEntityMapper
import com.rodrigobresan.remote.movies.model.movies.MovieResponse
import com.rodrigobresan.remote.service.MovieService
import com.rodrigobresan.remote.test.factory.MovieFactory
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MovieRemoteImplTest {

    private lateinit var movieService: MovieService
    private lateinit var movieMapper: MovieEntityMapper

    private lateinit var movieRemoteImpl: MovieRemoteImpl

    @Before
    fun setUp() {
        movieService = mock()
        movieMapper = mock()

        movieRemoteImpl = MovieRemoteImpl(movieService, movieMapper)
    }

    // Popular
    @Test
    fun getPopularMoviesCompletes() {
        stubMoviesServiceGetPopularMovies(Single.just(MovieFactory.makeMovieResponse()))

        val testObserver = movieService.getPopularMovies().test()
        testObserver.assertComplete()
    }

    @Test
    fun getPopularMoviesReturnsData() {
        val moviesResponse = MovieFactory.makeMovieResponse()
        val movieEntities = mutableListOf<MovieEntity>()

        stubMoviesServiceGetPopularMovies(Single.just(moviesResponse))

        moviesResponse.results.forEach {
            movieEntities.add(movieMapper.mapRemoteToEntity(it))
        }

        val testObserver = movieRemoteImpl.getPopularMovies().test()
        testObserver.assertValue(movieEntities)
    }

    private fun stubMoviesServiceGetPopularMovies(movieResponse: Single<MovieResponse>) {
        whenever(movieService.getPopularMovies())
                .thenReturn(movieResponse)
    }

    // Top Rated
    @Test
    fun getTopRatedMoviesCompletes() {
        stubMoviesServiceGetTopRatedMovies(Single.just(MovieFactory.makeMovieResponse()))

        val testObserver = movieService.getTopRatedMovies().test()
        testObserver.assertComplete()
    }

    @Test
    fun getTopRatedMoviesReturnsData() {
        val moviesResponse = MovieFactory.makeMovieResponse()
        val movieEntities = mutableListOf<MovieEntity>()

        stubMoviesServiceGetTopRatedMovies(Single.just(moviesResponse))

        moviesResponse.results.forEach {
            movieEntities.add(movieMapper.mapRemoteToEntity(it))
        }

        val testObserver = movieRemoteImpl.getTopRatedMovies().test()
        testObserver.assertValue(movieEntities)
    }

    private fun stubMoviesServiceGetTopRatedMovies(movieResponse: Single<MovieResponse>) {
        whenever(movieService.getTopRatedMovies())
                .thenReturn(movieResponse)
    }
}