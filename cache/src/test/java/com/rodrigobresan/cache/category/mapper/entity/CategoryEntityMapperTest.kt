package com.rodrigobresan.cache.mapper

import com.rodrigobresan.cache.category.mapper.entity.CategoryEntityMapper
import com.rodrigobresan.cache.category.model.CategoryCached
import com.rodrigobresan.cache.movie.model.MovieCached
import com.rodrigobresan.cache.test.factory.CategoryFactory
import com.rodrigobresan.cache.test.factory.MovieFactory
import com.rodrigobresan.data.model.CategoryEntity
import com.rodrigobresan.data.model.MovieEntity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class CategoryEntityMapperTest {

    private lateinit var categoryEntityMapper: CategoryEntityMapper

    @Before
    fun setUp() {
        categoryEntityMapper = CategoryEntityMapper()
    }

    @Test
    fun mapFromCachedMapsData() {
        val categoryCached = CategoryFactory.makeCategoryCached()
        val categoryEntity = categoryEntityMapper.mapFromCached(categoryCached)

        assertCategoryDataEquality(categoryEntity, categoryCached)
    }

    @Test
    fun mapToCachedMapsData() {
        val categoryEntity = CategoryFactory.makeCategoryEntity()
        val categoryCached = categoryEntityMapper.mapToCached(categoryEntity)

        assertCategoryDataEquality(categoryEntity, categoryCached)
    }

    private fun assertCategoryDataEquality(categoryEntity: CategoryEntity, categoryCached: CategoryCached) {
        assertEquals(categoryEntity.id, categoryCached.id)
        assertEquals(categoryEntity.name, categoryCached.name)
    }
}