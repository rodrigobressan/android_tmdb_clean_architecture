package com.rodrigobresan.cache.db.constants

object DbConstants {

    object DbConfig {
        val FILE_NAME = "sample-boilerplate-android-movies.db"
        val VERSION = 1
    }

    object MovieTable {

        const val TABLE_NAME = "movie"

        const val MOVIE_ID = "movie_id"
        const val TITLE = "title"
        const val RATING = "rating"
        const val PICTURE = "picture"

        const val CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        MOVIE_ID + " INTEGER PRIMARY KEY NOT NULL," +
                        TITLE + " TEXT NOT NULL, " +
                        RATING + " REAL NOT NULL," +
                        PICTURE + " TEXT " +
                        ");"

        const val SELECT_ALL = "SELECT * FROM " + TABLE_NAME
    }

    object CategoryTable {
        const val TABLE_NAME = "category"
        const val CATEGORY_ID = "category_id"
        const val CATEGORY_NAME = "name"

        const val CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        CATEGORY_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                        CATEGORY_NAME + " TEXT " +
                        ");"

        const val SELECT_ALL = "SELECT * FROM " + TABLE_NAME
    }

    object MovieCategoryTable {

        const val TABLE_NAME = "movie_category"
        const val MOVIE_ID = "movie_id"
        const val CATEGORY_ID = "category_id"

        const val CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        MOVIE_ID + " INTEGER NOT NULL " +
                        "REFERENCES " + DbConstants.MovieTable.TABLE_NAME + "(" + DbConstants.MovieTable.MOVIE_ID + "), " + // pk
                        CATEGORY_ID + " INTEGER NOT NULL " +
                        "REFERENCES " + DbConstants.CategoryTable.TABLE_NAME + "(" + DbConstants.CategoryTable.CATEGORY_ID + "), " +
                        "PRIMARY KEY (" + MOVIE_ID + "," + CATEGORY_ID + ")" + // pk
                        ");"

        const val SELECT_ALL = "SELECT * FROM " + TABLE_NAME
    }

    const val ENABLE_FOREIGN_KEYS = "PRAGMA foreign_keys=ON;"
}
