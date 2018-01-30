package com.rodrigobresan.cache.movie.impl

import android.arch.persistence.room.Room
import com.nhaarman.mockito_kotlin.mock
import com.rodrigobresan.cache.AppDatabase
import com.rodrigobresan.cache.PreferencesHelper
import com.rodrigobresan.cache.category.model.CategoryCached
import com.rodrigobresan.cache.mapper.MovieCacheMapper
import com.rodrigobresan.cache.movie.model.MovieCached
import com.rodrigobresan.cache.movie_category.model.MovieCategoryCached
import com.rodrigobresan.cache.test.factory.MovieFactory
import com.rodrigobresan.data.category.sources.CategoryCache
import com.rodrigobresan.data.movie.model.MovieEntity
import com.rodrigobresan.data.movie_category.sources.MovieCategoryCache
import com.rodrigobresan.domain.movie_category.model.Category
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import kotlin.test.assertEquals

/**
 * Class for testing MovieCacheImpl class
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = intArrayOf(21))
class MovieCacheImplTest {

    private val context = RuntimeEnvironment.application

    private var database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries().build()

    private var movieDao = database.movieDao()
    private var categoryDao = database.categoryDao()
    private var movieCategoryDao = database.movieCategoryDao()

    private var movieEntityMapper = MovieCacheMapper()
    private var preferencesHelper = PreferencesHelper(context)

    private lateinit var movieCategoryCache: MovieCategoryCache
    private lateinit var categoryCache: CategoryCache

    private lateinit var movieCacheImpl: MovieCacheImpl

    @Before
    fun setUp() {
        movieCategoryCache = mock()
        categoryCache = mock()

        movieCacheImpl = MovieCacheImpl(database.movieDao(), movieEntityMapper,
                preferencesHelper)

        clearPreviousDataFromDatabase()
    }

    private fun clearPreviousDataFromDatabase() {
        // TODO
    }

    @Test
    fun clearTableCompletes() {
        movieCacheImpl.clearMovies().test().assertComplete()
    }

    @Test
    fun saveMoviesSavesDataProperly() {
        val movie = MovieFactory.makeMovieEntity()
        val movieCategory = Category.POPULAR

        insertData(movieCategory, movieEntityMapper.mapToCached(movie))

        Thread.sleep(1000)

        checkDataRows(movie)
    }

    private fun checkDataRows(movie: MovieEntity) {
        val movieCached = movieDao.getMovieById(movie.id)

        assertEquals(movieCached.id, movie.id)
        assertEquals(movieCached.title, movie.title)
        assertEquals(movieCached.picture, movie.posterPath)
        assertEquals(movieCached.rating, movie.rating)

        movieCacheImpl.getMovie(movie.id).test().assertValue(movieEntityMapper.mapFromCached(movieCached))
    }

    @Test
    fun saveMoviesSavesData() {
        val movie = MovieFactory.makeMovieEntity()
        val movieCategory = Category.POPULAR

        insertData(movieCategory, movieEntityMapper.mapToCached(movie))

        Thread.sleep(1000)

        checkNumRows()
    }

    private fun insertData(category: Category, movie: MovieCached) {
        categoryDao.insert(CategoryCached(category.name))
        movieDao.insert(movie)

        movieCategoryDao.insert(MovieCategoryCached(database.movieDao().getAllMovies()[0].id,
                database.categoryDao().getAll()[0].id))
    }

    private fun checkNumRows() {
        var rowsCategory = categoryDao.getAll().size
        var rowsMovie = movieDao.getAllMovies().size
        var rowsMovieCategory = movieCategoryDao.getMovieCategories().size
        var rowsMovieInCategory = movieDao.getAllMovies(categoryDao.getAll()[0].id).size

        assertEquals(1, rowsCategory)
        assertEquals(1, rowsMovie)
        assertEquals(1, rowsMovieCategory)
        assertEquals(1, rowsMovieInCategory)
    }

    @Test
    fun saveMoviesCompletes() {
        val movieCategory = Category.POPULAR
        val movies = MovieFactory.makeMovieEntityList(2)
        movieCacheImpl.saveMovies(movieCategory, movies).test().assertComplete()
    }

    private fun insertMovies(movieCategory: Category, moviesCached: List<MovieCached>) {
        movieCacheImpl.saveMovies(movieCategory, moviesCached.map { movieEntityMapper.mapFromCached(it) }).test()
    }
}