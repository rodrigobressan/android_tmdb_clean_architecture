package com.rodrigobresan.cache.movie.impl

import android.arch.persistence.room.Room
import com.rodrigobresan.cache.AppDatabase
import com.rodrigobresan.cache.PreferencesHelper
import com.rodrigobresan.cache.movie_category.impl.MovieCategoryCacheImpl
import com.rodrigobresan.cache.movie_category.mapper.entity.MovieCategoryCacheMapper
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

/**
 * Class for testing MovieCategoryCacheImpl class
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = intArrayOf(21))
class MovieCategoryCacheImplTest {


    private val context = RuntimeEnvironment.application

    private var database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).allowMainThreadQueries().build()
    private var preferencesHelper = PreferencesHelper(context)

    private var movieCategoryCacheMapper: MovieCategoryCacheMapper = MovieCategoryCacheMapper()
    private var movieCategoryCacheImpl: MovieCategoryCacheImpl =
            MovieCategoryCacheImpl(database.movieCategoryDao(), movieCategoryCacheMapper)

    @Before
    fun setUp() {
        clearPreviousDataFromDatabase()
    }

    private fun clearPreviousDataFromDatabase() {
        // TODO
    }

    @Test
    fun clearTableCompletes() {
        movieCategoryCacheImpl.clearMovieCategories().test().assertComplete()
    }
//
//    @Test
//    fun saveMovieCategorySavesData() {
//        val movie = MovieFactory.makeMovieCached()
//        val category = CategoryFactory.makeCategoryCached()
//
//        insertRequiredForeignFields(movie, category)
//        Thread.sleep(1000)
//        val movieCategoryEntity = MovieCategoryEntity(movie.id, category.name)
//
//        movieCategoryCacheImpl.saveMovieCategory(movieCategoryEntity).test()
//        Thread.sleep(1000)
//
//        checkNumRows()
//    }
//
//    private fun checkNumRows() {
//        val size = database.movieCategoryDao().getMovieCategories().size
//        assertEquals(1, size)
//    }
//
//    private fun insertRequiredForeignFields(movie: MovieCached, category: CategoryCached) {
//        database.movieDao().insert(movie)
//        database.categoryDao().insert(category)
//    }
//
//    @Test
//    fun saveMovieCategoryCompletes() {
//
//        val movie = MovieFactory.makeMovieCached()
//        val category = CategoryFactory.makeCategoryCached()
//
//        insertRequiredForeignFields(movie, category)
//        Thread.sleep(1000)
//        movieCategoryCacheImpl.saveMovieCategory(MovieCategoryFactory.makeMovieCategoryEntity()).test()
//                .assertComplete()
//    }
}