package com.rodrigobresan.cache.db.mapper

import android.database.Cursor
import com.rodrigobresan.cache.BuildConfig
import com.rodrigobresan.cache.db.DbOpenHelper
import com.rodrigobresan.cache.db.constants.DbConstants
import com.rodrigobresan.cache.db.mapper.movie.MovieDbMapper
import com.rodrigobresan.cache.model.MovieCached
import com.rodrigobresan.cache.test.DefaultConfig
import com.rodrigobresan.cache.test.factory.MovieFactory
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(DefaultConfig.EMULATE_SDK))
class MovieDbMapperTest {


    private lateinit var movieDbMapper: MovieDbMapper

    private val database = DbOpenHelper(RuntimeEnvironment.application).writableDatabase

    @Before
    fun setUp() {
        movieDbMapper = MovieDbMapper()
    }

    @Test
    fun parseCursorMapsData() {
        val cachedMovie = MovieFactory.makeCachedMovie()

        insertCachedMovie(cachedMovie)

        val cursor = retrieveCachedMoviesCursor()
        assertEquals(cachedMovie, movieDbMapper.fromCursor(cursor))
    }

    private fun retrieveCachedMoviesCursor(): Cursor {
        val cursor = database.rawQuery("SELECT * FROM " + DbConstants.MovieTable.TABLE_NAME, null)
        cursor.moveToFirst()
        return cursor
    }

    private fun insertCachedMovie(cachedMovie : MovieCached) {
        database.insertOrThrow(DbConstants.MovieTable.TABLE_NAME, null,
                movieDbMapper.toContentValues(cachedMovie))
    }
}
