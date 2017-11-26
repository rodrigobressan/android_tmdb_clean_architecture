package com.rodrigobresan.cache.category.mapper.db

import android.content.ContentValues
import android.database.Cursor
import com.rodrigobresan.cache.base.mapper.db.ModelCacheMapper
import com.rodrigobresan.cache.category.CategoryQueries
import com.rodrigobresan.cache.category.model.CategoryCached
import javax.inject.Inject

/**
 * Category Mapper for persistence. CategoryCached <-> ContentValues
 */
class CategoryDbMapper @Inject constructor() : ModelCacheMapper<CategoryCached> {

    override fun toContentValues(model: CategoryCached): ContentValues {
        val values = ContentValues()
        values.put(CategoryQueries.CategoryTable.CATEGORY_ID, model.id)
        values.put(CategoryQueries.CategoryTable.CATEGORY_NAME, model.name)

        return values
    }

    override fun fromCursor(cursor: Cursor): CategoryCached {
        val id = cursor.getString(cursor.getColumnIndexOrThrow(CategoryQueries.CategoryTable.CATEGORY_ID))
        val name = cursor.getString(cursor.getColumnIndexOrThrow(CategoryQueries.CategoryTable.CATEGORY_NAME))
        val category = CategoryCached(name)
        category.id = id
        return category
    }
}