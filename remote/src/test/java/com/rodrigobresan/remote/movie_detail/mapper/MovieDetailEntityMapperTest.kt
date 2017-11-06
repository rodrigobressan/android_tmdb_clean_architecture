package com.rodrigobresan.remote.movie_detail.mapper

import com.rodrigobresan.remote.test.factory.MovieDetailFactory
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class MovieDetailEntityMapperTest {

    private lateinit var movieDetailEntityMapper: MovieDetailEntityMapper

    @Before
    fun setUp() {
        movieDetailEntityMapper = MovieDetailEntityMapper()
    }

    @Test
    fun mapsFromRemoteMapsDataProperly() {
        var movieDetail = MovieDetailFactory.makeMovieDetailResponse()
        val movieEntity = movieDetailEntityMapper.mapRemoteToEntity(movieDetail)

        assertEquals(movieDetail.id, movieEntity.id)
        assertEquals(movieDetail.title, movieEntity.title)
        assertEquals(movieDetail.voteAverage, movieEntity.voteAverage)
        assertEquals(movieDetailEntityMapper.moviePrefixImage + movieDetail.posterPath, movieEntity.posterPath)
    }

}