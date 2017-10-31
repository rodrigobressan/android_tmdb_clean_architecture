package com.rodrigobresan.sampleboilerplateandroid.movie.mapper

import com.rodrigobresan.sampleboilerplateandroid.movies.mapper.MovieMapper
import com.rodrigobresan.sampleboilerplateandroid.test.MovieFactory
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
    fun mapToViewMapsDataProperly() {
        val movieView = MovieFactory.makeMovieView()
        val movieViewModel = movieMapper.mapToViewModel(movieView)

        assertEquals(movieView.id, movieViewModel.id)
        assertEquals(movieView.title, movieViewModel.title)
        assertEquals(movieView.rating, movieViewModel.rating)
        assertEquals(movieView.posterPath, movieViewModel.posterPath)
    }
}