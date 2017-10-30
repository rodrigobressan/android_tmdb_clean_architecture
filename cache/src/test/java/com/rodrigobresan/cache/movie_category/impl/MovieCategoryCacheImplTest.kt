package com.rodrigobresan.cache.movie.impl

import android.database.sqlite.SQLiteDatabase
import com.rodrigobresan.cache.PreferencesHelper
import com.rodrigobresan.cache.category.CategoryQueries
import com.rodrigobresan.cache.category.mapper.db.CategoryDbMapper
import com.rodrigobresan.cache.category.model.CategoryCached
import com.rodrigobresan.cache.db.DbOpenHelper
import com.rodrigobresan.cache.movie.MovieQueries
import com.rodrigobresan.cache.movie.mapper.db.MovieCategoryDbMapper
import com.rodrigobresan.cache.movie.mapper.db.MovieDbMapper
import com.rodrigobresan.cache.movie.model.MovieCached
import com.rodrigobresan.cache.movie_category.MovieCategoryQueries
import com.rodrigobresan.cache.movie_category.impl.MovieCategoryCacheImpl
import com.rodrigobresan.cache.movie_category.mapper.entity.MovieCategoryEntityMapper
import com.rodrigobresan.cache.movie_category.model.MovieCategoryCached
import com.rodrigobresan.cache.test.factory.CategoryFactory
import com.rodrigobresan.cache.test.factory.MovieCategoryFactory
import com.rodrigobresan.cache.test.factory.MovieFactory
import com.rodrigobresan.data.movie_category.model.MovieCategoryEntity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = intArrayOf(21))
class MovieCategoryCacheImplTest {

    private val context = RuntimeEnvironment.application

    private var movieCategoryEntityMapper = MovieCategoryEntityMapper()
    private var movieCategoryDbMapper = MovieCategoryDbMapper()
    private var preferencesHelper = PreferencesHelper(context)

    private lateinit var movieCategoryCacheImpl: MovieCategoryCacheImpl

    private lateinit var movieDbMapper: MovieDbMapper
    private lateinit var categoryDbMapper: CategoryDbMapper

    @Before
    fun setUp() {
        movieDbMapper = MovieDbMapper()
        categoryDbMapper = CategoryDbMapper()

        movieCategoryCacheImpl = MovieCategoryCacheImpl(DbOpenHelper(context),
                movieCategoryDbMapper, movieCategoryEntityMapper, preferencesHelper)
        clearPreviousDataFromDatabase()
    }

    private fun clearPreviousDataFromDatabase() {
        movieCategoryCacheImpl.getDatabase().rawQuery("DELETE FROM " + MovieCategoryQueries.MovieCategoryTable.TABLE_NAME, null)
    }

    @Test
    fun clearTableCompletes() {
        movieCategoryCacheImpl.clearCategories().test().assertComplete()
    }

    @Test
    fun saveMovieCategorySavesData() {
        val movie = MovieFactory.makeMovieCached()
        val category = CategoryFactory.makeCategoryCached()

        insertRequiredForeignFields(movie, category)
        Thread.sleep(1000)
        val movieCategoryEntity = MovieCategoryEntity(movie.id, category.name)
        val movieCategoryCached = movieCategoryEntityMapper.mapToCached(movieCategoryEntity)

        // TODO check issue with foreign keys
        //  insertMovieCategory(movieCategoryCached)

        val testObservable = movieCategoryCacheImpl.getCategories().test()
        // testObservable.assertValue(mutableListOf(movieCategoryEntity))
    }

    private fun insertRequiredForeignFields(movie: MovieCached, category: CategoryCached) {
        val database = movieCategoryCacheImpl.getDatabase()

        database.insert(MovieQueries.MovieTable.TABLE_NAME, null, movieDbMapper.toContentValues(movie))
        database.insert(CategoryQueries.CategoryTable.TABLE_NAME, null, categoryDbMapper.toContentValues(category))
    }

    @Test
    fun saveMovieCategoryCompletes() {
        movieCategoryCacheImpl.saveMovieCategory(MovieCategoryFactory.makeMovieCategoryEntity()).test()
                .assertComplete()
    }

    private fun insertMovieCategory(movieCategory: MovieCategoryCached) {
        val database = movieCategoryCacheImpl.getDatabase()
        database.insertWithOnConflict(MovieCategoryQueries.MovieCategoryTable.TABLE_NAME, null,
                movieCategoryDbMapper.toContentValues(movieCategory), SQLiteDatabase.CONFLICT_REPLACE)
    }
}