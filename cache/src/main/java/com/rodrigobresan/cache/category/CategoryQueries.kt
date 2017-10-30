package com.rodrigobresan.cache.category

object CategoryQueries {
    object CategoryTable {
        const val TABLE_NAME = "category"
        const val CATEGORY_ID = "category_id"
        const val CATEGORY_NAME = "name"

        const val CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        CATEGORY_ID + " TEXT PRIMARY KEY NOT NULL, " +
                        CATEGORY_NAME + " TEXT " +
                        ");"

        const val SELECT_ALL = "SELECT * FROM " + TABLE_NAME
    }
}