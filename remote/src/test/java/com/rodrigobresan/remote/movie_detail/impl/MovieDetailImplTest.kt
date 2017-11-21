package com.rodrigobresan.remote.movie_detail.impl

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.rodrigobresan.remote.movie_detail.mapper.MovieDetailRemoteMapper
import com.rodrigobresan.remote.movie_detail.model.MovieDetailResponse
import com.rodrigobresan.remote.service.MovieService
import com.rodrigobresan.remote.test.factory.MovieDetailFactory
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MovieDetailImplTest {

    private lateinit var movieService: MovieService
    private lateinit var movieDetailMapper: MovieDetailRemoteMapper

    private lateinit var movieDetailRemoteImpl: MovieDetailRemoteImpl

    @Before
    fun setUp() {
        movieService = mock()
        movieDetailMapper = mock()

        movieDetailRemoteImpl = MovieDetailRemoteImpl(movieService, movieDetailMapper)
    }

    @Test
    fun getMovieDetailsCompletes() {
        stubMovieDetailServiceGetMovieDetails(Single.just(MovieDetailFactory.makeMovieDetailResponse()))

        movieService.getMovieDetails(0).test().assertComplete()
    }

    @Test
    fun getMovieDetailsReturnsData() {
        val movieResponse = MovieDetailFactory.makeMovieDetailResponse()
        val movieEntity = movieDetailMapper.mapRemoteToEntity(movieResponse)

        stubMovieDetailServiceGetMovieDetails(Single.just(movieResponse))

        movieDetailRemoteImpl.getMovieDetails(0).test().assertValue(movieEntity)
    }

    private fun stubMovieDetailServiceGetMovieDetails(singleResponse: Single<MovieDetailResponse>?) {
        whenever(movieService.getMovieDetails(any()))
                .thenReturn(singleResponse)
    }
}