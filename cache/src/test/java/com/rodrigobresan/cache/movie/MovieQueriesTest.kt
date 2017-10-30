package com.rodrigobresan.cache.movie

import com.rodrigobresan.domain.movie_category.model.MovieCategory
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals


@RunWith(JUnit4::class)
class MovieQueriesTest {

    @Test
    fun testGetQueryForMoviesOnCategory() {
        val query = MovieQueries.getQueryForMoviesOnCategory(MovieCategory.POPULAR.name)
        assertEquals(query, "SELECT * FROM movie INNER JOIN movie_category ON " +
                "movie_category.movie_id = movie.movie_id WHERE movie_category.category_id " +
                "= 'POPULAR'")
    }
}