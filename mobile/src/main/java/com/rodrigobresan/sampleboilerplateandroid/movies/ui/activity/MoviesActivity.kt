package com.rodrigobresan.sampleboilerplateandroid.movies.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.rodrigobresan.presentation.movies.contract.MoviesContract
import com.rodrigobresan.presentation.movies.model.MovieView
import com.rodrigobresan.presentation.movies.presenter.MoviesPresenter
import com.rodrigobresan.sampleboilerplateandroid.R
import com.rodrigobresan.sampleboilerplateandroid.movies.mapper.MovieMapper
import com.rodrigobresan.sampleboilerplateandroid.movies.ui.adapter.MoviesAdapter
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_movies.*
import javax.inject.Inject

class MoviesActivity : AppCompatActivity(), MoviesContract.View {

    @Inject lateinit var moviePresenter: MoviesContract.Presenter
    @Inject lateinit var movieAdapter: MoviesAdapter
    @Inject lateinit var movieMapper: MovieMapper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_movies)
        AndroidInjection.inject(this)
        setUpRecyclerView()
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

    private fun setUpRecyclerView() {
        rv_movies.layoutManager = GridLayoutManager(this, 2)
        rv_movies.adapter = movieAdapter
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

    override fun showMovies(movies: List<MovieView>) {
        movieAdapter.listItems = movies.map {
            movieMapper.mapToViewModel(it)
        }

        movieAdapter.notifyDataSetChanged()
        rv_movies.visibility = View.VISIBLE
    }

    override fun hideMovies() {
        rv_movies.visibility = View.GONE
    }

}