package com.rodrigobresan.sampleboilerplateandroid

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.rodrigobresan.cache.category.dao.CategoryDao
import com.rodrigobresan.cache.category.model.CategoryCached
import com.rodrigobresan.cache.movie.dao.MovieDao
import com.rodrigobresan.cache.movie.model.MovieCached
import com.rodrigobresan.cache.movie_category.dao.MovieCategoryDao
import com.rodrigobresan.cache.movie_category.model.MovieCategoryCached
import com.rodrigobresan.cache.movie_detail.dao.MovieDetailsDao
import com.rodrigobresan.cache.movie_detail.model.MovieDetailsCached

@Database(entities = arrayOf(MovieCached::class, CategoryCached::class, MovieCategoryCached::class,
        MovieDetailsCached::class),
        version = 1,
        exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun categoryDao(): CategoryDao
    abstract fun movieCategoryDao(): MovieCategoryDao
    abstract fun movieDetailsDao() : MovieDetailsDao
}