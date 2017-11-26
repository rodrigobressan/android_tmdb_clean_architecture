package com.rodrigobresan.tv

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.rodrigobresan.cache.category.dao.CategoryDao
import com.rodrigobresan.cache.category.model.CategoryCached
import com.rodrigobresan.cache.movie.dao.MovieDao
import com.rodrigobresan.cache.movie.model.MovieCached
import com.rodrigobresan.cache.movie_category.dao.MovieCategoryDao
import com.rodrigobresan.cache.movie_category.model.MovieCategoryCached

@Database(entities = arrayOf(MovieCached::class, CategoryCached::class, MovieCategoryCached::class),
        version = 1,
        exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun categoryDao(): CategoryDao
    abstract fun movieCategoryDao(): MovieCategoryDao
}