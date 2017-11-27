package com.rodrigobresan.cache.movie_detail.mapper.entity

import com.rodrigobresan.cache.movie_detail.model.MovieDetailsCached
import com.rodrigobresan.cache.test.factory.MovieDetailFactory
import com.rodrigobresan.data.movie_detail.model.MovieDetailEntity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

/**
 * Class for testing MovieDetailCacheMapper class
 */
@RunWith(JUnit4::class)
class MovieDetailCacheMapperTest {

    private lateinit var movieCacheMapper: MovieDetailCacheMapper

    @Before
    fun setUp() {
        movieCacheMapper = MovieDetailCacheMapper()
    }

    @Test
    fun mapFromCachedMapsData() {
        val movieCached = MovieDetailFactory.makeMovieDetailCached()
        val movieEntity = movieCacheMapper.mapFromCached(movieCached)

        assertMovieDataEquality(movieEntity, movieCached)
    }

    @Test
    fun mapToCachedMapsData() {
        val movieEntity = MovieDetailFactory.makeMovieDetailEntity()
        val movieCached = movieCacheMapper.mapToCached(movieEntity)

        assertMovieDataEquality(movieEntity, movieCached)
    }

    private fun assertMovieDataEquality(movieEntity: MovieDetailEntity, movieCached: MovieDetailsCached) {
        assertEquals(movieEntity.id, movieCached.id)
        assertEquals(movieEntity.title, movieCached.title)
        assertEquals(movieEntity.voteAverage, movieCached.voteAverage)
        assertEquals(movieEntity.posterPath, movieCached.posterPath)
        assertEquals(movieEntity.backdropPath, movieCached.backdropPath)
    }
}