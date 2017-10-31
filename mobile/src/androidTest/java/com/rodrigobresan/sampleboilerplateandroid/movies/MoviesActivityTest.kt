package com.rodrigobresan.sampleboilerplateandroid.movies

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.whenever
import com.rodrigobresan.domain.movies.model.Movie
import com.rodrigobresan.sampleboilerplateandroid.movies.ui.activity.MoviesActivity
import com.rodrigobresan.sampleboilerplateandroid.test.TestApplication
import com.rodrigobresan.sampleboilerplateandroid.test.factory.movie.MovieFactory
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MoviesActivityTest {

    @Rule
    @JvmField
    val activity = ActivityTestRule<MoviesActivity>(MoviesActivity::class.java, false, false)

    @Test
    fun activityLaunches() {
        stubMovieRepositoryGetMovies(Single.just(MovieFactory.makeMovieList(2)))
        activity.launchActivity(null)
    }

    private fun stubMovieRepositoryGetMovies(singleMovies: Single<List<Movie>>) {
        whenever(TestApplication.appComponent().movieRepository().getMovies(any()))
                .thenReturn(singleMovies)
    }
}