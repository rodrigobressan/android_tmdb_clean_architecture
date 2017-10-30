package com.rodrigobresan.cache.movie.impl

import com.rodrigobresan.cache.PreferencesHelper
import com.rodrigobresan.cache.category.CategoryQueries
import com.rodrigobresan.cache.category.impl.CategoryCacheImpl
import com.rodrigobresan.cache.category.mapper.db.CategoryDbMapper
import com.rodrigobresan.cache.category.mapper.entity.CategoryEntityMapper
import com.rodrigobresan.cache.category.model.CategoryCached
import com.rodrigobresan.cache.db.DbOpenHelper
import com.rodrigobresan.cache.test.factory.CategoryFactory
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = intArrayOf(21))
class CategoryCacheImplTest {

    private val context = RuntimeEnvironment.application

    private var categoryEntityMapper = CategoryEntityMapper()
    private var categoryDbMapper = CategoryDbMapper()
    private var preferencesHelper = PreferencesHelper(context)

    private lateinit var categoryCacheImpl: CategoryCacheImpl

    @Before
    fun setUp() {
        categoryCacheImpl = CategoryCacheImpl(DbOpenHelper(context),
                categoryEntityMapper, categoryDbMapper, preferencesHelper)
        clearPreviousDataFromDatabase()
    }

    private fun clearPreviousDataFromDatabase() {
        categoryCacheImpl.getDatabase().rawQuery("DELETE FROM " + CategoryQueries.CategoryTable.TABLE_NAME, null)
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

        val testObservable = categoryCacheImpl.getCategories().test()
        testObservable.assertValue(categoriesEntity)
    }

    @Test
    fun saveCategoriesCompletes() {
        categoryCacheImpl.saveCategories(CategoryFactory.makeCategoryEntityList(2)).test()
                .assertComplete()
    }

    private fun insertCategories(categories: List<CategoryCached>) {
        val database = categoryCacheImpl.getDatabase()

        categories.forEach {
            database.insertOrThrow(CategoryQueries.CategoryTable.TABLE_NAME, null,
                    categoryDbMapper.toContentValues(it))
        }
    }
}