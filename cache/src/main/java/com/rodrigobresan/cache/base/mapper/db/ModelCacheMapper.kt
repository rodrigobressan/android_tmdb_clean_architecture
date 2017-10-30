package com.rodrigobresan.cache.base.mapper.db

import android.content.ContentValues
import android.database.Cursor

interface ModelCacheMapper<T> {
    fun toContentValues(model: T): ContentValues
    fun fromCursor(cursor: Cursor): T
}