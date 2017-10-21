package com.rodrigobresan.cache.db.constants

object DbConstants {

    object DbConfig {
        val FILE_NAME = "sample-boilerplate-android-movies.db"
        val VERSION = 1
    }

    object MovieTable {

        const val TABLE_NAME = "movies"

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

        const val SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
    }

    const val ENABLE_FOREIGN_KEYS = "PRAGMA foreign_keys=ON;";
}
