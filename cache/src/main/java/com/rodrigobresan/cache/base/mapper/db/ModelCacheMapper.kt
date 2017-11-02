package com.rodrigobresan.cache.base.mapper.db

import android.content.ContentValues
import android.database.Cursor

/**
 * Contract for a class that will be serving for mapping an object to a ContentValue (and vice-versa)
 * to be persisted on the database later
 */
interface ModelCacheMapper<T> {
    fun toContentValues(model: T): ContentValues
    fun fromCursor(cursor: Cursor): T
}