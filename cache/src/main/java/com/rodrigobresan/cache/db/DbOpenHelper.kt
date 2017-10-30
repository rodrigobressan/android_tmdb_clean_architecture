package com.rodrigobresan.cache.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import com.rodrigobresan.cache.category.CategoryQueries
import com.rodrigobresan.cache.movie.MovieQueries
import com.rodrigobresan.cache.movie_category.MovieCategoryQueries
import javax.inject.Inject

class DbOpenHelper @Inject constructor(context: Context)
    : SQLiteOpenHelper(context, DbConstants.DbConfig.FILE_NAME, null, DbConstants.DbConfig.VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.beginTransaction()
        try {
            db.execSQL(MovieQueries.MovieTable.CREATE)
            db.execSQL(CategoryQueries.CategoryTable.CREATE)
            db.execSQL(MovieCategoryQueries.MovieCategoryTable.CREATE)
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }

    override fun onConfigure(db: SQLiteDatabase) {
        super.onConfigure(db)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            db.setForeignKeyConstraintsEnabled(true)
        } else {
            db.execSQL(DbConstants.ENABLE_FOREIGN_KEYS)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // no migrations yet
    }
}