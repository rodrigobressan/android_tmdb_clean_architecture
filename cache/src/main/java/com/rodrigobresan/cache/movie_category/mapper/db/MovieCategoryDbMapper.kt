package com.rodrigobresan.cache.movie.mapper.db

import android.content.ContentValues
import android.database.Cursor
import com.rodrigobresan.cache.base.mapper.db.ModelCacheMapper
import com.rodrigobresan.cache.movie_category.MovieCategoryQueries
import com.rodrigobresan.cache.movie_category.model.MovieCategoryCached
import javax.inject.Inject

/**
 * Mapper for MovieCategory entity. Maps MovieCategoryCached <-> ContentValues
 */
class MovieCategoryDbMapper @Inject constructor() : ModelCacheMapper<MovieCategoryCached> {

    override fun toContentValues(model: MovieCategoryCached): ContentValues {
        val values = ContentValues()
        values.put(MovieCategoryQueries.MovieCategoryTable.CATEGORY_ID, model.categoryId)
        values.put(MovieCategoryQueries.MovieCategoryTable.MOVIE_ID, model.movieId)

        return values
    }

    override fun fromCursor(cursor: Cursor): MovieCategoryCached {
        val movieId = cursor.getLong(cursor.getColumnIndexOrThrow(MovieCategoryQueries.MovieCategoryTable.MOVIE_ID))
        val categoryId = cursor.getString(cursor.getColumnIndexOrThrow(MovieCategoryQueries.MovieCategoryTable.CATEGORY_ID))

        return MovieCategoryCached(movieId, categoryId)
    }
}