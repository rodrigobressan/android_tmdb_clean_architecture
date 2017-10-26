package com.rodrigobresan.cache.db.mapper.movie

import android.content.ContentValues
import android.database.Cursor
import com.rodrigobresan.cache.db.constants.DbConstants
import com.rodrigobresan.cache.db.mapper.ModelCacheMapper
import com.rodrigobresan.cache.model.MovieCached
import javax.inject.Inject

class MovieDbMapper @Inject constructor() : ModelCacheMapper<MovieCached> {

    override fun toContentValues(model: MovieCached): ContentValues {
        val values = ContentValues()
        values.put(DbConstants.MovieTable.MOVIE_ID, model.id)
        values.put(DbConstants.MovieTable.TITLE, model.title)
        values.put(DbConstants.MovieTable.RATING, model.rating)
        values.put(DbConstants.MovieTable.PICTURE, model.picture)

        return values
    }

    override fun fromCursor(cursor: Cursor): MovieCached {
        val id = cursor.getLong(cursor.getColumnIndexOrThrow(DbConstants.MovieTable.MOVIE_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(DbConstants.MovieTable.TITLE))
        val rating = cursor.getDouble(cursor.getColumnIndexOrThrow(DbConstants.MovieTable.RATING))
        val picture = cursor.getString(cursor.getColumnIndexOrThrow(DbConstants.MovieTable.PICTURE))

        return MovieCached(id, title, rating, picture)
    }
}