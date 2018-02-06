package com.rodrigobresan.cache.movie_detail.impl

import android.arch.persistence.room.Room
import com.rodrigobresan.cache.AppDatabase
import com.rodrigobresan.cache.PreferencesHelper
import com.rodrigobresan.cache.movie_detail.mapper.entity.MovieDetailCacheMapper
import com.rodrigobresan.cache.test.factory.MovieDetailFactory
import com.rodrigobresan.data.movie_detail.model.MovieDetailEntity
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
class MovieDetailCacheImplTest {

    private val context = RuntimeEnvironment.application

    private var database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).allowMainThreadQueries().build()
    private var movieDetailEntityMapper = MovieDetailCacheMapper()
    private var preferencesHelper = PreferencesHelper(context)

    private lateinit var movieDetailCacheImpl: MovieDetailCacheImpl

    @Before
    fun setUp() {
        movieDetailCacheImpl = MovieDetailCacheImpl(database.movieDetailsDao(),
                movieDetailEntityMapper, preferencesHelper)
        clearPreviousDataFromDatabase()
    }

    private fun clearPreviousDataFromDatabase() {
        // TODO
    }

    @Test
    fun clearTableCompletes() {
        movieDetailCacheImpl.clearMovieDetails().test().assertComplete()
    }

    @Test
    fun saveMovieDetailsSavesData() {
        val movieDetail = MovieDetailFactory.makeMovieDetailEntity()

        insertMovieDetails(movieDetail)

        var movie = database.movieDetailsDao().getMovieDetails(movieDetail.id)
        assertEquals(movieDetail.tagline, movie.tagline)
    }

    @Test
    fun saveMovieDetailsCompletes() {
        val movie = MovieDetailFactory.makeMovieDetailEntity()
        movieDetailCacheImpl.saveMovieDetails(movie).test().assertComplete()
    }

    private fun insertMovieDetails(movieDetail: MovieDetailEntity) {
        movieDetailCacheImpl.saveMovieDetails(movieDetail).test()
    }
}