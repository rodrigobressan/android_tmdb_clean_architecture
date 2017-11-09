package com.rodrigobresan.cache.movie_detail.mapper.db

import android.content.ContentValues
import android.database.Cursor
import com.rodrigobresan.cache.base.mapper.db.ModelCacheMapper
import com.rodrigobresan.cache.movie_detail.MovieDetailQueries.MovieDetailTable
import com.rodrigobresan.cache.movie_detail.model.MovieDetailCached
import javax.inject.Inject

class MovieDetailDbMapper @Inject constructor() : ModelCacheMapper<MovieDetailCached> {
    override fun toContentValues(model: MovieDetailCached): ContentValues {
        var contentValues = ContentValues()
        contentValues.put(MovieDetailTable.MOVIE_ID, model.id)
        contentValues.put(MovieDetailTable.TITLE, model.title)
        contentValues.put(MovieDetailTable.PICTURE, model.posterPath)
        contentValues.put(MovieDetailTable.BACKDROP_PICTURE, model.backdropPath)
        contentValues.put(MovieDetailTable.RATING, model.voteAverage)
        contentValues.put(MovieDetailTable.OVERVIEW, model.overview)
        contentValues.put(MovieDetailTable.TAGLINE, model.tagline)

        return contentValues
    }

    override fun fromCursor(cursor: Cursor): MovieDetailCached {
        val id = cursor.getLong(cursor.getColumnIndexOrThrow(MovieDetailTable.MOVIE_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(MovieDetailTable.TITLE))
        val rating = cursor.getDouble(cursor.getColumnIndexOrThrow(MovieDetailTable.RATING))
        val picture = cursor.getString(cursor.getColumnIndexOrThrow(MovieDetailTable.PICTURE))
        val backdrop = cursor.getString(cursor.getColumnIndexOrThrow(MovieDetailTable.BACKDROP_PICTURE))
        val overview = cursor.getString(cursor.getColumnIndexOrThrow(MovieDetailTable.OVERVIEW))
        val tagline = cursor.getString(cursor.getColumnIndexOrThrow(MovieDetailTable.TAGLINE))

        return MovieDetailCached(id, title, rating, picture, backdrop, overview, tagline)
    }

}