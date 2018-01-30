package com.rodrigobresan.cache.movie.impl

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import com.rodrigobresan.cache.AppDatabase
import com.rodrigobresan.cache.PreferencesHelper
import com.rodrigobresan.cache.category.CategoryQueries
import com.rodrigobresan.cache.category.dao.CategoryDao
import com.rodrigobresan.cache.category.impl.CategoryCacheImpl
import com.rodrigobresan.cache.category.mapper.entity.CategoryCacheMapper
import com.rodrigobresan.cache.category.model.CategoryCached
import com.rodrigobresan.cache.test.factory.CategoryFactory
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import kotlin.test.assertEquals

/**
 * Class for testing CategoryCacheImpl class
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = intArrayOf(21))
class CategoryCacheImplTest {

    private var database = Room.inMemoryDatabaseBuilder(RuntimeEnvironment.application,
            AppDatabase::class.java).allowMainThreadQueries().build()

    private var categoryEntityMapper = CategoryCacheMapper()
    private var preferencesHelper = PreferencesHelper(RuntimeEnvironment.application)

    private lateinit var categoryCacheImpl: CategoryCacheImpl

    @Before
    fun setUp() {
        categoryCacheImpl = CategoryCacheImpl(database.categoryDao(),
                categoryEntityMapper, preferencesHelper)
        clearPreviousDataFromDatabase()
    }

    private fun clearPreviousDataFromDatabase() {
        // TODO
    }

    @Test
    fun clearTableCompletes() {
        categoryCacheImpl.clearCategories().test().assertComplete()
    }

    @Test
    fun saveCategorySavesData() {
        val categoriesEntity = CategoryFactory.makeCategoryEntityList(2)
        val categoriesCached = mutableListOf<CategoryCached>()

        categoriesEntity.forEach {
            categoriesCached.add(categoryEntityMapper.mapToCached(it))
        }

        insertCategories(categoriesEntity.map { categoryEntityMapper.mapToCached(it) })

        checkNumRowsInCategoriesTable(categoriesEntity.size)
    }

    private fun checkNumRowsInCategoriesTable(expectedRows: Int) {
        val numberOfRows = database.categoryDao().getAll().size
        assertEquals(expectedRows, numberOfRows)
    }

    private fun insertCategories(categories: List<CategoryCached>) {
        categories.forEach {
            categoryCacheImpl.saveCategory(categoryEntityMapper.mapFromCached(it)).test()
        }
    }

    @Test
    fun saveCategoriesCompletes() {
        categoryCacheImpl.saveCategory(CategoryFactory.makeCategoryEntity())
                .test()
                .assertComplete()
    }

}