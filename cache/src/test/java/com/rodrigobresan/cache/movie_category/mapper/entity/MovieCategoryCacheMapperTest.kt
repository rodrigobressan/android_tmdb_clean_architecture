package com.rodrigobresan.cache.mapper

import com.rodrigobresan.cache.movie_category.mapper.entity.MovieCategoryCacheMapper
import com.rodrigobresan.cache.movie_category.model.MovieCategoryCached
import com.rodrigobresan.cache.test.factory.MovieCategoryFactory
import com.rodrigobresan.data.movie_category.model.MovieCategoryEntity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals
/**
 * Class for testing MovieCategoryCacheMapper class
 */
@RunWith(JUnit4::class)
class MovieCategoryCacheMapperTest {

    private lateinit var movieCategoryCacheMapper: MovieCategoryCacheMapper

    @Before
    fun setUp() {
        movieCategoryCacheMapper = MovieCategoryCacheMapper()
    }

    @Test
    fun mapFromCachedMapsData() {
        val movieCategoryCached = MovieCategoryFactory.makeMovieCategoryCached()
        val movieCategoryEntity = movieCategoryCacheMapper.mapFromCached(movieCategoryCached)

        assertMovieCategoryDataEquality(movieCategoryEntity, movieCategoryCached)
    }

    @Test
    fun mapToCachedMapsData() {
        val movieCategoryEntity = MovieCategoryFactory.makeMovieCategoryEntity()
        val movieCategoryCached = movieCategoryCacheMapper.mapToCached(movieCategoryEntity)

        assertMovieCategoryDataEquality(movieCategoryEntity, movieCategoryCached)
    }

    private fun assertMovieCategoryDataEquality(movieCategoryEntity: MovieCategoryEntity, movieCategoryCached: MovieCategoryCached) {
        assertEquals(movieCategoryEntity.movieId, movieCategoryCached.movieId)
        assertEquals(movieCategoryEntity.categoryId, movieCategoryCached.categoryId)
    }
}