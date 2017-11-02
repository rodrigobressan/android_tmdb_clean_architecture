package com.rodrigobresan.cache.movie

import com.rodrigobresan.cache.movie_category.MovieCategoryQueries.MovieCategoryTable

/**
 * Object containing the queries for the movie table
 */
object MovieQueries {
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

    /**
     * @param movieCategory the movie category from where the movies will be fetched
     * @return the query for fetching the movies of a specified category
     */
    fun getQueryForMoviesOnCategory(movieCategory: String): String {
        return "SELECT * FROM " + MovieTable.TABLE_NAME +
                " INNER JOIN " + MovieCategoryTable.TABLE_NAME +
                " ON " +
                MovieCategoryTable.TABLE_NAME + "." + MovieCategoryTable.MOVIE_ID +
                " = " + MovieTable.TABLE_NAME + "." + MovieTable.MOVIE_ID +
                " WHERE " +
                MovieCategoryTable.TABLE_NAME + "." + MovieCategoryTable.CATEGORY_ID +
                " = '" + movieCategory + "'"
    }
}