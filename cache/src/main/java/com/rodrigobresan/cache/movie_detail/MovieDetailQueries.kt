package com.rodrigobresan.cache.movie_detail

object MovieDetailQueries {
    object MovieDetailTable {

        const val TABLE_NAME = "movie_detail"

        const val MOVIE_ID = "movie_id"
        const val TITLE = "title"
        const val RATING = "rating"
        const val OVERVIEW = "overview"
        const val TAGLINE = "tagline"
        const val PICTURE = "picture"
        const val BACKDROP_PICTURE = "backdrop_picture"

        const val CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        MOVIE_ID + " INTEGER PRIMARY KEY NOT NULL," +
                        TITLE + " TEXT NOT NULL, " +
                        RATING + " REAL NOT NULL, " +
                        PICTURE + " TEXT, " +
                        OVERVIEW + " TEXT, " +
                        TAGLINE + " TEXT, " +
                        BACKDROP_PICTURE + " TEXT" +
                        ");"

        const val SELECT_ALL = "SELECT * FROM " + TABLE_NAME
    }

    fun getQueryForMovieDetail(movieId: Long): String {
        return "SELECT * FROM " + MovieDetailTable.TABLE_NAME +
                " WHERE " + MovieDetailTable.MOVIE_ID + " = " + movieId
    }
}