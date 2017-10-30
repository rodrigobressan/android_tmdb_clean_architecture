package com.rodrigobresan.cache.mapper

import com.rodrigobresan.cache.movie.model.MovieCached
import com.rodrigobresan.cache.test.factory.MovieFactory
import com.rodrigobresan.data.movie.model.MovieEntity
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
    fun mapFromCachedMapsData() {
        val movieCached = MovieFactory.makeMovieCached()
        val movieEntity = movieEntityMapper.mapFromCached(movieCached)

        assertMovieDataEquality(movieEntity, movieCached)
    }

    @Test
    fun mapToCachedMapsData() {
        val movieEntity = MovieFactory.makeMovieEntity()
        val movieCached = movieEntityMapper.mapToCached(movieEntity)

        assertMovieDataEquality(movieEntity, movieCached)
    }

    private fun assertMovieDataEquality(movieEntity: MovieEntity, movieCached: MovieCached) {
        assertEquals(movieEntity.id, movieCached.id)
        assertEquals(movieEntity.title, movieCached.title)
        assertEquals(movieEntity.rating, movieCached.rating)
        assertEquals(movieEntity.posterPath, movieCached.picture)
    }
}