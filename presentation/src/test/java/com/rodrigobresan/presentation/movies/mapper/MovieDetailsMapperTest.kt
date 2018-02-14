package com.rodrigobresan.presentation.movies.mapper

import com.rodrigobresan.presentation.movie_details.mapper.MovieDetailsMapper
import com.rodrigobresan.presentation.movies.factory.MovieDetailFactory
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MovieDetailsMapperTest {

    private lateinit var movieMapper: MovieDetailsMapper

    @Before
    fun setUp() {
        movieMapper = MovieDetailsMapper()
    }

    @Test
    fun mapToViewProperly() {
        val movie = MovieDetailFactory.makeMovieDetail()

        val movieView = movieMapper.mapToView(movie)
        assertEquals(movie.id, movieView.id)
        assertEquals(movie.title, movieView.title)
        assertEquals(movie.isFavorite, movieView.isFavorite)
        assertEquals(movie.tagline, movieView.tagline)
        assertEquals(movie.overview, movieView.overview)
        assertEquals(movie.backdropPath, movieView.backdropPath)
        assertEquals(movie.posterPath, movieView.posterPath)
        assertEquals(movie.voteAverage, movieView.voteAverage, 0.1)
    }

}