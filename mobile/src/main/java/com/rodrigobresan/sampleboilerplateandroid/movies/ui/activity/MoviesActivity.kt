package com.rodrigobresan.sampleboilerplateandroid.movies.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import com.rodrigobresan.domain.movie_category.model.Category
import com.rodrigobresan.presentation.movies.contract.MoviesContract
import com.rodrigobresan.presentation.movies.model.MovieView
import com.rodrigobresan.sampleboilerplateandroid.R
import com.rodrigobresan.sampleboilerplateandroid.movie_detail.ui.activity.MovieDetailActivity
import com.rodrigobresan.sampleboilerplateandroid.movies.mapper.MovieMapper
import com.rodrigobresan.sampleboilerplateandroid.movies.ui.adapter.MoviesAdapter
import com.rodrigobresan.sampleboilerplateandroid.movies.ui.view.MovieSectionView
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_movies.*
import javax.inject.Inject

class MoviesActivity : AppCompatActivity(), MoviesContract.View, MoviesAdapter.MovieClickListener {

    @Inject lateinit var moviePresenter: MoviesContract.Presenter
    @Inject lateinit var popularMoviesAdapter: MoviesAdapter
    @Inject lateinit var topRatedMoviesAdapter: MoviesAdapter
    @Inject lateinit var upcomingMoviesAdapter: MoviesAdapter
    @Inject lateinit var nowPlayingMoviesAdapter: MoviesAdapter
    @Inject lateinit var favoriteMoviesAdapter: MoviesAdapter
    @Inject lateinit var seenMoviesAdapter: MoviesAdapter

    @Inject lateinit var movieMapper: MovieMapper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_movies)
        AndroidInjection.inject(this)
        setSupportActionBar(toolbar_movie)
    }

    override fun onMovieSelected(id: Long, imageView: ImageView) {
        val intentMovieDetail = MovieDetailActivity.makeIntent(this, id)
        startActivity(intentMovieDetail)
    }

    override fun onStart() {
        super.onStart()
        moviePresenter.loadMovies()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.movies, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_top_rated_refresh -> {
                moviePresenter.loadMovies()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setPresenter(presenter: MoviesContract.Presenter) {
        this.moviePresenter = presenter
    }

    override fun hideProgress(category: Category) {
        getMovieSectionView(category).hideProgress()
    }

    override fun showProgress(category: Category) {
        getMovieSectionView(category).showProgress()
    }

    override fun showErrorState(category: Category) {
        getMovieSectionView(category).showErrorState()
    }

    override fun hideErrorState(category: Category) {
        getMovieSectionView(category).hideErrorState()
    }

    override fun showEmptyState(category: Category) {
        getMovieSectionView(category).showEmptyState()
    }

    override fun hideEmptyState(category: Category) {
        getMovieSectionView(category).hideEmptyState()
    }

    override fun showMovies(category: Category, movies: List<MovieView>) {
        getMovieSectionView(category).showMovies(getMovieAdapter(category),
                movies.map { movieMapper.mapToViewModel(it) })
    }

    override fun hideMovies(category: Category) {
        getMovieSectionView(category).hideMovies()
    }

    fun getMovieSectionView(category: Category): MovieSectionView {
        when (category) {
            Category.TOP_RATED -> return movie_section_top_rated
            Category.NOW_PLAYING -> return movie_section_now_playing
            Category.UPCOMING -> return movie_section_upcoming
            Category.POPULAR -> return movie_section_popular
            Category.FAVORITE -> return movie_section_now_favorites
            Category.SEEN -> return movie_section_seen
        }
    }

    fun getMovieAdapter(category: Category): MoviesAdapter {
        when (category) {
            Category.TOP_RATED -> return topRatedMoviesAdapter
            Category.NOW_PLAYING -> return nowPlayingMoviesAdapter
            Category.UPCOMING -> return upcomingMoviesAdapter
            Category.POPULAR -> return popularMoviesAdapter
            Category.FAVORITE -> return favoriteMoviesAdapter
            Category.SEEN -> return seenMoviesAdapter
        }
    }

}