package com.rodrigobresan.cache.review;

import android.arch.persistence.room.Room
import com.rodrigobresan.cache.AppDatabase
import com.rodrigobresan.cache.movie_detail.dao.MovieDetailsDao
import com.rodrigobresan.cache.review.dao.ReviewDao
import com.rodrigobresan.cache.test.factory.MovieDetailFactory
import com.rodrigobresan.cache.test.factory.ReviewFactory
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = intArrayOf(21))
class MovieDetailEntityReadWriteTest {

    private lateinit var movieDetailsDao: MovieDetailsDao
    private lateinit var appDatabase: AppDatabase

    @Before
    fun createDb() {
        val targetContext = RuntimeEnvironment.application
        appDatabase = Room.inMemoryDatabaseBuilder(targetContext, AppDatabase::class.java)
                .allowMainThreadQueries()
                .build()

        movieDetailsDao = appDatabase.movieDetailsDao()
    }

    @After
    fun closeDb() {
        appDatabase.close()
    }

    @Test
    fun writeMovieDetailsAndReadInList() {
        val movieDetailsList = MovieDetailFactory.makeMovieDetailCachedList(5)

        movieDetailsList.forEach {
            movieDetailsDao.insertMovie(it)
        }

        movieDetailsList.forEachIndexed { _, movieDetails ->
            val storedMovie = movieDetailsDao.getMovieDetails(movieDetails.id)
            Assert.assertEquals(movieDetails, storedMovie)
        }

    }
}