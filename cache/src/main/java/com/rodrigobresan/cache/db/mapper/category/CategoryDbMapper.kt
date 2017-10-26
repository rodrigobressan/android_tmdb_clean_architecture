package com.rodrigobresan.cache.db.mapper.category

import android.content.ContentValues
import android.database.Cursor
import com.rodrigobresan.cache.db.constants.DbConstants
import com.rodrigobresan.cache.db.mapper.ModelCacheMapper
import com.rodrigobresan.cache.model.CategoryCached
import javax.inject.Inject

class CategoryDbMapper @Inject constructor() : ModelCacheMapper<CategoryCached> {

    override fun toContentValues(model: CategoryCached): ContentValues {
        val values = ContentValues()
        values.put(DbConstants.CategoryTable.CATEGORY_ID, model.id)
        values.put(DbConstants.CategoryTable.CATEGORY_NAME, model.name)
        return values
    }

    override fun fromCursor(cursor: Cursor): CategoryCached {
        val id = cursor.getLong(cursor.getColumnIndexOrThrow(DbConstants.CategoryTable.CATEGORY_ID))
        val name = cursor.getString(cursor.getColumnIndexOrThrow(DbConstants.CategoryTable.CATEGORY_NAME))
        return CategoryCached(id, name)
    }

}