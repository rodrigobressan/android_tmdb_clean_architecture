package com.rodrigobresan.sampleboilerplateandroid.movies.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
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

    private fun setUpRecyclerView() {
        rv_movies.layoutManager = LinearLayoutManager(this)
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
        rv_movies.visibility = View.VISIBLE
        val moviesAdapter = rv_movies.adapter as MoviesAdapter
        moviesAdapter.listItems = movies.map {
            movieMapper.mapToViewModel(it)
        }
        moviesAdapter.notifyDataSetChanged()
    }

    override fun hideMovies() {
        rv_movies.visibility = View.GONE
    }

}