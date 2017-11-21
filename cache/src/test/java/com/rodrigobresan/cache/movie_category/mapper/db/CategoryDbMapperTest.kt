package com.rodrigobresan.cache.movie_category.mapper.db

import android.database.Cursor
import com.rodrigobresan.cache.BuildConfig
import com.rodrigobresan.cache.category.CategoryQueries
import com.rodrigobresan.cache.category.mapper.db.CategoryDbMapper
import com.rodrigobresan.cache.category.model.CategoryCached
import com.rodrigobresan.cache.db.DbOpenHelper
import com.rodrigobresan.cache.movie.MovieQueries
import com.rodrigobresan.cache.movie.mapper.db.MovieCategoryCacheDbMapper
import com.rodrigobresan.cache.movie.mapper.db.MovieCacheDbMapper
import com.rodrigobresan.cache.movie.model.MovieCached
import com.rodrigobresan.cache.movie_category.MovieCategoryQueries
import com.rodrigobresan.cache.movie_category.model.MovieCategoryCached
import com.rodrigobresan.cache.test.DefaultConfig
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import kotlin.test.assertEquals


@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(DefaultConfig.EMULATE_SDK))
class CategoryDbMapperTest {

    private lateinit var movieCacheDbMapper: MovieCategoryCacheDbMapper

    private val database = DbOpenHelper(RuntimeEnvironment.application).writableDatabase

    @Before
    fun setUp() {
        movieCacheDbMapper = MovieCategoryCacheDbMapper()
    }

    @Test
    fun parseCursorMapsData() {
        database.insertOrThrow(MovieQueries.MovieTable.TABLE_NAME, null,
                MovieCacheDbMapper().toContentValues(MovieCached(1, "title", 10.0, "picture")))

        database.insertOrThrow(CategoryQueries.CategoryTable.TABLE_NAME, null,
                CategoryDbMapper().toContentValues(CategoryCached("title", "title")))

        val cachedMovieCategory = MovieCategoryCached(1, "title")

        insertCachedMovie(cachedMovieCategory)

        val cursor = retrieveCachedMoviesCursor()
        assertEquals(cachedMovieCategory, movieCacheDbMapper.fromCursor(cursor))
    }

    private fun retrieveCachedMoviesCursor(): Cursor {
        val cursor = database.rawQuery(MovieCategoryQueries.MovieCategoryTable.SELECT_ALL, null)
        cursor.moveToFirst()
        return cursor
    }

    private fun insertCachedMovie(cachedMovie: MovieCategoryCached) {
        database.insertOrThrow(MovieCategoryQueries.MovieCategoryTable.TABLE_NAME, null,
                movieCacheDbMapper.toContentValues(cachedMovie))
    }
}
