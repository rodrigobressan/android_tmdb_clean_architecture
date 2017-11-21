package com.rodrigobresan.data.movie_detail.model

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertTrue

@RunWith(JUnit4::class)
class MovieDetailEntityTest {

    @Test
    fun testEquals_Symmetric() {
        val movie1 = MovieDetailEntity(1, "Title", 10.0, "poster_path",
                "backdrop_path", "overview", "tagline")
        val movie2 = MovieDetailEntity(1, "Title", 10.0, "poster_path",
                "backdrop_path", "overview", "tagline")

        assertTrue(movie1.equals(movie2) && movie2.equals(movie1))
        assertTrue(movie1.hashCode() == movie2.hashCode())
    }

}