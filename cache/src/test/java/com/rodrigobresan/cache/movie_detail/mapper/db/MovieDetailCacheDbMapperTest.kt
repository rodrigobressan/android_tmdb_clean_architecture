package com.rodrigobresan.cache.movie_detail.mapper.db

import android.database.Cursor
import com.rodrigobresan.cache.BuildConfig
import com.rodrigobresan.cache.db.DbOpenHelper
import com.rodrigobresan.cache.movie_detail.MovieDetailQueries
import com.rodrigobresan.cache.movie_detail.model.MovieDetailsCached
import com.rodrigobresan.cache.test.DefaultConfig
import com.rodrigobresan.cache.test.factory.MovieDetailFactory
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import kotlin.test.assertEquals

/**
 * Class for testing MovieCacheDbMapper class
 */
@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(DefaultConfig.EMULATE_SDK))
class MovieDetailCacheDbMapperTest {

    private lateinit var movieCacheDbMapper: MovieDetailCacheDbMapper

    private val database = DbOpenHelper(RuntimeEnvironment.application).writableDatabase

    @Before
    fun setUp() {
        movieCacheDbMapper = MovieDetailCacheDbMapper()
    }

    @Test
    fun parseCursorMapsData() {
        val cachedMovie = MovieDetailFactory.makeMovieDetailCached()

        insertCachedMovieDetail(cachedMovie)

        val cursor = retrieveCachedMoviesCursor()
        assertEquals(cachedMovie, movieCacheDbMapper.fromCursor(cursor))
    }

    private fun retrieveCachedMoviesCursor(): Cursor {
        val cursor = database.rawQuery(MovieDetailQueries.MovieDetailTable.SELECT_ALL, null)
        cursor.moveToFirst()
        return cursor
    }

    private fun insertCachedMovieDetail(cachedMovie: MovieDetailsCached) {
        database.insertOrThrow(MovieDetailQueries.MovieDetailTable.TABLE_NAME, null,
                movieCacheDbMapper.toContentValues(cachedMovie))
    }
}
