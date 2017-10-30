package com.rodrigobresan.cache.movie.mapper.db

import android.database.Cursor
import com.rodrigobresan.cache.BuildConfig
import com.rodrigobresan.cache.category.CategoryQueries
import com.rodrigobresan.cache.category.mapper.db.CategoryDbMapper
import com.rodrigobresan.cache.category.model.CategoryCached
import com.rodrigobresan.cache.db.DbOpenHelper
import com.rodrigobresan.cache.test.DefaultConfig
import com.rodrigobresan.cache.test.factory.CategoryFactory
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

    private lateinit var categoryDbMapper: CategoryDbMapper

    private val database = DbOpenHelper(RuntimeEnvironment.application).writableDatabase

    @Before
    fun setUp() {
        categoryDbMapper = CategoryDbMapper()
    }

    @Test
    fun parseCursorMapsData() {
        val cachedCategory = CategoryFactory.makeCategoryCached()

        insertCachedCategory(cachedCategory)

        val cursor = retrieveCachedCategoriesCursor()
        assertEquals(cachedCategory, categoryDbMapper.fromCursor(cursor))
    }

    private fun retrieveCachedCategoriesCursor(): Cursor {
        val cursor = database.rawQuery(CategoryQueries.CategoryTable.SELECT_ALL, null)
        cursor.moveToFirst()
        return cursor
    }

    private fun insertCachedCategory(cachedCategory: CategoryCached) {
        database.insertOrThrow(CategoryQueries.CategoryTable.TABLE_NAME, null,
                categoryDbMapper.toContentValues(cachedCategory))
    }
}
