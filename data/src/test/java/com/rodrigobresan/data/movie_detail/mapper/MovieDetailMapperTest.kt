package com.rodrigobresan.data.movie_detail.mapper

import com.rodrigobresan.data.movie_detail.model.MovieDetailEntity
import com.rodrigobresan.data.test.factory.MovieDetailFactory
import com.rodrigobresan.domain.movie_detail.model.MovieDetail
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

/**
 * Class for testing MovieMapper class
 */
@RunWith(JUnit4::class)
class MovieDetailMapperTest {
    private lateinit var movieMapper: MovieDetailMapper

    @Before
    fun setUp() {
        movieMapper = MovieDetailMapper()
    }

    @Test
    fun mapFromEntityMaps() {
        val movieEntity = MovieDetailFactory.makeMovieDetailEntity()
        val movie = movieMapper.mapFromEntity(movieEntity)

        assertMovieDataEquality(movieEntity, movie)
    }

    @Test
    fun mapToEntityMapsData() {
        val cachedMovie = MovieDetailFactory.makeMovieDetail()
        val movieEntity = movieMapper.mapToEntity(cachedMovie)

        assertMovieDataEquality(movieEntity, cachedMovie)
    }

    private fun assertMovieDataEquality(movieEntity: MovieDetailEntity, movie: MovieDetail) {
        assertEquals(movieEntity.id, movie.id)
        assertEquals(movieEntity.title, movie.title)
        assertEquals(movieEntity.voteAverage, movie.voteAverage)
        assertEquals(movieEntity.posterPath, movie.posterPath)
        assertEquals(movieEntity.backdropPath, movie.backdropPath)
        assertEquals(movieEntity.overview, movie.overview)
        assertEquals(movieEntity.tagline, movie.tagline)
        assertEquals(movieEntity.isFavorite, movie.isFavorite)
    }
}