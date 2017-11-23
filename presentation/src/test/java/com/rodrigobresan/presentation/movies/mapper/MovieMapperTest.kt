package com.rodrigobresan.presentation.movies.mapper

import com.rodrigobresan.presentation.movies.factory.MovieFactory
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MovieMapperTest {

    private lateinit var movieMapper: MovieMapper

    @Before
    fun setUp() {
        movieMapper = MovieMapper()
    }

    @Test
    fun mapToViewProperly() {
        val movie = MovieFactory.makeMovie()

        val movieView = movieMapper.mapToView(movie)
        assertEquals(movie.id, movieView.id)
        assertEquals(movie.title, movieView.title)
        assertEquals(movie.posterPath, movieView.posterPath)
        assertEquals(movie.rating, movieView.rating, 0.1)
        assertEquals(movie.isFavorite, movieView.isFavorite)
    }

}