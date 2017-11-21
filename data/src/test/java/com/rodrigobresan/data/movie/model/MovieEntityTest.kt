package com.rodrigobresan.data.movie_detail.model

import com.rodrigobresan.data.movie.model.MovieEntity
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertTrue

@RunWith(JUnit4::class)
class MovieEntityTest {

    @Test
    fun testEquals_Symmetric() {
        val movie1 = MovieEntity(1, "Title", 10.0, "poster_path")
        val movie2 = MovieEntity(1, "Title", 10.0, "poster_path")

        assertTrue(movie1.equals(movie2) && movie2.equals(movie1))
        assertTrue(movie1.hashCode() == movie2.hashCode())
    }

}