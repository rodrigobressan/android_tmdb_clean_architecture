package com.rodrigobresan.remote.movies.mapper

import com.rodrigobresan.remote.test.factory.MovieFactory
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals
/**
 * Class for testing MovieRemoteMapper class
 */
@RunWith(JUnit4::class)
class MovieRemoteMapperTest {

    private lateinit var movieRemoteMapper: MovieRemoteMapper

    @Before
    fun setUp() {
        movieRemoteMapper = MovieRemoteMapper()
    }

    @Test
    fun mapsFromRemoteMapsDataProperly() {
        val movieModel = MovieFactory.makeMovieModel()
        val movieEntity = movieRemoteMapper.mapRemoteToEntity(movieModel)

        assertEquals(movieModel.id, movieEntity.id)
        assertEquals(movieModel.title, movieEntity.title)
        assertEquals(movieModel.voteAverage, movieEntity.rating)
        assertEquals(movieRemoteMapper.moviePrefixImage + movieModel.posterPath, movieEntity.posterPath)
    }
}