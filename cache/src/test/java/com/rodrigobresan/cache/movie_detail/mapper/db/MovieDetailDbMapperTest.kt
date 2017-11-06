package com.rodrigobresan.cache.movie_detail.mapper.db

import android.database.Cursor
import com.rodrigobresan.cache.BuildConfig
import com.rodrigobresan.cache.db.DbOpenHelper
import com.rodrigobresan.cache.movie.MovieQueries
import com.rodrigobresan.cache.movie.model.MovieCached
import com.rodrigobresan.cache.movie_detail.MovieDetailQueries
import com.rodrigobresan.cache.movie_detail.mapper.db.MovieDetailDbMapper
import com.rodrigobresan.cache.movie_detail.model.MovieDetailCached
import com.rodrigobresan.cache.test.DefaultConfig
import com.rodrigobresan.cache.test.factory.MovieDetailFactory
import com.rodrigobresan.cache.test.factory.MovieFactory
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import kotlin.test.assertEquals

/**
 * Class for testing MovieDbMapper class
 */
@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(DefaultConfig.EMULATE_SDK))
class MovieDetailDbMapperTest {

    private lateinit var movieDbMapper: MovieDetailDbMapper

    private val database = DbOpenHelper(RuntimeEnvironment.application).writableDatabase

    @Before
    fun setUp() {
        movieDbMapper = MovieDetailDbMapper()
    }

    @Test
    fun parseCursorMapsData() {
        val cachedMovie = MovieDetailFactory.makeMovieDetailCached()

        insertCachedMovieDetail(cachedMovie)

        val cursor = retrieveCachedMoviesCursor()
        assertEquals(cachedMovie, movieDbMapper.fromCursor(cursor))
    }

    private fun retrieveCachedMoviesCursor(): Cursor {
        val cursor = database.rawQuery(MovieDetailQueries.MovieDetailTable.SELECT_ALL, null)
        cursor.moveToFirst()
        return cursor
    }

    private fun insertCachedMovieDetail(cachedMovie: MovieDetailCached) {
        database.insertOrThrow(MovieDetailQueries.MovieDetailTable.TABLE_NAME, null,
                movieDbMapper.toContentValues(cachedMovie))
    }
}
