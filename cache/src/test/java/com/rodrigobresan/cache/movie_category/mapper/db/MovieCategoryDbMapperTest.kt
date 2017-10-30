package com.rodrigobresan.cache.movie.mapper.db

import android.database.Cursor
import com.rodrigobresan.cache.BuildConfig
import com.rodrigobresan.cache.category.CategoryQueries
import com.rodrigobresan.cache.db.DbOpenHelper
import com.rodrigobresan.cache.movie_category.MovieCategoryQueries
import com.rodrigobresan.cache.movie_category.model.MovieCategoryCached
import com.rodrigobresan.cache.test.DefaultConfig
import com.rodrigobresan.cache.test.factory.MovieCategoryFactory
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(DefaultConfig.EMULATE_SDK))
class MovieCategoryDbMapperTest {

    // TODO check issues related to foreign keys
//    private lateinit var movieCategoryDbMapper: MovieCategoryDbMapper
//
//    private val database = DbOpenHelper(RuntimeEnvironment.application).writableDatabase
//
//    @Before
//    fun setUp() {
//        movieCategoryDbMapper = MovieCategoryDbMapper()
//    }
//
//    @Test
//    fun parseCursorMapsData() {
//        val cachedMovieCategory = MovieCategoryFactory.makeMovieCategoryCached()
//
//        insertCachedCategory(cachedMovieCategory)
//
//        val cursor = retrieveCachedMovieCategoriesCursor()
//        assertEquals(cachedMovieCategory, movieCategoryDbMapper.fromCursor(cursor))
//    }
//
//    private fun retrieveCachedMovieCategoriesCursor(): Cursor {
//        val cursor = database.rawQuery(MovieCategoryQueries.MovieCategoryTable.SELECT_ALL, null)
//        cursor.moveToFirst()
//        return cursor
//    }
//
//    private fun insertCachedCategory(cachedCategory: MovieCategoryCached) {
//        database.insertOrThrow(MovieCategoryQueries.MovieCategoryTable.TABLE_NAME, null,
//                movieCategoryDbMapper.toContentValues(cachedCategory))
//    }
}
