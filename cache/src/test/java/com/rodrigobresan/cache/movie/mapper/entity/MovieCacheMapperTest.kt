package com.rodrigobresan.cache.mapper

import com.rodrigobresan.cache.movie.model.MovieCached
import com.rodrigobresan.cache.test.factory.MovieFactory
import com.rodrigobresan.data.movie.model.MovieEntity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals
/**
 * Class for testing MovieCacheMapper class
 */
@RunWith(JUnit4::class)
class MovieCacheMapperTest {

    private lateinit var movieCacheMapper: MovieCacheMapper

    @Before
    fun setUp() {
        movieCacheMapper = MovieCacheMapper()
    }

    @Test
    fun mapFromCachedMapsData() {
        val movieCached = MovieFactory.makeMovieCached()
        val movieEntity = movieCacheMapper.mapFromCached(movieCached)

        assertMovieDataEquality(movieEntity, movieCached)
    }

    @Test
    fun mapToCachedMapsData() {
        val movieEntity = MovieFactory.makeMovieEntity()
        val movieCached = movieCacheMapper.mapToCached(movieEntity)

        assertMovieDataEquality(movieEntity, movieCached)
    }

    private fun assertMovieDataEquality(movieEntity: MovieEntity, movieCached: MovieCached) {
        assertEquals(movieEntity.id, movieCached.id)
        assertEquals(movieEntity.title, movieCached.title)
        assertEquals(movieEntity.rating, movieCached.rating)
        assertEquals(movieEntity.posterPath, movieCached.picture)
    }
}