package com.rodrigobresan.cache.movie.mapper.db

import android.content.ContentValues
import android.database.Cursor
import com.rodrigobresan.cache.base.mapper.db.ModelCacheMapper
import com.rodrigobresan.cache.movie.MovieQueries.MovieTable
import com.rodrigobresan.cache.movie.model.MovieCached
import javax.inject.Inject

/**
 * Mapper for Movie. MovieCached <-> ContentValues
 */
class MovieCacheDbMapper @Inject constructor() : ModelCacheMapper<MovieCached> {

    override fun toContentValues(model: MovieCached): ContentValues {
        val values = ContentValues()
        values.put(MovieTable.MOVIE_ID, model.id)
        values.put(MovieTable.TITLE, model.title)
        values.put(MovieTable.RATING, model.rating)
        values.put(MovieTable.PICTURE, model.picture)

        return values
    }

    override fun fromCursor(cursor: Cursor): MovieCached {
        val id = cursor.getLong(cursor.getColumnIndexOrThrow(MovieTable.MOVIE_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(MovieTable.TITLE))
        val rating = cursor.getDouble(cursor.getColumnIndexOrThrow(MovieTable.RATING))
        val picture = cursor.getString(cursor.getColumnIndexOrThrow(MovieTable.PICTURE))

        return MovieCached(id, title, rating, picture)
    }
}