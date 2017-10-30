package com.rodrigobresan.data.movie.mapper

import com.rodrigobresan.data.movie.model.MovieEntity
import com.rodrigobresan.data.test.factory.MovieFactory
import com.rodrigobresan.domain.movies.model.Movie
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class MovieMapperTest {
    private lateinit var movieMapper: MovieMapper

    @Before
    fun setUp() {
        movieMapper = MovieMapper()
    }

    @Test
    fun mapFromEntityMaps() {
        val movieEntity = MovieFactory.makeMovieEntity()
        val movie = movieMapper.mapFromEntity(movieEntity)

        assertMovieDataEquality(movieEntity, movie)
    }

    @Test
    fun mapToEntityMapsData() {
        val cachedMovie = MovieFactory.makeMovie()
        val movieEntity = movieMapper.mapToEntity(cachedMovie)

        assertMovieDataEquality(movieEntity, cachedMovie)
    }

    private fun assertMovieDataEquality(movieEntity: MovieEntity, movie: Movie) {
        assertEquals(movieEntity.id, movie.id)
        assertEquals(movieEntity.title, movie.title)
        assertEquals(movieEntity.rating, movie.rating)
        assertEquals(movieEntity.posterPath, movie.posterPath)
    }
}