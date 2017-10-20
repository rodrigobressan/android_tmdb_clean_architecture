package com.rodrigobresan.remote.mapper

import com.rodrigobresan.remote.test.factory.MovieFactory
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class MovieEntityMapperTest {

    private lateinit var movieEntityMapper: MovieEntityMapper

    @Before
    fun setUp() {
        movieEntityMapper = MovieEntityMapper()
    }

    @Test
    fun mapsFromRemoteMapsDataProperly() {
        val movieModel = MovieFactory.makeMovieModel()
        val movieEntity = movieEntityMapper.mapRemoteToEntity(movieModel)

        assertEquals(movieModel.id, movieEntity.id)
        assertEquals(movieModel.title, movieEntity.title)
        assertEquals(movieModel.voteAverage, movieEntity.rating)
        assertEquals(movieModel.posterPath, movieEntity.posterPath)
    }
}