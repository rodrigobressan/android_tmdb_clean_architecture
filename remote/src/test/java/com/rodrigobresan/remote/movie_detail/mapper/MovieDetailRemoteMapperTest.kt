package com.rodrigobresan.remote.movie_detail.mapper

import com.rodrigobresan.remote.test.factory.MovieDetailFactory
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class MovieDetailRemoteMapperTest {

    private lateinit var movieDetailRemoteMapper: MovieDetailRemoteMapper

    @Before
    fun setUp() {
        movieDetailRemoteMapper = MovieDetailRemoteMapper()
    }

    @Test
    fun mapsFromRemoteMapsDataProperly() {
        var movieDetail = MovieDetailFactory.makeMovieDetailResponse()
        val movieEntity = movieDetailRemoteMapper.mapRemoteToEntity(movieDetail)

        assertEquals(movieDetail.id, movieEntity.id)
        assertEquals(movieDetail.title, movieEntity.title)
        assertEquals(movieDetail.voteAverage, movieEntity.voteAverage)
        assertEquals(movieDetailRemoteMapper.moviePrefixImage + movieDetail.posterPath, movieEntity.posterPath)
    }

}