package com.rodrigobresan.cache.movie_detail.mapper.entity

import com.rodrigobresan.cache.movie_detail.mapper.entity.MovieDetailEntityMapper
import com.rodrigobresan.cache.movie_detail.model.MovieDetailCached
import com.rodrigobresan.cache.test.factory.MovieDetailFactory
import com.rodrigobresan.data.movie_detail.model.MovieDetailEntity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

/**
 * Class for testing MovieDetailEntityMapper class
 */
@RunWith(JUnit4::class)
class MovieDetailEntityMapperTest {

    private lateinit var movieEntityMapper: MovieDetailEntityMapper

    @Before
    fun setUp() {
        movieEntityMapper = MovieDetailEntityMapper()
    }

    @Test
    fun mapFromCachedMapsData() {
        val movieCached = MovieDetailFactory.makeMovieDetailCached()
        val movieEntity = movieEntityMapper.mapFromCached(movieCached)

        assertMovieDataEquality(movieEntity, movieCached)
    }

    @Test
    fun mapToCachedMapsData() {
        val movieEntity = MovieDetailFactory.makeMovieDetailEntity()
        val movieCached = movieEntityMapper.mapToCached(movieEntity)

        assertMovieDataEquality(movieEntity, movieCached)
    }

    private fun assertMovieDataEquality(movieEntity: MovieDetailEntity, movieCached: MovieDetailCached) {
        assertEquals(movieEntity.id, movieCached.id)
        assertEquals(movieEntity.title, movieCached.title)
        assertEquals(movieEntity.voteAverage, movieCached.voteAverage)
        assertEquals(movieEntity.posterPath, movieCached.posterPath)
        assertEquals(movieEntity.backdropPath, movieCached.backdropPath)
    }
}