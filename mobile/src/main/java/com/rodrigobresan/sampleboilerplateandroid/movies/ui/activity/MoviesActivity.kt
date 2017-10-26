package com.rodrigobresan.sampleboilerplateandroid.movies.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.rodrigobresan.presentation.movies.contract.MoviesContract
import com.rodrigobresan.presentation.movies.model.MovieView
import com.rodrigobresan.sampleboilerplateandroid.R
import com.rodrigobresan.sampleboilerplateandroid.movies.mapper.MovieMapper
import com.rodrigobresan.sampleboilerplateandroid.movies.ui.adapter.MoviesAdapter
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_movies.*
import javax.inject.Inject

class MoviesActivity : AppCompatActivity(), MoviesContract.View {

    @Inject lateinit var moviePresenter: MoviesContract.Presenter

    @Inject lateinit var popularMoviesAdapter: MoviesAdapter
    @Inject lateinit var topRatedMoviesAdapter: MoviesAdapter
    @Inject lateinit var upcomingMoviesAdapter: MoviesAdapter
    @Inject lateinit var nowPlayingMoviesAdapter: MoviesAdapter

    @Inject lateinit var movieMapper: MovieMapper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_movies)
        AndroidInjection.inject(this)
        setUpRecyclerViews()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.movies, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.item_top_rated_refresh -> {
                Toast.makeText(this, "Reload", Toast.LENGTH_SHORT).show()
                moviePresenter.loadMovies()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUpRecyclerViews() {
        rv_movies_popular.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false)
        rv_movies_popular.adapter = popularMoviesAdapter


        rv_movies_top_rated.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false)
        rv_movies_top_rated.adapter = topRatedMoviesAdapter

        rv_movies_now_playing.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false)
        rv_movies_now_playing.adapter = nowPlayingMoviesAdapter

        rv_movies_upcoming.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false)
        rv_movies_upcoming.adapter = upcomingMoviesAdapter
    }

    override fun onStart() {
        super.onStart()
        moviePresenter.loadMovies()
    }

    override fun onStop() {
        super.onStop()
        moviePresenter.stop()
    }

    override fun setPresenter(presenter: MoviesContract.Presenter) {
        this.moviePresenter = presenter
    }

    override fun showProgress() {
        progress_movies.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress_movies.visibility = View.GONE
    }

    override fun showErrorState() {
    }

    override fun hideErrorState() {
    }

    override fun showEmptyState() {
    }

    override fun hideEmptyState() {
    }

    override fun showUpcomingMovies(movies: List<MovieView>) {
        log("upcoming", movies)
        upcomingMoviesAdapter.listItems = movies.map {
            movieMapper.mapToViewModel(it)
        }

        upcomingMoviesAdapter.notifyDataSetChanged()
        rv_movies_upcoming.visibility = View.VISIBLE
    }

    override fun showNowPlayingMovies(movies: List<MovieView>) {
        log("now playing", movies)
        nowPlayingMoviesAdapter.listItems = movies.map {
            movieMapper.mapToViewModel(it)
        }

        nowPlayingMoviesAdapter.notifyDataSetChanged()
        rv_movies_now_playing.visibility = View.VISIBLE
    }

    override fun showPopularMovies(movies: List<MovieView>) {
        log("popular", movies)
        popularMoviesAdapter.listItems = movies.map {
            movieMapper.mapToViewModel(it)
        }

        popularMoviesAdapter.notifyDataSetChanged()
        rv_movies_popular.visibility = View.VISIBLE
    }

    override fun showTopRatedMovies(movies: List<MovieView>) {
        log("top rated", movies)
        topRatedMoviesAdapter.listItems = movies.map {
            movieMapper.mapToViewModel(it)
        }

        topRatedMoviesAdapter.notifyDataSetChanged()
        rv_movies_top_rated.visibility = View.VISIBLE
    }

    private fun log(s: String, movies: List<MovieView>) {
        Log.d("LOG", ">>>>> " + s + " size: " + movies.size)
    }

    override fun hideMovies() {
        // rv_movies_popular.visibility = View.GONE
        // rv_movies_top_rated.visibility = View.GONE
    }

}