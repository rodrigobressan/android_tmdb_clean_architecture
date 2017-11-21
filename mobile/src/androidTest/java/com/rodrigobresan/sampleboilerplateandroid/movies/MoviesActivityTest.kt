package com.rodrigobresan.sampleboilerplateandroid.movies

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import com.rodrigobresan.domain.movies.model.Movie
import com.rodrigobresan.sampleboilerplateandroid.R
import com.rodrigobresan.sampleboilerplateandroid.movies.ui.activity.MoviesActivity
import com.rodrigobresan.sampleboilerplateandroid.test.TestApplication
import com.rodrigobresan.sampleboilerplateandroid.test.factory.movie.MovieFactory
import com.rodrigobresan.sampleboilerplateandroid.test.util.RecyclerViewMatcher
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

    @Test
    fun moviesDisplay() {
        val movies = MovieFactory.makeMovieList(2)
        stubMovieRepositoryGetMovies(Single.just(movies))

        activity.launchActivity(null)
        val posMovie = 0
        checkIfMovieDetailsAreBeingDisplayed(movies[posMovie], posMovie)
    }

    private fun checkIfMovieDetailsAreBeingDisplayed(movie: Movie, posMovie: Int) {
        onView(RecyclerViewMatcher.withRecyclerView(R.id.rv_movies).atPosition(posMovie))
                .check(ViewAssertions.matches(ViewMatchers.hasDescendant(ViewMatchers.withText(movie.title))))
    }

    private fun stubMovieRepositoryGetMovies(singleMovies: Single<List<Movie>>) {
        whenever(TestApplication.appComponent().movieRepository().getMovies(any()))
                .thenReturn(singleMovies)
    }
}